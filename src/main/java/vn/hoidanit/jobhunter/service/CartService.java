package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import vn.hoidanit.jobhunter.domain.Cart;
import vn.hoidanit.jobhunter.domain.CartItem;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResAddToCart;
import vn.hoidanit.jobhunter.domain.response.ResCartTotalQuantityDTO;
import vn.hoidanit.jobhunter.repository.CartItemRepository;
import vn.hoidanit.jobhunter.repository.CartRepository;
import vn.hoidanit.jobhunter.repository.ProductRepository;
import vn.hoidanit.jobhunter.util.SecurityUtil;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public ResAddToCart handleAddToCart(long userId, long productId, int quantity) {
        System.out.println("userId" + userId + productId + quantity);
        // Kiểm tra giỏ hàng active
        Optional<Cart> cartOptional = this.cartRepository.findByUserIdAndStatus(userId, "active");
        
        // Nếu không có giỏ hàng, tạo giỏ hàng mới
        Cart cart;

        if (cartOptional.isEmpty()) {
            System.out.println("new cart");
            cart = new Cart();
            cart.setUserId(userId);
            this.cartRepository.save(cart);
        } else {
            cart = cartOptional.get();
        }

        // Kiểm tra sản phẩm trong giỏ hàng
        Optional<CartItem> cartItemOptional = this.cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        // Kiểm tra nếu sản phẩm chưa từng tồn tại trong giỏ hàng
        if (cartItemOptional.isEmpty()) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(this.productRepository.findById(productId).orElseThrow());
            newCartItem.setPrice(newCartItem.getProduct().getMinPrice());
            newCartItem.setQuantity(quantity); // Thêm số lượng cho item mới
            this.cartItemRepository.save(newCartItem);
        } else {
            // Nếu sản phẩm đã có trong giỏ, cập nhật số lượng
            CartItem cartItem = cartItemOptional.get();
            cartItemOptional.get().setQuantity(cartItem.getQuantity() + quantity);
            this.cartItemRepository.save(cartItem);
        }

        // Tính tổng số lượng sản phẩm trong giỏ hàng
        int totalQuantity = this.cartItemRepository
        .findAllByCartId(cart.getId())
        .stream()
        .mapToInt(CartItem::getQuantity)
        .sum();

        return new ResAddToCart("カートに追加しました", totalQuantity);
    }

    public ResCartTotalQuantityDTO handleGetCartTotalQuantity() {
        // Lấy thông tin người dùng từ SecurityContextHolder
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String username = authentication.getName(); // Lấy username (hoặc email)
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
        SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        Optional<Cart> cartOptional = this.cartRepository.findByUserIdAndStatus(currentUserDB.getId(), "active");
        if (cartOptional.isEmpty()) {
            return new ResCartTotalQuantityDTO(0); // Giỏ hàng trống
        }

        Cart cart = cartOptional.get();
        int cartTotalQuantity = this.cartItemRepository
                .findAllByCartId(cart.getId())
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        
        return new ResCartTotalQuantityDTO(cartTotalQuantity);
        }
    
    public List<CartItem> handleGetAllCartItemByCartId() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
        SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        Optional<Cart> cartOptional = this.cartRepository.findByUserIdAndStatus(currentUserDB.getId(), "active");
        Cart cart = new Cart();
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        }

        return this.cartItemRepository.findAllByCartId(cart.getId());
    }

    public CartItem handleUpdateQuantityCartItem(long cartId, long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
        if (quantity <= 0) {
            cart.getCartItems().remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }

        this.cartRepository.save(cart);
        return cartItem;
    }

    public void handleDeleteCartItem(long id) {
        if (this.cartItemRepository.existsById(id)) {
            this.cartItemRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("CartItem with id " + id + " not found");
        }
    }
}
