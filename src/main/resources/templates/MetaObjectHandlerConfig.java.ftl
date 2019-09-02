package cn.ideamake.data.acquisition.api.config;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
 * @author ${author}
 * @date ${.now}
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(CREATED_AT, metaObject);
        Object updateTime = getFieldValByName(UPDATED_AT, metaObject);
        if (createTime == null){
            setFieldValByName(CREATED_AT, LocalDateTime.now(), metaObject);
        }
        if (updateTime == null){
            setFieldValByName(UPDATED_AT, LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName(UPDATED_AT, metaObject);
        if (updateTime == null) {
            setFieldValByName(UPDATED_AT, LocalDateTime.now(), metaObject);
        }
    }
}
