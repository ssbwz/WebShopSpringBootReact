package fontys.sem3.individualProject.business.impl;


import fontys.sem3.individualProject.business.ProductsService;
import fontys.sem3.individualProject.business.exception.InvalidProductException;
import fontys.sem3.individualProject.business.exception.ProductIsInOrderException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.CartRepository;
import fontys.sem3.individualProject.persistence.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductsService {

    ProductRepository productRepository;
    CartRepository cartRepository;

    @Override
    public Optional<Product> getProductById(long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) throws InvalidProductException {
        if (createProductRequest.getName().isBlank() ||
                createProductRequest.getDescription().isBlank() ||
                createProductRequest.getPrice() == 0) {
            throw new InvalidProductException("CreateProductRequest should be valid");
        }
        return CreateProductResponse.builder().productId(saveNewProduct(createProductRequest).getId()).build();
    }

    private Product saveNewProduct(CreateProductRequest createProductRequest) {

        Product newProduct = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .category(Category.valueOf(createProductRequest.getCategory().toString()))
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> getProductsByFilter(Category category) {
        if (category == null) {
            return productRepository.findAll();
        } else {
            return productRepository.findAll().stream().filter(productEntity -> productEntity.getCategory() == category).toList();
        }
    }

    @Override
    public void deleteProduct(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new InvalidProductException();
        }
        if(cartRepository.IsProductInOrder(productId)){
            throw new ProductIsInOrderException("You can't delete this product because the product is already in a cart");
        }
        productRepository.deleteById(productId);
    }

}
