package com.company.hrsystem.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUtil {

	public String generateFileName(MultipartFile multipartFile) {
		StringBuilder str = new StringBuilder();
		String cleanFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		str.append(UUID.randomUUID().toString());
		str.append("_");
		str.append(cleanFileName);
		return str.toString();
	}

	public String generateUploadDir(String originDir, Integer index) {
		StringBuilder str = new StringBuilder();
		str.append(originDir);
		str.append("/");
		str.append(index);
		return str.toString();
	}

	public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path saveToPath = Paths.get(uploadDir);
		if (!Files.exists(saveToPath)) {
			Files.createDirectory(saveToPath);
		} else {
			/*
			 * Make sure only one file in directory
			 */
			FileUtils.cleanDirectory(new File(saveToPath.toUri()));
		}
		InputStream inputStream = multipartFile.getInputStream();
		Path filePath = saveToPath.resolve(fileName);
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	}

}
