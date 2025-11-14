package com.verify.efileverifyservice.excel.util;

/**
 * @version v1.0
 * @ProjectName: road
 * @ClassName: ExcelExportOfTemplateUtil
 * @Description: //解决row循环中，row元素中包含list并且合并单元格时，报空的错误
 * @Author: xbx
 * @Date: 2022/3/4 10:56
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cn.afterturn.easypoi.cache.ExcelCache;
import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateSumEntity;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.base.BaseExportService;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.afterturn.easypoi.excel.export.template.TemplateSumHandler;
import cn.afterturn.easypoi.excel.html.helper.MergedRegionHelper;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.exception.excel.enums.ExcelExportEnum;
import cn.afterturn.easypoi.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public final class ExcelExportOfTemplateUtil extends BaseExportService {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(cn.afterturn.easypoi.excel.export.template.ExcelExportOfTemplateUtil.class);
    private Set<String> tempCreateCellSet = new HashSet();
    private TemplateExportParams templateParams;
    private MergedRegionHelper mergedRegionHelper;
    private cn.afterturn.easypoi.excel.export.template.TemplateSumHandler templateSumHandler;

    public ExcelExportOfTemplateUtil() {}

    private void addDataToSheet(Class<?> pojoClass, Collection<?> dataSet, Sheet sheet, Workbook workbook)
        throws Exception {
        Map<String, Integer> titlemap = this.getTitleMap(sheet);
        Drawing patriarch = PoiExcelGraphDataUtil.getDrawingPatriarch(sheet);
        Field[] fileds = PoiPublicUtil.getClassFields(pojoClass);
        ExcelTarget etarget = (ExcelTarget)pojoClass.getAnnotation(ExcelTarget.class);
        String targetId = null;
        if (etarget != null) {
            targetId = etarget.value();
        }

        List<ExcelExportEntity> excelParams = new ArrayList();
        this.getAllExcelField((String[])null, targetId, fileds, excelParams, pojoClass, (List)null, (ExcelEntity)null);
        this.sortAndFilterExportField(excelParams, titlemap);
        short rowHeight = this.getRowHeight(excelParams);
        int index = this.templateParams.getHeadingRows() + this.templateParams.getHeadingStartRow();
        int shiftRows = this.getShiftRows(dataSet, excelParams);
        sheet.shiftRows(this.templateParams.getHeadingRows() + this.templateParams.getHeadingStartRow(),
            sheet.getLastRowNum(), shiftRows, true, true);
        this.mergedRegionHelper.shiftRows(sheet,
            this.templateParams.getHeadingRows() + this.templateParams.getHeadingStartRow(), shiftRows,
            sheet.getLastRowNum() - this.templateParams.getHeadingRows() - this.templateParams.getHeadingStartRow());
        this.templateSumHandler
            .shiftRows(this.templateParams.getHeadingRows() + this.templateParams.getHeadingStartRow(), shiftRows);
        PoiExcelTempUtil.reset(sheet, this.templateParams.getHeadingRows() + this.templateParams.getHeadingStartRow(),
            sheet.getLastRowNum());
        if (excelParams.size() != 0) {
            Object t;
            for (Iterator its = dataSet.iterator(); its.hasNext();
                index += this.createCells(patriarch, index, t, excelParams, sheet, workbook, rowHeight, 0)[0]) {
                t = its.next();
            }

            this.mergeCells(sheet, excelParams, index);
        }
    }

    /**
     * @Author: 徐本锡
     * @Date: 2022/3/7 16:58
     * @param: [cell, map, name]
     * @return: void
     * @description: 修改源码 ：list循环遍历的时候 存在合并单元格情况 导致格式错乱
     */
    private void addListDataToExcel(Cell cell, Map<String, Object> map, String name) throws Exception {
        // 每次利用foreach循环输出数据时，重新处理 mergedRegionHelper
        // 原因：如果存在多个循环，前面循环时，下面的模板指令所在单元格的行号会发生变化，此时 mergedRegionHelper 中的缓存没有发生相应变化，需要重新获取一下 mergedRegionHelper
        mergedRegionHelper = new MergedRegionHelper(cell.getSheet());

        boolean isCreate = !name.contains("!fe:");
        boolean isShift = name.contains("$fe:");
        name = name.replace("!fe:", "").replace("$fe:", "").replace("fe:", "").replace("{{", "");
        String[] keys = name.replaceAll("\\s{1,}", " ").trim().split(" ");
        Collection<?> datas = (Collection)PoiPublicUtil.getParamsValue(keys[0], map);
        if (datas != null) {
            Object[] columnsInfo = this.getAllDataColumns(cell, name.replace(keys[0], ""), mergedRegionHelper);
            Iterator<?> its = datas.iterator();
            int rowspan = (Integer)columnsInfo[0];
            int colspan = (Integer)columnsInfo[1];
            List<ExcelForEachParams> columns = (List)columnsInfo[2];
            Row row = null;
            int rowIndex = cell.getRow().getRowNum() + 1;
            ExcelForEachParams indexColumn = this.getIndexColumn(columns);
            Object t;
            int loopSize;

            // 定义循环开始行号
            int startRowNum = cell.getRow().getRowNum();

            // 处理当前行
            if (its.hasNext()) {
                t = its.next();
                this.setForeachRowCellValue(isCreate, cell.getRow(), cell.getColumnIndex(), t, columns, map, rowspan,
                    colspan, mergedRegionHelper, startRowNum);
                rowIndex += rowspan - 1;
            }

            // if (its.hasNext()) {
            // t = its.next();
            // loopSize = this.setForeachRowCellValue(isCreate, cell.getRow(), cell.getColumnIndex(), t, columns, map,
            // rowspan, colspan, this.mergedRegionHelper,startRowNum)[0];
            // rowIndex += rowspan - 1 + loopSize - 1;
            // }

            if (isShift && datas.size() * rowspan > 1
                && cell.getRowIndex() + rowspan <= cell.getRow().getSheet().getLastRowNum()) {
                int lastRowNum = cell.getRow().getSheet().getLastRowNum();
                int shiftRows = lastRowNum - cell.getRowIndex() - rowspan;
                cell.getRow().getSheet().shiftRows(cell.getRowIndex() + rowspan, lastRowNum,
                    (datas.size() - 1) * rowspan, true, true);
                mergedRegionHelper.shiftRows(cell.getSheet(), cell.getRowIndex() + rowspan,
                    (datas.size() - 1) * rowspan, shiftRows);
                this.templateSumHandler.shiftRows(cell.getRowIndex() + rowspan, (datas.size() - 1) * rowspan);
                PoiExcelTempUtil.reset(cell.getSheet(), cell.getRowIndex() + rowspan + (datas.size() - 1) * rowspan,
                    cell.getRow().getSheet().getLastRowNum());
            }

            // 定义循环结束行号
            int endRowNum = 0;
            // // 创建行
            while (its.hasNext()) {
                t = its.next();
                row = createRow(rowIndex, cell.getSheet(), isCreate, rowspan);
                this.setForeachRowCellValue(isCreate, row, cell.getColumnIndex(), t, columns, map, rowspan, colspan,
                    mergedRegionHelper, startRowNum);
                rowIndex += rowspan;
                // 每次创建行后，重新给循环结束行号赋值
                endRowNum = row.getRowNum();
            }
            // 如果新创建行了
            // 合并循环左侧竖向单元格
            if (endRowNum != 0) {
                Sheet sheet = cell.getRow().getSheet();
                int sheetMergeCount = sheet.getNumMergedRegions();
                int columnIndex = cell.getColumnIndex();
                for (int i = 0; i < sheetMergeCount; ++i) {
                    CellRangeAddress ca = sheet.getMergedRegion(i);
                    // int firstColumn = ca.getFirstColumn();
                    int lastColumn = ca.getLastColumn();
                    int firstRow = ca.getFirstRow();
                    int lastRow = ca.getLastRow();
                    for (int ii = 0; ii < columnIndex; ii++) {
                        if (firstRow <= startRowNum && lastRow > startRowNum && lastColumn == ii) {
                            ca.setLastRow(lastRow + (endRowNum - startRowNum));
                        }
                    }
                }
            }

            // while(its.hasNext()) {
            // t = its.next();
            // row = this.createRow(rowIndex, cell.getSheet(), isCreate, rowspan);
            // indexColumn.addConstValue(1);
            // loopSize = this.setForeachRowCellValue(isCreate, row, cell.getColumnIndex(), t, columns, map, rowspan,
            // colspan, this.mergedRegionHelper,startRowNum)[0];
            // rowIndex += rowspan + loopSize - 1;
            // }

        }
    }

    private ExcelForEachParams getIndexColumn(List<ExcelForEachParams> columns) {
        for (int i = 0; i < columns.size(); ++i) {
            if (columns.get(i) != null && "&INDEX&".equals(((ExcelForEachParams)columns.get(i)).getConstValue())) {
                ((ExcelForEachParams)columns.get(i)).setConstValue("1");
                return (ExcelForEachParams)columns.get(i);
            }
        }

        return new ExcelForEachParams();
    }

    private int getShiftRows(Collection<?> dataSet, List<ExcelExportEntity> excelParams) throws Exception {
        int size = 0;

        Object t;
        for (Iterator its = dataSet.iterator(); its.hasNext(); size += this.getOneObjectSize(t, excelParams)) {
            t = its.next();
        }

        return size;
    }

    private int getOneObjectSize(Object t, List<ExcelExportEntity> excelParams) throws Exception {
        int maxHeight = 1;
        int k = 0;

        for (int paramSize = excelParams.size(); k < paramSize; ++k) {
            ExcelExportEntity entity = (ExcelExportEntity)excelParams.get(k);
            if (entity.getList() != null) {
                Collection<?> list = (Collection)entity.getMethod().invoke(t);
                if (list != null && list.size() > maxHeight) {
                    maxHeight = list.size();
                }
            }
        }

        return maxHeight;
    }

    public Workbook createExcelCloneByTemplate(TemplateExportParams params,
        Map<Integer, List<Map<String, Object>>> map) {
        if (params != null && map != null && !StringUtils.isEmpty(params.getTemplateUrl())) {
            Workbook wb = null;

            try {
                this.templateParams = params;
                wb = ExcelCache.getWorkbook(this.templateParams.getTemplateUrl(), this.templateParams.getSheetNum(),
                    true);
                int oldSheetNum = wb.getNumberOfSheets();
                List<String> oldSheetName = new ArrayList();

                for (int i = 0; i < oldSheetNum; ++i) {
                    oldSheetName.add(wb.getSheetName(i));
                }

                List<Integer> sheetNumList = new ArrayList();
                sheetNumList.addAll(map.keySet());
                Collections.sort(sheetNumList);
                Iterator var8 = sheetNumList.iterator();

                List mapList;
                while (var8.hasNext()) {
                    Integer sheetNum = (Integer)var8.next();
                    mapList = (List)map.get(sheetNum);

                    for (int i = mapList.size(); i > 0; --i) {
                        wb.cloneSheet(sheetNum);
                    }
                }

                int sheetIndex;
                for (sheetIndex = 0; sheetIndex < oldSheetName.size(); ++sheetIndex) {
                    wb.removeSheetAt(wb.getSheetIndex((String)oldSheetName.get(sheetIndex)));
                }

                this.setExcelExportStyler(
                    (IExcelExportStyler)this.templateParams.getStyle().getConstructor(Workbook.class).newInstance(wb));
                sheetIndex = 0;
                Iterator var15 = sheetNumList.iterator();

                while (var15.hasNext()) {
                    Integer sheetNum = (Integer)var15.next();
                    mapList = (List)map.get(sheetNum);

                    for (int i = mapList.size() - 1; i >= 0; --i) {
                        this.tempCreateCellSet.clear();
                        if (((Map)mapList.get(i)).containsKey("sheetName")) {
                            wb.setSheetName(sheetIndex, ((Map)mapList.get(i)).get("sheetName").toString());
                        }

                        this.parseTemplate(wb.getSheetAt(sheetIndex), (Map)mapList.get(i), params.isColForEach());
                        if (params.isReadonly()) {
                            wb.getSheetAt(i).protectSheet(UUID.randomUUID().toString());
                        }

                        ++sheetIndex;
                    }
                }

                return wb;
            } catch (Exception var12) {
                LOGGER.error(var12.getMessage(), var12);
                return null;
            }
        } else {
            throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
        }
    }

    public Workbook createExcelByTemplate(TemplateExportParams params, Map<Integer, Map<String, Object>> map) {
        if (params != null && map != null && !StringUtils.isEmpty(params.getTemplateUrl())) {
            Workbook wb = null;

            try {
                this.templateParams = params;
                wb = this.getCloneWorkBook();
                this.setExcelExportStyler(
                    (IExcelExportStyler)this.templateParams.getStyle().getConstructor(Workbook.class).newInstance(wb));
                int i = 0;

                for (int le = params.isScanAllsheet() ? wb.getNumberOfSheets() : params.getSheetNum().length; i < le;
                    ++i) {
                    if (params.getSheetName() != null && params.getSheetName().length > i
                        && StringUtils.isNotEmpty(params.getSheetName()[i])) {
                        wb.setSheetName(i, params.getSheetName()[i]);
                    }

                    this.tempCreateCellSet.clear();
                    this.parseTemplate(wb.getSheetAt(i), (Map)map.get(i), params.isColForEach());
                    if (params.isReadonly()) {
                        wb.getSheetAt(i).protectSheet(UUID.randomUUID().toString());
                    }
                }

                return wb;
            } catch (Exception var6) {
                LOGGER.error(var6.getMessage(), var6);
                return null;
            }
        } else {
            throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
        }
    }

    public Workbook createExcelByTemplate(TemplateExportParams params, Class<?> pojoClass, Collection<?> dataSet,
        Map<String, Object> map) {
        if (params != null && map != null
            && (!StringUtils.isEmpty(params.getTemplateUrl()) || params.getTemplateWb() != null)) {
            Workbook wb = null;

            try {
                this.templateParams = params;
                if (params.getTemplateWb() != null) {
                    wb = params.getTemplateWb();
                } else {
                    wb = this.getCloneWorkBook();
                }

                if (params.getDictHandler() != null) {
                    this.dictHandler = params.getDictHandler();
                }

                if (params.getI18nHandler() != null) {
                    this.i18nHandler = params.getI18nHandler();
                }

                this.setExcelExportStyler(
                    (IExcelExportStyler)this.templateParams.getStyle().getConstructor(Workbook.class).newInstance(wb));
                int i = 0;

                for (int le = params.isScanAllsheet() ? wb.getNumberOfSheets() : params.getSheetNum().length; i < le;
                    ++i) {
                    if (params.getSheetName() != null && params.getSheetName().length > i
                        && StringUtils.isNotEmpty(params.getSheetName()[i])) {
                        wb.setSheetName(i, params.getSheetName()[i]);
                    }

                    this.tempCreateCellSet.clear();
                    this.parseTemplate(wb.getSheetAt(i), map, params.isColForEach());
                    if (params.isReadonly()) {
                        wb.getSheetAt(i).protectSheet(UUID.randomUUID().toString());
                    }
                }

                if (dataSet != null) {
                    this.dataHandler = params.getDataHandler();
                    if (this.dataHandler != null) {
                        this.needHandlerList = Arrays.asList(this.dataHandler.getNeedHandlerFields());
                    }

                    this.addDataToSheet(pojoClass, dataSet, wb.getSheetAt(params.getDataSheetNum()), wb);
                }

                return wb;
            } catch (Exception var8) {
                LOGGER.error(var8.getMessage(), var8);
                return null;
            }
        } else {
            throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
        }
    }

    private Workbook getCloneWorkBook() throws Exception {
        return ExcelCache.getWorkbook(this.templateParams.getTemplateUrl(), this.templateParams.getSheetNum(),
            this.templateParams.isScanAllsheet());
    }

    private Map<String, Integer> getTitleMap(Sheet sheet) {
        Row row = null;
        Map<String, Integer> titlemap = new HashMap();

        for (int j = 0; j < this.templateParams.getHeadingRows(); ++j) {
            row = sheet.getRow(j + this.templateParams.getHeadingStartRow());
            Iterator<Cell> cellTitle = row.cellIterator();

            for (int i = row.getFirstCellNum(); cellTitle.hasNext(); ++i) {
                Cell cell = (Cell)cellTitle.next();
                String value = cell.getStringCellValue();
                if (!StringUtils.isEmpty(value)) {
                    titlemap.put(value, i);
                }
            }
        }

        return titlemap;
    }

    private void parseTemplate(Sheet sheet, Map<String, Object> map, boolean colForeach) throws Exception {
        if (sheet.getWorkbook() instanceof XSSFWorkbook) {
            super.type = ExcelType.XSSF;
        }

        this.deleteCell(sheet, map);
        this.mergedRegionHelper = new MergedRegionHelper(sheet);
        this.templateSumHandler = new TemplateSumHandler(sheet);
        if (colForeach) {
            this.colForeach(sheet, map);
        }

        Row row = null;
        int index = 0;

        while (true) {
            do {
                if (index > sheet.getLastRowNum()) {
                    this.handlerSumCell(sheet);
                    return;
                }

                row = sheet.getRow(index++);
            } while (row == null);

            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); ++i) {
                if (row.getCell(i) != null
                    && !this.tempCreateCellSet.contains(row.getRowNum() + "_" + row.getCell(i).getColumnIndex())) {
                    this.setValueForCellByMap(row.getCell(i), map);
                }
            }
        }
    }

    private void handlerSumCell(Sheet sheet) {
        Iterator var2 = this.templateSumHandler.getDataList().iterator();

        while (var2.hasNext()) {
            TemplateSumEntity sumEntity = (TemplateSumEntity)var2.next();
            Cell cell = sheet.getRow(sumEntity.getRow()).getCell(sumEntity.getCol());
            if (cell.getStringCellValue().contains(sumEntity.getSumKey())) {
                cell.setCellValue(cell.getStringCellValue().replace("sum:(" + sumEntity.getSumKey() + ")",
                    sumEntity.getValue() + ""));
            } else {
                cell.setCellValue(cell.getStringCellValue() + sumEntity.getValue());
            }
        }

    }

    private void colForeach(Sheet sheet, Map<String, Object> map) throws Exception {
        Row row = null;
        Cell cell = null;
        int index = 0;

        while (true) {
            do {
                if (index > sheet.getLastRowNum()) {
                    return;
                }

                row = sheet.getRow(index++);
            } while (row == null);

            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); ++i) {
                cell = row.getCell(i);
                if (row.getCell(i) != null
                    && (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.NUMERIC)) {
                    String text = PoiCellUtil.getCellValue(cell);
                    if (text.contains("#fe:") || text.contains("v_fe:")) {
                        this.foreachCol(cell, map, text);
                    }
                }
            }
        }
    }

    private void foreachCol(Cell cell, Map<String, Object> map, String name) throws Exception {
        boolean isCreate = name.contains("v_fe:");
        name = name.replace("v_fe:", "").replace("#fe:", "").replace("{{", "");
        String[] keys = name.replaceAll("\\s{1,}", " ").trim().split(" ");
        Collection<?> datas = (Collection)PoiPublicUtil.getParamsValue(keys[0], map);
        Object[] columnsInfo = this.getAllDataColumns(cell, name.replace(keys[0], ""), this.mergedRegionHelper);
        if (datas != null) {
            Iterator<?> its = datas.iterator();
            int rowspan = (Integer)columnsInfo[0];
            int colspan = (Integer)columnsInfo[1];

            for (List columns = (List)columnsInfo[2]; its.hasNext();
                cell = cell.getRow().getCell(cell.getColumnIndex() + colspan)) {

                // 定义循环开始行号
                int startRowNum = cell.getRow().getRowNum();

                Object t = its.next();
                this.setForeachRowCellValue(true, cell.getRow(), cell.getColumnIndex(), t, columns, map, rowspan,
                    colspan, this.mergedRegionHelper, startRowNum);
                if (cell.getRow().getCell(cell.getColumnIndex() + colspan) == null) {
                    cell.getRow().createCell(cell.getColumnIndex() + colspan);
                }
            }

            if (isCreate) {
                cell = cell.getRow().getCell(cell.getColumnIndex() - 1);
                cell.setCellValue(cell.getStringCellValue() + "}}");
            }

        }
    }

    private void deleteCell(Sheet sheet, Map<String, Object> map) throws Exception {
        Row row = null;
        Cell cell = null;
        int index = 0;

        while (true) {
            do {
                if (index > sheet.getLastRowNum()) {
                    return;
                }

                row = sheet.getRow(index++);
            } while (row == null);

            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); ++i) {
                cell = row.getCell(i);
                if (row.getCell(i) != null
                    && (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.NUMERIC)) {
                    cell.setCellType(CellType.STRING);
                    String text = cell.getStringCellValue();
                    if (text.contains("!if:")) {
                        if (Boolean.valueOf(PoiElUtil
                            .eval(text.substring(text.indexOf("{{") + 2, text.indexOf("}}")).trim(), map).toString())) {
                            PoiSheetUtil.deleteColumn(sheet, i);
                            --i;
                        }

                        cell.setCellValue("");
                    }
                }
            }
        }
    }

    private void setValueForCellByMap(Cell cell, Map<String, Object> map) throws Exception {
        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING || cellType == CellType.NUMERIC) {
            String oldString = cell.getStringCellValue();
            if (oldString != null && oldString.indexOf("{{") != -1 && !oldString.contains("fe:")) {
                boolean isNumber = false;
                if (this.isHasSymbol(oldString, "n:")) {
                    isNumber = true;
                    oldString = oldString.replaceFirst("n:", "");
                }

                boolean isStyleBySelf = false;
                if (this.isHasSymbol(oldString, "sy:")) {
                    isStyleBySelf = true;
                    oldString = oldString.replaceFirst("sy:", "");
                }

                boolean isDict = false;
                String dict = null;
                if (this.isHasSymbol(oldString, "dict:")) {
                    isDict = true;
                    dict = oldString.substring(oldString.indexOf("dict:") + 5).split(";")[0];
                    oldString = oldString.replaceFirst("dict:", "");
                }

                boolean isI18n = false;
                if (this.isHasSymbol(oldString, "i18n:")) {
                    isI18n = true;
                    oldString = oldString.replaceFirst("i18n:", "");
                }

                if (this.isHasSymbol(oldString, "merge:")) {
                    String mergeStr = PoiPublicUtil.getElStr(oldString, "merge:");
                    oldString = oldString.replace(mergeStr, "");
                    mergeStr = mergeStr.replaceFirst("merge:", "");

                    try {
                        int colSpan = (int)Double.parseDouble(PoiPublicUtil.getRealValue(mergeStr, map).toString());
                        PoiMergeCellUtil.addMergedRegion(cell.getSheet(), cell.getRowIndex(), cell.getRowIndex(),
                            cell.getColumnIndex(), cell.getColumnIndex() + colSpan - 1);
                    } catch (Exception var12) {
                        LOGGER.error(var12.getMessage(), var12);
                    }
                }

                Object obj = PoiPublicUtil.getRealValue(oldString, map);
                if (isDict) {
                    obj = this.dictHandler.toName(dict, (Object)null, oldString, obj);
                }

                if (isI18n) {
                    obj = this.i18nHandler.getLocaleName(obj.toString());
                }

                if (obj instanceof ImageEntity) {
                    ImageEntity img = (ImageEntity)obj;
                    cell.setCellValue("");
                    if (img.getRowspan() > 1 || img.getColspan() > 1) {
                        img.setHeight(0);
                        PoiMergeCellUtil.addMergedRegion(cell.getSheet(), cell.getRowIndex(),
                            cell.getRowIndex() + img.getRowspan() - 1, cell.getColumnIndex(),
                            cell.getColumnIndex() + img.getColspan() - 1);
                    }

                    this.createImageCell(cell, (double)img.getHeight(), img.getRowspan(), img.getColspan(),
                        img.getUrl(), img.getData());
                } else if (isNumber && StringUtils.isNotBlank(obj.toString())) {
                    cell.setCellValue(Double.parseDouble(obj.toString()));
                } else {
                    cell.setCellValue(obj.toString());
                }
            }

            if (oldString != null && oldString.contains("fe:")) {
                this.addListDataToExcel(cell, map, oldString.trim());
            }

        }
    }

    private boolean isHasSymbol(String text, String symbol) {
        return text.startsWith(symbol) || text.contains("{" + symbol) || text.contains(" " + symbol);
    }

    private Row createRow(int rowIndex, Sheet sheet, boolean isCreate, int rows) {
        for (int i = 0; i < rows; ++i) {
            if (isCreate) {
                sheet.createRow(rowIndex++);
            } else if (sheet.getRow(rowIndex++) == null) {
                sheet.createRow(rowIndex - 1);
            }
        }

        return sheet.getRow(rowIndex - rows);
    }

    /**
     * @Author: 徐本锡
     * @Date: 2022/3/7 16:59
     * @param: [isCreate, row, columnIndex, t, columns, map, rowspan, colspan, mergedRegionHelper, startRowNum]
     * @return: int[]
     * @description: 修改源码 ：list循环遍历的时候 存在合并单元格情况 导致格式错乱
     */
    private int[] setForeachRowCellValue(boolean isCreate, Row row, int columnIndex, Object t,
        List<ExcelForEachParams> columns, Map<String, Object> map, int rowspan, int colspan,
        MergedRegionHelper mergedRegionHelper, int startRowNum) throws Exception {
        this.createRowCellSetStyle(row, columnIndex, columns, rowspan, colspan);
        int loopSize = 1;
        int loopCi = 1;
        row = row.getSheet().getRow(row.getRowNum() - rowspan + 1);

        for (int k = 0; k < rowspan; ++k) {
            int ci = columnIndex;
            row.setHeight(this.getMaxHeight(k, colspan, columns));

            for (int i = 0; i < colspan && i < columns.size(); ++i) {
                boolean isNumber = false;
                ExcelForEachParams params = (ExcelForEachParams)columns.get(colspan * k + i);
                this.tempCreateCellSet.add(row.getRowNum() + "_" + ci);
                if (params != null) {
                    if (StringUtils.isEmpty(params.getName()) && StringUtils.isEmpty(params.getConstValue())) {
                        row.getCell(ci).setCellStyle(params.getCellStyle());
                        ci += params.getColspan();
                    } else {
                        Object obj = null;
                        String tempStr = params.getName();
                        String val;
                        boolean isNeedMerge;
                        if (StringUtils.isEmpty(params.getName())) {
                            val = params.getConstValue();
                        } else {
                            if (this.isHasSymbol(tempStr, "n:")) {
                                isNumber = true;
                                tempStr = tempStr.replaceFirst("n:", "");
                            }

                            map.put(this.templateParams.getTempParams(), t);
                            isNeedMerge = false;
                            String dict = null;
                            if (this.isHasSymbol(tempStr, "dict:")) {
                                isNeedMerge = true;
                                dict = tempStr.substring(tempStr.indexOf("dict:") + 5).split(";")[0];
                                tempStr = tempStr.replaceFirst("dict:", "");
                                tempStr = tempStr.replaceFirst(dict + ";", "");
                            }

                            obj = PoiElUtil.eval(tempStr, map);
                            if (isNeedMerge && !(obj instanceof Collection)) {
                                obj = this.dictHandler.toName(dict, t, tempStr, obj);
                            }

                            val = obj.toString();
                        }

                        if (obj != null && obj instanceof Collection) {
                            String collectName = PoiElUtil.evalFindName(tempStr, map);
                            int[] loop = this.setForEachLoopRowCellValue(row, ci, (Collection)obj, columns, params, map,
                                rowspan, colspan, mergedRegionHelper, collectName);
                            loopSize = Math.max(loopSize, loop[0]);
                            i += loop[1] - 1;
                            ci = loop[2] - params.getColspan();
                        } else if (obj != null && obj instanceof ImageEntity) {
                            ImageEntity img = (ImageEntity)obj;
                            row.getCell(ci).setCellValue("");
                            if (img.getRowspan() > 1 || img.getColspan() > 1) {
                                img.setHeight(0);
                                row.getCell(ci).getSheet()
                                    .addMergedRegion(new CellRangeAddress(row.getCell(ci).getRowIndex(),
                                        row.getCell(ci).getRowIndex() + img.getRowspan() - 1,
                                        row.getCell(ci).getColumnIndex(),
                                        row.getCell(ci).getColumnIndex() + img.getColspan() - 1));
                            }

                            this.createImageCell(row.getCell(ci), (double)img.getHeight(), img.getRowspan(),
                                img.getColspan(), img.getUrl(), img.getData());
                        } else if (isNumber && StringUtils.isNotEmpty(val)) {
                            row.getCell(ci).setCellValue(Double.parseDouble(val));
                        } else {
                            try {
                                row.getCell(ci).setCellValue(val);
                            } catch (Exception var22) {
                                LOGGER.error(var22.getMessage(), var22);
                            }
                        }

                        if (params.getCellStyle() != null) {
                            row.getCell(ci).setCellStyle(params.getCellStyle());
                        }

                        if (params.isNeedSum()) {
                            this.templateSumHandler.addValueOfKey(params.getName(), val);
                        }

                        this.setMergedRegionStyle(row, ci, params);
                        // isNeedMerge = (params.getRowspan() != 1 || params.getColspan() != 1) &&
                        // !mergedRegionHelper.isMergedRegion(row.getRowNum() + 1, ci);
                        // if (isNeedMerge) {
                        // PoiMergeCellUtil.addMergedRegion(row.getSheet(), row.getRowNum(), row.getRowNum() +
                        // params.getRowspan() - 1, ci, ci + params.getColspan() - 1);
                        // }

                        // 合并对应单元格
                        // 存在合并单元格时，这个判断出问题了，需要注释
                        // && !mergedRegionHelper.isMergedRegion(row.getRowNum() + 1, ci)
                        // 将第二个参数改为：循环开始行号
                        // 原因：这个方法原先是判断当前行这一列，是否需要合并单元格
                        // 如果是新创建的行，这个方法恒定返回 false ，判断出现问题
                        // 所以需要改为：判断循环开始行这一列，是否需要合并单元格
                        isNeedMerge = (params.getRowspan() != 1 || params.getColspan() != 1)
                            && PoiCellUtil.isMergedRegion(row.getSheet(), startRowNum, ci);
                        if (isNeedMerge) {
                            Sd3eUtil.addMergedRegionByListForEach(row.getSheet(), row.getRowNum(),
                                row.getRowNum() + params.getRowspan() - 1, ci, ci + params.getColspan() - 1);
                        }

                        if (params.getRowspan() == 1 && params.getColspan() == 1) {
                            row.getCell(ci).getSheet().setColumnWidth(row.getCell(ci).getColumnIndex(),
                                params.getWidth());
                        }

                        ci += params.getColspan();
                    }
                }
            }

            loopCi = Math.max(loopCi, ci);
            if (loopSize > 1) {
                this.handlerLoopMergedRegion(row, columnIndex, columns, loopSize);
            }

            row = row.getSheet().getRow(row.getRowNum() + 1);
        }

        return new int[] {loopSize, loopCi};
    }

    private void handlerLoopMergedRegion(Row row, int columnIndex, List<ExcelForEachParams> columns, int loopSize) {
        for (int i = 0; i < columns.size(); ++i) {
            if (!((ExcelForEachParams)columns.get(i)).isCollectCell()) {
                PoiMergeCellUtil.addMergedRegion(row.getSheet(), row.getRowNum(), row.getRowNum() + loopSize - 1,
                    columnIndex, columnIndex + ((ExcelForEachParams)columns.get(i)).getColspan() - 1);
            }

            columnIndex += ((ExcelForEachParams)columns.get(i)).getColspan();
        }

    }

    private short getMaxHeight(int k, int colspan, List<ExcelForEachParams> columns) {
        short high = ((ExcelForEachParams)columns.get(0)).getHeight();

        for (int n = k; n > 0; --n) {
            if (((ExcelForEachParams)columns.get(n * colspan)).getHeight() != 0) {
                high = ((ExcelForEachParams)columns.get(n * colspan)).getHeight();
                break;
            }
        }

        return high;
    }

    private int[] setForEachLoopRowCellValue(Row row, int columnIndex, Collection obj, List<ExcelForEachParams> columns,
        ExcelForEachParams params, Map<String, Object> map, int rowspan, int colspan,
        MergedRegionHelper mergedRegionHelper, String collectName) throws Exception {
        List<ExcelForEachParams> temp = this.getLoopEachParams(columns, columnIndex, collectName);
        Iterator<?> its = obj.iterator();
        Row tempRow = row;
        int nums = 0;

        int ci;
        int[] loopArr;
        for (ci = columnIndex; its.hasNext();
            tempRow = this.createRow(tempRow.getRowNum() + loopArr[0], row.getSheet(), false, rowspan)) {
            // 定义循环开始行号
            int startRowNum = tempRow.getRowNum();

            Object data = its.next();
            map.put("loop_" + columnIndex, data);
            loopArr = this.setForeachRowCellValue(false, tempRow, columnIndex, data, temp, map, rowspan, colspan,
                mergedRegionHelper, startRowNum);
            nums += loopArr[0];
            ci = Math.max(ci, loopArr[1]);
            map.remove("loop_" + columnIndex);
        }

        for (int i = 0; i < temp.size(); ++i) {
            ((ExcelForEachParams)temp.get(i)).setName((String)((ExcelForEachParams)temp.get(i)).getTempName().pop());
            ((ExcelForEachParams)temp.get(i)).setCollectCell(true);
        }

        return new int[] {nums, temp.size(), ci};
    }

    /**
     * @Author: 徐本锡
     * @Date: 2022/3/7 17:04
     * @param: [columns, columnIndex, collectName]
     * @return: java.util.List<cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams>
     * @description: 修改：解决一对多 row循环中，row元素中包含list并且合并单元格时，报空的错误
     */
    private List<ExcelForEachParams> getLoopEachParams(List<ExcelForEachParams> columns, int columnIndex,
        String collectName) {
        List<ExcelForEachParams> temp = new ArrayList();

        for (int i = 0; i < columns.size(); ++i) {

            // 解决row循环中，row元素中包含list并且合并单元格时，报空的错误
            if (columns.get(i) == null) {
                continue;
            }

            ((ExcelForEachParams)columns.get(i)).setCollectCell(false);
            if (columns.get(i) == null || ((ExcelForEachParams)columns.get(i)).getName().contains(collectName)) {
                temp.add(columns.get(i));
                if (((ExcelForEachParams)columns.get(i)).getTempName() == null) {
                    ((ExcelForEachParams)columns.get(i)).setTempName(new Stack());
                }

                ((ExcelForEachParams)columns.get(i)).setCollectCell(true);
                ((ExcelForEachParams)columns.get(i)).getTempName().push(((ExcelForEachParams)columns.get(i)).getName());
                ((ExcelForEachParams)columns.get(i)).setName(
                    ((ExcelForEachParams)columns.get(i)).getName().replace(collectName, "loop_" + columnIndex));
            }
        }

        return temp;
    }

    private void createRowCellSetStyle(Row row, int columnIndex, List<ExcelForEachParams> columns, int rowspan,
        int colspan) {
        for (int i = 0; i < rowspan; ++i) {
            int size = columns.size();
            int j = columnIndex;

            for (int max = columnIndex + colspan; j < max; ++j) {
                if (row.getCell(j) == null) {
                    row.createCell(j);
                    CellStyle style = row.getRowNum() % 2 == 0
                        ? this.getStyles(false,
                            size <= j - columnIndex ? null : (ExcelForEachParams)columns.get(j - columnIndex))
                        : this.getStyles(true,
                            size <= j - columnIndex ? null : (ExcelForEachParams)columns.get(j - columnIndex));
                    if (style != null) {
                        row.getCell(j).setCellStyle(style);
                    }
                }
            }

            if (i < rowspan - 1) {
                row = row.getSheet().getRow(row.getRowNum() + 1);
            }
        }

    }

    private CellStyle getStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
        return this.excelExportStyler.getTemplateStyles(isSingle, excelForEachParams);
    }

    private void setMergedRegionStyle(Row row, int ci, ExcelForEachParams params) {
        int i;
        for (i = 1; i < params.getColspan(); ++i) {
            if (params.getCellStyle() != null) {
                row.getCell(ci + i).setCellStyle(params.getCellStyle());
            }
        }

        for (i = 1; i < params.getRowspan(); ++i) {
            for (int j = 0; j < params.getColspan(); ++j) {
                if (params.getCellStyle() != null) {
                    row.getCell(ci + j).setCellStyle(params.getCellStyle());
                }
            }
        }

    }

    private Object[] getAllDataColumns(Cell cell, String name, MergedRegionHelper mergedRegionHelper) {
        List<ExcelForEachParams> columns = new ArrayList();
        cell.setCellValue("");
        columns.add(this.getExcelTemplateParams(name.replace("}}", ""), cell, mergedRegionHelper));
        int rowspan = 1;
        int colspan = 1;
        int index;
        if (!name.contains("}}")) {
            index = cell.getColumnIndex();
            int startIndex = cell.getColumnIndex();
            Row row = cell.getRow();

            label81:
            while (true) {
                while (true) {
                    if (index >= row.getLastCellNum()) {
                        break label81;
                    }

                    int colSpan = columns.get(columns.size() - 1) != null
                        ? ((ExcelForEachParams)columns.get(columns.size() - 1)).getColspan() : 1;
                    index += colSpan;

                    for (int i = 1; i < colSpan; ++i) {
                        columns.add(null);
                    }

                    cell = row.getCell(index);
                    if (cell == null) {
                        columns.add(null);
                    } else {
                        String cellStringString;
                        try {
                            cellStringString = cell.getStringCellValue();
                            if (StringUtils.isBlank(cellStringString) && colspan + startIndex <= index) {
                                throw new ExcelExportException("for each 当中存在空字符串,请检查模板");
                            }

                            if (StringUtils.isBlank(cellStringString) && colspan + startIndex > index) {
                                columns.add(new ExcelForEachParams((String)null, cell.getCellStyle(), (short)0));
                                continue;
                            }
                        } catch (Exception var14) {
                            throw new ExcelExportException(ExcelExportEnum.TEMPLATE_ERROR, var14);
                        }

                        cell.setCellValue("");
                        if (cellStringString.contains("}}")) {
                            columns.add(this.getExcelTemplateParams(cellStringString.replace("}}", ""), cell,
                                mergedRegionHelper));
                            int lastCellColspan = ((ExcelForEachParams)columns.get(columns.size() - 1)).getColspan();
                            int i = 1;

                            while (true) {
                                if (i >= lastCellColspan) {
                                    break label81;
                                }

                                columns.add(null);
                                ++i;
                            }
                        }

                        if (cellStringString.contains("]]")) {
                            columns.add(this.getExcelTemplateParams(cellStringString.replace("]]", ""), cell,
                                mergedRegionHelper));
                            colspan = index - startIndex + 1;
                            index = startIndex - ((ExcelForEachParams)columns.get(columns.size() - 1)).getColspan();
                            row = row.getSheet().getRow(row.getRowNum() + 1);
                            ++rowspan;
                        } else {
                            columns.add(this.getExcelTemplateParams(cellStringString.replace("]]", ""), cell,
                                mergedRegionHelper));
                        }
                    }
                }
            }
        }

        colspan = 0;

        for (index = 0; index < columns.size(); ++index) {
            colspan += columns.get(index) != null ? ((ExcelForEachParams)columns.get(index)).getColspan() : 0;
        }

        colspan /= rowspan;
        return new Object[] {rowspan, colspan, columns};
    }

    private ExcelForEachParams getExcelTemplateParams(String name, Cell cell, MergedRegionHelper mergedRegionHelper) {
        name = name.trim();
        ExcelForEachParams params = new ExcelForEachParams(name, cell.getCellStyle(), cell.getRow().getHeight());
        if (name.startsWith("'") && name.endsWith("'")) {
            params.setName((String)null);
            params.setConstValue(name.substring(1, name.length() - 1));
        }

        if ("&NULL&".equals(name)) {
            params.setName((String)null);
            params.setConstValue("");
        }

        if ("&INDEX&".equals(name)) {
            params.setName((String)null);
            params.setConstValue("&INDEX&");
        }

        if (mergedRegionHelper.isMergedRegion(cell.getRowIndex() + 1, cell.getColumnIndex())) {
            Integer[] colAndrow = mergedRegionHelper.getRowAndColSpan(cell.getRowIndex() + 1, cell.getColumnIndex());
            params.setRowspan(colAndrow[0]);
            params.setColspan(colAndrow[1]);
        }

        params.setNeedSum(this.templateSumHandler.isSumKey(params.getName()));
        params.setWidth(cell.getSheet().getColumnWidth(cell.getColumnIndex()));
        return params;
    }

    private void sortAndFilterExportField(List<ExcelExportEntity> excelParams, Map<String, Integer> titlemap) {
        for (int i = excelParams.size() - 1; i >= 0; --i) {
            if (((ExcelExportEntity)excelParams.get(i)).getList() != null
                && ((ExcelExportEntity)excelParams.get(i)).getList().size() > 0) {
                this.sortAndFilterExportField(((ExcelExportEntity)excelParams.get(i)).getList(), titlemap);
                if (((ExcelExportEntity)excelParams.get(i)).getList().size() == 0) {
                    excelParams.remove(i);
                } else {
                    ((ExcelExportEntity)excelParams.get(i)).setOrderNum(i);
                }
            } else if (titlemap.containsKey(((ExcelExportEntity)excelParams.get(i)).getName())) {
                ((ExcelExportEntity)excelParams.get(i)).setOrderNum(i);
            } else {
                excelParams.remove(i);
            }
        }

        this.sortAllParams(excelParams);
    }
}
