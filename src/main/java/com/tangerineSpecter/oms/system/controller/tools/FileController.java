package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.system.service.tools.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 文件控制
 *
 * @author 丢失的橘子
 * @version 0.4.1
 * @date 2022年01月07日12:58:01
 */
@RestController
@Api(tags = "文件处理接口")
@RequestMapping("/tools/file")
public class FileController {
	
	@Resource
	private IFileService fileService;
	
	/**
	 * 文件上传
	 */
	@ApiOperation("文件上传")
	@PostMapping("upload")
	public String uploadFile(MultipartFile file, String fileName) throws IOException {
		return fileService.uploadFile(file, fileName);
	}
}
