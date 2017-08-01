package com.by.service;

import com.by.model.WechartResponse;

public interface WechartResponseService {
	WechartResponse save(WechartResponse response);
	
	WechartResponse findByOncestr(String oncestr);
}
