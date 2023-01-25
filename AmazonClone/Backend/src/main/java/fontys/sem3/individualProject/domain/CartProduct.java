package fontys.sem3.individualProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Objects;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "cart_product")
public class CartProduct {

    @Transient
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product  product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    Cart cart;

    @Transient
    double price;

    @Column(name = "quantity")
    int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public double getPrice() {
        return Double.valueOf(decimalFormat.format(product.price * quantity));
    }
}