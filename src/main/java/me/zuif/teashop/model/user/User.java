package me.zuif.teashop.model.user;

import lombok.Data;
import me.zuif.teashop.model.Rating;
import org.hibernate.annotations.GenericGenerator;

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
    private boolean blocked;
    @NotNull
    private Role role;
    @NotNull
    private LocalDateTime addTime;
    @Email
    @NotBlank
    private String email;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Rating> ratings;
}
