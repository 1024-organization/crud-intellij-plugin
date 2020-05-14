package ${package};

<#if ormType==0>
import org.mybatis.spring.annotation.MapperScan;
</#if>
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan;
/**
* 应用启动类
* @author ${author}
*/
@SpringBootApplication
<#if ormType==0 || ormType==1>
@MapperScan(basePackages = {
    "${package}.dao"
})
</#if>
@ComponentScan(basePackages = {
    "${package}"
})
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
