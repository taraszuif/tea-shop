package me.zuif.teashop.model;

import lombok.Data;
import me.zuif.teashop.model.tea.Tea;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private int rate;


    @NotBlank
    private String title;
    @NotBlank
    private String comment;
    @NotNull
    private LocalDateTime addTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tea_id")
    private Tea tea;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tea getTea() {
        return tea;
    }

    public void setTea(Tea tea) {
        this.tea = tea;
    }
}