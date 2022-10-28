package me.zuif.teashop.model.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private BigDecimal totalPrice;
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Tea> teas;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private List<OrderDetails> details;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
    @NotNull
    private LocalDateTime addTime;

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", totalPrice=" + totalPrice +
                ", teasCount=" + details +
                ", user=" + user +
                ", addTime=" + addTime +
                '}';
    }
}
