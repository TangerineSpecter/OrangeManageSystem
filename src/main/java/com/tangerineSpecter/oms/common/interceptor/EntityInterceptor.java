package com.tangerinespecter.oms.common.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus 字段填充
 *
 * @author 丢失的橘子
 * @version v0.5.0
 */
@Component
public class EntityInterceptor implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        createField(metaObject);
        updateField(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        updateField(metaObject);
    }

    /**
     * @Field 创建时间
     */
    public void createField(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * @Field 修改时间
     */
    public void updateField(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}