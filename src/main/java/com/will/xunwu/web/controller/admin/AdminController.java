package com.will.xunwu.web.controller.admin;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.will.xunwu.base.ApiDataTableResponse;
import com.will.xunwu.base.ApiResponse;
import com.will.xunwu.entity.SupportAddress;
import com.will.xunwu.service.*;
import com.will.xunwu.web.dto.*;
import com.will.xunwu.web.form.DatatableSearch;
import com.will.xunwu.web.form.HouseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private IQiNiuService qiNiuService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IHouseService houseService;

    @Autowired
    private Gson gson;

    @GetMapping("/admin/center")
    public String adminCenterPage() {
        return "admin/center";

    }

    /**
     * 新增房源功能页
     *
     * @return
     */
    @GetMapping("/admin/add/house")
    public String addHousePage() {
        return "admin/house-add";
    }

    @GetMapping("/admin/welcome")
    public String welcomePage() {
        return "admin/welcome";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }

    @PostMapping(value = "admin/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        String fileName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            Response response = qiNiuService.uploadFile(inputStream);
            if (response.isOK()) {
                QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
                return ApiResponse.ofSuccess(ret);
            } else {
                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }
        } catch (QiniuException e) {
            Response response = e.response;
            return ApiResponse.ofMessage(response.statusCode, response.getInfo());

        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }

    }

    /***
     * 新增房源功能页
     * @param houseform
     * @param bindingResule
     * @return
     */
    @PostMapping("admin/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseform
            , BindingResult bindingResule) {
        if (bindingResule.hasErrors()) {
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), bindingResule.getAllErrors().get(0).getDefaultMessage(), null);
        }
        if (houseform.getPhotos() == null || houseform.getCover() == null) {
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须上传图片");
        }
        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseform.getCityEnName(), houseform.getRegionEnName());
        if (addressMap.keySet().size() != 2){
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        ServiceResult<HouseDTO> result = houseService.save(houseform);
        if(result.isSuccess()){
            return ApiResponse.ofSuccess(result.getResult());
        }
        return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAM);
    }

    /***
     * 房源列表页
     */
    @GetMapping("admin/house/list")
    public String houseListPage(){
        return "admin/house-list";
    }

    /***
     *
     * @return
     */
    @RequestMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse houses(@ModelAttribute DatatableSearch searchBody){
        ServiceMultiResult<HouseDTO> result = houseService.adminQuery(searchBody);
        ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);
        response.setData(result.getResult());
        response.setRecordsFiltered(result.getTotal());
        response.setRecordsTotal(result.getTotal());
        response.setDraw(searchBody.getDraw());
        return response;
    }

    /***
     * 房源信息编辑页
     * @return
     */
    @GetMapping("admin/houes/edit")
    public String houseEditPage(@RequestParam(value = "id") Long id, Model model){
        if(id == null || id <1 ){
            return "404";
        }
        ServiceResult<HouseDTO> serviceResult = houseService.findCompleteOne(id);
        if(!serviceResult.isSuccess()){
            return "404";
        }
        HouseDTO result = serviceResult.getResult();
        model.addAttribute("house",result);

        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(result.getCityEnName(), result.getRegionEnName());
        model.addAttribute("city", addressMap.get(SupportAddress.Level.CITY));
        model.addAttribute("region", addressMap.get(SupportAddress.Level.REGION));

        HouseDetailDTO detailDTO = result.getHouseDetail();
        ServiceResult<SubwayDTO> subwayServiceResult = addressService.findSubway(detailDTO.getSubwayLineId());
        if (subwayServiceResult.isSuccess()) {
            model.addAttribute("subway", subwayServiceResult.getResult());
        }

        ServiceResult<SubwayStationDTO> subwayStationServiceResult = addressService.findSubwayStation(detailDTO.getSubwayStationId());
        if (subwayStationServiceResult.isSuccess()) {
            model.addAttribute("station", subwayStationServiceResult.getResult());
        }

        return "admin/house-edit";

    }



}
