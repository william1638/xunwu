package com.will.xunwu.repository;

import com.will.xunwu.entity.HousePicture;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author William
 * @date 2018/3/24
 */
public interface HousePictureRepository extends CrudRepository<HousePicture,Long> {
    List<HousePicture> findAllByHouseId(Long id);
}
