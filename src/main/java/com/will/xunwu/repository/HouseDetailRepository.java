package com.will.xunwu.repository;

import com.will.xunwu.entity.HouseDetail;
import org.springframework.data.repository.CrudRepository;

/**
 * @author William
 * @date 2018/3/24
 */
public interface HouseDetailRepository extends CrudRepository<HouseDetail,Long> {

    HouseDetail findByHouseId(Long houseId);
}
