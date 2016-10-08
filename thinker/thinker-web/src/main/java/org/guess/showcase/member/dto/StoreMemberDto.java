package org.guess.showcase.member.dto;

import java.util.List;

/**
 * Created by wan.peng on 2016/10/8.
 */
public class StoreMemberDto {
    private Long id;
    private List<MemberDto> memberDtos;
    private String name;
    private Long storeId;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MemberDto> getMemberDtos() {
        return memberDtos;
    }

    public void setMemberDtos(List<MemberDto> memberDtos) {
        this.memberDtos = memberDtos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
