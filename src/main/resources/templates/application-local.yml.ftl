spring:
  datasource:
    url: jdbc:mysql://${conn.host}:${conn.port?c}/${db}?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
    username: ${conn.username}
    password: ${conn.password}