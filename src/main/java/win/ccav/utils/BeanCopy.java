package win.ccav.utils;

import win.ccav.model.OrderDetails;
import win.ccav.model.SheetModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/30.
 */
public class BeanCopy {
    public static Set<OrderDetails> fromListSheetModelToSetOrderDtails(List<SheetModel> sheetModels){
        Set<OrderDetails> orderDetailsSet=new HashSet<>();
        for (int i=0;i<sheetModels.size();i++){
            orderDetailsSet.add(fromSheetModelToOrderDetails(sheetModels.get(i)));
        }
        return orderDetailsSet;
    }

    public static OrderDetails fromSheetModelToOrderDetails(SheetModel sheetModel){
        OrderDetails orderDetails=new OrderDetails();
        orderDetails.setAdd1(sheetModel.getAdd1());
        orderDetails.setAdd2(sheetModel.getAdd2());
        orderDetails.setNum(sheetModel.getNum());
        orderDetails.setSize(sheetModel.getSize());
        orderDetails.setHname(sheetModel.getName());
        orderDetails.setPic(sheetModel.getPic());
        orderDetails.setAmount(sheetModel.getAmount());
        orderDetails.setUnitPrice(new Double(sheetModel.getUnitPrice()).floatValue());
        orderDetails.setChengBen(new Double(sheetModel.getCBTotalPrice()).floatValue());
        orderDetails.setBeizu(sheetModel.getRemark());
        return orderDetails;
    }
}
