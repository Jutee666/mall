package com.imooc.mall.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.imooc.mall.common.Constant;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * ClassName: QRCodeGenerator
 * Package: com.imooc.mall.util
 *
 * @Author 马学兴
 * @Create 2023/6/6 12:51
 * @Version 1.0
 * Description:二维码生成工具
 */
public class QRCodeGenerator {
    public static void generateQRCodeImage(String text,int width,int height,String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);

    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("hello word",350,350, "D:/JAVA/springboot/mall_file/QRTest.png");
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
