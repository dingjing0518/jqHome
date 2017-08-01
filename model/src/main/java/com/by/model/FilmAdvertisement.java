package com.by.model;

import com.by.typeEnum.ShowOnIndex;

import javax.persistence.*;

/**
 * Created by yagamai on 16-3-30.
 */
@Entity
@DiscriminatorValue("f")
public class FilmAdvertisement extends Advertisement {
    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "content_id")
    private FilmContent content;

    @Enumerated
    @Column(name = "is_show_on_index")
    private ShowOnIndex isShowOnIndex;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FilmContent getContent() {
        return content;
    }

    public void setContent(FilmContent content) {
        this.content = content;
    }

	public ShowOnIndex getIsShowOnIndex() {
		return isShowOnIndex;
	}

	public void setIsShowOnIndex(ShowOnIndex isShowOnIndex) {
		this.isShowOnIndex = isShowOnIndex;
	}
    
}
