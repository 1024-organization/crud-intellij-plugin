spring:
  datasource:
    url: jdbc:mysql://${conn.host}:${conn.port?c}/${db}?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
    username: ${conn.username}
    password: ${conn.password}
    hikari:
      minimum-idle: 5
      maximum-pool-size: 100
      idle-timeout: 30000
      validation-timeout: 250
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8

<#if ormType==1>
mybatis:
  mapper-locations: classpath:/mapper/**/*.xml
</#if>
<#if ormType==0>
mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
  typeAliasesPackage: ${package}
  global-config:
    db-config:
      id-type: UUID
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
</#if>