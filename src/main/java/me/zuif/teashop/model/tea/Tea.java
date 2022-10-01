package me.zuif.teashop.model.tea;

import lombok.Data;
import me.zuif.teashop.model.rating.Rating;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Tea {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String manufacturer;
    @NotBlank
    private TeaType type;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private int count;
    @NotBlank
    private LocalDateTime addTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rating_id")
    private Rating rating;
}
