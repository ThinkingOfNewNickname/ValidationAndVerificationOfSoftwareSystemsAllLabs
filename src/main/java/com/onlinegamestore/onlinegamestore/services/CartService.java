package com.onlinegamestore.onlinegamestore.services;

import com.onlinegamestore.onlinegamestore.dao.CartDao;
import com.onlinegamestore.onlinegamestore.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartDao cartDao;

    public Cart addCart(Cart cart)
    {
        return cartDao.addCart(cart);
    }
//    public Cart getCart(int id)
//    {
//        return cartDao.getCart(id);
//    }
public List<Cart> getCarts(){
    return this.cartDao.getCarts();
}

    public void updateCart(Cart cart){
        cartDao.updateCart(cart);
    }
    public void deleteCart(Cart cart)
    {
        cartDao.deleteCart(cart);
    }



}
