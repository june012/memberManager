package org.guess.showcase.consume.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wan.peng on 2016/9/11.
 * 充值记录表
 */
@Entity
@Table(name = "fill_record")
public class FillRecord {
    /**
     * 充值记录id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    /**
     * 会员编号
     */
    private long userid;
    /**
     * 日期
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    /**
     * 充值金额
     */
    private BigDecimal money;
    /**
     * 充值后钱包余额
     */
    @Column(name = "account_after")
    private BigDecimal accountAfter;
    /**
     * 充值后本金余额
     */
    @Column(name = "principal_after")
    private BigDecimal principalAfter;
    /**
     * 提现时间
     */
    @Column(name = "draw_time")
    private Date drawTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAccountAfter() {
        return accountAfter;
    }

    public void setAccountAfter(BigDecimal accountAfter) {
        this.accountAfter = accountAfter;
    }

    public BigDecimal getPrincipalAfter() {
        return principalAfter;
    }

    public void setPrincipalAfter(BigDecimal principalAfter) {
        this.principalAfter = principalAfter;
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }
}
