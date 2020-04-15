package com.qraffa.easyrentboot.model;

/**
 * 状态码枚举类，在此定义各种状态码与对应信息
 */
public enum StatusEnum {
    /**
     * 成功返回状态
     */
    OK(200,"OK"),

    /**
     * 表示用户名、密码等为空
     */
    USER_MESSAGE_INCOMPLETE(201,"USER_MESSAGE_INCOMPLETE"),

    /**
     * 表示管理员权限为空
     */
    ADMIN_INCOMPLETE(202,"ADMIN_INCOMPLETE"),

    /**
     * 表示密码错误
     */
    PASSWORD_INCORRECT(203,"PASSWORD_INCORRECT"),

    /**
     * 表示用户不存在
     */
    USER_NONEXISTENT(204,"USER_NONEXISTENT"),

    /**
     * 表示商品不存在
     */
    COMMODITY_NONEXISTENT(205,"COMMODITY_NONEXISTENT"),

    /**
     * 表示图片不存在
     */
    COMMODITY_PICTURE_NONEXISTENT(206,"COMMODITY_PICTURE_NONEXISTENT"),

    /**
     * 表示订单不存在
     */
    ORDER_NONEXISTENT(207,"ORDER_NONEXISTENT"),

    /**
     * 表示收藏夹不存在
     */
    FAVORITE_NONEXISTENT(208,"FAVORITE_NONEXISTENT"),

    /**
     * 表示收藏夹不存在
     */
    COMMODITY_MESSAGE_NONEXISTENT(209,"COMMODITY_MESSAGE_NONEXISTENT"),

    /**
     * 表示用户名或电话已存在
     */
    USER_EXISTENT(210,"USER_EXISTENT"),

    /**
     * 表示信息不完整
     */
    INFORMATION_INCOMPLETE(211,"INFORMATION_INCOMPLETE"),

    /**
     * 表示商品被租借
     */
    COMMODITY_RENTED(212,"COMMODITY_RENTED"),

    /**
     * 表示收藏重复
     */
    FAVORITE_EXISTENT(213,"FAVORITE_EXISTENT"),

    /**
     * 表示用户没有权限（令牌、用户名、密码错误）
     */
    UNAUTHORIZED(401,"Unauthorized"),

    /**
     * 服务器发生错误，用户将无法判断发出的请求是否成功
     */
    INTERNAL_SERVER_ERROR(500,"Internal server error");

    private int code;
    private String msg;

    StatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
