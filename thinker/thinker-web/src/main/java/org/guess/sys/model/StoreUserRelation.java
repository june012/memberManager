package org.guess.sys.model;

import org.guess.core.orm.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wan.peng on 2016/9/17.
 */
@Entity
@Table(name = "store_user_relation")
public class StoreUserRelation extends IdEntity {
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "store_id")
    private long storeId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
