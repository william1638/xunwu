package com.will.xunwu.repository;

import com.will.xunwu.entity.Subway;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author William
 * @date 2018/3/24
 */
public interface SubwayRepository extends CrudRepository<Subway,Long> {
    List<Subway> findAllByCityEnName(String cityEnName);
}
