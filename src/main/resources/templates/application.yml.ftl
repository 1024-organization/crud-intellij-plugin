server:
  port: 8082
  servlet:
    context-path: /api/


spring:
  datasource:
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
  profiles:
    active: local
  servlet:
    multipart:
      max-request-size: 10MB
      max-file-size: 15MB

<#if ormType==1>
mybatis:
  mapper-locations: classpath:/mapper/**/*.xml
</#if>
<#if ormType==0>
mybatis-plus:
  mapper-locations: classpath*:/mapper/*.xml
  typeAliasesPackage: ${package}.pojo.entity
  global-config:
    db-config:
      id-type: UUID
      field-strategy: not_empty
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
</#if>