package org.guess.showcase.qixiu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.guess.core.orm.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户车详细记录
 *
 * @author rguess
 * @version 2014-11-22
 */
@Entity
@Table(name = "q_record_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rdetail extends IdEntity {

    /**
     * 类别
     */
    private String leibie;

    /**
     * 维修价格
     */
    private Double jiage = new Double(0);

    /**
     * 维修时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date xiudate;

    /**
     * 提醒下次更换或维修日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date nextxiu;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Record.class)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Record record;

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }

    public Double getJiage() {
        return jiage;
    }

    public void setJiage(Double jiage) {
        this.jiage = jiage;
    }

    public Date getXiudate() {
        return xiudate;
    }

    public void setXiudate(Date xiudate) {
        this.xiudate = xiudate;
    }

    @XmlTransient
    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Date getNextxiu() {
        return nextxiu;
    }

    public void setNextxiu(Date nextxiu) {
        this.nextxiu = nextxiu;
    }


}