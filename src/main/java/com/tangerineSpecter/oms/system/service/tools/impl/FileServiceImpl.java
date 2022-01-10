package com.tangerinespecter.oms.system.service.tools.impl;

import com.tangerinespecter.oms.common.utils.FileUtils;
import com.tangerinespecter.oms.system.service.tools.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements IFileService {
	
	@Override
	public String uploadFile(MultipartFile file, String fileName) throws IOException {
		return FileUtils.uploadFile(file.getBytes());
	}
}
