package com.by.model;

import javax.persistence.*;

/**
 * Created by yagamai on 15-12-18.
 */
@Entity
@Table(name = "by_content")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	protected String summary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Content content = (Content) o;

		return id == content.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
