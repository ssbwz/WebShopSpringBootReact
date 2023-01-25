package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.CartsService;
import fontys.sem3.individualProject.business.exception.InvalidCartException;
import fontys.sem3.individualProject.business.exception.InvalidProductException;
import fontys.sem3.individualProject.business.exception.InvalidQuantityException;
import fontys.sem3.individualProject.business.exception.InvalidUserException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.CartRepository;
import fontys.sem3.individualProject.persistence.ProductRepository;
import fontys.sem3.individualProject.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartsService {

    CartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Override
    public Optional<Cart> getCartByCreatorId(long creatorId) {
        return cartRepository.getCartByCreatorId(creatorId);
    }


    @Transactional
    @Override
    public void addProduct(AddProductRequest addProductRequest) {


        //Getting the cart or creating new one
        Optional<Cart> optionalCart = getCartByCreatorId(addProductRequest.getCreatorId());
        Cart cart = null;

        if(addProductRequest.getIncreasedQuantity() <= 0){
            throw new InvalidQuantityException();
        }

        if(optionalCart.isEmpty()){
            //Creating new cart
            Optional<User> creatorOptional = userRepository.findById(addProductRequest.getCreatorId());
            if(creatorOptional.isEmpty()){
                throw new InvalidUserException("The creator id is invalid");
            }
            Cart newCart = Cart.builder()
                    .status(CartStatus.OPENED)
                    .creator(creatorOptional.get())
                    .build();
            cart = cartRepository.save(newCart);
        }else{
            //Getting the existed cart
            cart = optionalCart.get();
        }

        //Adding new cartProduct or updating current cartProduct
        CartProduct cartProduct = cart.getProductList().stream().filter(p -> p.getProduct().getId() == addProductRequest.getProductId()).findFirst().orElse(null);
        if(cartProduct != null){
            //Update quantity for current cartProduct
            int newQuantity = cartProduct.getQuantity() + addProductRequest.getIncreasedQuantity();
            cart.getProductList().get(cart.getProductList().indexOf(cartProduct)).setQuantity(newQuantity);
        }
        else {
            //Insert new cartProduct
           Optional<Product>  optionalProduct = productRepository.findById(addProductRequest.getProductId());
           if(optionalProduct.isEmpty()){
               throw new InvalidProductException("The product doesn't exist");
           }
           Product product = optionalProduct.get();
            cart.getProductList().add(CartProduct.builder().product(product).cart(cart).quantity(addProductRequest.getIncreasedQuantity()).build());
        }

    }

    @Transactional
    @Override
    public void deleteProduct(DeleteProductRequest deleteProductRequest) {
        //Getting the cart
         Optional<Cart> optionalCart = getCartByCreatorId(deleteProductRequest.getCreatorId());

        if(deleteProductRequest.getDecreasedQuantity() <= 0){
            throw new InvalidQuantityException();
        }

         if(optionalCart.isEmpty()){
             throw  new InvalidCartException();
         }
         Cart cart = optionalCart.get();
        //Update quantity for current cartProduct or deleting the cartProduct
        CartProduct cartProduct = cart.getProductList().stream().filter(p -> p.getProduct().getId() == deleteProductRequest.getProductId()).findFirst().orElse(null);
        if(cartProduct != null) {
            int newQuantity = cartProduct.getQuantity() - deleteProductRequest.getDecreasedQuantity();
            //Remove cartProduct
            if (newQuantity <= 0) {
                cart.getProductList().remove(cartProduct);
            }
            //Update quantity for current cartProduct
            else {
                cart.getProductList().get(cart.getProductList().indexOf(cartProduct)).setQuantity(newQuantity);
            }
        }

        //Delete cart if there are no cartProducts
        if(cart.getProductList().size() == 0){
            cartRepository.deleteById(cart.getId());
        }
    }
}
