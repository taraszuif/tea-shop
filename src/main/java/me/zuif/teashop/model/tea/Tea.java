package me.zuif.teashop.model.tea;

import lombok.Data;
import me.zuif.teashop.model.rating.Rating;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Tea {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String manufacturer;
    @NotNull
    private TeaType teaType;
    @NotBlank
    private String name;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int count;
    @NotNull
    private LocalDateTime addTime;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Rating> ratings;
}
