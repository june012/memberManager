package org.guess.sdk.dto;

import java.util.List;

/**
 * Created by wan.peng on 2016/10/13.
 */
public class ConsumeRespData {
    private String month;
    private List<ConsumeInfo> itemList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ConsumeInfo> getItemList() {
        return itemList;
    }

    public void setItemList(List<ConsumeInfo> itemList) {
        this.itemList = itemList;
    }
}
