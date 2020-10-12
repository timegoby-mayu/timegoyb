package com.fly.my.Util.execl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author wuwenze
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class POIUtil {

    private static final int mDefaultRowAccessWindowSize = 100;
    public static final String XLSX_SUFFIX = ".xlsx";
    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String XLSX_HEADER_KEY = "Content-disposition";
    public static final String XLSX_HEADER_VALUE_TEMPLATE = "attachment; filename=%s";
    /**
     * 导入数据错误信息Excel文件
     */
    private final static String Import_Error_Excel_FILE = "importError.xls";

    private static SXSSFWorkbook newSXSSFWorkbook(int rowAccessWindowSize) {
        return new SXSSFWorkbook(rowAccessWindowSize);
    }

    public static SXSSFWorkbook newSXSSFWorkbook() {
        return POIUtil.newSXSSFWorkbook(POIUtil.mDefaultRowAccessWindowSize);
    }

    public static SXSSFSheet newSXSSFSheet(SXSSFWorkbook wb, String sheetName) {
        return wb.createSheet(sheetName);
    }

    public static SXSSFRow newSXSSFRow(SXSSFSheet sheet, int index) {
        return sheet.createRow(index);
    }

    public static SXSSFCell newSXSSFCell(SXSSFRow row, int index) {
        return row.createCell(index);
    }

    public static void setColumnWidth(
            SXSSFSheet sheet, int index, Short width, String value) {
        boolean widthNotHaveConfig = (null == width || width == -1);
        if (widthNotHaveConfig && !StringUtils.isEmpty(value)) {
            sheet.setColumnWidth(index, (short) (value.length() * 2048));
        } else {
            width = widthNotHaveConfig ? 200 : width;
            sheet.setColumnWidth(index, (short) (width * 35.7));
        }
    }

    public static void write(SXSSFWorkbook wb, OutputStream out) {
        try {
            if (null != out) {
                wb.write(out);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void download(
            SXSSFWorkbook wb, HttpServletResponse response, String filename) {
        try {
            OutputStream out = response.getOutputStream();
            response.setContentType(XLSX_CONTENT_TYPE);
            response.setHeader(XLSX_HEADER_KEY,
                    String.format(XLSX_HEADER_VALUE_TEMPLATE, filename));
            POIUtil.write(wb, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object convertByExp(Object propertyValue, String converterExp)
            throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    public static int countNullCell(String ref, String ref2) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = ref2.replaceAll("\\d+", "");

        xfd = POIUtil.fillChar(xfd, 3, '@', true);
        xfd_1 = POIUtil.fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res =
                (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2]
                        - letter_1[2]);
        return res - 1;
    }

    private static String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }

    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellTypeEnum()){
        case NUMERIC: //数字
            cellValue = stringDateProcess(cell);
            break;
        case STRING: //字符串
            cellValue = String.valueOf(cell.getStringCellValue());
            break;
        case BOOLEAN: //Boolean
            cellValue = String.valueOf(cell.getBooleanCellValue());
            break;
        case FORMULA: //公式
            cellValue = String.valueOf(cell.getCellFormula());
            break;
        case BLANK: //空值
            cellValue = "";
            break;
        case ERROR: //故障
            cellValue = "非法字符";
            break;
        default:
            cellValue = "未知类型";
            break;
        }

        return cellValue;
    }
    
    /**
     * 时间格式处理
     */
    public static String stringDateProcess(Cell cell){

        String result = new String();  
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
            SimpleDateFormat sdf = null;  
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
                sdf = new SimpleDateFormat("HH:mm");  
            } else {// 日期  
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            }  
            Date date = cell.getDateCellValue();  
            result = sdf.format(date);  
        } else if (cell.getCellStyle().getDataFormat() == 58) {  
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            double value = cell.getNumericCellValue();  
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
            result = sdf.format(date);  
        } else {  
            double value = cell.getNumericCellValue();  
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();  
            String temp = style.getDataFormatString();  
            // 单元格设置成常规  
            if (temp.equals("General")) {  
                format.applyPattern("#");  
            }  
            result = format.format(value);  
        }  

        return result;
    }

    public static CellStyle getHeaderCellStyle(SXSSFWorkbook wb) {
        CellStyle mHeaderCellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        mHeaderCellStyle.setFillForegroundColor((short) 12);
        mHeaderCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        mHeaderCellStyle.setBorderTop(BorderStyle.DOTTED);
        mHeaderCellStyle.setBorderRight(BorderStyle.DOTTED);
        mHeaderCellStyle.setBorderBottom(BorderStyle.DOTTED);
        mHeaderCellStyle.setBorderLeft(BorderStyle.DOTTED);
        mHeaderCellStyle.setAlignment(HorizontalAlignment.LEFT);// 对齐
        mHeaderCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        mHeaderCellStyle.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        font.setColor(IndexedColors.WHITE.getIndex());
        // 应用标题字体到标题样式
        mHeaderCellStyle.setFont(font);
        //设置单元格文本形式
        DataFormat dataFormat = wb.createDataFormat();
        mHeaderCellStyle.setDataFormat(dataFormat.getFormat("@"));
        return mHeaderCellStyle;
    }
    
    /**
     * 过滤特殊字符
     * @author 陈强
     * @param 
     * @return String
     * @throws Exception
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
    
    /**
     * 生成导入错误信息文件
     * @author 陈强
     * @param errorMessageList 错误信息
     * @return File
     * @throws Exception
     */
    /*public static File upload(List<SystemMessage> errorMessageList) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("导入错误信息");
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle  style = wb.createCellStyle();
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("错误信息");
        cell.setCellStyle(style);
        for (int i = 0; i < errorMessageList.size(); i++) {
            SystemMessage message = errorMessageList.get(i);
            //创建行  
            row = sheet.createRow(i+1);
            //创建单元格并且添加数据 
            row.createCell(0).setCellValue(message.getKey());
            row.createCell(1).setCellValue(message.getMessage());
        }
        FileOutputStream fout = null;
        File file = null;
        try {
            fout = new FileOutputStream(Import_Error_Excel_FILE);  
            wb.write(fout);
            file = new File(Import_Error_Excel_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fout) {
                try {
                    fout.close(); 
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            if (null != wb) {
                try {
                    //关闭poi流
                    wb.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
        }
        
        return file;
    }*/
    
}
