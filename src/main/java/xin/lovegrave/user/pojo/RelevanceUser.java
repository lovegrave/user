package xin.lovegrave.user.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @author HL
 * @date 2018-6-19
 *
 * 同一个用户创建多个用户信息记录时，提供用户合并以及解绑的关联表
 */
@Data
public class RelevanceUser {

    /**
     * 主键id
     */
    private Integer ruId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分用户id
     */
    private Integer relevanceUserId;

    /**
     * 是否关联
     */
    private Integer reStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
