package fontys.sem3.individualProject.controller;

import fontys.sem3.individualProject.business.OrdersService;
import fontys.sem3.individualProject.configuration.security.isauthenticated.IsAuthenticated;
import fontys.sem3.individualProject.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class OrdersController {
    private final OrdersService ordersService;
    private AccessToken requestAccessToken;

    @IsAuthenticated
    @RolesAllowed({"ROLE_CUSTOMER"})
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request){
        return  ResponseEntity.ok().body(ordersService.createOrder(request));
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_OWNER"})
    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") final long id){
        final Optional<Order> optionalOrder = ordersService.getOrderById(id);
        if(optionalOrder.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(optionalOrder.get());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_OWNER"})
    @GetMapping
    public ResponseEntity<List<OrderDetails>> getOrders(){
        return ResponseEntity.ok().body(ordersService.getOrders());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_OWNER"})
    @PutMapping
    public ResponseEntity<Void> updateOrderStatus(@RequestBody @Valid ChangeOrderStatusRequest request){
        ordersService.updateOrderStatus(request);
        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_OWNER"})
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") final long id){
        ordersService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
