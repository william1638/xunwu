package com.will.xunwu.service;

import com.will.xunwu.entity.SupportAddress;
import com.will.xunwu.web.dto.SubwayDTO;
import com.will.xunwu.web.dto.SubwayStationDTO;
import com.will.xunwu.web.dto.SupportAddressDTO;

import java.util.List;
import java.util.Map;

/**
 * 地址服务接口
 * @author William
 * @date 2018/3/24
 */
public interface IAddressService {

    ServiceMultiResult<SupportAddressDTO>  findAllCity();

    ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName);

    List<SubwayDTO> findAllSubwayByCity(String cityEnName);

    List<SubwayStationDTO> findAllStationBySubway(Long subwayId);

    Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);

    /**
     * 获取地铁线信息
     * @param subwayId
     * @return
     */
    ServiceResult<SubwayDTO> findSubway(Long subwayId);

    /**
     * 获取地铁站点信息
     * @param stationId
     * @return
     */
    ServiceResult<SubwayStationDTO> findSubwayStation(Long stationId);

}
