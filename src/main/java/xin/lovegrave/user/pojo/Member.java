package xin.lovegrave.user.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @author HL
 * @date 2018-6-19
 *
 * 用户第三方会员信息表
 */
@Data
public class Member {

    /**
     * 会员id 主键
     */
    private Integer memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员积分
     */
    private Integer memberScore;
    /**
     * 会员等级
     */
    private Integer memberGrade;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 合并算法(积分是否能合并0.否,1.是)
     */
    private Integer memberMerge;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;


}
