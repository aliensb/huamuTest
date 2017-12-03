package win.ccav.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/11/30.
 */
@Table(name="hua")
@Entity()
public class Hua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String type;
    @Column
    private String hname;
    @Column
    private Float unitPrice;
    @Column
    private Float chengBen;
    @Column
    private String pic;
    @Column
    private Integer special;
    @Column
    private Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof Hua){
            Hua hua=(Hua)obj;
            if(hua.getId().equals(this.getId())&&hua.getHname().equals(this.getHname())){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

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

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
       return hname;
    }
}
