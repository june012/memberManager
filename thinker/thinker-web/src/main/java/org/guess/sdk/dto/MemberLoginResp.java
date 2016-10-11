package org.guess.sdk.dto;

/**
 * Created by wan.peng on 2016/10/12.
 */
public class MemberLoginResp {
    private String name;
    private String phone;
    private String userImage;
    private Long interestCount;
    private Long userLevel;
    private String homeUrl;
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

    public Long getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(Long interestCount) {
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
