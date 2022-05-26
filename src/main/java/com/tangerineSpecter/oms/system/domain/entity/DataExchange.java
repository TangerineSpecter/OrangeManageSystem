package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 货币数据表
 *
 * @author TangerineSpecter
 * @date 2022年05月26日20:03:02
 */
@Data
@Alias("DataExchange")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_exchange")
@EqualsAndHashCode(callSuper = true)
public class DataExchange extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 货币名称
     */
    @TableField("name")
    private String name;
    /**
     * 货币代码
     */
    @TableField("code")
    private String code;

}



