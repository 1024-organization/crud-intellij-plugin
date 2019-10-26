${r'<?xml version="1.0" encoding="UTF-8"?>
<!-- 分级别异步文件日志输出配置 -->
<configuration>
    <!-- 日志级别 -->
    <property name="logLevel" value="INFO"></property>
    <!-- 日志地址 -->
    <property name="logPath" value="/data/logs/timesgroup/"></property>
    <!-- 最大保存时间 -->
    <property name="maxHistory" value="30"/>
    <!-- 异步缓冲队列的深度,该值会影响性能.默认值为256 -->
    <property name="queueSize" value="512"></property>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] %-5level %logger{35} [%file : %L]- %msg %n</pattern>
        </encoder>
    </appender>

    <appender name="FILE_DEBUG"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <file>${logPath}/debug.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logPath}/debug.log.%d{yyyy-MM-dd}.zip
            </fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] %-5level %logger{35} [%file : %L]- %msg%n</pattern>
        </encoder>
    </appender>
    <appender name="FILE_INFO"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <file>${logPath}/info.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logPath}/info.log.%d{yyyy-MM-dd}.zip
            </fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] %-5level %logger{35} [%file : %L]- %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE_WARN"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>WARN</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <file>${logPath}/warn.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logPath}/warn.log.%d{yyyy-MM-dd}.zip
            </fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] %-5level %logger{35} [%file : %L]- %msg%n</pattern>
        </encoder>
    </appender>
    <appender name="FILE_ERROR"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <file>${logPath}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logPath}/error.log.%d{yyyy-MM-dd}.zip
            </fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] %-5level %logger{35} [%file : %L]- %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="ASYNC_LOG_DEBUG" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志 -->
        <discardingThreshold>0</discardingThreshold>
        <!-- 更改默认的队列的深度,该值会影响性能.默认值为256 -->
        <queueSize>${queueSize}</queueSize>
        <includeCallerData>true</includeCallerData>
        <appender-ref ref="FILE_DEBUG"/>
    </appender>
    <appender name="ASYNC_LOG_INFO" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志 -->
        <discardingThreshold>0</discardingThreshold>
        <!-- 更改默认的队列的深度,该值会影响性能.默认值为256 -->
        <queueSize>${queueSize}</queueSize>
        <includeCallerData>true</includeCallerData>
        <appender-ref ref="FILE_INFO"/>
    </appender>
    <appender name="ASYNC_LOG_WARN" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志 -->
        <discardingThreshold>0</discardingThreshold>
        <!-- 更改默认的队列的深度,该值会影响性能.默认值为256 -->
        <queueSize>${queueSize}</queueSize>
        <includeCallerData>true</includeCallerData>
        <appender-ref ref="FILE_WARN"/>
    </appender>
    <appender name="ASYNC_LOG_ERROR" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志 -->
        <discardingThreshold>0</discardingThreshold>
        <!-- 更改默认的队列的深度,该值会影响性能.默认值为256 -->
        <queueSize>${queueSize}</queueSize>
        <includeCallerData>true</includeCallerData>
        <appender-ref ref="FILE_ERROR"/>
    </appender>
    <root level="${logLevel}">
        <!-- appender referenced after it is defined -->
        <!--<appender-ref ref="STDOUT"/>-->
        <!--<appender-ref ref="ASYNC_LOG_DEBUG"/>-->
        <!--<appender-ref ref="ASYNC_LOG_INFO"/>-->
        <appender-ref ref="ASYNC_LOG_WARN"/>
        <appender-ref ref="ASYNC_LOG_ERROR"/>
    </root>
</configuration>
'}