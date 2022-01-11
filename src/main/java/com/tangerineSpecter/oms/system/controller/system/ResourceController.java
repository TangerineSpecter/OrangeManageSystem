package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.system.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 资源控制
 *
 * @author TangerineSpecter
 * @version 0.3.1
 * @date 2020年05月13日14:03:19
 */
@RestController
@Api(tags = "资源处理接口")
@RequestMapping("/resource")
public class ResourceController {
	
	@Resource
	private IResourceService resourceService;
	
	/**
	 * 上传图片
	 *
	 * @param file 图片资源
	 * @return 图片地址
	 */
	@ApiOperation("上传图片")
	@PostMapping("/upload-image")
	public ServiceResult uploadImage(@Param("file") MultipartFile file) {
		return resourceService.uploadImage(file);
	}

	/**
	 * 上传文件
	 */
	@ApiOperation("上传文件")
	@PostMapping("upload")
	public String uploadFile(MultipartFile file, String fileName) throws IOException {
		return resourceService.uploadFile(file, fileName);
	}
}
