package win.ccav.model;


import javax.persistence.*;

/**
 * Created by Administrator on 2017/11/30.
 */
@Table
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue
    private Integer detailId;
    @Column
    private Integer orderId;
    @Column
    private String add1;
    @Column
    private String add2;
    @Column
    private Integer num;
    @Column
    private String size;
    @Column
    private String hname;
    @Column
    private String pic;
    @Column
    private Integer amount;
    @Column
    private Float unitPrice;
    @Column
    private Float chengBen;
    @Column
    private String beizu;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getChengBen() {
        return chengBen;
    }

    public void setChengBen(Float chengBen) {
        this.chengBen = chengBen;
    }

    public String getBeizu() {
        return beizu;
    }

    public void setBeizu(String beizu) {
        this.beizu = beizu;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "detailId=" + detailId +
                ", orderId=" + orderId +
                ", add1='" + add1 + '\'' +
                ", add2='" + add2 + '\'' +
                ", num=" + num +
                ", size='" + size + '\'' +
                ", hname='" + hname + '\'' +
                ", pic='" + pic + '\'' +
                ", amount=" + amount +
                ", unitPrice=" + unitPrice +
                ", chengBen=" + chengBen +
                ", beizu='" + beizu + '\'' +
                '}';
    }
}
