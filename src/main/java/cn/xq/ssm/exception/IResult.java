package cn.xq.ssm.exception;

/**
 * @author qiong.xie
 * @description 该接口定义了异常信息返回的通用字段
 * @date 2021/5/14
 */
public interface IResult {
    /**
     * 异常的编码
     *
     * @return
     */
    int getCode();

    /**
     * 异常的信息
     *
     * @return
     */
    String getMsg();
}
