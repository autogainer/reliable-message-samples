# RMQ对接示例

## 介绍
RMQ对接示例，请先根据[RMQ项目依赖](https://www.showdoc.cc/rmq?page_id=1812484091467621 "RMQ项目依赖")，准备好RMQ运行环境。

# [中文文档](https://www.showdoc.cc/rmq?page_id=1820953552972418 "中文文档")

## 示例源码仓库地址

|  源码仓库地址 |
| ------------ |
| https://gitee.com/NuLiing/reliable-message-samples  |
| https://github.com/a327919006/reliable-message-samples |

## 示例代码流程说明
示例代码模拟简单的账户余额充值业务，上层系统为支付订单系统，负责处理支付订单，下层系统为业务系统，负责处理操作账户余额。
```
模式（不用）：可靠消息服务用于传递数据为上层系统的状态，确保下层系统与上层系统状态一致
1. 调用上层系统充值接口，生成充值订单，渠道支付单
2. 调用下层系统账务接口，生成记账单
3. --预发送，记账成功状态发送，可靠消息服务持久化后返回应答
4. --上层系统业务处理，调用支付回调返回结果，模拟支付成功或者失败
5. --确认发送，直接在“上层系统业务处理”中调用异步确认发送接口，可靠消息服务将成功或者失败的消息转发
6. --下层系统业务处理，更新账务记账状态
7. --确认消费，直接在“下层系统业务处理”中返回可靠消息服务确认消费，可靠消息服务删除消息
```

```
模式（采纳）：可靠消息服务用于传递数据为上层系统的真实报文
1. 调用上层系统充值接口，生成充值订单入库，渠道支付单入库，生成记账报文
3. --预发送，预发送记账报文，可靠消息服务持久化后返回应答
4. --上层系统业务处理，调用支付回调返回结果，模拟支付成功或者失败
5. --确认发送，直接在“上层系统业务处理”中调用异步确认发送接口，可靠消息服务将成功或者失败的消息转发
6. --下层系统业务处理，更新账务记账状态
7. --确认消费，直接在“下层系统业务处理”中返回可靠消息服务确认消费，可靠消息服务删除消息
```

## 初始化示例代码数据库
下载项目源码并解压，执行 "数据库初始化SQL脚本"，正常情况下会自动创建数据库（**reliable-message-sample**）以及生成**3**张表。"数据库初始化SQL脚本" 路径为:
```
/reliable-message-samples/sql/rmq-sample-init.sql
```

## 运行示例代码
#### 运行RMQ
先根据RMQ中文文档《[快速入门](https://www.showdoc.cc/rmq?page_id=1815635527586509 "快速入门")》，运行RMQ系统。

------------

### 配置、运行示例代码
#### 配置
配置文件路径：
```
/reliable-message-samples/spring-boot-sample/src/main/resources/application.yaml
```
配置文件说明：
```yaml
# 运行端口
server:
  port: 10010

mybatis:
  typeAliasesPackage: com.cn.rmq.sample.model.po
  mapperLocations: classpath:com/cn/rmq/sample/mapper/*.xml

spring:
  # 数据库连接配置
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/reliable-message-sample?useUnicode=true&characterEncoding=utf-8
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    hikari:
      connection-test-query: SELECT 1
  # ActiveMQ配置
  activemq:
    broker-url: tcp://127.0.0.1:61616
    user: admin
    password: admin

# Dubbo配置
dubbo:
  application:
    name: spring-boot-sample
    logger: slf4j
  registry:
    address: zookeeper://127.0.0.1:2181
  protocol:
    name: dubbo
    port: 20882
  scan:
    base-packages: com.cn.rmq.sample.service.impl
```

#### 运行
模块基于SpringBoot构建，可使用Maven命令(mvn package)打成jar包运行（java -jar）。
调试阶段可直接在IDE中运行ServiceApplication。文件路径为：
```
/reliable-message-samples/spring-boot-sample/src/main/java/com/cn/rmq/service/BootSampleApplication.java
```

------------

## 调用示例接口
示例代码集成了Swagger组件，方便接口调试。
```
访问地址： http://127.0.0.1:10010/swagger-ui.html
```
###调用过程
#### 生成订单
调用接口成功后将生成待支付的充值订单与支付订单，并发送预发送请求。接口响应数据data为订单ID，消息服务ID，为了方便，用于下一步模拟支付成功回调。
![生成订单及应答](https://github.com/autogainer/reliable-message-samples/blob/dev/Image/%E4%B8%8A%E6%B8%B8%E4%B8%9A%E5%8A%A1%E5%A4%84%E7%90%86%E5%AE%8C%E6%88%90.png)



#### 支付结果回调
模拟银行支付成功回调，成功调用接口后可查看数据库数据，充值订单、支付订单状态改变成已支付，账户金额已增加。
![支付成功回调及应答](https://github.com/autogainer/reliable-message-samples/blob/dev/Image/%E5%88%9B%E5%BB%BA%E5%85%85%E5%80%BC%E8%AE%A2%E5%8D%95.png)

# 基本功能接入指引
## 上层业务系统实现接口清单
```java
   /**
     * 创建预发送消息
     *
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     * @return 消息ID
     */
    String createPreMessage(String consumerQueue, String messageBody);
```
```java
    /**
     * 确认发送消息(异步实现)
     *
     * @param messageId 消息 ID
     */
    void confirmAndSendMessage(String messageId);

```
```java
    /**
     * 根据消息ID删除消息（异步实现，用于取消发送）
     * @param messageId 消息ID
     */
    void deleteMessageById(String messageId);

```
```java
    /**
     * 本接口提供给RMQ消息确认子系统，确认业务是否处理正常，是否发送消息
     * @param req 支付订单信息
     * @return 业务处理结果 data 1成功 0失败
     * 数据格式  {"code":0,"msg":"SUCCESS","data":1}
     */
    @ApiOperation("消息确认子系统-确认是否发送消息")
    @PostMapping("check")
    public Object check(@RequestBody @Valid PayOrder req) {
        BaseRsp rsp = new BaseRsp();
        int result = payOrderService.check(req);
        rsp.setData(result);
        return rsp;
    }
```
## 下层业务系统实现接口清单
```java
    /**
     * 监听MQ队列处理消息
     *
     * @param msg 消息内容
     */
    推荐使用Java消息服务 JMS模式
    @JmsListener(destination = Constants.QUEUE_PAY)
    public void handleMsg(RmqMessage msg);
```
```java
    /**
     * 根据消息ID删除消息（用于确认消费）
     * @param messageId 消息ID
     */
    void deleteMessageById(String messageId);

```