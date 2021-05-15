package cn.xq.ssm.enums;

/**
 * @author qiong.xie
 * @description 状态码定义约束，共6位数，前三位代表服务，后4位代表接口
 * 比如 商品服务210,购物车是220、用户服务230，403代表权限
 * @date 2021/5/14
 */
public enum CodeEnum {

    /** 商品 */
    ITEM_ADD_DESC_EX(210001,"商品描述保存异常"),
    ITEM_ADD_PARAM_EX(210002,"商品规格参数保存异常"),
    ITEM_DEL_DESC_EX(210003,"删除商品描述异常"),
    ITEM_DEL_PARAM_EX(210004,"删除商品规格异常"),
    ITEM_EDIT_DESC_EX(210009,"修改商品描述异常"),
    ITEM_EDIT_PARAM_EX(210009,"修改商品规格异常"),

    /** 用户 */
    USER_LOGIN_USERNAME_EX(230001,"登录用户名错误，没有这个用户"),
    USER_LOGIN_PASSWORD_EX(230002,"登录密码错误"),
    USER_CHECK_EX(230003,"用户数据不完整"),
    USER_CHECK_USERNAME_EX(230004,"此用户名已经被占用"),
    USER_CHECK_PHONE_EX(230005,"此手机号已经注册过了，请登录"),
    USER_TOKEN_OUT(230006,"token过期"),

    /**
     * 通用操作码
     */
    OPS_REPEAT(110001,"重复操作");


    private String message;

    private int code;

    private CodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
