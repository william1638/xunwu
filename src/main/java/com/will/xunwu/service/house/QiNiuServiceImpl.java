package com.will.xunwu.service.house;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.will.xunwu.service.IQiNiuService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * @author William
 * @date 2018/3/18
 */
@Service
public class QiNiuServiceImpl implements IQiNiuService, InitializingBean {
    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;

    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file,null,getUploadToken());
        int retry = 0 ;
        while(response.needRetry() && retry < 3){
            response = this.uploadManager.put(file,null,getUploadToken());
            retry ++ ;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
        int retry = 0 ;
        while(response.needRetry() && retry<3){
            response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
            retry ++ ;
        }
        return response;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        Response respon = bucketManager.delete(bucket, key);
        int retry = 0 ;
        while(respon.needRetry() && retry++ < 3){
            respon = bucketManager.delete(bucket,key);
        }
        return respon;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"," +
                "\"width\":$(imageInfo.width),\"heigth\":\"$(imageInfo.height)\"}");
    }

    /**
     * 获取七牛上传凭证
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}
