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
 */
@Entity
@Table(name = "award_record")
public class AwardRecord {
    /**
     * 奖励记录id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    /**
     * 会员编号
     */
    @Column(name = "member_id")
    private long memberId;
    /**
     * 日期
     */
    private Date date;
    /**
     * 奖励金额
     */
    @Column(name = "award_money")
    private BigDecimal awardMoney;
    /**
     * 奖励类型
     */
    @Column(name = "award_type")
    private String awardType;
    /**
     * 奖励后钱包金额
     */
    @Column(name = "award_after")
    private BigDecimal awardAfter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAwardMoney() {
        return awardMoney;
    }

    public void setAwardMoney(BigDecimal awardMoney) {
        this.awardMoney = awardMoney;
    }

    public BigDecimal getAwardAfter() {
        return awardAfter;
    }

    public void setAwardAfter(BigDecimal awardAfter) {
        this.awardAfter = awardAfter;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }
}
