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
 * 利息历史记录表
 */
@Entity
@Table(name = "interest_record")
public class InterestRecord {
    /**
     * 利息编号
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private long userId;
    /**
     * 日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    /**
     * 本金金额
     */
    private BigDecimal principal;
    /**
     *利息比例
     */
    private double rate;
    /**
     * 增加利息
     */
    @Column(name = "interest_add")
    private BigDecimal interestAdd;
    /**
     * 增加后钱包金额
     */
    @Column(name = "account_after")
    private BigDecimal accountAfter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterestAdd() {
        return interestAdd;
    }

    public void setInterestAdd(BigDecimal interestAdd) {
        this.interestAdd = interestAdd;
    }

    public BigDecimal getAccountAfter() {
        return accountAfter;
    }

    public void setAccountAfter(BigDecimal accountAfter) {
        this.accountAfter = accountAfter;
    }
}
