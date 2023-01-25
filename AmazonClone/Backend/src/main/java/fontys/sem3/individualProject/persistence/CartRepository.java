package fontys.sem3.individualProject.persistence;

import fontys.sem3.individualProject.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query(value = "select * from cart c where c.creator_id= :creatorId and c.status = 'OPENED'",
            nativeQuery = true)
    Optional<Cart> getCartByCreatorId(@Param("creatorId") long creatorId);
    @Query(value = "select IF(count(product_id) = 0 ,'false','true') as is_in_order  from cart_product where product_id = :productId",
            nativeQuery = true)
    boolean IsProductInOrder(@Param("productId")long productId);
}
