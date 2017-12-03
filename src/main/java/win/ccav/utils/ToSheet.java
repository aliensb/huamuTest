package win.ccav.utils;

import java.io.FileOutputStream;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import win.ccav.model.ExcelExportStatisticStyler;
import win.ccav.model.SheetModel;

public class ToSheet {
	 public static void writeToSheel(List res,String filePath,String title) throws Exception{
	        ExportParams params = new ExportParams(title, "表格1", ExcelType.HSSF);
	        //todo 改样式
	        params.setStyle(ExcelExportStatisticStyler.class);
	        Workbook workbook = ExcelExportUtil.exportExcel(params,SheetModel.class, res);
//	        Sheet sheet=workbook.getSheetAt(0);
//	        int i=2;
//	        String cellValue;
//	        String tmp=sheet.getRow(i).getCell(1).getStringCellValue();
//	        int tmpIndex=i;
//	        //while(!(cellValue=(sheet.getRow(i).getCell(1).getStringCellValue())).equals("")){
//	        while(sheet.getRow(i).getCell(1)!=null){
//	        	cellValue=(sheet.getRow(i).getCell(1).getStringCellValue());
//				if(tmp.equals(cellValue)){
//					
//				}
//				else{
//					System.out.println(1+"::"+tmpIndex+1+"::1::"+i);
//					tmpIndex=i;
//					tmp=cellValue;
//				}
//				i++;
//	        }
	        FileOutputStream fos = new FileOutputStream(filePath);
	        workbook.write(fos);
	        fos.close();
	    }
}
