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
import org.springframework.web.client.RestTemplate;

import client.model.Photo;

@Service
public class PhotoService {

	@Autowired
	private Environment env;

	@Autowired
	private ResourceLoader resourceLoader;

	private static RestTemplate restTemplate;

	static {
		restTemplate = new RestTemplate();
	}

	public Photo getPhoto(String photoId) {
		String apiBaseUrl = env.getProperty("rest.api.base.url");
		String url = apiBaseUrl + "photo/photo/{photoId}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("photoId", photoId);

		return restTemplate.getForObject(url, Photo.class, params);
	}

	public Photo createPhoto() {
		Photo photo = new Photo();
		String apiBaseUrl = env.getProperty("rest.api.base.url");
		String url = apiBaseUrl + "photo/create";

		Resource resource = resourceLoader
				.getResource("classpath:InputImages/flowers-04.jpg");
		try {
			File file = resource.getFile();
			byte[] data = new byte[(int) file.length()];
			photo.setData(data);
			photo.setImageName(resource.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return restTemplate.postForObject(url, photo, Photo.class);
	}
}
