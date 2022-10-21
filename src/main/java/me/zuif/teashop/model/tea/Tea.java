package me.zuif.teashop.model.tea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.zuif.teashop.model.Rating;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tea")
    private List<Rating> ratings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tea tea = (Tea) o;
        return count == tea.count &&
                Objects.equals(id, tea.id) &&
                Objects.equals(manufacturer, tea.manufacturer) &&
                teaType == tea.teaType && Objects.equals(name, tea.name) &&
                Objects.equals(imageUrl, tea.imageUrl) &&
                Objects.equals(description, tea.description) &&
                Objects.equals(price, tea.price) &&
                Objects.equals(addTime, tea.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturer, teaType, name, imageUrl, description, price, count, addTime);
    }
}
