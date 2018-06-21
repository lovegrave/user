package xin.lovegrave.user.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @author HL
 * @date 2018-6-19 13:32
 *
 * 第三方登陆信息与用户表 关联表
 */
@Data
public class UserOpenId {

    /**
     * 主键id
     */
    private Integer ouId;
    /**
     * 关联用户信息表 用户id
     */
    private Integer userId;
    /**
     * 第三方用户信息id
     */
    private String openId;
    /**
     * 是否已关联主账号 0.未关联  1.已关联
     */
    private Integer ouStatus;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
