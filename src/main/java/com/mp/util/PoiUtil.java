package com.mp.util;

import java.io.IOException;
import java.io.InputStream;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class PoiUtil {
	private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static final String EXCEL_CSV = "csv";

//	public static Workbook getWorkbok(InputStream in, MultipartFile file) throws IOException {
//        Workbook wb = null;
//        if(file.getOriginalFilename().endsWith(EXCEL_XLS)){     //Excel 2003
//            wb = new HSSFWorkbook(in);
//        }else if(file.getOriginalFilename().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
//            wb = new XSSFWorkbook(in);
//        }else if(file.getOriginalFilename().endsWith(EXCEL_CSV)){    // Excel 2007/2010
//            wb = new XSSFWorkbook(in);
//        }
//        return wb;
//    }

}
