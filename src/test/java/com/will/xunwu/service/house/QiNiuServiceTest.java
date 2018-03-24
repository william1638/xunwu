package com.will.xunwu.service.house;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.will.xunwu.XunwuApplicationTests;
import com.will.xunwu.service.IQiNiuService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author William
 * @date 2018/3/18
 */
public class QiNiuServiceTest extends XunwuApplicationTests {
    @Autowired
    private IQiNiuService qiNiuService;

    @Test
    public void testUploadTest(){
        String fileName = "F:/javaproject/xunwu/tmp/X1H.jpg";
        File file = new File(fileName);
        Assert.assertTrue(file.exists());

        try {
            Response response = qiNiuService.uploadFile(file);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete()  {
        String key = "FkmV8bXXzfwBBBibV-J29ICWRps2" ;
        try {
            Response respon = qiNiuService.delete(key);
            Assert.assertTrue(respon.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
