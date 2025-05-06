package project.ecommerce.api.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ecommerce.api.cart.dto.request.CartItemCreateRequest;
import project.ecommerce.api.cart.dto.response.CartItemResponse;
import project.ecommerce.api.cart.entity.CartItem;
import project.ecommerce.api.cart.exception.CartItemException;
import project.ecommerce.api.cart.repository.CartItemRepository;
import project.ecommerce.api.product.entity.Product;
import project.ecommerce.api.product.exception.ProductException;
import project.ecommerce.api.product.repository.ProductRepository;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.exception.UserExceptionType;
import project.ecommerce.api.user.repository.UserRepository;
import project.ecommerce.common.exception.BaseException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public List<CartItemResponse> getCartItems(String username) {
        return cartItemRepository.findAllByUserEmail(username).stream()
                .map(CartItemResponse::of)
                .toList();
    }

    public List<CartItemResponse> addCartItem(String username, CartItemCreateRequest request) {
        if (request.quantity() <= 0) {
            throw new BaseException(CartItemException.INVALID_QUANTITY);
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new BaseException(ProductException.PRODUCT_NOT_FOUND));

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product).orElse(null);

        if (existingCartItem != null) {
            existingCartItem.updateQuantity(existingCartItem.getQuantity() + request.quantity());
            cartItemRepository.save(existingCartItem);
        } else {
            cartItemRepository.save(CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(request.quantity())
                    .build());
        }

        return cartItemRepository.findAllByUserEmail(username).stream()
                .map(CartItemResponse::of)
                .toList();
    }

    public void updateCartItem(Long cartItemId, Long quantity) {

    }

    public void deleteCartItem(Long cartItemId) {

    }
}
