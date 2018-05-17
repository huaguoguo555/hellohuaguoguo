package com.huaguoguo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;


public class TwoBarCodeUtil {
    public static void main(String[] args) {
        String value = encoderTwoBarCode("小白成长群：623773253，欢迎大佬小白加入一起成长(*^▽^*)");
        System.out.println(value);
    }

    /**
     *
     * 功能描述：生成二维码64进制
     *
     * @param values
     * @return String
     *
     */
    public static String encoderTwoBarCode(String values) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        // 指定编码格式
        String value = "";
        try {

            BitMatrix byteMatrix;
            byteMatrix = new MultiFormatWriter().encode(values, BarcodeFormat.QR_CODE, 500, 500, hints);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(byteMatrix, "png", bao);
            value = "data:image/gif;base64," + Base64.encodeBase64String(bao.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value;

    }
}
