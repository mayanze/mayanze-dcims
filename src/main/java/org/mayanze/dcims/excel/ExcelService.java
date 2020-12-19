package org.mayanze.dcims.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * author: mayanze
 * date: 2020/12/13 12:15 下午
 */
public interface ExcelService {
    /**
     * 天石业务
     * @return
     */
    Workbook tsyw(MultipartFile[] files);
}
