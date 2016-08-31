package org.guess.modules.test.entity;


import java.util.Date;

/**
 * Created by garyr on 2015/4/18.
 */
public class MybatiesTest {

    private int id;
    private String loginId;
    /** 密码 */
    private String passwd;
    private String name;
    /** 电子邮件 */
    private String email;
    /** 手机号 */
    private String mobilePhone;
    /** 地址 */
    private String address;
    /** 状态 0 无效 1 有效 */
    private int status = 0;
    private String remark;
    /** 创建时间 */
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public MybatiesTest(int id, String loginId, String passwd, String name, String email, String mobilePhone, String address, int status, String remark, Date createDate) {
        this.id = id;
        this.loginId = loginId;
        this.passwd = passwd;
        this.name = name;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.address = address;
        this.status = status;
        this.remark = remark;
        this.createDate = createDate;
    }

    public MybatiesTest() {
        super();
    }
}
