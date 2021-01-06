package org.mayanze.dcims.excel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author: mayanze
 * date: 2020/12/13 12:12 下午
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService{

    //数据头
    private String[] dataHeadrs = {"询价商品名称", "规格描述", "询价品牌", "询价数量", "单位", "特殊要求"};
    private String[] tempHeadrs = {"名称", "牌号、规格", "品牌", "数量", "单位", "备注说明"};
    private static final String targetExcelName = "天石模板.xlsx";

    /**
     * 天石业务
     * 1. 从多个文件中，分别拿到数据流：source_inps（可以多个），和一个模板流：temp_inp
     * 2. 遍历数据流，新的数据流（另一个数据excel），追加模板excel,需要隔开【两个】空行，
     *    以及一个合并11列的行填值：询价单编号：     询价人姓名：     截止报价：
     *    再复制一行列头
     * 3. 新流，序号从1开始
     * 4. 数据流处理：
     *    4.1 根据列头对应名称获取对应【数据】在那些列
     *    4.2 根据列头对应名称获取对应【模板数据】在那些列
     *    4.3 寻找模板中应该从那一行开始追加数据（row 为null的那一行）
     *    4.4 复制列头样式，加上自动换行，以及2、3列左对齐方式作为新单元格样式
     *    4.5 复制数据表格中的数据到追加到模板中
     *
     * @param inps 多个输入流，其中【天石模板】属于目标excel,其他属于数据源excel
     */
    @SneakyThrows
    @Override
    public Workbook tsyw(MultipartFile[] files) {
        Workbook temp_wb = null;
        List<InputStream> source_inps = new ArrayList<>();
        for (MultipartFile file : files) {//获取数据源excel和目标excel
            String name = file.getOriginalFilename();
            if (targetExcelName.equals(name)) {//目标excel
                InputStream temp_inp = file.getInputStream();
                temp_wb = WorkbookFactory.create(temp_inp);
            } else {
                source_inps.add(file.getInputStream());
            }
        }
        Assert.notNull(temp_wb, "天石模板excel不能为空");
        Assert.notEmpty(source_inps, "数据excel不能为空");
        for (InputStream source_inp : source_inps) {//遍历数据源excel追加到目标源上
            Workbook wb = WorkbookFactory.create(source_inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row10 = sheet.getRow(10);//列头
            //寻找数据源excel需要复制的数据在那几列
            List<Integer> headrIndex = getHeadrIndex(row10, dataHeadrs);

            Sheet temp_sheet = temp_wb.getSheetAt(0);
            Row temp_row1 = temp_sheet.getRow(1);
            //寻找应该被复制到模板excel中的那几列
            List<Integer> tempHeadrsIndex = getHeadrIndex(temp_row1, tempHeadrs);
            //寻找头部行的数据样式
            Row temp_row2 = temp_sheet.getRow(1);

            if (headrIndex.size() != tempHeadrsIndex.size()) {
                throw new RuntimeException("源数据需要复制列数，与模板被需要复制列数不匹配，请检查");
            }
            int temRowDataFirstIndex = temp_sheet.getLastRowNum()+1;//模板excel从第几行开始追加数据,最后一行加1

            //2. 遍历数据流，新的数据流（另一个数据excel），追加模板excel,需要隔开【两个】空行，
            // 以及一个合并11列的行,填值：询价单编号：     询价人姓名：     截止报价：
            // 复制一行列头
            if(temRowDataFirstIndex > 2){//非空模板才需要此操作
                temRowDataFirstIndex+=2;//空两行
                Row mergedTempRow = temp_sheet.createRow(temRowDataFirstIndex);//创建合并行
                mergedTempRow.createCell(0);
                int mergedRegion = temp_sheet.addMergedRegion(new CellRangeAddress(
                        temRowDataFirstIndex, //first row (0-based)
                        temRowDataFirstIndex, //last row  (0-based)
                        0, //first column (0-based)
                        10  //last column  (0-based)
                ));
                Cell mergedCell = mergedTempRow.getCell(0);
                mergedCell.setCellValue("询价单编号：     询价人姓名：     截止报价：");

                //复制一行列头
                temRowDataFirstIndex++;
                Row tempNewHeardRow = temp_sheet.createRow(temRowDataFirstIndex);
                for (int i = 0; i < 11; i++) {
                    Cell cell = tempNewHeardRow.createCell(i);
                    cell.setCellStyle(temp_row1.getCell(i).getCellStyle());//复制头样式
                    cell.setCellValue(temp_row1.getCell(i).getStringCellValue());//复制头值
                }
                temRowDataFirstIndex++;//为新的一行数据做准备
            }

            for (int i = 0; i < 10000; i++) {//最多获取10000行数据
                Row dataRow = sheet.getRow(i + 11);//第12行开始是源数据行，下标需要从0开始需减1
                if (dataRow == null) {//数据复制完之后跳出复制
                    break;
                }

                Row tempRow = temp_sheet.createRow(temRowDataFirstIndex + i);//新追加数据行

                //复制第一行的样式
//                tempRow.setHeight(temp_row2.getHeight());
                for (int j = 0; j < 11; j++) {//总共11列
                    Cell cell = tempRow.createCell(j);

                    CellStyle cellStyle = temp_wb.createCellStyle();;
                    CellStyle head_cellStyle = temp_row2.getCell(j).getCellStyle();
                    cellStyle.cloneStyleFrom(head_cellStyle);
                    cellStyle.setWrapText(true);

                    if(j == 2 || j == 3){//第二列和第三列左对齐
                        cellStyle.setAlignment(HorizontalAlignment.LEFT);
                    }

                    cell.setCellStyle(cellStyle);
                    if (j == 0) {
                        Cell cellNo = tempRow.getCell(0);//序号
                        cellNo.setCellValue(i + 1);//设置序号i
                    }
                }

                //复制数据
                for (int i1 = 0; i1 < headrIndex.size(); i1++) {
                    Cell cell = dataRow.getCell(headrIndex.get(i1));
                    Cell cell1 = tempRow.getCell(tempHeadrsIndex.get(i1));
                    cell1.setCellValue(cell.getStringCellValue());
                }
            }
        }

        return temp_wb;
    }

    /**
     * 根据需要数据的头名称数组，寻找对应哪些列
     *
     * @param row
     */
    private List<Integer> getHeadrIndex(Row row, String[] dataHeadrs) {
        List<Integer> headrIndex = new ArrayList<>();
        H:
        for (int i1 = 0; i1 < dataHeadrs.length; i1++) {
            for (int i = 0; i < 30; i++) {
                if (row.getCell(i) != null) {
                    String heard = row.getCell(i).getStringCellValue();
                    if (dataHeadrs[i1].equals(heard)) {
                        headrIndex.add(i);
                        continue H;
                    }
                }
            }
        }
        return headrIndex;
    }
}
