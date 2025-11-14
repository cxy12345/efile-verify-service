package com.verify.efileverifyservice.controller;

import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.verify.efileverifyservice.excel.util.ExcelExportUtil;
import com.verify.efileverifyservice.service.DataTransferService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author JinPeng
 * @since 2025/11/7
 */
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelDealController {
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Value("${excel.template.day}")
    private String dayTemplate;

    @Value("${excel.template.month}")
    private String monthTemplate;

    @Resource
    private DataTransferService dataTransferService;


    @PostMapping(value = "/export/day")
    public String exportDay(@RequestBody JSONObject json, HttpServletResponse response) {
        ByteArrayOutputStream fos = null;
        try {
            TemplateExportParams params = new TemplateExportParams(dayTemplate);
            params.setColForEach(true);
            Workbook book = ExcelExportUtil.exportExcel(params, processJsonObject(json));
            // int listMaintenanceRecordMStartRow = 3;
            // CellRangeAddress regionMaintenanceRecord = new CellRangeAddress(listMaintenanceRecordMStartRow,
            // listMaintenanceRecordMStartRow + listMaintenanceRecordM.size() - 1, 0, 2);
            // book.getSheetAt(0).addMergedRegion(regionMaintenanceRecord);

            // PoiMergeCellUtil.mergeCells(book.getSheetAt(0), 1, 0,1);
            fos = new ByteArrayOutputStream();
            // 设置响应头
            // response.setContentType(CONTENT_TYPE);
            // response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s.xlsx\"",
            // URLEncoder.encode("负荷单位日账单", "UTF-8").replaceAll("\\+", "%20")));
            // book.write(response.getOutputStream());
            book.write(fos);
            book.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(fos.toByteArray());
    }


    @PostMapping(value = "/export/days")
    public void exportDays(@RequestBody JSONObject json, HttpServletResponse response) {
        try {
            TemplateExportParams params = new TemplateExportParams(dayTemplate);
            params.setSheetNum(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13});
            params.setColForEach(true);
            Workbook book = ExcelExportUtil.exportExcel(params, processJsonObject(json));

            // 设置响应头，支持文件下载
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Content-Disposition", "attachment;filename=\"day_report.xlsx\"");
            response.setCharacterEncoding("UTF-8");

            // 直接将Workbook写入响应输出流
            book.write(response.getOutputStream());
            book.close();

            // 确保数据被发送
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("导出Excel文件失败", e);
        }
    }


    @PostMapping(value = "/export/daysZip")
    public void exportDaysAsZip(@RequestBody List<JSONObject> jsonList, HttpServletResponse response) {
        try {
            // 创建ZIP输出流
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=days_export.zip");

            try (ByteArrayOutputStream zipOutput = new ByteArrayOutputStream();
                 ZipOutputStream zipOutputStream = new ZipOutputStream(zipOutput)) {

                // 循环处理每个JSON对象
                for (int i = 0; i < Math.min(jsonList.size(), 4); i++) {
                    JSONObject json = jsonList.get(i);

                    // 生成Excel文件
                    TemplateExportParams params = new TemplateExportParams(dayTemplate);
                    params.setColForEach(true);
                    Workbook workbook = ExcelExportUtil.exportExcel(params, processJsonObject(json));

                    // 将workbook写入字节数组
                    ByteArrayOutputStream excelOutput = new ByteArrayOutputStream();
                    workbook.write(excelOutput);
                    workbook.close();

                    // 添加文件到ZIP
                    ZipEntry zipEntry = new ZipEntry("day_report_" + (i + 1) + ".xlsx");
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(excelOutput.toByteArray());
                    zipOutputStream.closeEntry();
                    excelOutput.close();
                }

                zipOutputStream.finish();

                // 写入响应
                response.getOutputStream().write(zipOutput.toByteArray());
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            log.error("导出ZIP文件失败", e);
            throw new RuntimeException("导出ZIP文件失败", e);
        }
    }




//    @PostMapping(value = "/export/month")
//    public String exportMonth(@RequestBody JSONObject json, HttpServletResponse response) {
//        ByteArrayOutputStream fos = null;
//        try {
//            TemplateExportParams params = new TemplateExportParams(monthTemplate);
//            params.setColForEach(true);
//            Workbook book = ExcelExportUtil.exportExcel(params, processJsonObject(json));
//            // int listMaintenanceRecordMStartRow = 3;
//            // CellRangeAddress regionMaintenanceRecord = new CellRangeAddress(listMaintenanceRecordMStartRow,
//            // listMaintenanceRecordMStartRow + listMaintenanceRecordM.size() - 1, 0, 2);
//            // book.getSheetAt(0).addMergedRegion(regionMaintenanceRecord);
//
//            // PoiMergeCellUtil.mergeCells(book.getSheetAt(0), 1, 0,1);
//            fos = new ByteArrayOutputStream();
//            // 设置响应头
//            // response.setContentType(CONTENT_TYPE);
//            // response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s.xlsx\"",
//            // URLEncoder.encode("负荷单位日账单", "UTF-8").replaceAll("\\+", "%20")));
//            // book.write(response.getOutputStream());
//            book.write(fos);
//            book.close();
//            fos.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return Base64.getEncoder().encodeToString(fos.toByteArray());
//    }

    private JSONObject processJsonObject(Map<String, Object> original) {
        JSONObject result = new JSONObject();
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Double) {
                // 将double转换为避免科学计数法的字符串表示
                Double doubleValue = (Double)value;
                // 使用BigDecimal来保持精度并避免科学计数法
                BigDecimal bd = new BigDecimal(doubleValue.toString());
                result.put(key, bd);
            } else if (value instanceof Map) {
                // 递归处理嵌套的JSONObject
                result.put(key, processJsonObject((Map<String, Object>)value));
            } else if (value instanceof List) {
                // 处理JSONArray中的元素
                result.put(key, processJsonArray((List)value));
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    private JSONArray processJsonArray(List original) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < original.size(); i++) {
            Object item = original.get(i);
            if (item instanceof Double) {
                Double doubleValue = (Double)item;
                BigDecimal bd = new BigDecimal(doubleValue.toString());
                result.add(bd);
            } else if (item instanceof Map) {
                result.add(processJsonObject((Map<String, Object>)item));
            } else if (item instanceof List) {
                result.add(processJsonArray((List)item));
            } else {
                result.add(item);
            }
        }
        return result;
    }

    @GetMapping("/dataTransferSupplement")
    public void dataTransferSupplement(String supplementDate) {
        dataTransferService.transferData(supplementDate);
        log.info("结束数据传输，补充日期：{}", supplementDate);
    }

}
