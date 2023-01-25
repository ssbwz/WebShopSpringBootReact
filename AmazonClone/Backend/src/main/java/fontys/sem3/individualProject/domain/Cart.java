package fontys.sem3.individualProject.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "cart")
public class Cart {

    @Transient
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CartStatus status;


    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<CartProduct> productList = new ArrayList<>();

    @Transient
    double total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public double getTotal() {
        double total = 0;
        for (CartProduct cartProduct : productList) {
            total += cartProduct.getPrice();
        }
        return Double.valueOf(decimalFormat.format(total));
    }
}
