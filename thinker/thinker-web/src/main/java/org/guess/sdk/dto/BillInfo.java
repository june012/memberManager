package org.guess.sdk.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wan.peng on 2016/10/13.
 */
public class BillInfo {
    private Date date;
    private BigDecimal billPrice;
    private String billName;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(BigDecimal billPrice) {
        this.billPrice = billPrice;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }
}
