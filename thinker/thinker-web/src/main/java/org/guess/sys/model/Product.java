package org.guess.sys.model;

import org.guess.core.orm.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by wan.peng on 2016/10/9.
 */
@Entity
@Table(name = "product")
public class Product extends IdEntity {
    @Column(name = "product_name")
    private String productName;
    @Column(name = "type_id")
    private Long typeId;
    @Column(name = "type_name")
    private String typeName;
    private BigDecimal price;
    @Column(name = "oa_id")
    private long oaId;
    public long getOaId() {
        return oaId;
    }

    public void setOaId(long oaId) {
        this.oaId = oaId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
