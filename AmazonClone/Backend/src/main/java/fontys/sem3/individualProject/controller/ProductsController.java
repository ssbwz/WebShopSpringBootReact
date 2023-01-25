package fontys.sem3.individualProject.controller;

import fontys.sem3.individualProject.business.ProductsService;
import fontys.sem3.individualProject.business.exception.InvalidProductException;
import fontys.sem3.individualProject.configuration.security.isauthenticated.IsAuthenticated;
import fontys.sem3.individualProject.domain.Category;
import fontys.sem3.individualProject.domain.CreateProductRequest;
import fontys.sem3.individualProject.domain.CreateProductResponse;
import fontys.sem3.individualProject.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ProductsController {

    private final ProductsService productByUseCases;

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") final long id) {
        final Optional<Product> productOptional = productByUseCases.getProductById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productOptional.get());
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "category", required = false) final Category category) {
        final List<Product> products = productByUseCases.getProductsByFilter(category);
        return ResponseEntity.ok(products);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_OWNER"})
    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) throws InvalidProductException {
        var response = productByUseCases.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_OWNER"})
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") final long id) {
        productByUseCases.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
