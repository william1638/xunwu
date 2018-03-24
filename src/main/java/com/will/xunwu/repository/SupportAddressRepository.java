package com.will.xunwu.repository;

import com.will.xunwu.entity.SupportAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author William
 * @date 2018/3/24
 */
public interface SupportAddressRepository extends CrudRepository<SupportAddress,Long> {

    /***
     * 获取所有对应行政级别的信息
     * @return
     * @param value
     */
    List<SupportAddress> findAllByLevel(String value);

    List<SupportAddress> findAllByLevelAndBelongTo(String level, String cityName);

    SupportAddress findByEnNameAndLevel(String enName, String level);

    SupportAddress findByEnNameAndBelongTo(String enName, String belongTo);

}
