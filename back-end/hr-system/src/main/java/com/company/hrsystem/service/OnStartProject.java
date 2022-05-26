package com.company.hrsystem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.company.hrsystem.utils.LogUtil;

@Service
public class OnStartProject implements ApplicationListener<ContextRefreshedEvent> {

	@Value("${upload.employee.img.dir}")
	private String uploadEmployeeImgDir;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*
		 * Create direction for saving upload profile image
		 */
		Path saveToPath = Paths.get(uploadEmployeeImgDir);
		if (!Files.exists(saveToPath)) {
			try {
				Files.createDirectory(saveToPath);
			} catch (IOException e) {
				LogUtil.error("can't create direction on start project");
				LogUtil.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

}
