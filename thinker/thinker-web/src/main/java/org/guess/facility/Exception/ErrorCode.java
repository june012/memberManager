package org.guess.facility.Exception;

/**
 * Created by wan.peng on 2016/10/14.
 */
public enum ErrorCode {

    NOT_EXSIST_THIS_MEMBER(0x80003301, "该会员不存在"),
    ACCOUNT_IS_NOT_ENOUGH(0x80003302, "余额不足");

    private final int value;

    private final String message;

    ErrorCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public String getCode() {
        return Integer.toHexString(this.value).toUpperCase();
    }

    public String getSystem() {
        return Integer.toHexString(this.value >>> 24).toUpperCase();
    }

    public String getModule() {
        return Integer.toHexString(this.value >>> 16).toUpperCase();
    }

    public static ErrorCode getByCode(int value){
        for(ErrorCode _enum : values()){
            if(_enum.getValue() == value){
                return _enum;
            }
        }
        return null;
    }
}
