<#if jdkType==0>
FROM registry.cn-shenzhen.aliyuncs.com/ideamake/ideamake-openjdk8-openj9:v1.0.0
<#elseif jdkType==1>
FROM registry.cn-shenzhen.aliyuncs.com/ideamake/ideamake-openjdk8:v1.0.0
</#if>

#维护者信息
LABEL maintainer "${author}@example.com"

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp

WORKDIR /app

ADD ${artifactId}-${version}.jar ${artifactId}-${version}.jar

#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8082

#指定容器启动程序及参数
ENTRYPOINT ["java","-jar","/app/${artifactId}-${version}.jar"]