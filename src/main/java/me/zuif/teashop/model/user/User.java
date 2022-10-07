package me.zuif.teashop.model.user;

import lombok.Data;
import me.zuif.teashop.model.rating.Rating;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private int age;
    @NotBlank
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Rating> ratings;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private BigDecimal balance;
}
