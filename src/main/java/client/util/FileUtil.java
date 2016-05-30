package client.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static BufferedReader getBufferedReader(InputStream in) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
		} catch (Exception e) {
			br = null;
			e.printStackTrace();
		}
		return br;
	}

	public static InputStream getResourceFileInputStream(Resource resource) {
		log.info(">> Start on getResourceFileInputStream...");
		InputStream in = null;
		try {
			in = resource.getInputStream();
			log.info("Resource InputStream is OK to go!");
		} catch (FileNotFoundException e) {
			in = null;
			log.error(e.getMessage());
		} catch (Exception e) {
			in = null;
			log.error(e.getMessage());
		}
		log.info(">> End getResourceFileInputStream...");
		return in;
	}

	public static InputStream getInputStream(MultipartFile file) {
		log.info(">> Start on getInputStream...");
		InputStream in = null;
		try {
			in = file.getInputStream();
			log.info("MultipartFile is OK to go!");
		} catch (FileNotFoundException e) {
			in = null;
			log.error(e.getMessage());
		} catch (Exception e) {
			in = null;
			log.error(e.getMessage());
		}
		log.info(">> End on getInputStream...");
		return in;
	}
}
