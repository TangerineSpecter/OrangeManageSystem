package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统公告信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemNoticeInfo {

    private List<SystemBulletin> noticeInfos;

}
