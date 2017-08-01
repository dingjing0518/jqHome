package com.by.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.by.json.NotifyJson;
import com.by.model.Notify;

public interface NotifyService {
	List<Notify> findAll();
	List<Notify> findAll(Sort sort);
	Page<Notify> findAll(String name, Pageable pageable);
	Page<NotifyJson> findAllJson(String name,Pageable pageable);
	List<NotifyJson> toJson(List<Notify> notifys);
	Notify save(Notify notify);
	Notify save(NotifyJson notify);
	List<Notify> refresh(Sort sort);
	Notify findOne(int id);
	Notify update(NotifyJson notify);
	Notify update(Notify notify);
}
