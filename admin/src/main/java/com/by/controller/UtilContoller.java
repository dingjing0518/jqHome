package com.by.controller;

import com.by.exception.MemberNotValidException;
import com.by.model.Member;

public interface UtilContoller {
    default void validMember(Member member) {
        if (!member.isValidMember())
            throw new MemberNotValidException();
    }
}
