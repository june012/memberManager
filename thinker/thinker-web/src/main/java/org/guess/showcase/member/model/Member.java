package org.guess.showcase.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by wan.peng on 2016/9/11.
 *会员信息
 */
@Entity
@Table(name = "Member")
public class Member {
    /**
     * 会员编号
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 微信号(微信id)
     */
    private String openid;
    /**
     * 密码
     */
    private String password;
    /**
     * 年龄
     */
    private int age;
    /**
     * 头像 有默认头像
     */
    private String avater;
    /**
     * 地址
     */
    private String address;
    /**
     * 本金(用于计算利息)
     */
    private BigDecimal principal;
    /**
     * 利息
     */
    private BigDecimal interest;
    /**
     * 可被消费金额
     */
    @Column(name = "can_be_consumed")
    private BigDecimal canBeConsumed;
    /**
     * 积分
     */
    private Long credit;
    /**
     * 钱包余额
     */
    private BigDecimal account;
    /**
     * 钱包奖金
     */
    private BigDecimal award;
    /**
     * 级别
     */
    private String level;
//
//    @Column(name = "last_login_time")
//    private Data lastLoginTime;

//    public Data getLastLoginTime() {
//        return lastLoginTime;
//    }
//
//    public void setLastLoginTime(Data lastLoginTime) {
//        this.lastLoginTime = lastLoginTime;
//    }

    /**
     * 门店编号
     */
    @Column(name = "store_id")
    private Long storeId;
    /**
     * 状态(是否激活、是否已删除)
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getAward() {
        return award;
    }

    public void setAward(BigDecimal award) {
        this.award = award;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getCanBeConsumed() {
        return canBeConsumed;
    }

    public void setCanBeConsumed(BigDecimal canBeConsumed) {
        this.canBeConsumed = canBeConsumed;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }
}
