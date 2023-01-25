package fontys.sem3.individualProject.controller;


import fontys.sem3.individualProject.business.impl.AccessTokenEncoderDecoderImpl;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class CartsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //I stub the repo in the service layer
    @MockBean
    private CartRepository cartRepositoryMock;

    //For the authorization part, I need to create a token bc my controller uses the token info after decoding it.
    @Autowired
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;
    /*
    @Test
    void getCartByCreatorId_shouldReturn200ResponseWithOwnerCart() throws Exception {

        User creator = User.builder()
                .id(3l)
                .email("C@d.n")
                .firstname("Jessie")
                .lastname("Van Nuenen")
                .userType(UserType.Customer)
                .build();

        String accessToken = accessTokenEncoderDecoder.encode(AccessToken.builder()
                .subject(creator.getEmail())
                .userId(creator.getId())
                .userType(creator.getUserType().toString())
                .build());

        List<CartProduct> productList = List.of(
                CartProduct.builder()
                        .id(55L)
                        .product(
                                Product.builder()
                                        .id(1)
                                        .name("Winterjas")
                                        .description("warm")
                                        .price(50.5)
                                        .category(Category.Mens)
                                        .build()
                        )
                        .quantity(1)
                        .build()
        );

        Cart response = Cart.builder()
                .id(70L)
                .creator(creator)
                .productList(productList)
                .status(CartStatus.OPENED)
                .build();

        when(cartRepositoryMock.getCartByCreatorId(creator.getId()))
                .thenReturn(Optional.of(response));


        mockMvc.perform(get("/carts/creator")
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                        .andExpect(content().json("""
                        "id": 70,
                           "creator": {
                               "id": ,
                               "email": "C@d.n",
                               "firstname": "Jessie",
                               "lastname": "Van Nuenen",
                               "userType": "Customer"
                           },
                           "status": "OPENED",
                           "productList": [
                               {
                                   "id": 55,
                                   "product": {
                                       "id": 1,
                                       "name": "Winterjas",
                                       "description": "warm",
                                       "price": 50.5,
                                       "category": "Mens"
                                   },
                                   "price": 50.5,
                                   "quantity": 1
                               }
                           ],
                           "total": 50.5
                        """));

        verify(cartRepositoryMock).getCartByCreatorId(creator.getId());
    }
/*


json("""
                        "id": 70,
                           "creator": {
                               "id": 3,
                               "email": "C@d.n",
                               "firstname": "Jessie",
                               "lastname": "Van Nuenen",
                               "userType": "Customer"
                           },
                           "status": "OPENED",
                           "productList": [
                               {
                                   "id": 55,
                                   "product": {
                                       "id": 1,
                                       "name": "Winterjas",
                                       "description": "warm",
                                       "price": 50.5,
                                       "category": "Mens"
                                   },
                                   "price": 50.5,
                                   "quantity": 1
                               }
                           ],
                           "total": 50.5
                        """)












    @Test

    void getStudent_shouldReturn200WithStudent_whenStudentFound() throws Exception {
        Student student = Student.builder().country(getBrazil()).name("Rivaldo Vítor Borba Ferreira").pcn(222L).id(10L).build();
        when(getStudentUseCase.getStudent(10L)).thenReturn(Optional.of(student));
        mockMvc.perform(get("/students/10")).andDo(print()).andExpect(status().isOk()).andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE)).andExpect(content().json(""" {"id":10,"pcn":222,"name":"Rivaldo Vítor Borba Ferreira","country":{"id":1,"code":"BR","name":"Brazil"}} """));
        verify(getStudentUseCase).getStudent(10L);
    }*/
}