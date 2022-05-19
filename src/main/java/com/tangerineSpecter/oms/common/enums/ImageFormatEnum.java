package com.tangerinespecter.oms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 图片格式
 *
 * @author TangineSpecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ImageFormatEnum implements IBaseDbEnum {
	
	/**
	 * 图片格式-jpg
	 */
	JPG("jpg", 0),
	/**
	 * 图片格式-bmp
	 */
	BMP("bmp", 1),
	/**
	 * 图片格式-png
	 */
	PNG("png", 2),
	/**
	 * 图片格式-gif
	 */
	GIF("gif", 3);
	
	private String name;
	
	private Integer value;
}
