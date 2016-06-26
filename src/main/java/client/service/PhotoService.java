package client.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import client.model.Photo;

@Service
public class PhotoService {

	private final static String FILE_PATH = "InputImages/flowers-04.jpg";

	@Autowired
	private Environment env;

	@Autowired
	private ResourceLoader resourceLoader;

	private static RestTemplate restTemplate;

	static {
		restTemplate = new RestTemplate();
	}

	public Photo getPhoto(String photoId) throws HttpStatusCodeException {
		String url = getBaseUrl() + "photo/photo/{photoId}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("photoId", photoId);

		return restTemplate.getForObject(url, Photo.class, params);
	}

	public Photo createPhoto() throws HttpStatusCodeException, IOException {
		String url = getBaseUrl() + "photo/create";

		File file = getImage();
		byte[] data = new byte[(int) file.length()];

		Photo photo = new Photo();
		photo.setData(data);
		photo.setImageName(file.getName());

		return restTemplate.postForObject(url, photo, Photo.class);
	}

	private File getImage() throws IOException {
		Resource re = resourceLoader.getResource("classpath:" + FILE_PATH);
		return re.getFile();
	}

	private String getBaseUrl() {
		return env.getProperty("rest.api.base.url");
	}
}
