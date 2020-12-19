package org.mayanze.dcims.excel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
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
    private String[] dataHeadrs = {"询价商品名称", "规格描述", "询价品牌/制造商", "询价数量", "单位", "特殊要求"};
    private String[] tempHeadrs = {"名称", "牌号、规格", "品牌", "数量", "单位", "备注说明"};
    private static final String targetExcelName = "天石模板.xlsx";

    /**
     * 天石业务
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
            //寻找第头部行的数据样式
            Row temp_row2 = temp_sheet.getRow(1);

            if (headrIndex.size() != tempHeadrsIndex.size()) {
                throw new RuntimeException("源数据需要复制列数，与模板被需要复制列数不匹配，请检查");
            }
            int temRowDataFirstIndex = 0;//模板excel从第几行开始追加数据
            for (int j = 0; j < 10000; j++) {//找从第几行是空的，开始追加数据；最多找10000行
                Row tempRow = temp_sheet.getRow(j);
                if (tempRow == null) {
                    temRowDataFirstIndex = j;
                    break;
                }

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
