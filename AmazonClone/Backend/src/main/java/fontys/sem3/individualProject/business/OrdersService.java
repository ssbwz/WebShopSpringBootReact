package fontys.sem3.individualProject.business;

import fontys.sem3.individualProject.domain.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrdersService {
    Optional<Order> getOrderById(long orderId);
    CreateOrderResponse createOrder(CreateOrderRequest newOrder);
    List<OrderDetails> getOrders();
    void updateOrderStatus(ChangeOrderStatusRequest request);

    void deleteOrderById(long id);
}
