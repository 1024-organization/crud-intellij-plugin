${r'<configuration>
    <!--为了防止进程退出时，内存中的数据丢失，请加上此选项-->
    <shutdownHook class="ch.qos.logback.core.hook.DelayingShutdownHook"/>

    <appender name="loghubAppender1" class="com.aliyun.openservices.log.logback.LoghubAppender">
        <!--必选项-->
        <!-- 账号及网络配置 -->
        <endpoint>请配置阿里云日志地址</endpoint>
        <accessKeyId>请配置阿里云日志key</accessKeyId>
        <accessKeySecret>请配置阿里云日志密码</accessKeySecret>

        <!-- sls 项目配置 -->
        <project>请配置阿里云项目日志设置</project>
        <logStore>请配置阿里云日志存储地址</logStore>
        <!--必选项 (end)-->

        <!-- 可选项 -->
        <!--<topic>topic1</topic>
        <source>source1</source>-->

        <!-- 可选项 详见 "参数说明"-->
        <totalSizeInBytes>104857600</totalSizeInBytes>
        <maxBlockMs>60000</maxBlockMs>
        <ioThreadCount>8</ioThreadCount>
        <batchSizeThresholdInBytes>524288</batchSizeThresholdInBytes>
        <batchCountThreshold>4096</batchCountThreshold>
        <lingerMs>2000</lingerMs>
        <retries>10</retries>
        <baseRetryBackoffMs>100</baseRetryBackoffMs>
        <maxRetryBackoffMs>100</maxRetryBackoffMs>

        <filter class="ch.qos.logback.classic.filter.ThresholdFilter"><!-- 打印WARN,ERROR级别的日志 -->
            <level>ERROR</level>
        </filter>

        <mdcFields>THREAD_ID,MDC_KEY</mdcFields>
    </appender>



    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] %-5level %logger{35} - %msg %n</pattern>
        </encoder>
    </appender>

    <!-- 可用来获取StatusManager中的状态 -->
    <statusListener class="ch.qos.logback.core.status.OnConsoleStatusListener"/>

    <root>
        <level value="ERROR"/>
        <appender-ref ref="loghubAppender1"/>
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
'}