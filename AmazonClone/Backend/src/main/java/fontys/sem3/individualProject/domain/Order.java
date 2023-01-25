package fontys.sem3.individualProject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @NotNull
    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @NotNull
    @Column(name = "address_street")
    private String street;
    @NotNull
    @Column(name = "address_house_number")
    private String houseNumber;
    @NotNull
    @Column(name = "address_postcode")
    private String postcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
}
