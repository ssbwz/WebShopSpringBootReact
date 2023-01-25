package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.exception.InvalidCartException;
import fontys.sem3.individualProject.business.exception.InvalidUserException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.CartRepository;
import fontys.sem3.individualProject.persistence.OrderRepository;
import fontys.sem3.individualProject.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {

    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private CartRepository cartRepositoryMock;

    @InjectMocks
    private OrdersServiceImpl orderByUseCasesImpl;


    @Test
    void getOrderById_GetExistentOrder_ReturnOrder() {
    }

    @Test
    void getOrderById_GetNonExistentOrder_ThrowsExceptionOrderDoesNotExist() {
    }

    @Test
    void createOrder_CreateValidOrder_ReturnNewOrder() {
        //Arrange
        CreateOrderRequest request = CreateOrderRequest.builder()
                .creatorId(1L)
                .street("TestStraat")
                .houseNumber("50A")
                .postcode("1564 AL")
                .build();


        Optional<User> optionalCreator = Optional.of(User.builder().id(1L).build());
        when(userRepositoryMock.findById(1L))
                .thenReturn(optionalCreator);

        Optional<Cart> optionalCart = Optional.of(Cart.builder().id(1L).productList(List.of()).build());

        when(cartRepositoryMock.getCartByCreatorId(optionalCreator.get().getId()))
                .thenReturn(optionalCart);



        Order createdOrder = Order.builder()
                .cart(optionalCart.get())
                .creator(optionalCreator.get())
                .street(request.getStreet())
                .houseNumber(request.getHouseNumber())
                .postcode(request.getPostcode())
                .status(OrderStatus.Received)
                .build();

        Order newOrder = Order.builder()
                .id(1L)
                .build();
        when(orderRepositoryMock.save(createdOrder))
                .thenReturn(newOrder);


        //Act
        CreateOrderResponse actualResult = orderByUseCasesImpl.createOrder(request);

        //Assert
        CreateOrderResponse expectedResult = CreateOrderResponse.builder().orderId(1L).build();
        assertEquals("It don't create an order with valid request",
                expectedResult, actualResult);

        verify(userRepositoryMock).findById(1L);
        verify(orderRepositoryMock).save(createdOrder);
    }

    @Test
    void createOrder_CreateWithInvalidCart_ThrowsInvalidCartException() {
        //Arrange
        CreateOrderRequest request = CreateOrderRequest.builder()
                .creatorId(1L)
                .street("TestStraat")
                .houseNumber("50A")
                .postcode("1564 AL")
                .build();


        Optional<User> optionalCreator = Optional.of(User.builder().id(1L).build());
        when(userRepositoryMock.findById(1L))
                .thenReturn(optionalCreator);

        Optional<Cart> emptyOptionalCart = Optional.empty();

        Order createdOrder = Order.builder()
                .creator(optionalCreator.get())
                .street(request.getStreet())
                .houseNumber(request.getHouseNumber())
                .postcode(request.getPostcode())
                .status(OrderStatus.Received)
                .build();

        Order newOrder = Order.builder()
                .id(1L)
                .build();

        //Assert
        Exception exception = assertThrows(InvalidCartException.class, () -> {
            //Act
            CreateOrderResponse actualResult = orderByUseCasesImpl.createOrder(request);
        }, "It don't throw an exception when the cart is in valid");

        verify(userRepositoryMock).findById(1L);
    }
    /*
    @Test
    void createOrder_CreateWithInvalidAddress_ThrowsInvalidAddressException() {
        //ToDo: Test the address validation

        //Arrange
        CreateOrderRequest request = CreateOrderRequest.builder()
                .address(Address.builder().street("123").houseNumber("50A").postcode("1564 AL").build())
                .build();

        Order createdOrder = Order.builder()
                .address(request.getAddress())
                .build();


        //Assert
        Exception exception = assertThrows(InvalidAddressException.class, () -> {
            //Act
            orderByUseCasesImpl.createOrder(request);
        }, "It don't throw an exception when the address is in valid");
         
    }
*/
    @Test
    void createOrder_CreateWithInvalidCreator_ThrowsInvalidUserException() {
        //Arrange
        CreateOrderRequest request = CreateOrderRequest.builder()
                .creatorId(1L)
                .build();

        when(userRepositoryMock.findById(1L))
                .thenReturn(Optional.empty());

        //Assert
        Exception exception = assertThrows(InvalidUserException.class, () -> {
            //Act
            orderByUseCasesImpl.createOrder(request);
        }, "It don't throw an exception when the creator is in valid");

        verify(userRepositoryMock).findById(1L);
    }

}