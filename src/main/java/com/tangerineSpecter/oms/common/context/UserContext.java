package com.tangerinespecter.oms.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {

    private String uid;

    private String nickName;
}
