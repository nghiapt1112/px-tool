//package com.px.tool.infrastructure.service.impl;
//
//import org.apache.commons.io.IOUtils;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//
//public class Download {
//    @RequestMapping("/tuonglop/download-post-file/{postId}")
//    public void downloadPostFile(HttpServletResponse response, @PathVariable("postId") Integer postId) throws IOException {
//        BaiDang baiDang = baiDangService.findById(postId);
//        downloadFile(response, baiDang.getFileName(), baiDang.getFileRealName(), "post");
//    }
//
//    @RequestMapping("/tuonglop/download-doc-file/{docId}")
//    public void downloadDocFile(HttpServletResponse response, @PathVariable("docId") Integer docId) throws IOException {
//        TaiLieu taiLieu = taiLieuService.findById(docId);
//        downloadFile(response, taiLieu.getFileName(), taiLieu.getFileRealName(), "doc");
//    }
//
//
//    public void downloadFile(HttpServletResponse response, String fileName, String fileRealName, String location) throws IOException {
//        File file = ResourceUtils.getFile("classpath:" + location + "/" + fileRealName).getAbsoluteFile();
//        byte[] fileContent = Files.readAllBytes(file.toPath());
//        Path newFile = null;
//        try {
//            Path newPath = Paths.get("./../" + fileName);
//            if (!Files.exists(newPath)) {
//                newFile = Files.createFile(newPath);
//            } else {
//                newFile = newPath;
//            }
//            if (!Files.exists(newFile)) {
//                String errorMessage = "Sorry. The file you are looking for does not exist";
//                System.out.println(errorMessage);
//                OutputStream outputStream = response.getOutputStream();
//                outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//                outputStream.close();
//                return;
//            }
//            Files.write(newFile, fileContent, StandardOpenOption.APPEND);
//            InputStream inputStream = Files.newInputStream(newFile);
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + newFile.getFileName() + "\"");
//            IOUtils.copy(inputStream, response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException e) {
//            String errorMessage = "Error!";
//            System.out.println(errorMessage);
//            OutputStream outputStream = response.getOutputStream();
//            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//            outputStream.close();
//            return;
//        }
//    }
//
//
//
//    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
//    public ModelAndView downloadExcel(Model model, HttpServletRequest request, HttpServletResponse responsel) {
//        String maLop = request.getParameter("maLop");
//        List<SinhVien> sinhViens=sinhVienService.getListSinhVienbyLopHocId(maLop);
//        model.addAttribute("maLop",maLop);
//        model.addAttribute("sinhViens",sinhViens);
//        return new ModelAndView(new ExcelView());
//    }
//
//
//    public class ExcelView extends AbstractXlsView {
//
//        @Override
//        public void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
//                                       HttpServletResponse response) throws Exception {
//            // TODO Auto-generated method stub
////
//            String maLop = (String) model.get("maLop");
//            String value="attachment; filename=\""+maLop+".xlsx"+"\"";
//            // change the file name
//            response.setHeader("Content-Disposition", value);
//
//            @SuppressWarnings("unchecked")
//            List<SinhVien> sinhViens= (List<SinhVien>) model.get("sinhViens");
//
//            // create excel xls sheet
//            Sheet sheet = workbook.createSheet("Users Detail");
//            sheet.setDefaultColumnWidth(30);
//
//            // create style for header cells
//            CellStyle style = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setFontName("Arial");
//            font.setBold(true);
//
//            // create header row
//            Row header = sheet.createRow(0);
//            header.createCell(0).setCellValue("STT");
//            header.getCell(0).setCellStyle(style);
//            header.createCell(1).setCellValue("Mã SV");
//            header.getCell(1).setCellStyle(style);
//            header.createCell(2).setCellValue("Tên SV");
//            header.getCell(2).setCellStyle(style);
//            header.createCell(3).setCellValue("Email");
//            header.getCell(3).setCellStyle(style);
//            header.createCell(4).setCellValue("Điểm thực hành");
//            header.getCell(4).setCellStyle(style);
//            header.createCell(5).setCellValue("Điểm lý thuyết");
//            header.getCell(5).setCellStyle(style);
//            header.createCell(6).setCellValue("Điểm cuối kì");
//            header.getCell(6).setCellStyle(style);
//            int rowCount = 1;
//            int stt=0;
//
//            for(SinhVien  sv: sinhViens){
//                Row userRow =  sheet.createRow(rowCount++);
//                userRow.createCell(0).setCellValue(stt);
//                userRow.createCell(1).setCellValue(sv.getMaSinhVien());
//                userRow.createCell(2).setCellValue(sv.getUser().getFullName());
//                userRow.createCell(3).setCellValue(sv.getUser().getUserEmail());
//                stt++;
//            }
//
//        }
//    }
//}
