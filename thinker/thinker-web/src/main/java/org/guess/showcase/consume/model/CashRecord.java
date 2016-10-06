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
 * 消费记录表
 */
@Entity
@Table(name = "cash_record")
public class CashRecord {
    /**
     * 消费记录id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    /**
     * 会员编号
     */
    private long userid;
    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;
    /**
     * 产品类别
     */
    @Column(name = "product_type")
    private String productType;
    /**
     * 产品价格
     */
    @Column(name = "product_price")
    private long productPrice;
    /**
     * 购买数量
     */
    private int count;
    /**
     * 折扣
     */
    private double discount;
    /**
     * 日期
     */
    @Column(name ="create_time")
    private Date createTime;
    /**
     * 消费金额
     */
    private BigDecimal money;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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
}
