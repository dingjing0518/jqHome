package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * Created by yagamai on 16-3-30.
 */
@Entity
@DiscriminatorValue("f")
public class FilmContent extends Content {
    @OneToOne(mappedBy = "content", fetch = FetchType.LAZY)
    private FilmAdvertisement filmAdvertisement;

    public FilmAdvertisement getFilmAdvertisement() {
        return filmAdvertisement;
    }

    public void setFilmAdvertisement(FilmAdvertisement filmAdvertisement) {
        this.filmAdvertisement = filmAdvertisement;
    }
}
