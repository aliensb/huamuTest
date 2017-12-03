package win.ccav.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/30.
 */
@Table
@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Integer orderId;
    @Column
    private String title;
    @Column
    private Timestamp orderDate;
    @Column
    private String userName;

    public Orders() {
    }

    public Orders(String title, Timestamp orderDate, Set<OrderDetails> items) {
        this.title = title;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Orders(String title, Timestamp orderDate, String userName, Set<OrderDetails> items) {
        this.title = title;
        this.orderDate = orderDate;
        this.userName = userName;
        this.items = items;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "orderid")
    private Set<OrderDetails> items = new HashSet<OrderDetails>();

    public Set<OrderDetails> getItems() {
        return items;
    }

    public void setItems(Set<OrderDetails> items) {
        this.items = items;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", title='" + title + '\'' +
                ", orderDate=" + orderDate +
                ", userName='" + userName + '\'' +
                ", items=" + items +
                '}';
    }
}
