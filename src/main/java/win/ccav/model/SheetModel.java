package win.ccav.model;
import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;


/**
 * Created by paul on 2017/4/1.
 */
public class SheetModel implements Comparable {
	


public SheetModel(int num, String add1, String add2, String size, String name, int amount, double unitPrice,
			String remark, double cBUnitPrice) {
		super();
		this.num = num;
		this.add1 = add1;
		this.add2 = add2;
		this.size = size;
		this.name = name;
		this.amount = amount;
		this.unitPrice = unitPrice;
		this.remark = remark;
		CBUnitPrice = cBUnitPrice;
	}

public SheetModel() {
		super();
	}

//    @Excel(name = "位置一",needMerge=true)
	@Excel(name = "序号",width = 4)
    private int num;
    @Excel(name = "位置一",width = 4,mergeVertical=true)
    private String add1;
    @Excel(name = "位置二",width = 4)
    private String add2;
    @Excel(name = "型号",width = 4)
    private String size;
    @Excel(name = "植物名称",width = 4)
    private String name;
    @Excel(name = "植物图片", imageType = 1, type = 2, width = 25, height = 60)
    //@Excel(name = "植物图片", imageType = 1, type = 2, width = 15, height = 40)
    private String pic;
    @Excel(name = "数量",width = 3)
    private int amount;
    @Excel(name = "单价（元/月）",width = 6)
    private double unitPrice;
    @Excel(name = "总价", isStatistics = true,width = 6)
    private double totalPrice;
    @Excel(name = "备注",width = 6)
    private String remark;
    @Excel(name = "成本单价",width = 6)
    private double CBUnitPrice;
    @Excel(name = "成本总价", isStatistics = true,width = 6)
    private double CBTotalPrice;

	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
    	SheetModel mo=(SheetModel)arg0;
		return add1.compareTo(mo.add1);
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getUnitPrice() {
    	BigDecimal   b   =   new   BigDecimal(unitPrice);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getCBUnitPrice() {
        return CBUnitPrice;
    }

    public void setCBUnitPrice(double CBUnitPrice) {
        this.CBUnitPrice = CBUnitPrice;
    }

    public double getCBTotalPrice() {
        return CBTotalPrice;
    }

    public void setCBTotalPrice(double CBTotalPrice) {
        this.CBTotalPrice = CBTotalPrice;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.num+""+this.add1+""+this.add2+""+this.size+""+this.name+"";
	}
    
}
