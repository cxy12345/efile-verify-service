package com.verify.efileverifyservice.excel.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version v1.0
 * @ProjectName: easypoi-test
 * @ClassName: Sd3eUtil
 * @Description:
 * @Author: xbx
 * @Date: 2022/3/7 16:49
 */
public class Sd3eUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sd3eUtil.class);

    /**
     * @Author: 徐本锡
     * @Date: 2022/3/7 16:49
     * @param: [sheet, firstRow, lastRow, firstCol, lastCol]
     * @return: void
     * @description: list循环的时候 存在合并单元格的时候 格式错乱问题
     */
    public static void addMergedRegionByListForEach(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        try {
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        } catch (IllegalStateException var6) {
            // 合并单元格的循环 可能存在 已经合并的再次合并 不管他
        } catch (Exception var6) {
            LOGGER.debug("发生了一次合并单元格错误,{},{},{},{}", new Integer[] {firstRow, lastRow, firstCol, lastCol});
            LOGGER.debug(var6.getMessage(), var6);
        }

    }
}
