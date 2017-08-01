package com.by.service;

import com.by.model.FilmAdvertisement;
import com.by.model.ShoppingMall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yagamai on 16-3-30.
 */
public interface FilmAdvertisementService {
    List<FilmAdvertisement> findByMall(ShoppingMall mall);

    Page<FilmAdvertisement> findByMall(ShoppingMall mall, Pageable pageable);

    FilmAdvertisement findOne(int id);

    FilmAdvertisement update(FilmAdvertisement adv);

    FilmAdvertisement save(FilmAdvertisement adv);

    List<FilmAdvertisement> findOnIndex();
}
