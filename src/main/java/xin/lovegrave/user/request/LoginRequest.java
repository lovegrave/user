package xin.lovegrave.user.request;

import lombok.Data;

/**
 * @version 1.0
 * @author HL
 * @date 2018-6-19
 *
 * 用户登陆请求同意请求数据
 */
@Data
public class LoginRequest {

    /**
     * 登陆方式
     */
    private String loginWay;
    /**
     * 电话
     */
    private String tel;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
}
