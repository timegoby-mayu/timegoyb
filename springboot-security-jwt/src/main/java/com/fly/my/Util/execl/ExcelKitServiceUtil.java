package com.fly.my.Util.execl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.xmlbeans.impl.validator.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/** 
 * 描述: Excel操作接口实现类
 * @author：马羽
 * @date：2019年4月18日14:22:18
 *
 */
public class ExcelKitServiceUtil {
    
    private final static Logger log = LoggerFactory.getLogger(ExcelKitServiceUtil.class);
    
    private static final Integer XLSX_DEFAULT_BEGIN_READ_ROW_INDEX = 1;
    
    private static final Integer mMaxSheetRecords = 50000;
    
    /**
     * 2007 及2007以上版本的Excel文件后缀
     */
    private static final String EXCEL_SUFFIX_XLSX = "xlsx";
    
    /**
     * 2003版本的Excel文件后缀
     */
    private static final String EXCEL_SUFFIX_XLS = "xls";
    
    /**
     * 上传的文本框field
     */
    private final static String FILE = "file";
    
    private static final String ENCODING = "UTF-8";

    public static List<String> getExcelHeaderRow() throws Exception {
        //生成工作表
        Workbook workbook = null;
        //要返回的数据
        List<String> headers = new ArrayList<String>();
        try {
            workbook = checkFileAndGetWorkbook();
            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);
            DataFormatter dataFormatter = new DataFormatter();
            for (int i = 0; i < header.getLastCellNum(); i++) {
                //获取单元格
                Cell cell = header.getCell(i);
                headers.add(dataFormatter.formatCellValue(cell));
            }
        } catch (Exception e) {
            log.error("获取Excel列头行数据时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG05_ERROR");
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
        return headers;
    }

    public static List<Map<String, Object>> getExcelData() throws Exception {
        //生成工作表
        Workbook workbook = null;
        
        //需要的变量以及要返回的数据
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<String> headers = new ArrayList<String>();
        try {
            workbook = checkFileAndGetWorkbook();
            for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue; 
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //列头行
                Row header = sheet.getRow(0);
                
                DataFormatter dataFormatter = new DataFormatter();
                //获取标题信息
                for (int i = 0; i < header.getLastCellNum(); ++i) {
                    Cell cell = header.getCell(i);
                    headers.add(dataFormatter.formatCellValue(cell));
                }
                //获取内容信息
                for (int i = firstRowNum; i <= lastRowNum; i++) {
                    Row currentRow = sheet.getRow(i);
                    if (Objects.isNull(currentRow)) {
                        continue;
                    }
                    if (currentRow.getRowNum() >= XLSX_DEFAULT_BEGIN_READ_ROW_INDEX) {
                        Map<String, Object> dataMap = new HashMap<>();
                        for (int j = 0; j < currentRow.getLastCellNum(); j++) {
                            if (j >= headers.size()) {
                                continue;
                            }
                            //将null转化为Blank
                            Cell data = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            switch (data.getCellTypeEnum()) {
                            //不同的类型分别进行存储
                            case STRING:
                                dataMap.put(headers.get(j), data.getRichStringCellValue().getString());
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(data)) {
                                    dataMap.put(headers.get(j), data.getDateCellValue());
                                } else {
                                    dataMap.put(headers.get(j), NumberToTextConverter.toText(data.getNumericCellValue()));
                                }
                                break;
                            case FORMULA:
                                dataMap.put(headers.get(j), data.getCellFormula());
                                break;
                            case BOOLEAN:
                                dataMap.put(headers.get(j), data.getBooleanCellValue());
                                break;
                            case BLANK: //空值
                                dataMap.put(headers.get(j), "");
                                break;
                            default:
                                dataMap.put(headers.get(j), null);
                            }
                        }
                        result.add(dataMap);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取Excel中要导入的数据集时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG05_ERROR");
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
        
        return result;
    }

    public static List<Map<String, String>> getExcelData(List<String> titles) throws Exception {
        //获得Workbook工作薄对象
        Workbook workbook = null;
        //创建返回对象，把每行中的值作为一个map，所有行作为一个集合返回
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        try {
            workbook = checkFileAndGetWorkbook();
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue; 
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();

                //循环所有行 
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    Map<String,String>  map = new HashMap<String, String>();

                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < titles.size() ;cellNum++){
                        //获取表头用于组装数据
                        if(rowNum == 0) {
                            continue;
                        }else {
                            Cell cell = row.getCell(cellNum);
                            map.put(titles.get(cellNum), POIUtil.getCellValue(cell).replace("\\","/"));
                        }
                    }
                    if(rowNum >0 ) {
                        list.add(map);
                    }
                }
            }
        } catch (Exception e) {
            log.error("根据指定的列头获取Excel中要导入的数据集时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG07_ERROR");
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
        
        return list;
    }
    
