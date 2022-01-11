package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.tangerinespecter.oms.common.enums.ImageFormatEnum;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片处理工具类
 *
 * @author 丢失的橘子
 * @date 2022年01月11日10:24:25
 */
@Slf4j
@Component
public class ImageUtils {
	
	/**
	 * 根据图片Url网址进行图片合成
	 *
	 * @param bigPicUrl   背景图Url网址
	 * @param smallPicUrl logo图片Url网址
	 * @param xOffset     logo——x坐标偏移
	 * @param yOffset     logo——y坐标偏移
	 * @return 输入流
	 */
	public static InputStream overlapImage(URL bigPicUrl, URL smallPicUrl, int xOffset, int yOffset) {
		try {
			return overlapImage(urlFileCheck(bigPicUrl), urlFileCheck(smallPicUrl), "jpg", xOffset, yOffset);
		} catch (Exception e) {
			log.error("overlap image error...", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Url图片有效性校验
	 *
	 * @return 有效返回BufferedImage，无效返回null
	 */
	private static BufferedImage urlFileCheck(URL url) throws Exception {
		if (url == null) {
			return null;
		}
		final int successCode = 200;
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int responseCode = con.getResponseCode();
		if (responseCode != successCode) {
			return null;
		}
		return ImageIO.read(url.openStream());
	}
	
	/**
	 * 根据文件地址进行图片合成
	 *
	 * @param bigPath   背景图文件地址
	 * @param smallPath logo图片文件地址
	 * @param xOffset   logo——x坐标偏移
	 * @param yOffset   logo——y坐标偏移
	 * @return 输入流
	 */
	public static InputStream overlapImage(String bigPath, String smallPath, int xOffset, int yOffset) {
		try {
			//logo不存在直接返回
			if (StrUtil.isEmpty(smallPath) || !new File(smallPath).exists()) {
				return new FileInputStream(bigPath);
			}
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			//String suffix = bigPath.split("\\.")[1]
			return overlapImage(big, small, "jpg", xOffset, yOffset);
		} catch (Exception e) {
			log.error("overlap image error...", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 图片合成
	 *
	 * @param big     背景图对象
	 * @param small   logo图片对象
	 * @param suffix  后缀名
	 * @param xOffset logo——x坐标偏移
	 * @param yOffset logo——y坐标偏移
	 * @return 输入流
	 */
	public static InputStream overlapImage(BufferedImage big, BufferedImage small, String suffix, int xOffset, int yOffset) {
		Graphics2D g = null;
		try {
			g = big.createGraphics();
			if (small != null) {
				g.drawImage(small, xOffset, yOffset, small.getWidth(), small.getHeight(), null);
			}
			@Cleanup ByteArrayOutputStream bs = new ByteArrayOutputStream();
			@Cleanup ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(big, suffix, imOut);
			return new ByteArrayInputStream(bs.toByteArray());
		} catch (Exception e) {
			log.error("overlap image error...", e);
			throw new RuntimeException(e);
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
	}
	
	/**
	 * 绘制文字到图片
	 *
	 * @param picFileInput 图片输入流
	 * @param content      文本内容
	 * @param contentColor 内容颜色
	 * @param charNum      每行文字个数
	 * @param fontSize     字号大小
	 */
	public static InputStream drawStringForImage(InputStream picFileInput, String content, Color contentColor, int charNum, int fontSize) {
		Graphics2D g = null;
		try {
			ImageIcon imgIcon = new ImageIcon(toByteArray(picFileInput));
			Image theImg = imgIcon.getImage();
			int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
			int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
			BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			g = bimage.createGraphics();
			g.setColor(contentColor);
			g.setBackground(Color.red);
			g.drawImage(theImg, 0, 0, null);
			// 设置字体、字型、字号
			Font font = new Font("黑体", Font.PLAIN, fontSize);
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics(font);
			//字体抗锯齿
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//计算换行
			for (int index = 0; index <= content.length() / charNum; index++) {
				//切割文本片段
				String subContent = StrUtil.sub(content, charNum * index, charNum * (index + 1));
				//文本宽度,居中计算
				int textWidth = fm.stringWidth(subContent);
				int textHeight = fm.getHeight();
				int widthOffset = (width - textWidth) / 2;
				int heightOffset = height / 2 + textHeight * index;
				// 写入文字
				g.drawString(subContent, widthOffset, heightOffset);
			}
			@Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bimage, "png", os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			log.error("draw string for image exception...", e);
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
		return null;
	}
	
	/**
	 * 压缩图片为jpg格式
	 *
	 * @param sourceFile 源文件
	 * @param scale      缩放比例，必须大于0.0
	 */
	public static InputStream scaleImage2Jpg(File sourceFile, double scale) throws IOException {
		return scaleImage2Jpg(new FileInputStream(sourceFile), scale);
	}
	
	/**
	 * 压缩图片为jpg格式
	 *
	 * @param sourceInputStream 源文件输入流
	 * @param scale             缩放比例，必须大于0.0
	 */
	public static InputStream scaleImage2Jpg(InputStream sourceInputStream, double scale) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(sourceInputStream);
		//将图片输出为一个新的8bit非透明alpha数据图片
		BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		//重绘加载的图片色值，剔除透明，调整背景色为白色
		newImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
		//压缩图片大小返回
		@Cleanup ByteArrayOutputStream bs = new ByteArrayOutputStream();
		@Cleanup ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
		ImageIO.write(Thumbnails.of(newImage).scale(scale).asBufferedImage(), ImageFormatEnum.JPG.getName(), imOut);
		return new ByteArrayInputStream(bs.toByteArray());
	}
	
	/**
	 * 压缩图片为jpg格式
	 * 指定高宽，图片比例不变，压缩到指定高宽边界为标准
	 *
	 * @param sourceFile 源文件
	 * @param width      宽度
	 * @param height     高度
	 */
	public static InputStream scaleImage2Jpg(File sourceFile, int width, int height) throws IOException {
		return scaleImage2Jpg(new FileInputStream(sourceFile), width, height);
	}
	
	/**
	 * 压缩图片为jpg格式
	 * 指定高宽，图片比例不变，压缩到指定高宽边界为标准
	 *
	 * @param sourceInputStream 源文件输入流
	 * @param width             宽度下限
	 * @param height            高度下限
	 */
	public static InputStream scaleImage2Jpg(InputStream sourceInputStream, int width, int height) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(sourceInputStream);
		int originWidth = bufferedImage.getWidth();
		int originHeight = bufferedImage.getHeight();
		//将图片输出为一个新的8bit非透明alpha数据图片
		BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		//重绘加载的图片色值，剔除透明，调整背景色为白色
		newImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
		//压缩图片大小返回
		BufferedImage thumbnailBI;
		if (originHeight / height > originWidth / width) {
			thumbnailBI = Thumbnails.of(newImage).width(width).asBufferedImage();
		} else {
			thumbnailBI = Thumbnails.of(newImage).height(height).asBufferedImage();
		}
		@Cleanup ByteArrayOutputStream bs = new ByteArrayOutputStream();
		@Cleanup ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
		ImageIO.write(thumbnailBI, ImageFormatEnum.JPG.getName(), imOut);
		return new ByteArrayInputStream(bs.toByteArray());
	}
	
	/**
	 * 压缩图片原格式返回
	 *
	 * @param sourceInputStream 源文件输入流
	 * @param scale             缩放比例，必须大于0.0
	 */
	public static InputStream scaleImage(InputStream sourceInputStream, double scale) throws IOException {
		BufferedImage thumbnailBI = Thumbnails.of(sourceInputStream).scale(scale).asBufferedImage();
		@Cleanup ByteArrayOutputStream bs = new ByteArrayOutputStream();
		@Cleanup ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
		ImageIO.write(thumbnailBI, FileTypeUtil.getType(sourceInputStream), imOut);
		return new ByteArrayInputStream(bs.toByteArray());
	}
	
	/**
	 * 压缩图片原格式返回
	 *
	 * @param sourceFile 源文件
	 * @param scale      缩放比例，必须大于0.0
	 */
	public static InputStream scaleImage(File sourceFile, double scale) throws IOException {
		return scaleImage(new FileInputStream(sourceFile), scale);
	}
	
	private static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int index;
		while (-1 != (index = input.read(buffer))) {
			output.write(buffer, 0, index);
		}
		return output.toByteArray();
	}
	
	/**
	 * 裁剪图片（根据起始坐标点和高宽）
	 *
	 * @param sourceFile 源文件
	 * @param xOffset    左上角x坐标偏移量
	 * @param yOffset    左上角y坐标偏移量
	 * @param width      宽度
	 * @param height     高度
	 * @return 裁剪结果流
	 * @throws Exception 异常
	 */
	public static InputStream regionImage(File sourceFile, int xOffset, int yOffset, int width, int height) throws Exception {
		@Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(sourceFile).sourceRegion(new Rectangle(xOffset, yOffset, width, height))
				.size(width, height).outputFormat(ImageFormatEnum.PNG.getName()).toOutputStream(os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * 裁剪图片（根据起始坐标点和高宽）
	 *
	 * @param sourceImage 源图片文件
	 * @param xOffset     左上角x坐标偏移量
	 * @param yOffset     左上角y坐标偏移量
	 * @param width       宽度
	 * @param height      高度
	 * @return 裁剪结果流
	 * @throws Exception 异常
	 */
	public static InputStream regionImage(BufferedImage sourceImage, int xOffset, int yOffset, int width, int height) throws Exception {
		@Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(sourceImage).sourceRegion(new Rectangle(xOffset, yOffset, width, height))
				.size(width, height).outputFormat(ImageFormatEnum.PNG.getName()).toOutputStream(os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * 裁剪图片（根据起始坐标点和高宽）
	 *
	 * @param imageUrl 源图片地址
	 * @param xOffset  左上角x坐标偏移量
	 * @param yOffset  左上角y坐标偏移量
	 * @param width    宽度
	 * @param height   高度
	 * @return 裁剪结果图片
	 */
	public static BufferedImage regionImage(URL imageUrl, int xOffset, int yOffset, int width, int height) {
		BufferedImage bufferedImage = null;
		if (width <= 0 || height <= 0) {
			return null;
		}
		try {
			bufferedImage = Thumbnails.of(urlFileCheck(imageUrl)).sourceRegion(new Rectangle(xOffset, yOffset, width, height))
					.size(width, height).outputFormat(ImageFormatEnum.PNG.getName()).asBufferedImage();
		} catch (Exception e) {
			log.error("图片裁剪异常，参数：地址[{}], 裁剪信息[{}-{}-{}-{}]，异常：" + e, imageUrl, xOffset, yOffset, width, height);
		}
		return bufferedImage;
	}
	
	/**
	 * 裁剪图片（根据对角坐标点）
	 *
	 * @param imageUrl     源图片地址
	 * @param startXOffset 左上角x坐标偏移量
	 * @param startYOffset 左上角y坐标偏移量
	 * @param endXOffset   右下角x坐标偏移量
	 * @param endYOffset   右下角y坐标偏移量
	 * @return 裁剪结果流
	 * @throws Exception 异常
	 */
	public static InputStream regionImageDiagonal(URL imageUrl, int startXOffset, int startYOffset, int endXOffset, int endYOffset) throws Exception {
		@Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
		int width = Math.abs(endXOffset - startXOffset);
		int height = Math.abs(endYOffset - startYOffset);
		Thumbnails.of(urlFileCheck(imageUrl)).sourceRegion(new Rectangle(startXOffset, startYOffset, width, height))
				.size(width, height).outputFormat(ImageFormatEnum.PNG.getName()).toOutputStream(os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	
}
