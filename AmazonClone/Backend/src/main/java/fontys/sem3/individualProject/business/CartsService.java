package fontys.sem3.individualProject.business;

import fontys.sem3.individualProject.domain.AddProductRequest;
import fontys.sem3.individualProject.domain.Cart;
import fontys.sem3.individualProject.domain.DeleteProductRequest;

import java.util.Optional;

public interface CartsService {
    Optional<Cart> getCartByCreatorId(long creatorId);
    void addProduct(AddProductRequest addProductRequest);

    void deleteProduct(DeleteProductRequest deleteProductRequest);
}
