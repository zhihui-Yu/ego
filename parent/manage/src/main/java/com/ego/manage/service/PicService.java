package com.ego.manage.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PicService {

	/**
	 * 文件上传到nginx
	 * @param file
	 * @return
	 * @throws IOException
	 */
	Map<String,Object> upload(MultipartFile file) throws IOException;
}
