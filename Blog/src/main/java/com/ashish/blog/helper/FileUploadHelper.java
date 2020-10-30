package com.ashish.blog.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	public final String UPLOAD_DIR= new ClassPathResource("static/img/").getFile().getAbsolutePath();
	
	public FileUploadHelper() throws IOException
	{
		
	}
	
	public boolean  uploadfile(MultipartFile file,String nameOfFile)
	{
		boolean status=false;
		
		try {
			Files.copy(file.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+nameOfFile), StandardCopyOption.REPLACE_EXISTING);
			status=true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status ;
		
	}

}
