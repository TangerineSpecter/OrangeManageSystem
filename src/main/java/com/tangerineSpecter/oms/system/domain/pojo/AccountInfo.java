package com.tangerinespecter.oms.system.domain.pojo;

import com.tangerinespecter.oms.common.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    /**
     * 帐号
     */
    @NotNull
    private String username;
    /**
     * 密码
     */
    private String password;
}
