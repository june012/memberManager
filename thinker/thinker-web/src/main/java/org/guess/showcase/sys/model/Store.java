package org.guess.showcase.sys.model;

import org.guess.core.orm.IdEntity;
import org.guess.core.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by wan.peng on 2016/9/17.
 */
@Entity
@Table(name = "Store")
public class Store extends IdEntity {
    @Column(name = "store_name")
    private String storeName;
    @Column
    private String address;
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date createTime= DateUtil.parseFormat("yyyy-MM-dd");

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
