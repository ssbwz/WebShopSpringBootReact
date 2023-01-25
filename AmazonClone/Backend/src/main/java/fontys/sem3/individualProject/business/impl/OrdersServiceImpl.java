package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.OrdersService;
import fontys.sem3.individualProject.business.exception.InvalidAddressException;
import fontys.sem3.individualProject.business.exception.InvalidCartException;
import fontys.sem3.individualProject.business.exception.InvalidUserException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.CartRepository;
import fontys.sem3.individualProject.persistence.OrderRepository;
import fontys.sem3.individualProject.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    UserRepository userRepository;
    CartRepository cartRepository;
    OrderRepository orderRepository;

    @Override
    public Optional<Order> getOrderById(long orderId) {
        Optional<Order> optionalOrder =  orderRepository.findById(orderId);
        return optionalOrder;
    }

    @Override
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) throws InvalidAddressException {
        addressIsValid(request.getStreet(), request.getHouseNumber(), request.getPostcode());
        Order newOrder = saveNewOrder(request);
        return CreateOrderResponse.builder().orderId(newOrder.getId()).build();
    }

    @Override
    public List<OrderDetails> getOrders() {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for(Order order : orderRepository.findAllByOrderByStatusAsc()){
            orderDetails.add(
                    OrderDetails.builder()
                            .id(order.getId())
                            .creatorEmail(order.getCart().getCreator().getEmail())
                            .orderStatus(order.getStatus())
                            .build()
            );
        }
        return orderDetails;
    }

    @Override
    public void updateOrderStatus(ChangeOrderStatusRequest request) {
        Optional<Order> orderOptional = orderRepository.findById(request.getId());
        if(orderOptional.isPresent()){
        Order order = orderOptional.get();
        order.setStatus(request.getStatus());
        orderRepository.save(order);
        }
    }

    @Override
    public void deleteOrderById(long id) {
         orderRepository.deleteById(id);
    }

    private Order saveNewOrder(CreateOrderRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getCreatorId());
        if (optionalUser.isEmpty()) {
            throw new InvalidUserException();
        }
        User creator = optionalUser.get();

        Optional<Cart> optionalCart = cartRepository.getCartByCreatorId(request.getCreatorId());
        if (optionalCart.isEmpty()) {
            throw new InvalidCartException();
        }
        Cart cart = optionalCart.get();

        //Changing the cart status to closed because the order is created
        cart.setStatus(CartStatus.CLOSED);

        Order newOrder = Order.builder()
                .creator(creator)
                .status(OrderStatus.Received)
                .street(request.getStreet())
                .houseNumber(request.getHouseNumber())
                .postcode(request.getPostcode())
                .cart(cart)
                .build();

        return orderRepository.save(newOrder);
    }

    private void addressIsValid(String street, String houseNumber, String postcode) {
        //ToDo: work in the regular expressions
        /*
        Pattern pattern = Pattern.compile("/^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$", Pattern.CASE_INSENSITIVE);
        if(!pattern.matcher(address.getPostcode()).find()){
            throw  new InvalidAddressException("Postcode address is invalid");
        }
        if(!pattern.matches("^[1-9]\\d*(?:[ -]?(?:[a-zA-Z]+|[1-9]\\d*))?$", address.getHouseNumber())){
            throw  new InvalidAddressException("House number address is invalid");
        }
         */
    }
}
