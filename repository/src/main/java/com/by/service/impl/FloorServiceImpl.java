package com.by.service.impl;

import com.by.model.Floor;
import com.by.repository.FloorRepository;
import com.by.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yagamai on 16-3-25.
 */
@Service
@Transactional
public class FloorServiceImpl implements FloorService {
    @Autowired
    private FloorRepository repository;

    @Override
    public List<Floor> findAll() {
        return repository.findAll();
    }
}
