package com.example.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Title: ExcelExportUtils
 * Package: com.doyd.core.excel
 * Description：
 * Author: 周涛
 * Date: Created in 2019/8/5 10:45
 */
public class ExcelExportUtils {

    /**
     *
     * @param response http响应类
     * @param fileName 文件名称
     * @param dataSet 数据集合
     * @param tClass
     * @param <T>
     */
    public static <T extends BaseRowModel> void export(HttpServletResponse response, String fileName, List<T> dataSet, Class<T> tClass){
        try (OutputStream out = response.getOutputStream()){
            response.setContentType("multipart/form-data;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            Sheet sheet1 = new Sheet(1, 0, tClass);
            writer.write(dataSet, sheet1);
            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
