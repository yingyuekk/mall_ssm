package cn.xq.ssm.exception;

import cn.xq.ssm.enums.CodeEnum;

/**
 * @author qiong.xie
 * @description
 * @date 2021/5/14
 */
public class BaseException extends RuntimeException {

    private int code;
    private String msg;

    public BaseException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(CodeEnum bizCodeEnum){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
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
