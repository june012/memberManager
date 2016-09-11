package org.guess.showcase.interest.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
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
    private BigInteger principal;
    /**
     *利息比例
     */
    private BigInteger rate;
    /**
     * 增加利息
     */
    @Column(name = "interest_add")
    private BigInteger interestAdd;
    /**
     * 增加后钱包金额
     */
    @Column(name = "account_after")
    private BigInteger accountAfter;

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

    public BigInteger getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigInteger principal) {
        this.principal = principal;
    }

    public BigInteger getRate() {
        return rate;
    }

    public void setRate(BigInteger rate) {
        this.rate = rate;
    }

    public BigInteger getInterestAdd() {
        return interestAdd;
    }

    public void setInterestAdd(BigInteger interestAdd) {
        this.interestAdd = interestAdd;
    }

    public BigInteger getAccountAfter() {
        return accountAfter;
    }

    public void setAccountAfter(BigInteger accountAfter) {
        this.accountAfter = accountAfter;
    }
}
