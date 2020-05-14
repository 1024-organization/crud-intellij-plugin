Crud Intellij Plugin
===============

一个增删改查的插件，可以根据数据库表结构，帮助您快速生成相关代码。

使用的方式有两种: 

- 从Crud Plugin 生成项目
- 已有项目右键选择生成代码



### 支持的功能

1. 通过MySQL库生成Controller,Service,DAO,POJO,Mapper的代码, 支持选择JPA,Mybatis,Mybatis-Plus作为ORM  

2. 生成项目时  
   1. 选择生成Mybatis-Plus的分页插件、性能分析插件、乐观锁插件、自动填充插件、AR模式等
   2. 选择生成docker镜像打包支持google-jib、spotify
   3. 选择支持lombok，作者名称

   



### 注意: 

1. 目前暂不支持字段类型的映射关系设置
2. 目前暂不支持表之间的对应关系处理
3. 为表和字段设置合适的注释。
4. 建议根据《阿里巴巴Java开发手册》的MySQL数据库规约来设计数据库表
5. 表需要有`id`字段作为主键



### 支持的环境

| 环境          | 版本       |
| ------------- | ---------- |
| Java          | 1.8以上    |
| Intellij Idea | 2017.3以上 |



### 插件安装



1. 下载zip包

   [https://github.com/imyzt/crud-intellij-plugin/releases](https://github.com/imyzt/crud-intellij-plugin/releases)

2. 打开idea

Preferences -> Plugins -> Install Plugins from Disk -> 选择下载的zi包，点击确定。重启即可

![image-20200514104216252](http://blog.imyzt.top/upload/2020/05/3sj9jedo0gh7toa03fgdp3p3gl.png)





### 使用教程

### 1.创建一个新的项目:

  - <kbd>New</kbd> > <kbd>Project</kbd> / <kbd>Module</kbd> > <kbd>Crud</kbd> > <kbd>数据库表选择</kbd>
  - 启动Application

![image](https://raw.githubusercontent.com/mars05/static/master/image/crud3.gif)

### 2.项目右键单击生成代码:

![image](https://raw.githubusercontent.com/mars05/static/master/image/crud4.gif)