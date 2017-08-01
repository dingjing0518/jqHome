package com.by.service;

import com.by.json.HelpJson;
import com.by.model.MemberHelp;

import java.util.List;

/**
 * Created by yagamai on 15-12-18.
 */
public interface MemberHelperService {
    List<MemberHelp> findAll();

    MemberHelp findOne(int id);

    HelpJson save(MemberHelp memberHelp);

    HelpJson update(MemberHelp memberHelp);

    List<HelpJson> findAllJson();

    HelpJson findOneJson(int id);
}
