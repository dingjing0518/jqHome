package com.by.repository;

import com.by.model.FilmAdvertisement;
import com.by.model.ShoppingMall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yagamai on 16-3-30.
 */
public interface FilmAdvertisementRepository extends CrudRepository<FilmAdvertisement, Integer> {
    List<FilmAdvertisement> findByMall(ShoppingMall mall);

    Page<FilmAdvertisement> findByMall(ShoppingMall mall, Pageable pageable);
    
}
