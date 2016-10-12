package org.guess.sdk.dto;

import java.math.BigDecimal;

/**
 * Created by wan.peng on 2016/10/12.
 */
public class MemberLoginResp {
    private String name;//姓名
    private String phone;//电话
    private String userImage;//头像
    private BigDecimal interestCount;//积分
    private Long userLevel;//等级
    private String homeUrl;//首页
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public BigDecimal getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(BigDecimal interestCount) {
        this.interestCount = interestCount;
    }

    public Long getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Long userLevel) {
        this.userLevel = userLevel;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
