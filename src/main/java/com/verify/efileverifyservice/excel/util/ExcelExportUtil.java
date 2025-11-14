package com.verify.efileverifyservice.excel.util;

import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author JinPeng
 * @since 2025/11/7
 */
public class ExcelExportUtil {
    /**
     * 导出文件通过模板解析只有模板,没有集合
     *
     * @param params 导出参数类
     * @param map    模板集合
     * @return
     */
    public static Workbook exportExcel(TemplateExportParams params, Map<String, Object> map) {
        return new ExcelExportOfTemplateUtil().createExcelByTemplate(params, null, null, map);
    }

    /**
     * 导入数据到模版
     */
    public static ByteArrayOutputStream exportDataExcel(JSONObject json, HttpServletResponse response, Integer[] sheetNum, String templateUrl) throws IOException {
        TemplateExportParams params = new TemplateExportParams(templateUrl);
        params.setSheetNum(sheetNum);
        params.setColForEach(true);
        Workbook workbook = exportExcel(params, processJsonObject(json));
        ByteArrayOutputStream excelOutput = new ByteArrayOutputStream();
        workbook.write(excelOutput);
        workbook.close();
        return excelOutput;
    }


    /**
     * 打包成压缩包下载
     */
    public static void exportZip(List<ByteArrayOutputStream> list,List<String> nameList, HttpServletResponse response) throws IOException {
        try {
            // 创建ZIP输出流
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=days_export.zip");
            try (ByteArrayOutputStream zipOutput = new ByteArrayOutputStream();
                 ZipOutputStream zipOutputStream = new ZipOutputStream(zipOutput)) {
                for (int l = 0; l < list.size(); l++) {
                    ByteArrayOutputStream excelOutput = list.get(l);
                    // 添加文件到ZIP
                    ZipEntry zipEntry = new ZipEntry(nameList.get(l));
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
            throw new RuntimeException("导出ZIP文件失败", e);
        }
    }


    public static JSONObject processJsonObject(Map<String, Object> original) {
        JSONObject result = new JSONObject();
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Double) {
                // 将double转换为避免科学计数法的字符串表示
                Double doubleValue = (Double) value;
                // 使用BigDecimal来保持精度并避免科学计数法
                BigDecimal bd = new BigDecimal(doubleValue.toString());
                result.put(key, bd);
            } else if (value instanceof Map) {
                // 递归处理嵌套的JSONObject
                result.put(key, processJsonObject((Map<String, Object>) value));
            } else if (value instanceof List) {
                // 处理JSONArray中的元素
                result.put(key, processJsonArray((List) value));
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    public static JSONArray processJsonArray(List original) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < original.size(); i++) {
            Object item = original.get(i);
            if (item instanceof Double) {
                Double doubleValue = (Double) item;
                BigDecimal bd = new BigDecimal(doubleValue.toString());
                result.add(bd);
            } else if (item instanceof Map) {
                result.add(processJsonObject((Map<String, Object>) item));
            } else if (item instanceof List) {
                result.add(processJsonArray((List) item));
            } else {
                result.add(item);
            }
        }
        return result;
    }
}
