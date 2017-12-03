package win.ccav.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by john on 2017/12/2.
 */
@Entity
@Table
public class Type {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String typeName;
    @Column
    private Float unitPrice;
    @Column
    private Float chenBen;


    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof Type){
            Type type=(Type)obj;
            if(type.getId().equals(this.id)&&type.getTypeName().equals(this.typeName)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getChenBen() {
        return chenBen;
    }

    public void setChenBen(Float chenBen) {
        this.chenBen = chenBen;
    }
}
