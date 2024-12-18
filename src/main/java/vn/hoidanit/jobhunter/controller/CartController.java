package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.CartItem;
import vn.hoidanit.jobhunter.domain.request.AddToCartDTO;
import vn.hoidanit.jobhunter.domain.request.UpdateQuantityCartItemDTO;
import vn.hoidanit.jobhunter.domain.response.ApiResponseDTO;
import vn.hoidanit.jobhunter.domain.response.ResAddToCart;
import vn.hoidanit.jobhunter.domain.response.ResCartTotalQuantityDTO;
import vn.hoidanit.jobhunter.service.CartService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    @ApiMessage("add to cart")
    public ResponseEntity<ResAddToCart> addToCart(@RequestBody AddToCartDTO addToCartRequest) {
        ResAddToCart resAddToCart = cartService.handleAddToCart(addToCartRequest.getUserId(), addToCartRequest.getProductId(), addToCartRequest.getQuantity());
        return ResponseEntity.status(HttpStatus.OK).body(new ResAddToCart(resAddToCart.getMessage(), resAddToCart.getTotalQuantity()));
    }

    @GetMapping("/cart/quantity")
    @ApiMessage("get cart total quantity")
    public ResCartTotalQuantityDTO getCartTotalQuantity() {
        return this.cartService.handleGetCartTotalQuantity();
    }

    @GetMapping("/cart")
    @ApiMessage("get list cart item")
    public List<CartItem> getCartItemsByCartId() {
        return this.cartService.handleGetAllCartItemByCartId();
    }

    @PutMapping("/cart-item")
    @ApiMessage("update cart item")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateQuantityCartItemDTO updateQuantityCartItemRequest) {
        CartItem updatedCartItem = this.cartService.handleUpdateQuantityCartItem(updateQuantityCartItemRequest.getCartId(), updateQuantityCartItemRequest.getProductId(), updateQuantityCartItemRequest.getQuantity());
        return ResponseEntity.status(HttpStatus.OK).body(updatedCartItem);
    }

    @DeleteMapping("/cart-item/{id}")
    @ApiMessage("delete cart item")
    public ResponseEntity<ApiResponseDTO> deleteCartItem(@PathVariable("id") long id) {
        this.cartService.handleDeleteCartItem(id);
        return ResponseEntity.ok(new ApiResponseDTO("削除しました"));
    }
    
}
