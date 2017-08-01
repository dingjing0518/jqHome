package com.by.repository;

import com.by.model.Floor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yagamai on 16-3-25.
 */
public interface FloorRepository extends CrudRepository<Floor, Integer> {
    List<Floor> findAll();
}
