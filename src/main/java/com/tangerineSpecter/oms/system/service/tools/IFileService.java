package com.tangerinespecter.oms.system.service.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
	
	String uploadFile(MultipartFile file, String fileName) throws IOException;
}
