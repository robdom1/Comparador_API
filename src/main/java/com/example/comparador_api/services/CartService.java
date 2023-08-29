package com.example.comparador_api.services;

import com.example.comparador_api.entities.Cart;
import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.entities.Usuario;
import com.example.comparador_api.repositories.CartRepo;
import com.example.comparador_api.repositories.ProductoRepo;
import com.example.comparador_api.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final UserRepo userRepo;
    private final ProductoRepo productoRepo;
    private final CartRepo cartRepo;

    public void addToCart(String userId, String productId) {
        Usuario user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Producto product = productoRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Cart carrito = user.getCarrito();

        if(carrito == null){
            carrito = new Cart();
            carrito.setProducts(new ArrayList<>());
        }

        if(carrito.getProducts().contains(product)){
            throw new IllegalArgumentException("El producto ya se encuentra en el carrito");
        }else{
            carrito.getProducts().add(product);
            cartRepo.save(carrito);
            user.setCarrito(carrito);
            userRepo.save(user);
        }

    }

    public void deleteFromCart(String userId, String productId) {
        Usuario user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Producto product = productoRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Cart carrito = user.getCarrito();

        if(!carrito.getProducts().contains(product)){
            throw new IllegalArgumentException("El producto no está en el carrito");
        }else{
            carrito.getProducts().remove(product);
            cartRepo.save(carrito);
            user.setCarrito(carrito);
            userRepo.save(user);
        }

    }

    public Cart getUserCart(String userId) {
        Usuario user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Cart carrito = user.getCarrito();

        if (carrito == null){
            carrito = new Cart();
            carrito.setProducts(new ArrayList<>());
            cartRepo.save(carrito);
            user.setCarrito(carrito);
            userRepo.save(user);
        }

        return carrito;

    }
}
