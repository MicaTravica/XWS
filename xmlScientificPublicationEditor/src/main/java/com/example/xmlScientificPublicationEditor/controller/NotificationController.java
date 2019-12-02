package com.example.xmlScientificPublicationEditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xmlScientificPublicationEditor.service.NotificationService;

@RestController
@RequestMapping("/api")
public class NotificationController extends BaseController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping(value="/notification/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getNotificationById(@PathVariable("id") String id) throws Exception{
		String notification = notificationService.findOne(id);
		return new ResponseEntity<>(notification, HttpStatus.OK);
	}
	
	@PostMapping(value="/notification", 
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> makeNotification(@RequestBody String notification) throws Exception {
		String id = notificationService.makeNotification(notification);
		return new ResponseEntity<>(String.format("You succesfully add Notification with id %s", id), HttpStatus.OK);
	}
	
	@PutMapping(value="/notification", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> updateNotification(@RequestBody String notification) throws Exception {
		notification = notificationService.update(notification);
		return new ResponseEntity<>(notification, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/notification/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id")String id) throws Exception{
		notificationService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
