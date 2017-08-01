package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("h")
public class HelpContent extends Content {
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "content")
    private MemberHelp memberHelp;

    public MemberHelp getMemberHelp() {
        return memberHelp;
    }

    public void setMemberHelp(MemberHelp memberHelp) {
        this.memberHelp = memberHelp;
    }
}
