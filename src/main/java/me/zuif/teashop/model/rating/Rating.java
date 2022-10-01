package me.zuif.teashop.model.rating;

import lombok.Data;
import me.zuif.teashop.model.tea.Tea;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private int total;
    @NotBlank
    private int count;
    @OneToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<Tea> teas;

    private String comment;

}
