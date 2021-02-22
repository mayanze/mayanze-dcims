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

    //天石业务数据头
    private String[] dataHeadrs = {"询价商品名称", "规格描述", "询价品牌", "询价数量", "单位", "特殊要求"};
    private String[] tempHeadrs = {"名称", "牌号、规格", "品牌", "数量", "单位", "备注说明"};

    //天石业务-报价数据头
    private String[] offter_dataHeadrs = {"询价人姓名", "截止报价", "询价单编号", "名称", "牌号、规格", "品牌", "数量", "单位"};
    private String[] offter_tempHeadrs = {"姓名", "时间", "询价单编号", "名称", "牌号、规格", "产地或品牌", "数量", "单位"};
    private String targetExcelName;
    private Integer appendFirstRow;//新追加数据的行下标

    @Override
    public String getTargetExcelName() {
        return targetExcelName;
    }

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
        for (int i = 0; i < files.length; i++) {//获取数据源excel和目标excel
            MultipartFile file = files[i];
            if(i == files.length-1){//最后一个文件为模板文件
                targetExcelName = file.getOriginalFilename();
                InputStream temp_inp = file.getInputStream();
                temp_wb = WorkbookFactory.create(temp_inp);
            }else {//剩下的都是数据文件
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
            this.appendFirstRow = temRowDataFirstIndex;
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
        //自适应高度
        Sheet sheetAt = temp_wb.getSheetAt(0);
        for (int i = this.appendFirstRow+3; i <= sheetAt.getLastRowNum(); i++) {
            Row row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();
            float height = 0;
            for (int j = 0; j < lastCellNum; j++) {
                int rowBytesLength = row.getCell(j).toString().getBytes().length;
                double floor = Math.floor(sheetAt.getColumnWidthInPixels(row.getCell(j).getColumnIndex()));
                float rowHeight = (float)Math.ceil(rowBytesLength / floor);
                if (rowHeight > height) {
                    height = rowHeight;
                }
            }
            row.setHeightInPoints(height*20);
        }
        return temp_wb;
    }

    /**
     * 天石业务-报价
     * 1. 从多个文件中，分别拿到数据流：source_inps（可以多个），和一个报价流：offter_inp
     * 2. 从数据源询价人姓名找到报价execl对应sheet
     * 3. 总共匹配8列，"询价人姓名", "截止报价", "询价单编号" 【3列】取合并单元格的值
     * 4. "名称", "牌号、规格", "品牌", "数量", "单位" 各自表格对应
     *
     * @param inps 多个输入流，其中【天石模板】属于目标excel,其他属于数据源excel
     */
    @SneakyThrows
    @Override
    public Workbook tsyw_offter(MultipartFile[] files) {
        Workbook offter_wb = null;
        List<InputStream> source_inps = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {//获取数据源excel和目标excel
            MultipartFile file = files[i];
            if(i == files.length-1){//最后一个文件为模板文件
                targetExcelName = file.getOriginalFilename();
                InputStream offter_inp = file.getInputStream();
                offter_wb = WorkbookFactory.create(offter_inp);
            }else {//剩下的都是数据文件
                source_inps.add(file.getInputStream());
            }
        }

        Assert.notNull(offter_wb, "天石报价excel不能为空");
        Assert.notEmpty(source_inps, "数据excel不能为空");
        //遍历数据源excel追加到目标源上
        for (int k = 0; k < source_inps.size(); k++) {
            InputStream source_inp = source_inps.get(k);
            Workbook wb = WorkbookFactory.create(source_inp);
            Sheet sheet = wb.getSheetAt(0);
            Row rowPart = null;//第一行，合并数据行部分
            Row rowHead = null;//数据头部分
            Sheet offter_sheet = null;//报价sheet
            int dataRowNumStart = 0;//数据行开始部分
            int temRowDataFirstIndex = 0;//模板excel从第几行开始追加数据
            List<Integer> headrIndex = new ArrayList<>();//寻找数据源excel需要复制的数据在那几列
            List<Integer> tempHeadrsIndex = new ArrayList<>();//寻找应该被复制到模板excel中的那几列
            String enquiry_name_value = "";//询价人姓名
            String quotation_value = "";//截止报价
            String enquiry_no_value = "";//询价单编号
            for (int i = 0; i < 65535; i++) {//最多获取65535行数据,//第2行开始是源数据头部分行，下标需要从0开始需减1
                if(i == 0){
                    rowPart = sheet.getRow(i);
                    rowHead = sheet.getRow(1);//数据列头所在行
                    //寻找数据源excel需要复制的数据在那几列
                    headrIndex = getHeadrIndex(rowHead, offter_dataHeadrs);
                    enquiry_no_value = getEnquiry_no_value(rowPart);//询价单编号
                    quotation_value = getQuotation_value(rowPart);//截止报价
                    // 根据数据execl询价人姓名找到报价execl中的sheet
                    enquiry_name_value = getEnquiry_name_value(rowPart);
                    offter_sheet = offter_wb.getSheet(enquiry_name_value);
                    //寻找头部行
                    Row offter_row2 = offter_sheet.getRow(0);
                    //寻找应该被复制到模板excel中的那几列
                    tempHeadrsIndex = getHeadrIndex(offter_row2, offter_tempHeadrs);
                    temRowDataFirstIndex = offter_sheet.getLastRowNum();//模板excel从第几行开始追加数据,最后一行加1
                    if (headrIndex.size() + 3 != tempHeadrsIndex.size()) {//数据中有3列取得是合并单元格的值
                        throw new RuntimeException("源数据需要复制列数，与模板被需要复制列数不匹配，请检查");
                    }
                    dataRowNumStart = rowHead.getRowNum();
                }
                Row dataRow = sheet.getRow(++dataRowNumStart);//数据行是头部行的下一行
                if (dataRow == null) {//数据复制完之后跳出复制
                    //数据execl,有多个数据部分，一个部分完了后，隔了两个行会有新的部分，如果隔了两个行还没有新的部分则视为已经把数据读完
                    Row newPartRow = sheet.getRow(++dataRowNumStart);
                    if(newPartRow == null){//空一行没有数据找空两行
                        newPartRow = sheet.getRow(++dataRowNumStart);
                    }
                    if (newPartRow != null) {
                        rowPart = newPartRow;
                        rowHead = sheet.getRow(++dataRowNumStart);//头行在新部分的下一行
                    } else {
                        break;
                    }
                    //寻找数据源excel需要复制的数据在那几列
                    headrIndex = getHeadrIndex(rowHead, offter_dataHeadrs);
                    enquiry_no_value = getEnquiry_no_value(rowPart);//询价单编号
                    quotation_value = getQuotation_value(rowPart);//截止报价
                    // 根据数据execl询价人姓名找到报价execl中的sheet
                    enquiry_name_value = getEnquiry_name_value(rowPart);
                    offter_sheet = offter_wb.getSheet(enquiry_name_value);
                    //寻找头部行
                    Row offter_row2 = offter_sheet.getRow(0);
                    //寻找应该被复制到模板excel中的那几列
                    tempHeadrsIndex = getHeadrIndex(offter_row2, offter_tempHeadrs);
                    temRowDataFirstIndex = offter_sheet.getLastRowNum();//模板excel从第几行开始追加数据,最后一行加1
                    if (headrIndex.size() + 3 != tempHeadrsIndex.size()) {//数据中有3列取得是合并单元格的值
                        throw new RuntimeException("源数据需要复制列数，与模板被需要复制列数不匹配，请检查");
                    }
                    dataRowNumStart = rowHead.getRowNum();
                    continue;
                }
                Row tempRow = offter_sheet.createRow(++temRowDataFirstIndex);//新追加数据行
                //复制之前最后一行的样式
                for (int j = 0; j < 8; j++) {//总共8列
                    Cell cell = tempRow.createCell(j);

                    CellStyle cellStyle = offter_wb.createCellStyle();;
                    CellStyle head_cellStyle = offter_sheet.getRow(temRowDataFirstIndex).getCell(j).getCellStyle();
                    cellStyle.cloneStyleFrom(head_cellStyle);
                    cellStyle.setWrapText(true);

                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                    cellStyle.setBorderTop(BorderStyle.THIN);
                    cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

                    if(j == 3 || j == 4){//第四列和第五列左对齐
                        cellStyle.setAlignment(HorizontalAlignment.LEFT);
                    }else {
                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    }
                    cell.setCellStyle(cellStyle);
                }

                //复制数据
                for (int i1 = 0; i1 < tempHeadrsIndex.size(); i1++) {
                    Cell cell1 = tempRow.getCell(tempHeadrsIndex.get(i1));
                    switch (i1){
                        case 0://姓名
                            cell1.setCellValue(enquiry_name_value);
                            break;
                        case 1://时间
                            cell1.setCellValue(quotation_value);
                            break;
                        case 2://询价单编号
                            cell1.setCellValue(enquiry_no_value);
                            break;
                        default:
                            Cell cell = dataRow.getCell(headrIndex.get(i1-3));//数据中有3列取得是合并单元格的值
                            cell1.setCellValue(cell.getStringCellValue());
                            break;

                    }
                }
            }
        }
        return offter_wb;
    }

    /**
     * 获取询价人姓名值
     * @param offter_wb
     * @param rowPart
     * @return
     */
    private String getEnquiry_name_value(Row rowPart) {
        String stringCellValue = rowPart.getCell(0).getStringCellValue();
        String enquiry_name = "询价人姓名：";//中文冒号：
        int enquiry_name_start_index = stringCellValue.indexOf(enquiry_name) + enquiry_name.length();//询价人姓名值位置
        int enquiry_name_end_index = stringCellValue.indexOf(" ",enquiry_name_start_index);//询价人姓名值之后空格位置
        for (int i = 0; i < 100; i++) {
            if(enquiry_name_start_index == enquiry_name_end_index){//如果结果等于开始，说明姓名后面是有空格的加一再去找
                enquiry_name_end_index = stringCellValue.indexOf(" ",++enquiry_name_start_index);
            }else {
                break;
            }
        }
        String enquiry_name_value = stringCellValue.substring(enquiry_name_start_index,enquiry_name_end_index);//询价人姓名值
        return enquiry_name_value.trim();
    }

    /**
     * 获取询价单编号值
     * @param offter_wb
     * @param rowPart
     * @return
     */
    private String getEnquiry_no_value(Row rowPart) {
        String stringCellValue = rowPart.getCell(0).getStringCellValue();
        String enquiry_no = "询价单编号：";//中文冒号：
        int enquiry_no_start_index = stringCellValue.indexOf(enquiry_no) + enquiry_no.length();//询价单编号值位置
        int enquiry_no_end_index = stringCellValue.indexOf("（",enquiry_no_start_index);//询价单编号值之后中文括号位置
        for (int i = 0; i < 100; i++) {
            if(enquiry_no_start_index == enquiry_no_end_index){//如果结果等于开始，说明在值前面是有空格的加一再去找
                enquiry_no_end_index = stringCellValue.indexOf("（",++enquiry_no_start_index);
            }else if(enquiry_no_end_index < enquiry_no_start_index){
                enquiry_no_end_index = stringCellValue.indexOf("(",++enquiry_no_start_index);
            }else {
                break;
            }
        }
        String enquiry_no_value = stringCellValue.substring(enquiry_no_start_index,enquiry_no_end_index);//询价人姓名值
        return enquiry_no_value.trim();
    }

    /**
     * 获取截止报价值
     * @param offter_wb
     * @param rowPart
     * @return
     */
    private String getQuotation_value(Row rowPart) {
        String stringCellValue = rowPart.getCell(0).getStringCellValue();
        String quotation = "截止报价：";//中文冒号：
        int quotation_start_index = stringCellValue.indexOf(quotation) + quotation.length();//询价单编号值位置
        int quotation_end_index = stringCellValue.indexOf("（",quotation_start_index);//询价单编号值之后中文括号位置
        for (int i = 0; i < 100; i++) {
            if(quotation_start_index == quotation_end_index){//如果结果等于开始，说明在值前面是有空格的加一再去找
                quotation_end_index = stringCellValue.indexOf("（",++quotation_start_index);
            }else if(quotation_end_index < quotation_start_index){
                quotation_end_index = stringCellValue.indexOf("(",++quotation_start_index);
            }else {
                break;
            }
        }
        String quotation_value = stringCellValue.substring(quotation_start_index,quotation_end_index);//询价人姓名值
        String[] split = quotation_value.split("-");
        quotation_value = split[0]+"年"+split[1]+"月"+split[2]+"日";//日期格式化为，年月日(加汉字)
        return quotation_value.trim();
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
                    //设置单元格类型
                    row.getCell(i).setCellType(CellType.STRING);
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
