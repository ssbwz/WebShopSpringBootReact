package fontys.sem3.individualProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "price")
    double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    Set<CartProduct> productList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
