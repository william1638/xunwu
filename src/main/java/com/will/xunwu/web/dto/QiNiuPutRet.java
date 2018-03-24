package com.will.xunwu.web.dto;

/**
 * @author William
 * @date 2018/3/22
 */
public final class QiNiuPutRet {

    public String key ;
    public String hash;
    public String bucket;
    public int width ;
    public int height ;

    @Override
    public String toString() {
        return "QiNiuPutRet{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }


}
