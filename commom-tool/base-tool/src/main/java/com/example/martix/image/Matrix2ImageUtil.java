package com.example.martix.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Title Matrix2ImageUtil
 * @ProjectName jkb-plat-backend
 * @Description 二维码矩阵工具类
 * @Date
 */
public final class Matrix2ImageUtil {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private Matrix2ImageUtil() {
    }

    /**
     * 生成二维码矩阵
     *
     * @param content
     * @param width   宽
     * @param height  高
     * @param margin  白边(单位非像素,慎用)
     * @return
     */
    public static BitMatrix createQrCode(String content, int width, int height, int margin) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 不能写成 UTF-8 哈 会出现奇怪的问题的 比如会有前缀 \000026
        hints.put(EncodeHintType.MARGIN, margin);

        try {
            return multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
        }
        return null;
    }

    /**
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 以流的形式写回客户端
     *
     * @param matrix
     * @param format
     * @param stream
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 生成多个二维码图片
     *
     * @param qrCodeContentArray 二维码内容
     * @param width              宽
     * @param height             高
     * @param margin             边距
     * @return
     */
    public static BufferedImage[] createQrCodeBufferedImage(String[] qrCodeContentArray, int width, int height, int margin) {
        BufferedImage[] bufferedImages = new BufferedImage[qrCodeContentArray.length];
        for (int idx = 0; idx < qrCodeContentArray.length; idx++) {
            String qrCodeContent = qrCodeContentArray[idx];
            final BitMatrix bitMatrix = createQrCode(qrCodeContent, width, height, margin);
            bufferedImages[idx] = toBufferedImage(bitMatrix);
        }
        return bufferedImages;
    }

    /**
     * 根据给定的底板和二维码合成新的图片
     *
     * @param baseImage      底板图片
     * @param bufferedImages 二维码图片
     * @param x              绘制于底板的x坐标
     * @param y              绘制于底板的y坐标
     * @param width          绘制于底板的宽
     * @param height         绘制于底板的高
     * @param formatName     绘制的图片格式(很重要)
     * @return
     * @throws IOException
     */
    public static byte[][] mergeImages2BaseImg(BufferedImage baseImage, BufferedImage[] bufferedImages, int x, int y, int width, int height, String formatName) throws IOException {
        byte[][] mergedImgByteArray = new byte[bufferedImages.length][];
        for (int idx = 0; idx < bufferedImages.length; idx++) {
            BufferedImage bufferedImage = bufferedImages[idx];
            final byte[] bytes = mergeImage(baseImage, bufferedImage, x, y, width, height, formatName);
            mergedImgByteArray[idx] = bytes;
        }
        return mergedImgByteArray;
    }

    /**
     * 合并图片
     *
     * @param baseImg     底图
     * @param overrideImg 覆盖图
     * @param x           绘制坐标x轴
     * @param y           绘制坐标y轴
     * @param width       绘制宽度
     * @param height      绘制高度
     * @param formatName  绘制图像类型
     * @return
     * @throws IOException
     */
    private static byte[] mergeImage(BufferedImage baseImg, BufferedImage overrideImg, int x, int y, int width, int height, String formatName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Graphics graphics = baseImg.getGraphics();
        graphics.drawImage(overrideImg, x, y, width, height, null);
        graphics.dispose();
        ImageIO.write(baseImg, formatName, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}