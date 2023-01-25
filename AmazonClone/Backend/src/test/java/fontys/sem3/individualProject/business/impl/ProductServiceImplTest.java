package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.exception.InvalidProductException;
import fontys.sem3.individualProject.business.exception.ProductIsInOrderException;
import fontys.sem3.individualProject.domain.Category;
import fontys.sem3.individualProject.domain.CreateProductRequest;
import fontys.sem3.individualProject.domain.CreateProductResponse;
import fontys.sem3.individualProject.domain.Product;
import fontys.sem3.individualProject.persistence.CartRepository;
import fontys.sem3.individualProject.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private CartRepository cartRepositoryMock;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    void getProductById_ExistsProduct_ReturnProduct(){
        //Arrange
        long productId = 1;
        Optional<Product> productOptional = Optional.of(Product.builder().id(productId).build());

        when(productRepositoryMock.findById(productId))
                .thenReturn(productOptional);

        //Act
        Product actual =productServiceImpl.getProductById(productId).get();
        //Assert
        assertEquals("it does not return the right product",productOptional.get().getId(),actual.getId());
        verify(productRepositoryMock).findById(productId);
    }
    @Test
    void createProduct_CreatingValidProduct_ReturnProductId() throws InvalidProductException {
        //Arrange
        Product inputProduct = Product.builder()
                .name("Apple Watch Series 8 (GPS) 45‐mm kast van middernacht aluminium Middernacht sportbandje - Standaardmaat")
                .description("Nice watch for runners")
                .price(500)
                .category(Category.Unisex).build();

        Product outputProduct = Product.builder()
                .name("Apple Watch Series 8 (GPS) 45‐mm kast van middernacht aluminium Middernacht sportbandje - Standaardmaat")
                .id(1L)
                .description("Nice watch for runners")
                .price(500)
                .category(Category.Unisex).build();
        
        when(productRepositoryMock.save(inputProduct))
                .thenReturn(outputProduct);

        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .name("Apple Watch Series 8 (GPS) 45‐mm kast van middernacht aluminium Middernacht sportbandje - Standaardmaat")
                .description("Nice watch for runners")
                .price(500)
                .category(Category.Unisex)
                .build();
        //Act
        CreateProductResponse actualResult = productServiceImpl.createProduct(createProductRequest);
        //Assert
        CreateProductResponse expectedResult = CreateProductResponse.builder().productId(outputProduct.getId()).build();
        assertEquals("The method getProductById_CreatingValidProduct_ReturnTheNewProductId doesn't return the new product Id",
                expectedResult, actualResult);
    }


    @Test
    void createProduct_CreatingInvalidProductByHavingBlankInDescriptionField_ThrowsExceptionProductInvalid() {
        //Arrange
        CreateProductRequest createProductRequest = CreateProductRequest.builder().name("Apple Watch Series 8 (GPS) 45‐mm kast van middernacht aluminium Middernacht sportbandje - Standaardmaat")
                .description(" ")
                .price(500)
                .category(Category.Unisex).build();

        //Assert
        Exception exception = assertThrows(InvalidProductException.class, () -> {
            //Act
            productServiceImpl.createProduct(createProductRequest);
        });
    }

    @Test
    void getProductsByFilter_GetMensProducts_GetAllTheMensProducts() {
        List<Product> products = new ArrayList<>();

        products.add(Product.builder().id(1L).name("Nike trouser").description("Sport trouser for runners").price(15.5).category(Category.Mens).build());
        products.add(Product.builder().id(2L).name("Apple Watch Series 8 (GPS) 45‐mm kast van middernacht aluminium Middernacht sportbandje - Standaardmaat")
                .description("About this item\n" +
                        "Temperature sensor provides retrospective ovulation estimates and advanced cycle tracking features\n" +
                        "Measure your blood oxygen with a powerful sensor and app\n" +
                        "Take an ECG anytime, anywhere\n" +
                        "Get high and low heart rate, and irregular rhythm notifications\n" +
                        "Advanced safety features, including Fall Detection, Emergency SOS, and Crash Detection\n" +
                        "Enhanced Workout app with more advanced metrics and ways to train\n" +
                        "A completely redesigned Compass app with waypoints and Backtrack\n" +
                        "Track your daily activity on Apple Watch, and see your trends in the Fitness app on iPhone\n" +
                        "The most crack-resistant front crystal yet on an Apple Watch, IP6X dust resistance and swimproof design, and increased durability for fitness and activity\n" +
                        "Call, text, and email with just a few taps").price(539.00).category(Category.Unisex).build());
        products.add(Product.builder().
                id(3L).
                name("Frigid wind pearl round card bracelet stainless steel Roman portrait stitching bracelet for women").
                description("All prices include VAT.\n" +
                        "[OCCASION] Our bangle bracelets for women can be dressed up or down for any occasion, from everyday casual, home, school and holiday to a more formal look for prom, wedding party, office, work or business event.\n" +
                        "[Designed to last] bracelet will maintain its brilliance over time when simple care practices are observed; remove before contact with water, lotions or perfumes to extend your bracelet's life.\n" +
                        "[A meaningful gift] This bracelet is an expression of eternal love, making it an excellent gift for an important woman in your life; an essential addition to every bracelet collection\n" +
                        "[Unique Gifts For Women] Jewelry makes a great gift for Mother's Day, Anniversary, Wedding, Birthday, Holiday, Stocking Stuffer, Christmas, Valentine's Day, Graduation Gift for Sister, Mother, Mom, Grandmother, Daughter, Wife, Girlfriend, Aunt, Mum, Mommy, Grandma, Female, Toe, BFF, Best Friend or Treat Yourself.\n" +
                        "[SATISFACTION GUARANTEED] We offer easy exchange or refund if you are not completely satisfied with your bracelet, just contact our customer service and we will do whatever it takes to make it right.").
                price(26.00).
                category(Category.Females).
                build());

        when(productRepositoryMock.findAll())
                .thenReturn(products);

        List<Product> expectedResult = products.stream().filter(product -> product.getCategory() == Category.Mens).toList();
        //Act
        List<Product> actualResult = productServiceImpl.getProductsByFilter(Category.Mens);
        //Assert
        assertEquals("It doesn't get the mens products",expectedResult,actualResult);
        verify(productRepositoryMock).findAll();
    }

    @Test
    void getProductsByFilter_WithoutFilterSpecify_ReturnAllProducts(){
        //Arrange
        List<Product> products = new ArrayList<>();

        products.add(Product.builder().id(1L).name("Nike trouser").description("Sport trouser for runners").price(15.5).category(Category.Mens).build());
        products.add(Product.builder().id(2L).name("Apple Watch Series 8 (GPS) 45‐mm kast van middernacht aluminium Middernacht sportbandje - Standaardmaat")
                .description("About this item\n" +
                        "Temperature sensor provides retrospective ovulation estimates and advanced cycle tracking features\n" +
                        "Measure your blood oxygen with a powerful sensor and app\n" +
                        "Take an ECG anytime, anywhere\n" +
                        "Get high and low heart rate, and irregular rhythm notifications\n" +
                        "Advanced safety features, including Fall Detection, Emergency SOS, and Crash Detection\n" +
                        "Enhanced Workout app with more advanced metrics and ways to train\n" +
                        "A completely redesigned Compass app with waypoints and Backtrack\n" +
                        "Track your daily activity on Apple Watch, and see your trends in the Fitness app on iPhone\n" +
                        "The most crack-resistant front crystal yet on an Apple Watch, IP6X dust resistance and swimproof design, and increased durability for fitness and activity\n" +
                        "Call, text, and email with just a few taps").price(539.00).category(Category.Unisex).build());
        products.add(Product.builder().
                id(3L).
                name("Frigid wind pearl round card bracelet stainless steel Roman portrait stitching bracelet for women").
                description("All prices include VAT.\n" +
                        "[OCCASION] Our bangle bracelets for women can be dressed up or down for any occasion, from everyday casual, home, school and holiday to a more formal look for prom, wedding party, office, work or business event.\n" +
                        "[Designed to last] bracelet will maintain its brilliance over time when simple care practices are observed; remove before contact with water, lotions or perfumes to extend your bracelet's life.\n" +
                        "[A meaningful gift] This bracelet is an expression of eternal love, making it an excellent gift for an important woman in your life; an essential addition to every bracelet collection\n" +
                        "[Unique Gifts For Women] Jewelry makes a great gift for Mother's Day, Anniversary, Wedding, Birthday, Holiday, Stocking Stuffer, Christmas, Valentine's Day, Graduation Gift for Sister, Mother, Mom, Grandmother, Daughter, Wife, Girlfriend, Aunt, Mum, Mommy, Grandma, Female, Toe, BFF, Best Friend or Treat Yourself.\n" +
                        "[SATISFACTION GUARANTEED] We offer easy exchange or refund if you are not completely satisfied with your bracelet, just contact our customer service and we will do whatever it takes to make it right.").
                price(26.00).
                category(Category.Females).
                build());

        when(productRepositoryMock.findAll())
                .thenReturn(products);

        List<Product> expectedResult = products;
        //Act
        List<Product> actualResult = productServiceImpl.getProductsByFilter(null);
        //Assert
        assertEquals("It doesn't get the all of the products",expectedResult,actualResult);
        verify(productRepositoryMock).findAll();
    }

    @Test
    void deleteProduct_deletingProductDoesExist_returnNothing(){
        //Arrange
        when(productRepositoryMock.existsById(1L))
                .thenReturn(true);

        when(cartRepositoryMock.IsProductInOrder(1L))
                .thenReturn(false);


        //Act
        productServiceImpl.deleteProduct(1L);
        //Assert
        verify(productRepositoryMock).existsById(1L);
    }

    @Test
    void deleteProduct_whenProductIsAlreadyInOrder_throwsProductIsInOrderException(){
        //Arrange
        long productId = 1L;
        when(productRepositoryMock.existsById(productId))
                .thenReturn(true);

        when(cartRepositoryMock.IsProductInOrder(productId))
                .thenReturn(true);


        Exception exception = assertThrows(ProductIsInOrderException.class, () -> {
            //Act
            productServiceImpl.deleteProduct(productId);
        },"It does delete product that is already in order");

        verify(productRepositoryMock).existsById(productId);
        verify(cartRepositoryMock).IsProductInOrder(productId);
    }

    @Test
    void deleteProduct_deletingProductDoesNotExist_ThrowsInvalidProductException(){
        //Arrange
        when(productRepositoryMock.existsById(1L))
                .thenReturn(false);
        //Assert
        Exception exception = assertThrows(InvalidProductException.class, () -> {
            //Act
            productServiceImpl.deleteProduct(1L);
        },"It does delete product that doesn't exist");
        verify(productRepositoryMock).existsById(1L);
    }

}
