package com.will.xunwu.repository;

import com.will.xunwu.entity.HouseTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author William
 * @date 2018/3/24
 */
public interface HouseTagRespository extends CrudRepository<HouseTag,Long>{
    List<HouseTag> findAllByHouseId(Long id);
}
