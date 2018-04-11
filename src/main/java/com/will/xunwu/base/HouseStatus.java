package com.will.xunwu.base;

/**
 * @author William
 * @date 2018/3/31
 */
public enum HouseStatus {
    NOT_AUDITED(0),//未审核
    PASSES(1),//审核通过
    RENTED(2),//已出租
    DELETED(3);//逻辑删除
    private int value ;

    HouseStatus(int status){
        this.value = value ;
    }
    public int getValue(){
        return value ;
    }

}
