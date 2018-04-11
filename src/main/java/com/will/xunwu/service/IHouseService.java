package com.will.xunwu.service;

import com.will.xunwu.web.dto.HouseDTO;
import com.will.xunwu.web.form.DatatableSearch;
import com.will.xunwu.web.form.HouseForm;

/**
 * 房屋管理服务接口
 * @author William
 * @date 2018/3/24
 */
public interface IHouseService {
    ServiceResult<HouseDTO> save(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody);

    /**
     * 查询完整房源信息
     * @param id
     * @return
     */
    ServiceResult<HouseDTO> findCompleteOne(Long id);
}
