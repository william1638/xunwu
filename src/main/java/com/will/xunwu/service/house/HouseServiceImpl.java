package com.will.xunwu.service.house;

import com.will.xunwu.base.LoginUserUtil;
import com.will.xunwu.entity.House;
import com.will.xunwu.repository.HouseRepository;
import com.will.xunwu.service.IHouseService;
import com.will.xunwu.service.ServiceResult;
import com.will.xunwu.web.dto.HouseDTO;
import com.will.xunwu.web.form.HouseForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author William
 * @date 2018/3/24
 */
@Service
public class HouseServiceImpl implements IHouseService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HouseRepository houseRepository;
    @Override
    public ServiceResult<HouseDTO> save(HouseForm houseForm) {
        House house = new House();
        modelMapper.map(houseForm,house);
        Date now = new Date();
        house.setCreateTime(now);
        house.setLastUpdateTime(now);
        house.setAdminId(LoginUserUtil.getLoginUserId());

       return null ;


    }
}
