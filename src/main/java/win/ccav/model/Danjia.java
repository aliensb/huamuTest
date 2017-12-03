package win.ccav.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/11/30.
 */
@Entity
@Table
public class Danjia {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String type;
    @Column
    private Float unitPrice;
    @Column
    private Float monthlyPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Float monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
}
