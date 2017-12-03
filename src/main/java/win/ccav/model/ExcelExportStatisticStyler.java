package win.ccav.model;
//
//
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerBorderImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Created by paul on 2017/4/1.
 */

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 自定义styler 的例子
 * @author JueYue
 *   2015年3月29日 下午9:04:41
 */
public class ExcelExportStatisticStyler extends ExcelExportStylerBorderImpl {

    private CellStyle numberCellStyle;

    public ExcelExportStatisticStyler(Workbook workbook) {
        super(workbook);
        createNumberCellStyler();
    }

    private void createNumberCellStyler() {
        numberCellStyle = workbook.createCellStyle();

        
        //numberCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        numberCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        numberCellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("0.00"));
        //numberCellStyle.setWrapText(true);
        /*
         * 字体
         * 
         * */
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.RED.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        numberCellStyle.setFont(font);
        /*边框*/
        numberCellStyle.setBorderBottom(BorderStyle.MEDIUM);

        numberCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        numberCellStyle.setBorderRight(BorderStyle.MEDIUM);
        numberCellStyle.setBorderTop(BorderStyle.MEDIUM);
        numberCellStyle.setBorderBottom((short) 5);
        //居中
        numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    }

    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
        if (entity != null
                && (entity.getName().contains("int") || entity.getName().contains("double"))) {
            return numberCellStyle;
        }
//        CellStyle style=workbook.createCellStyle();
//        style.setBorderBottom(BorderStyle.MEDIUM);
//        style.setBorderLeft(BorderStyle.MEDIUM);
//        style.setBorderRight(BorderStyle.MEDIUM);
//        style.setBorderTop(BorderStyle.MEDIUM);
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        style.setWrapText(true);

        //return getCellStyle(workbook);
        return super.getStyles(noneStyler, entity);
    }
    private static CellStyle getCellStyle(Workbook workbook ){
        CellStyle style=workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        return style;
    }

}


/***********************************************************************************/

//import org.apache.poi.hssf.usermodel.HSSFDataFormat;
//import org.apache.poi.ss.usermodel.BuiltinFormats;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
//import org.jeecgframework.poi.excel.export.styler.ExcelExportStylerDefaultImpl;
//
///**
// * Excel 自定义styler 的例子
// * @author JueYue
// *   2015年3月29日 下午9:04:41
// */
//public class ExcelExportStatisticStyler extends ExcelExportStylerDefaultImpl {
//
//    private CellStyle numberCellStyle;
//
//    public ExcelExportStatisticStyler(Workbook workbook) {
//        super(workbook);
//        createNumberCellStyler();
//    }
//
//    private void createNumberCellStyler() {
//        numberCellStyle = workbook.createCellStyle();
//        numberCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        numberCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        numberCellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("0.00"));
//        numberCellStyle.setWrapText(true);
//    }
//
//    @Override
//    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
//        if (entity != null
//            && (entity.getName().contains("int") || entity.getName().contains("double"))) {
//            return numberCellStyle;
//        }
//        return super.getStyles(noneStyler, entity);
//    }
//
//}
