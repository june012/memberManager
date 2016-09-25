package org.guess.showcase.sys;

/**
 * Created by wan.peng on 2016/9/22.
 */
public enum ResouceEnum {
    SYS_MANAGER("48","会员管理"),
    SYS_MANAGER_ADMIN("49","管理员管理"),
    MEMBER_MANAGER("50","会员管理"),
    MEMBER_LIST("51","会员列表"),
    FINANCE_MANAGER("42","个人财务"),
    FINANCE_LIST("43","财务列表"),
    FINANCE_ANALYZE("44","财务分析")
    ;

    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * @param code    枚举值
     * @param message 枚举描述
     */
    private ResouceEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return ResouceEnum
     */
    public static ResouceEnum getByCode(String code) {
        for (ResouceEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<ResouceEnum>
     */
    public java.util.List<ResouceEnum> getAllEnum() {
        java.util.List<ResouceEnum> list = new java.util.ArrayList<ResouceEnum>();
        for (ResouceEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public java.util.List<String> getAllEnumCode() {
        java.util.List<String> list = new java.util.ArrayList<String>();
        for (ResouceEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
