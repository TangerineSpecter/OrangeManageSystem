package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfo {

    private String title = "首页";

    private String icon = "fa fa-home";

    private String href = "home";
}
