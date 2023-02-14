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
import java.util.Objects;

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
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tea> teas;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private List<OrderDetails> details;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
    @NotNull
    private LocalDateTime addTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && totalPrice.equals(order.totalPrice) && teas.equals(order.teas) && details.equals(order.details) && user.equals(order.user) && addTime.equals(order.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, teas, details, user, addTime);
    }

    public void dismissUser() {
        this.user = null;
    }

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
