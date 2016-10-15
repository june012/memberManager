package org.guess.sys.model;

import org.guess.core.orm.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wan.peng on 2016/10/9.
 * 投诉记录
 */
@Entity
@Table(name = "suggestion_log")
public class SuggestionLog extends IdEntity {

    /**
     * 会员编号
     */
    @Column(name = "member_id")
    private long memberId;
    /**
     * 投诉内容
     */
    private String content;
    /**
     * 日期
     */
    @Column(name ="create_time")
    private Date createTime;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
