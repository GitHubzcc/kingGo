/*
 *
 *
 *
 */
package com.go.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity - 会员
 */
@Entity
@Table(name = "zz_member")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "zz_member_sequence")
public class Member {

    private static final long serialVersionUID = 1533130686714725835L;

    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 性别
     */
    public enum Gender {

        /**
         * 男
         */
        male,

        /**
         * 女
         */
        female
    }

    /**
     * 身份
     */
    public enum Type {

        /**
         * 用户
         */
        member,

        /**
         * 业务员
         */
        salesman,

        /**
         * 主播
         */
        anchor;

        private static Map<String, Type> map = new HashMap<String,Type>();

        static {
            map.put("普通用户", member);
            map.put("业务员", salesman);
            map.put("主播", anchor);
        }

        public static Type forValue(String value) {
            return map.get(value);
        }

        @JsonValue
        public String toValue() {
            for (Map.Entry<String, Type> entry : map.entrySet()) {
                if (entry.getValue() == this) {
                    return entry.getKey();
                }
            }

            return "";
        }

        public static Type getDefault() {
            return Type.member;
        }
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    /**
     * "用户名"Cookie名称
     */
    public static final String USERNAME_COOKIE_NAME = "username";

    /**
     * 最大收藏商品数
     */
    public static final Integer MAX_FAVORITE_COUNT = 10;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录令牌
     */
    private String token;

    /**
     * 登录令牌过期时间
     */
    private Date tokenExpire;

    /**
     * 密码
     */
    private String password;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 积分(米币)
     */
    private BigDecimal point;

    /**
     * 消费金额
     */
    private BigDecimal amount;

    /**
     * 余额（高米币）
     */
    private BigDecimal balance;

    /**
     * e币
     */
    private BigDecimal ecoin;

    /**
     * 公益基金
     */
    private BigDecimal publicWelfare = BigDecimal.ZERO;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 连续登录失败次数
     */
    private Integer loginFailureCount;

    /**
     * 锁定日期
     */
    private Date lockedDate;

    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录日期
     */
    private Date loginDate;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String phone;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 地址
     */
    private String address;

    /**
     * E-mail
     */
    private String email;

    /**
     * 邮编
     */
    private String zipCode;


    /**
     * 临时属性，用于抛出临时字段（获得积分数）
     */
    private BigDecimal tempDistributionPoint;

    /**
     * 临时属性，用于抛出临时字段（订单数）
     */
    private Integer tempDistributionCount;


    /**
     * 头像
     */
    private String avator;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 用于接收Excel导入时，
     */
    private Integer gender_Int;

    /**
     * app端唯一标识 clientId
     */
    private String clientId;



    /**
     * 推荐码
     */
    private String code;

    /**
     * 是否开通小额免密支付
     */
    private Boolean isWithoutCode = Boolean.FALSE;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 获取登录令牌
     *
     * @return
     */
    @JsonProperty
    public String getToken() {
        return token;
    }

    /**
     * 设置登录令牌
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取登录令牌过期时间
     *
     * @return
     */
    public Date getTokenExpire() {
        return tokenExpire;
    }

    /**
     * 设置登录令牌过期时间
     *
     * @param tokenExpire
     */
    public void setTokenExpire(Date tokenExpire) {
        this.tokenExpire = tokenExpire;
    }


    /**
     * 获取密码
     *
     * @return 密码
     */
    @Pattern(regexp = "^[^\\s&\"<>]+$")
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    /**
     * 获取E-mail
     *
     * @return E-mail
     */
    @NotEmpty
    @Email
    @Length(max = 200)
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * 设置E-mail
     *
     * @param email E-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取积分
     *
     * @return 积分
     */
    @Min(0)
    public BigDecimal getPoint() {
        return point;
    }

    /**
     * 设置积分
     *
     * @param point 积分
     */
    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    /**
     * 获取消费金额
     *
     * @return 消费金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置消费金额
     *
     * @param amount 消费金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取余额
     *
     * @return 余额
     */
//    @NotNull(groups = Save.class)
    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(nullable = false, precision = 12, scale = 2)
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     *
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(precision = 12, scale = 2)
    public BigDecimal getEcoin() {
        return ecoin;
    }

    public void setEcoin(BigDecimal ecoin) {
        this.ecoin = ecoin;
    }

    public BigDecimal getPublicWelfare() {
        return publicWelfare;
    }

    public void setPublicWelfare(BigDecimal publicWelfare) {
        this.publicWelfare = publicWelfare;
    }

    /**
     * 获取是否启用
     *
     * @return 是否启用
     */
    @NotNull
    @Column(nullable = false)
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否启用
     *
     * @param isEnabled 是否启用
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }


    /**
     * 获取是否锁定
     *
     * @return 是否锁定
     */
    @Column(nullable = false)
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * 设置是否锁定
     *
     * @param isLocked 是否锁定
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * 获取连续登录失败次数
     *
     * @return 连续登录失败次数
     */
    @Column(nullable = false)
    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    /**
     * 设置连续登录失败次数
     *
     * @param loginFailureCount 连续登录失败次数
     */
    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    /**
     * 获取锁定日期
     *
     * @return 锁定日期
     */
    public Date getLockedDate() {
        return lockedDate;
    }

    /**
     * 设置锁定日期
     *
     * @param lockedDate 锁定日期
     */
    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    /**
     * 获取注册IP
     *
     * @return 注册IP
     */
    @Column(nullable = false, updatable = false)
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * 设置注册IP
     *
     * @param registerIp 注册IP
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * 获取最后登录IP
     *
     * @return 最后登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置最后登录IP
     *
     * @param loginIp 最后登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取最后登录日期
     *
     * @return 最后登录日期
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 设置最后登录日期
     *
     * @param loginDate 最后登录日期
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    @Length(max = 200)
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 获取出生日期
     *
     * @return 出生日期
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 设置出生日期
     *
     * @param birth 出生日期
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 获取地址
     *
     * @return 地址
     */
    @Length(max = 200)
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮编
     *
     * @return 邮编
     */
    @Length(max = 200)
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取电话
     *
     * @return 电话
     */
    @Length(max = 200)
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * 获取头像
     *
     * @return the avator
     */
    public String getAvator() {
        return avator;
    }

    /**
     * 设置头像
     *
     * @param avator the avator to set
     */
    public void setAvator(String avator) {
        this.avator = avator;
    }

    /**
     * 获取app端唯一标识
     *
     * @return
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置app端唯一标识
     *
     * @param clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public BigDecimal getTempDistributionPoint() {
        return tempDistributionPoint;
    }

    public void setTempDistributionPoint(BigDecimal tempDistributionPoint) {
        this.tempDistributionPoint = tempDistributionPoint;
    }

    public Integer getTempDistributionCount() {
        return tempDistributionCount;
    }

    public void setTempDistributionCount(Integer tempDistributionCount) {
        this.tempDistributionCount = tempDistributionCount;
    }

    public Boolean getIsWithoutCode() {
        return isWithoutCode;
    }

    public void setIsWithoutCode(Boolean isWithoutCode) {
        this.isWithoutCode = isWithoutCode;
    }

    @Transient
    public Integer getGender_Int() {
        return gender_Int;
    }

    public void setGender_Int(Integer gender_Int) {
        this.gender_Int = gender_Int;
    }


    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}