package org.guess.showcase.consume.model;

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
 * 提现记录
 */
@Entity
@Table(name = "draw_record")
public class DrawRecord {
    /**
     * 提现记录id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    /**
     * 会员编号
     */
    private long userid;
    /**
     * 提现时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 提现金额
     */
    private BigDecimal money;
    /**
     * 提现后钱包余额
     */
    @Column(name = "account_after")
    private BigDecimal accountAfter;
    /**
     * 提现原因
     */
    private String reson;

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


    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
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
}
