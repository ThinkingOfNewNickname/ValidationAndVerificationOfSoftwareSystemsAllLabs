package com.onlinegamestore.onlinegamestore.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name="CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User customer;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;


    public Cart() {
        games = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<Game> getProducts() {
        return games;
    }

    public void setProducts(List<Game> games) {
        this.games = games;
    }

    public void addProduct(Game game) {
        games.add(game);
    }

    public void removeProduct(Game game) {
        games.remove(game);
    }
}
