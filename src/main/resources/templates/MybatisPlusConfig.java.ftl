package ${package+'.config'};

<#if paginationSelected>
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
</#if>
<#if performanceSelected>
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
</#if>
<#if optimisticLockerSelected>
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
</#if>
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author ${author}
 * @date ${.now}
 * @description MybatisPlus配置文件
 */
@Configuration
public class MybatisPlusConfig {

<#if paginationSelected>
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptorMysql() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
</#if>

<#if performanceSelected>
    /**
    * SQL执行效率插件
    */
    @Bean
    @Profile({"dev", "local"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        //interceptor.setFormat(true);
        interceptor.setMaxTime(3000);
        return interceptor;
    }
</#if>

<#if optimisticLockerSelected>
    /**
    * 乐观锁插件
    * todo 乐观锁插件已开启, 请按照官方文档配置
    * https://mybatis.plus/guide/optimistic-locker-plugin.html
    */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
</#if>
}
