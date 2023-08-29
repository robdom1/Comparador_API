package com.example.comparador_api.controllers;

import com.example.comparador_api.entities.Cart;
import com.example.comparador_api.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable String userId,
            @PathVariable String productId) {
        try {
            cartService.addToCart(userId, productId);
            return new ResponseEntity<>("Producto agregado satisfactoriamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<?> deleteFromCart(
            @PathVariable String userId,
            @PathVariable String productId) {
        try {
            cartService.deleteFromCart(userId, productId);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserCart(@PathVariable String userId){
        try{
            Cart cartList = cartService.getUserCart(userId);
            return new ResponseEntity<>(cartList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
