package fontys.sem3.individualProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "firstname")
    private String firstname;

    @NotBlank
    @Column(name = "lastname")
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

}
