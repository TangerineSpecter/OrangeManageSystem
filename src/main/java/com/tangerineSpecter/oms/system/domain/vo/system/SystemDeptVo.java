package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemDeptVo {

    private Long id;
    @NotBlank(message = "部门名字不能为空")
    @Length(max = 15, min = 2, message = "部门名字长度要在2~15个字之间")
    private String name;
    private Long parentId;
    @NotNull(message = "部门排序不能为空")
    private Integer sort;
    @Length(max = 150, message = "备注长度不能超过150个字")
    private String remark;
}
