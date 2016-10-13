package org.guess.facility;

/**
 * Created by wan.peng on 2016/10/11.
 */
public class DefinedConstant {
    //会员状态
    /**已激活**/
    public static final String MEMBER_STATUS_ACTIVATION="A";
    /**未激活**/
    public static final String MEMBER_STATUS_UNACTIVATED ="N";
    /**已删除**/
    public static final String MEMBER_STATUS_DELETE="D";

    //奖金类型
    public static final String AWARD_TYPR_RECOMMEND = "M";//推荐奖

    //充值金额是否可到期
    public static final String FILL_HANDLE_YES = "Y";//到期

    public static final String FILL_HANDLE_NO = "N";//未到期

    //消费记录
    public static final String CONSUME_TYPE_FILL = "F";//充值
    public static final String CONSUME_TYPE_AWARD = "A";//奖励
    public static final String CONSUME_TYPE_DRAW = "D";//提现
    public static final String CONSUME_TYPE_INTEREST = "I";//利息
    public static final String CONSUME_TYPE_CASH = "C";//消费

    //状态码
    public static final String   RESPONSE_CODE_ERROR = "0";//失败
    public static final String RESPONSE_CODE_SUCCESS = "1";//成功

}
