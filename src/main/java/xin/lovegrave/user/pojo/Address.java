package xin.lovegrave.user.pojo;

import lombok.Data;

/**
 * @version 1.0
 * @date 2018-6-29 12:00
 * @author HL
 *
 * 用户地址表
 */
@Data
public class Address {

    private Integer addressId;
    /**
     * 省区
     */
    private String addrProvince;
    /**
     * 市区
     */
    private String addrCity;
    /**
     * 详细地址
     */
    private String AddrDetail;
    /**
     * 地址电话
     */
    private String addrTel;
    /**
     * 是否是默认地址
     */
    private Integer addrStatus;
    /**
     * 地址标记，例如：家 or 公司
     */
    private String addrRemark;
    /**
     * 地址描述
     */
    private String addrDescribe;
}
