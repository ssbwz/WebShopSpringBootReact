package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.exception.InvalidCartException;
import fontys.sem3.individualProject.business.exception.InvalidProductException;
import fontys.sem3.individualProject.business.exception.InvalidQuantityException;
import fontys.sem3.individualProject.business.exception.InvalidUserException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.CartRepository;
import fontys.sem3.individualProject.persistence.ProductRepository;
import fontys.sem3.individualProject.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {


    @Mock
    private CartRepository cartRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private CartServiceImpl cartByUseCasesImpl;

    @Test
    void getCartByCreatorId_GetExistentCart_ReturnCart() {
        //Arrange
        Cart outputCart = Cart.builder()
                .id(1L)
                .creator(User.builder().id(1L).build())
                .build();

        when(cartRepositoryMock.getCartByCreatorId(1L))
                .thenReturn(Optional.of(outputCart));

        //Act
        Optional<Cart> actualResult = cartByUseCasesImpl.getCartByCreatorId(1L);
        //Assert
        Cart expectedResult = Cart.builder()
                .id(outputCart.getId())
                .creator(outputCart.getCreator())
                .build();

        assertEquals("It doesn't return the expected cart",
                Optional.of(expectedResult), actualResult);

        verify(cartRepositoryMock).getCartByCreatorId(1L);
    }

    @Test
    void addProduct_WithNonExistentCart_ReturnNothing() {
        //Arrange
        Optional<User> optionalUser = Optional.of(User.builder().id(1L).build());
        when(userRepositoryMock.findById(1L))
                .thenReturn(optionalUser);

        when(cartRepositoryMock.getCartByCreatorId(optionalUser.get().getId()))
                .thenReturn(Optional.empty());

        Cart input = Cart.builder().creator(optionalUser.get()).status(CartStatus.OPENED).build();
        Cart output = Cart.builder().id(1L).creator(optionalUser.get()).status(CartStatus.OPENED).build();
        AddProductRequest addProductRequest = AddProductRequest.builder().
                creatorId(1L)
                .productId(1L)
                .increasedQuantity(2).build();
        when(cartRepositoryMock.save(input))
                .thenReturn(output);

        Optional<Product> optionalProduct = Optional.of(Product.builder().id(1L).build());
        when(productRepositoryMock.findById(optionalProduct.get().getId()))
                .thenReturn(optionalProduct);

        Cart cart = Cart.builder().creator(User.builder().id(1L).build()).build();
        //Act
        cartByUseCasesImpl.addProduct(addProductRequest);
        //Assert
        verify(productRepositoryMock).findById(optionalProduct.get().getId());
        verify(userRepositoryMock).findById(optionalUser.get().getId());
        verify(cartRepositoryMock).save(input);
        verify(cartRepositoryMock).getCartByCreatorId(optionalUser.get().getId());
    }

    @Test
    void addProduct_WithInvalidUser_ThrowsInvalidUserException() {
        //Arrange
        Optional<User> optionalUser = Optional.of(User.builder().id(1L).build());
        when(userRepositoryMock.findById(1L))
                .thenReturn(Optional.empty());

        when(cartRepositoryMock.getCartByCreatorId(optionalUser.get().getId()))
                .thenReturn(Optional.empty());


        AddProductRequest addProductRequest = AddProductRequest.builder().
                creatorId(1L)
                .productId(1L)
                .increasedQuantity(2).build();

        //Assert
        Exception exception = assertThrows(InvalidUserException.class, () -> {
            //Act
            cartByUseCasesImpl.addProduct(addProductRequest);
        }, "The method does add product with invalid parameters");

        verify(userRepositoryMock).findById(optionalUser.get().getId());
        verify(cartRepositoryMock).getCartByCreatorId(optionalUser.get().getId());
    }

    @Test
    void addProduct_WithInvalidQuantity_ThrowsInvalidQuantityException() {
        //Arrange
        AddProductRequest addProductRequest = AddProductRequest.builder().
                creatorId(1L)
                .productId(1L)
                .increasedQuantity(-1)
                .build();
        //Assert
        Exception exception = assertThrows(InvalidQuantityException.class, () -> {
            //Act
            cartByUseCasesImpl.addProduct(addProductRequest);
        }, "The method does add product with invalid parameters");
    }

    @Test
    void addProduct_WithNonExistentProduct_ThrowsInvalidProductException() {
        //Arrange

        Optional<Cart> optionalCart = Optional.of(Cart.builder().id(1L).build());

        when(cartRepositoryMock.getCartByCreatorId(1L))
                .thenReturn(optionalCart);

        Optional<Product> optionalProduct = Optional.empty();
        when(productRepositoryMock.findById(1L))
                .thenReturn(optionalProduct);

        AddProductRequest addProductRequest = AddProductRequest.builder().
                creatorId(1L)
                .productId(1L)
                .increasedQuantity(2).build();
        //Assert
        Exception exception = assertThrows(InvalidProductException.class, () -> {
            //Act
            cartByUseCasesImpl.addProduct(addProductRequest);
        }, "The method does add product with invalid parameters");

        verify(cartRepositoryMock).getCartByCreatorId(1L);
        verify(productRepositoryMock).findById(1L);

    }

    @Test
    void deleteProduct_WithExistentCartAndProductInTheCart_ReturnNothing() {
        //Arrange
        List<CartProduct> productLists = new ArrayList<>();
        productLists.addAll(List.of(CartProduct.builder().id(1L).product(Product.builder().id(1L).build()).quantity(1).build()
                , CartProduct.builder().id(2L).product(Product.builder().id(2L).build()).quantity(1).build()));
        Optional<Cart> optionalCart = Optional.of(Cart.builder().id(1L).productList(productLists).build());
        when(cartRepositoryMock.getCartByCreatorId(1L))
                .thenReturn(optionalCart);

        DeleteProductRequest request = DeleteProductRequest.builder()
                .productId(1L)
                .creatorId(1L)
                .decreasedQuantity(1)
                .build();
        //Act
        cartByUseCasesImpl.deleteProduct(request);
        //Assert
        verify(cartRepositoryMock).getCartByCreatorId(1L);
    }

    @Test
    void deleteProduct_WithExistentCartAndLastProductInTheCart_ReturnNothing() {
        //Arrange
        List<CartProduct> productLists = new ArrayList<>();
        productLists.addAll(List.of(CartProduct.builder().id(2L).product(Product.builder().id(2L).build()).quantity(1).build()));
        Optional<Cart> optionalCart = Optional.of(Cart.builder().id(1L).productList(productLists).build());
        when(cartRepositoryMock.getCartByCreatorId(1L))
                .thenReturn(optionalCart);

        DeleteProductRequest request = DeleteProductRequest.builder()
                .productId(2L)
                .creatorId(1L)
                .decreasedQuantity(1)
                .build();

        doNothing().when(cartRepositoryMock).deleteById(optionalCart.get().getId());
        //Act
        cartByUseCasesImpl.deleteProduct(request);
        //Assert
        verify(cartRepositoryMock).deleteById(optionalCart.get().getId());
        verify(cartRepositoryMock).getCartByCreatorId(1L);
    }

    @Test
    void deleteProduct_WithInvalidQuantity_ThrowsInvalidQuantityException() {
        //Arrange
        List<CartProduct> productLists = new ArrayList<>();
        productLists.addAll(List.of(CartProduct.builder().id(2L).product(Product.builder().id(2L).build()).quantity(1).build()));
        Optional<Cart> optionalCart = Optional.of(Cart.builder().id(1L).productList(productLists).build());
        when(cartRepositoryMock.getCartByCreatorId(1L))
                .thenReturn(optionalCart);

        DeleteProductRequest request = DeleteProductRequest.builder()
                .productId(2L)
                .creatorId(1L)
                .decreasedQuantity(0)
                .build();
        //Assert
        Exception exception = assertThrows(InvalidQuantityException.class, () -> {
            //Act
            cartByUseCasesImpl.deleteProduct(request);
        }, "The method does add product with invalid parameters");

        verify(cartRepositoryMock).getCartByCreatorId(1L);
    }

    @Test
    void deleteProduct_WithNonExistentCart_ThrowInvalidCartException() {
        //Arrange
        when(cartRepositoryMock.getCartByCreatorId(1L))
                .thenReturn(Optional.empty());

        DeleteProductRequest request = DeleteProductRequest.builder()
                .productId(2L)
                .creatorId(1L)
                .decreasedQuantity(1)
                .build();
        //Assert
        Exception exception = assertThrows(InvalidCartException.class, () -> {
            //Act
            cartByUseCasesImpl.deleteProduct(request);
        }, "The method does add product with invalid parameters");

        verify(cartRepositoryMock).getCartByCreatorId(1L);
    }

}