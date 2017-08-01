package com.by.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.by.form.EventDetailsForm;
import com.by.json.EventDetailsJson;
import com.by.model.EventDetails;

public interface EventDetailsService {
	Page<EventDetailsJson> findAll(EventDetailsForm form, Pageable pageable);
	EventDetails save(EventDetails eventDetails);
	EventDetails findOne(int id);
	EventDetails update(EventDetails eventDetails);
	Optional<EventDetails> findById(int id);
	List<EventDetails> findAll(Sort sort);
}
