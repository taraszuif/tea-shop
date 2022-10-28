package me.zuif.teashop.model.user;

import lombok.Data;
import me.zuif.teashop.model.Rating;
import me.zuif.teashop.model.order.Order;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "Users")
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String password;
    @NotNull
    private int age;
    @NotNull
    private String city;
    @NotNull
    private Role role;
    @NotNull
    private LocalDateTime addTime;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String imageUrl;
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rating> ratings;
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Order> orders;
}