     /**
      * 检查导入文件，并生成能够支持Excel2003和Excel2007及以上版本的工作表
      * @author 陈强
      * @param 
      * @return Workbook
      * @throws Exception
      */
     public static Workbook checkFileAndGetWorkbook() throws Exception {
         log.debug("开始检查导入的Excel文件，包括获取Excel列头和需要导入的数据...");
         
         MultiValueMap<String, MultipartFile> multiFileMap = null;
         MultipartFile file = null;
         try {
             multiFileMap = getMultipartHttpServletRequest().getMultiFileMap();
             file = multiFileMap.getFirst(FILE);
         } catch (Exception e) {
             log.error("构造上传的MultipartHttpServletRequest时发生异常，错误信息：{}", e);
             throw new RuntimeException("ExcelKitServiceMSG04_ERROR");
         }
         
         //判断文件是否存在
         if(null == file){
             throw new RuntimeException("ExcelKitServiceMSG01_ERROR");
         }
         //获得文件名
         String fileName = file.getOriginalFilename();
         log.debug("Excel导入的原始文件名为：{}", fileName);
         //判断文件是否是excel文件
         if(!StringUtils.endsWith(fileName, EXCEL_SUFFIX_XLS) && !StringUtils.endsWith(fileName, EXCEL_SUFFIX_XLSX)){
             throw new RuntimeException("ExcelKitServiceMSG02_ERROR");
         }
         
         //生成工作表
         Workbook workbook = null;
         try {
            workbook = WorkbookFactory.create(file.getInputStream());
         } catch (Exception e) {
            log.error("导入文件类型错误，无法转换生成支持Excel2003和Excel2007及以上版本的工作表，异常信息为：", e);
            throw new RuntimeException("ExcelKitServiceMSG03_ERROR");
         } 
         
         return workbook;
     }

