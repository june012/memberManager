package org.guess.sys.model;

import org.guess.core.orm.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wan.peng on 2016/10/9.
 */
@Entity
@Table(name = "product")
public class Product extends IdEntity {
}
