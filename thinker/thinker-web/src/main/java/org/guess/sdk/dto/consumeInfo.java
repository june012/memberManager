package org.guess.sdk.dto;

import java.math.BigDecimal;

/**
 * Created by wan.peng on 2016/10/13.
 */
public class ConsumeInfo {
    private String week;
    private String date;
    private String billType;
    private BigDecimal billAmount;
    private String billTitle;
    private String billMsg;
    private String billId;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public String getBillMsg() {
        return billMsg;
    }

    public void setBillMsg(String billMsg) {
        this.billMsg = billMsg;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}