    public static void doExportExcel(List<String> titles, List<Map<String,Object>> data,
            boolean isTemplate, String excelName, String exportComments) throws Exception {
        SXSSFWorkbook workbook = null;
        try {
            workbook = POIUtil.newSXSSFWorkbook();
            // 传入对象名、关系名作为导出的Excel名称，如果为空，则默认用当前时间作为Excel名称前缀
            if (StringUtils.isBlank(excelName)) {
                excelName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            }
            String fileName = isTemplate ? (excelName + "_导出模板.xlsx") : (excelName + "_导出结果.xlsx");
            log.debug("doExportExcel导出的文件名为：{}", fileName);
            double sheetNo = Math.ceil(data == null ? 0 : data.size() / (double) mMaxSheetRecords);
            for (int index = 0; index <= (sheetNo == 0.0 ? sheetNo : sheetNo - 1); index++) {
              SXSSFSheet sheet = workbook.createSheet();
              SXSSFRow headerRow = sheet.createRow(0);
              for (int i = 0; i < titles.size(); i++) {
                  String property = titles.get(i);
                  SXSSFCell cell = POIUtil.newSXSSFCell(headerRow, i);
                  POIUtil.setColumnWidth(sheet, i, (short)200, property);
                  cell.setCellStyle(POIUtil.getHeaderCellStyle(workbook));
                  cell.setCellValue(property);
                }
              if (null != data && data.size() > 0) {
                  int startNo = index * mMaxSheetRecords;
                  int endNo = Math.min(startNo + mMaxSheetRecords, data.size());
                  for (int i = startNo; i < endNo; i++) {
                    SXSSFRow bodyRow = POIUtil.newSXSSFRow(sheet, i + 1 - startNo);
                    for (int j = 0; j < titles.size(); j++) {
                      SXSSFCell cell = POIUtil.newSXSSFCell(bodyRow, j);
                      String key = titles.get(j);
                      Object obj =  data.get(i).get(key);
                      String cellValue = ""; 
                      if(obj == null){
                          cellValue = "";
                      } 
                      else if(obj instanceof Date) {
                          cellValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
                      }
                      else if(obj instanceof Float || obj instanceof Double) {
                          cellValue= new BigDecimal(obj.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                      } 
                      else {
                          cellValue = obj.toString();
                      }
                      cell.setCellValue(cellValue);
                    }
                  }
              }
            }
            // 导出模板中写入填写说明备注信息
            if (StringUtils.isNotBlank(exportComments)) {
                log.debug("{}中的填写说明为：{}", fileName, exportComments);
                
                SXSSFSheet commentSheet = POIUtil.newSXSSFSheet(workbook, "填写说明");
                
                //表头样式
                CellStyle commentStyle = workbook.createCellStyle();
                // 设置单元格的背景颜色为浅绿色
                commentStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                commentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // 设置单元格水平居左对齐
                commentStyle.setAlignment(HorizontalAlignment.LEFT);
                // 设置单元格垂直居上对齐
                commentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                // 创建单元格内容显示不下时自动换行
                commentStyle.setWrapText(true);
                Font commentFont = workbook.createFont();
                commentFont.setFontHeightInPoints((short) 12);
                commentFont.setBold(false);
                commentStyle.setFont(commentFont);
                
                // 设置单元格边框线条厚度以及颜色
                commentStyle.setBorderBottom(BorderStyle.DOUBLE);
                commentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderLeft(BorderStyle.DOUBLE);
                commentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderRight(BorderStyle.DOUBLE);
                commentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderTop(BorderStyle.DOUBLE);
                commentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                
                SXSSFRow titleRow = commentSheet.createRow(0);
                titleRow.createCell(0).setCellValue(exportComments);
                titleRow.getCell(0).setCellStyle(commentStyle);
                // 合并单元格（参数1：起始行号 参数2：终止行号 参数3：起始列号 参数4：终止列号）
                commentSheet.addMergedRegion(new CellRangeAddress(0, 29, 0, 19));
            }
            //获取response
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            
            POIUtil.download(workbook, response, URLEncoder.encode(fileName, ENCODING));
        } catch (Exception e) {
            log.error("导出Excel时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG08_ERROR");
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        } 
        
    }
    
    public static void doExportExcel(Map<String, String> titles, List<Map<String,Object>> data,
            boolean isTemplate, String excelName, String exportComments) throws Exception {
        SXSSFWorkbook workbook = null;
        SXSSFDrawing sxssfDrawing = null;
        try {
            workbook = POIUtil.newSXSSFWorkbook();
            // 传入对象名、关系名作为导出的Excel名称，如果为空，则默认用当前时间作为Excel名称前缀
            if (StringUtils.isBlank(excelName)) {
                excelName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            }
            String fileName = isTemplate ? (excelName + "_导出模板.xlsx") : (excelName + "_导出结果.xlsx");
            log.debug("doExportExcel导出的文件名为：{}", fileName);
            double sheetNo = Math.ceil(data == null ? 0 : data.size() / (double) mMaxSheetRecords);
            
            for (int index = 0; index <= (sheetNo == 0.0 ? sheetNo : sheetNo - 1); index++) {
              SXSSFSheet sheet = workbook.createSheet();
              //解决:因为是大数据量，所以需要在一个excel中需要创建多个sheet，然后此时就发现一个问题：当创建新sheet的行数超过windowSize的大小时，会报错误：(Attempting to write a row[0] in the range [0,3032] that is already written to disk.)
              sheet.setRandomAccessWindowSize(-1);
              
              //用于处理列明长度自适应
              Map<String, Integer> map = new HashMap<String, Integer>();
              //往excel写数据 从第二行开始写
              if (null != data && data.size() > 0) {
                  int startNo = index * mMaxSheetRecords;
                  int endNo = Math.min(startNo + mMaxSheetRecords, data.size());
                  for (int i = startNo; i < endNo; i++) {
                    SXSSFRow bodyRow = POIUtil.newSXSSFRow(sheet, i + 1 - startNo);
                    int j = 0;
                    for (Iterator<Entry<String, String>> iter = titles.entrySet().iterator(); iter.hasNext();) {
                      Entry<String, String> title = iter.next();
                      
                      SXSSFCell cell = POIUtil.newSXSSFCell(bodyRow, j);
                      String key = title.getKey();
                      Object obj =  data.get(i).get(key);
                      String cellValue = ""; 
                      try {
                          cellValue = dealDateFormat(String.valueOf(obj));
                      } catch (Exception e) {
                          if(obj == null){
                              cellValue = "";
                          } else {
                              cellValue = String.valueOf(obj);
                          }
                      }
                      cell.setCellValue(cellValue);
                      if(map.containsKey(key) && cellValue.length() > map.get(key)){
                          map.put(key, cellValue.length());
                      } else if (!map.containsKey(key)){
                          map.put(key, cellValue.length());
                      }
                      j++;
                    }
                  }
              }
              
              //往excel写列头数据
              SXSSFRow headerRow = sheet.createRow(0);
              int k = 0;
              for (Iterator<Entry<String, String>> iter = titles.entrySet().iterator(); iter.hasNext();) {
                  Entry<String, String> title = iter.next();
                  
                  SXSSFCell cell = POIUtil.newSXSSFCell(headerRow, k);
                  if(null != data && data.size() > 0) {
                      Integer columnWidth = map.get(title.getKey()) == 0 ? (short)(200 * 25) : map.get(title.getKey()) * 270;
                      if(title.getKey().length() > map.get(title.getKey())){
                          sheet.setColumnWidth(k, (short)(200 * 25));
                      } else {
                          sheet.setColumnWidth(k, columnWidth);
                      }
                  } else {
                      sheet.setColumnWidth(k, (short)(200 * 25));
                  }
                  
                  // cell comment.
                  if (null == sxssfDrawing) {
                    sxssfDrawing = sheet.createDrawingPatriarch();
                  }
                  if (!StringUtils.isEmpty(title.getValue())) {
                    // int col1, int row1, int col2, int row2
                    Comment cellComment = sxssfDrawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, k, 0, k, 0));
                    XSSFRichTextString xssfRichTextString = new XSSFRichTextString(title.getValue());
                    Font commentFormatter = workbook.createFont();
                    xssfRichTextString.applyFont(commentFormatter);
                    cellComment.setString(xssfRichTextString);
                    cell.setCellComment(cellComment);
                  }
                  
                  cell.setCellStyle(POIUtil.getHeaderCellStyle(workbook));
                  cell.setCellValue(title.getKey());
                  
                  k++;
              }
            }
            
            // 导出模板中写入填写说明备注信息
            if (StringUtils.isNotBlank(exportComments)) {
                log.debug("{}中的填写说明为：{}", fileName, exportComments);
                
                SXSSFSheet commentSheet = POIUtil.newSXSSFSheet(workbook, "填写说明");
                
                //表头样式
                CellStyle commentStyle = workbook.createCellStyle();
                // 设置单元格的背景颜色为浅绿色
                commentStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                commentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // 设置单元格水平居左对齐
                commentStyle.setAlignment(HorizontalAlignment.LEFT);
                // 设置单元格垂直居上对齐
                commentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                // 创建单元格内容显示不下时自动换行
                commentStyle.setWrapText(true);
                Font commentFont = workbook.createFont();
                commentFont.setFontHeightInPoints((short) 12);
                commentFont.setBold(false);
                commentStyle.setFont(commentFont);
                
                // 设置单元格边框线条厚度以及颜色
                commentStyle.setBorderBottom(BorderStyle.DOUBLE);
                commentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderLeft(BorderStyle.DOUBLE);
                commentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderRight(BorderStyle.DOUBLE);
                commentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderTop(BorderStyle.DOUBLE);
                commentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                
                SXSSFRow titleRow = commentSheet.createRow(0);
                titleRow.createCell(0).setCellValue(exportComments);
                titleRow.getCell(0).setCellStyle(commentStyle);
                // 合并单元格（参数1：起始行号 参数2：终止行号 参数3：起始列号 参数4：终止列号）
                commentSheet.addMergedRegion(new CellRangeAddress(0, 29, 0, 19));
            }
            //获取response
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            
            POIUtil.download(workbook, response, URLEncoder.encode(fileName, ENCODING));
        } catch (Exception e) {
            log.error("导出Excel时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG08_ERROR");
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        } 
        
    }

    public static List<MultipartFile> getMultipartFiles() throws Exception {
        List<MultipartFile> fileList = null;
        try {
            fileList = getMultipartHttpServletRequest().getFiles(FILE);
        } catch (Exception e) {
            log.error("构造上传的MultipartHttpServletRequest时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG04_ERROR");
        }
        
        return fileList;
    }
    
    /**
     * 获取上传的MultipartHttpServletRequest
     * @author 陈强
     * @param 
     * @return MultipartHttpServletRequest
     * @throws Exception
     */
    private static MultipartHttpServletRequest getMultipartHttpServletRequest() throws Exception{
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        
        MultipartResolver resolver = new StandardServletMultipartResolver();
        //构造上传的MultipartHttpServletRequest
        MultipartHttpServletRequest multipartHttpServletRequest = resolver.resolveMultipart(request); 
        return multipartHttpServletRequest;
    }

    public static void doExportExcelWithComments(Map<String, String> titles, String excelName, String exportComments)
            throws Exception {
        SXSSFDrawing sxssfDrawing = null;
        SXSSFWorkbook workbook = null;
        try {
            workbook = POIUtil.newSXSSFWorkbook();
            // 传入对象名、关系名作为导出的Excel名称，如果为空，则默认用当前时间作为Excel名称前缀
            if (StringUtils.isBlank(excelName)) {
                excelName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            }
            String fileName = MessageFormat.format("{0}{1}", excelName, "_导出模板.xlsx");
            log.debug("doExportExcelWithComments导出带列头批注的Excel模板文件名为：{}", fileName);

            SXSSFSheet sheet = workbook.createSheet(excelName);
            SXSSFRow headerRow = sheet.createRow(0);
            int i = 0;
            for (Iterator<Entry<String, String>> iter = titles.entrySet().iterator(); iter.hasNext();) {
                Entry<String, String> title = iter.next();
                
                SXSSFCell cell = POIUtil.newSXSSFCell(headerRow, i);
                POIUtil.setColumnWidth(sheet, i, (short)200, title.getKey());
                // cell comment.
                if (null == sxssfDrawing) {
                  sxssfDrawing = sheet.createDrawingPatriarch();
                }
                if (!StringUtils.isEmpty(title.getValue())) {
                  // int col1, int row1, int col2, int row2
                  Comment cellComment = sxssfDrawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, i, 0, i, 0));
                  XSSFRichTextString xssfRichTextString = new XSSFRichTextString(title.getValue());
                  Font commentFormatter = workbook.createFont();
                  xssfRichTextString.applyFont(commentFormatter);
                  cellComment.setString(xssfRichTextString);
                  cell.setCellComment(cellComment);
                }
                
                cell.setCellStyle(POIUtil.getHeaderCellStyle(workbook));
                cell.setCellValue(title.getKey());
                
                i++;
            }
          
            // 导出模板中写入填写说明备注信息
            if (StringUtils.isNotBlank(exportComments)) {
                log.debug("{}中的填写说明为：{}", fileName, exportComments);
                SXSSFSheet commentSheet = POIUtil.newSXSSFSheet(workbook, "填写说明");
                
                //表头样式
                CellStyle commentStyle = workbook.createCellStyle();
                // 设置单元格的背景颜色为浅绿色
                commentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                commentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                // 设置单元格水平居左对齐
                commentStyle.setAlignment(HorizontalAlignment.LEFT);
                // 设置单元格垂直居上对齐
                commentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                // 创建单元格内容显示不下时自动换行
                commentStyle.setWrapText(true);
                Font commentFont = workbook.createFont();
                commentFont.setFontHeightInPoints((short) 12);
                commentFont.setBold(false);
                commentStyle.setFont(commentFont);
                
                // 设置单元格边框线条厚度以及颜色
                commentStyle.setBorderBottom(BorderStyle.DOUBLE);
                commentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderLeft(BorderStyle.DOUBLE);
                commentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderRight(BorderStyle.DOUBLE);
                commentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                commentStyle.setBorderTop(BorderStyle.DOUBLE);
                commentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                
                SXSSFRow titleRow = commentSheet.createRow(0);
                titleRow.createCell(0).setCellValue(exportComments);
                titleRow.getCell(0).setCellStyle(commentStyle);
                // 合并单元格（参数1：起始行号 参数2：终止行号 参数3：起始列号 参数4：终止列号）
                commentSheet.addMergedRegion(new CellRangeAddress(0, 29, 0, 19));
            }
            //获取response
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            
            POIUtil.download(workbook, response, URLEncoder.encode(fileName, ENCODING));
        } catch (Exception e) {
            log.error("导出带列头批注的Excel模板时发生异常，错误信息：{}", e);
            throw new RuntimeException("ExcelKitServiceMSG09_ERROR");
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
        
    }
    
    
    public static String dealDateFormat(String oldDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = df.parse(oldDate);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        df1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date1 = df1.parse(date.toString());
        Calendar ca = Calendar.getInstance();
        ca.setTime(date1);
        ca.add(Calendar.HOUR_OF_DAY, 8);
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(ca.getTime());
    }

}
