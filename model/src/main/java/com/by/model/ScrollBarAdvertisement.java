package com.by.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by yagamai on 16-3-28.
 */
@Entity
@DiscriminatorValue("s")
public class ScrollBarAdvertisement extends Advertisement {
}
