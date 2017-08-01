package com.by.model;

import javax.persistence.*;

@Entity
@Table(name = "by_shopping_mall")
public class ShoppingMall {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public ShoppingMall() {
    }

    public ShoppingMall(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingMall that = (ShoppingMall) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
