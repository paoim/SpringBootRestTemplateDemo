package client.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;

import client.model.Photo;
import client.service.PhotoService;

@Controller
@RequestMapping("/client/photo/")
public class PhotoController {

	@Autowired
	private PhotoService photoService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE })
	public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id) {
		if (id == null) {
			return new ResponseEntity<Photo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Photo photo = null;
		try {
			photo = photoService.getPhoto(id);
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		if (photo == null) {
			return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Photo>(photo, HttpStatus.OK);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ResponseEntity<Photo> createPhoto() {
		try {
			photoService.createPhoto();
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Photo>(HttpStatus.CREATED);
	}
}
