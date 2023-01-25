package fontys.sem3.individualProject.business;

import fontys.sem3.individualProject.business.exception.InvalidProductException;
import fontys.sem3.individualProject.domain.*;

import java.util.List;
import java.util.Optional;

public interface ProductsService {

    Optional<Product> getProductById(long productId);

    CreateProductResponse createProduct(CreateProductRequest createProductRequest) throws InvalidProductException;

    List<Product> getProductsByFilter(Category category);

    void deleteProduct(long productId);

}
