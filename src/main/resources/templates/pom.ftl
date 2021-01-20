<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>cn.ideamake</groupId>
        <artifactId>parent-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath/> <!-- lookup parent from cloud-mapper -->
    </parent>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    <#if ormType==1>
        <mybatis.version>2.0.1</mybatis.version>
        <pagehelper.version>1.2.10</pagehelper.version>
    </#if>
    </properties>

    <dependencies>



    <#if ormType==1>
        <!--spring-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--db dependency-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.10</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!--tool-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <!--doc-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${r'${swagger.version}'}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${r'${swagger.version}'}</version>
        </dependency>
    <#elseif ormType==2>
        <!--spring-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!--tool-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <!--doc-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${r'${swagger.version}'}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${r'${swagger.version}'}</version>
        </dependency>

        <!--aliyun elk sdk-->
        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-dysmsapi</artifactId>
            <version>1.1.0</version>
        </dependency>
    </#if>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <#if dockerfileSelected>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.2.0</version>
                <configuration>
                    <imageName>${r'${artifactId}'}:${r'${project.version}'}</imageName>
                    <dockerDirectory>${r'${project.basedir}'}/src/main/docker</dockerDirectory>
                    <resources>
                        <resoulsrce>
                            <targetPath>/</targetPath>
                            <directory>${r'${project.build.directory}'}</directory>
                            <include>${r'${project.build.finalName}'}.jar</include>
                        </resoulsrce>
                    </resources>
                </configuration>
                <executions>
                    <execution>
                        <id>build-image</id>
                        <phase>package</phase>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>push-image</id>
                        <phase>deploy</phase>
                        <goals>
                            <goal>push</goal>
                        </goals>
                        <configuration>
                            <imageName>${r'${artifactId}'}:${r'${project.version}'}</imageName>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            </#if>
            <#if jibSelected>
                <plugin>
                    <groupId>com.google.cloud.tools</groupId>
                    <artifactId>jib-maven-plugin</artifactId>
                    <version>1.6.1</version>
                    <configuration>
                        <from>
                            <#if jdkType==0>
                            <image>registry.cn-shenzhen.aliyuncs.com/ideamake/ideamake-openjdk8-openj9:v1.0.0</image>
                            <#elseif jdkType==1>
                            <image>registry.cn-shenzhen.aliyuncs.com/ideamake/ideamake-openjdk8:v1.0.0</image>
                            </#if>
                        </from>
                        <to>
                            <#--配置镜像仓库地址-->
                            <image>registry.cn-shenzhen.aliyuncs.com/ideamake/${r'${artifactId}'}</image>
                            <tags>
                                <tag>${r'${project.version}'}</tag>
                            </tags>
                        </to>
                        <container>
                            <mainClass>${package}.Application</mainClass>
                            <jvmFlags>
                                <jvmFlag>-server</jvmFlag>
                                <jvmFlag>-Xms512m</jvmFlag>
                                <jvmFlag>-Xmx512m</jvmFlag>
                                <jvmFlag>-Djava.awt.headless=true</jvmFlag>
                                <jvmFlag>-Duser.timezone=PRC</jvmFlag>
                            </jvmFlags>
                            <environment>
                                <server.port>8080</server.port>
                            </environment>
                            <ports>
                                <port>80</port>
                            </ports>
                            <useCurrentTimestamp>true</useCurrentTimestamp>
                        </container>
                        <allowInsecureRegistries>true</allowInsecureRegistries>
                    </configuration>
                    <executions>
                        <execution>
                            <id>build-and-push-docker-image</id>
                            <phase>package</phase>
                            <goals>
                                <goal>build</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </#if>
        </plugins>
    </build>

</project>
