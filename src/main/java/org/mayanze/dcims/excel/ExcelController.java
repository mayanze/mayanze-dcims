package org.mayanze.dcims.excel;

import cn.hutool.core.lang.Assert;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: mayanze
 * date: 2020/12/10 4:51 下午
 */
@RestController
@Slf4j
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    private List<Map<String, Object>> files = new ArrayList<>();

    private int _fileSize = 0;//判断是否是同一批上传文件的标识

    private Workbook _workbook;

    /**
     * 天石业务
     *
     * @param rep
     * @param files
     */
    @SneakyThrows
    @PostMapping("/tsyw")
    public void tsyw(HttpServletRequest req, HttpServletResponse rep, @RequestParam("file") MultipartFile file, int fileSize) {
        Map<String, Object> fileObj = new HashMap<>();
        fileObj.put("inputStream", file.getInputStream());
        fileObj.put("fileName", file.getOriginalFilename());
        files.add(fileObj);
        this._fileSize++;
        if (this._fileSize == fileSize) {
            try {
                Workbook workbook = excelService.tsyw(files);
                _workbook = workbook;
            } finally {
                this._fileSize = 0;
                this.files = new ArrayList<>();
            }
        }
        if (this._fileSize > fileSize) {
            this._fileSize = 0;
            this.files = new ArrayList<>();
        }
    }

    /*
     * 下载天石业务execl
     */
    @SneakyThrows
    @GetMapping("/tsyw_downd")
    public void tsyw_downd(HttpServletResponse rep) {
        Assert.notNull(_workbook, "没有可下载文件");
        //输出Excel文件
        OutputStream output = rep.getOutputStream();
        rep.reset();
        String codedFileName = URLEncoder.encode("天石模板", "UTF-8");
        rep.setHeader("Content-disposition", "attachment; filename=" + codedFileName + ".xlsx");
        rep.setContentType("application/x-xls");
        _workbook.write(output);
        output.close();
        _workbook = null;
    }
}
