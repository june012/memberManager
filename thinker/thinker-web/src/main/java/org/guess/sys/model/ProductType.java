package org.guess.sys.model;

import org.guess.core.orm.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wan.peng on 2016/10/9.
 */
@Entity
@Table(name = "product_type")
public class ProductType extends IdEntity {
    @Column(name = "type_name")
    private String typeName;

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
}
