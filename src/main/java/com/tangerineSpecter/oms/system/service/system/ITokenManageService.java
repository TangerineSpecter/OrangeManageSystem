package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemTokenQueryObject;
import com.tangerinespecter.oms.system.domain.vo.system.SystemTokenVo;
import com.tangerinespecter.oms.system.domain.entity.SystemToken;
import com.tangerinespecter.oms.system.service.BaseService;

public interface ITokenManageService extends BaseService<SystemTokenQueryObject, SystemToken> {


    /**
     * 新增令牌
     *
     * @param param 参数
     */
    void insert(SystemTokenVo param);

    /**
     * 更新令牌
     *
     * @param param 参数
     */
    void update(SystemTokenVo param);

    /**
     * 删除令牌
     *
     * @param id 主键
     */
    void delete(Long id);

}
