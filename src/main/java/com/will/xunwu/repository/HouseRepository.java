package com.will.xunwu.repository;

import com.will.xunwu.entity.House;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author William
 * @date 2018/3/24
 */
public interface HouseRepository extends PagingAndSortingRepository<House,Long>,
        JpaSpecificationExecutor<House> {
}
