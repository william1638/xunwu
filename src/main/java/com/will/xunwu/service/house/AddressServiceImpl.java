package com.will.xunwu.service.house;

import com.will.xunwu.entity.Subway;
import com.will.xunwu.entity.SubwayStation;
import com.will.xunwu.entity.SupportAddress;
import com.will.xunwu.repository.SubwayRepository;
import com.will.xunwu.repository.SubwayStationRepostory;
import com.will.xunwu.repository.SupportAddressRepository;
import com.will.xunwu.service.IAddressService;
import com.will.xunwu.service.ServiceMultiResult;
import com.will.xunwu.web.dto.SubwayDTO;
import com.will.xunwu.web.dto.SubwayStationDTO;
import com.will.xunwu.web.dto.SupportAddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author William
 * @date 2018/3/24
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private SupportAddressRepository supportAddressRepository;
    @Autowired
    private SubwayRepository subwayRepository ;
    @Autowired
    private SubwayStationRepostory subwayStationRepostory;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllCity() {
        List<SupportAddress> addresses = supportAddressRepository.findAllByLevel(SupportAddress.Level.CITY.getValue());
        List<SupportAddressDTO> addressDTOS = new ArrayList<>();
        for (SupportAddress supportAddress : addresses) {
            SupportAddressDTO target = modelMapper.map(supportAddress, SupportAddressDTO.class);
            addressDTOS.add(target);
        }
        return new ServiceMultiResult<>(addressDTOS.size(),addressDTOS);
    }

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName) {
        if(cityName == null){
            return new ServiceMultiResult<>(0,null);
        }
        List<SupportAddressDTO> result = new ArrayList<>();
        List<SupportAddress> regions = supportAddressRepository.findAllByLevelAndBelongTo(SupportAddress.Level.REGION.getValue(),cityName);
        for (SupportAddress region : regions) {
            result.add(modelMapper.map(region,SupportAddressDTO.class));
        }
        return new ServiceMultiResult<>(result.size(),result);
    }

    @Override
    public List<SubwayDTO> findAllSubwayByCity(String cityEnName) {
        List<SubwayDTO> result = new ArrayList<>();
        List<Subway> subways = subwayRepository.findAllByCityEnName(cityEnName);
        if(subways.isEmpty()){
            return result ;
        }
        subways.forEach(subway -> result.add(modelMapper.map(subway,SubwayDTO.class)));
        return result;
    }

    @Override
    public List<SubwayStationDTO> findAllStationBySubway(Long subwayId) {
        List<SubwayStationDTO> result = new ArrayList<>();
        List<SubwayStation> stations = subwayStationRepostory.findAllBySubwayId(subwayId);
        if(stations.isEmpty()){
            return result;
        }
        stations.forEach(station -> result.add(modelMapper.map(station,SubwayStationDTO.class)));
        return result;
    }

    @Override
    public Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {
        Map<SupportAddress.Level, SupportAddressDTO> result = new HashMap<>();

        SupportAddress city = supportAddressRepository.findByEnNameAndLevel(cityEnName, SupportAddress.Level.CITY
                .getValue());
        SupportAddress region = supportAddressRepository.findByEnNameAndBelongTo(regionEnName, city.getEnName());

        result.put(SupportAddress.Level.CITY, modelMapper.map(city, SupportAddressDTO.class));
        result.put(SupportAddress.Level.REGION, modelMapper.map(region, SupportAddressDTO.class));
        return result;
    }


}
