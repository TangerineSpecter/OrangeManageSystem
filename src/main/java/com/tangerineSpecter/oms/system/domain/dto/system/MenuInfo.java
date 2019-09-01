package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfo {

    private List<MenuChildInfo> menuInfos;
}
