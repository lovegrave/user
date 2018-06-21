package xin.lovegrave.user.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @author HL
 * @date 2018-6-19 13:09
 *
 * 用户信息
 */
@Data
public class User {

    /**
     * 用户主键id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String UserName;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String userPic;
    /**
     * 用户注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 用户最后登陆时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 用户性别
     */
    private Integer userSex;
    /**
     * 用户电话
     */
    private String userTel;
    /**
     * 用户备用联系方式
     */
    private String userPhone;
    /**
     * 用户默认地址
     */
    private String defaultAddressId;
    /**
     * 用户邮箱 作用于邮箱验证以及用户找回,更换手机号等
     */
    private String userEmail;
    /**
     * 关联父级用户userId
     */
    private Integer parentUserId;

    /**
     * 用户等级
     */
    private Integer userGrade;

    /**
     * 用户积分数
     */
    private Integer userScore;

}
