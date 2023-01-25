package fontys.sem3.individualProject.persistence;


import fontys.sem3.individualProject.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
