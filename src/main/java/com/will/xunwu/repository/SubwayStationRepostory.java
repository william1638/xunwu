package com.will.xunwu.repository;

import com.will.xunwu.entity.SubwayStation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author William
 * @date 2018/3/24
 */
public interface SubwayStationRepostory extends CrudRepository<SubwayStation,Long> {

    List<SubwayStation> findAllBySubwayId(Long subwayId);

}
