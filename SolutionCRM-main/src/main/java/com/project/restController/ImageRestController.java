package com.project.restController;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.entities.ImageDB;
import com.project.response.JSONResponse;
import com.project.response.ResponseImage;
import com.project.services.ImageStorageService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/images")
public class ImageRestController {

	@Autowired
	private ImageStorageService storageService;
	
	@PostMapping("/upload")
	  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	      return ResponseEntity.status(HttpStatus.OK).body(storageService.store(file));
	    } catch (Exception e) {
	      message = "Could not upload the image: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONResponse(message));
	    }
	  }
	

	
	@PostMapping("/update/{id}")
	  public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file,@PathVariable("id") Long id) {
	    String message = "";
	    try {
	   
	      return ResponseEntity.status(HttpStatus.OK).body(storageService.update(file, id));
	    } catch (Exception e) {
	      message = "Could not update the image: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONResponse(message));
	    }
	  }
	
	/*@RequestMapping(path="/delete/user/{username}",method = RequestMethod.DELETE)
	  public ResponseEntity<?> deleteImage(@PathVariable("username") String username) {
		
			 storageService.deleteImageByUserUsername(username);
				JSONResponse response = new JSONResponse("Image Supprimé !");
				return ResponseEntity.ok(response);
	  }*/
	

	  @GetMapping("/all")
	  public ResponseEntity<List<ResponseImage>> getListFiles() {
	    List<ResponseImage> images = storageService.getAllImages().map(dbImage->{
	    	String fileDownloadUri = ServletUriComponentsBuilder
	    	          .fromCurrentContextPath()
	    	          .path("/files/")
	    	          .path(dbImage.getId())
	    	          .toUriString();
	    	      return new ResponseImage(
	    	          dbImage.getName(),
	    	          fileDownloadUri,
	    	          dbImage.getType(),
	    	          dbImage.getData().length);
	    	    }).collect(Collectors.toList());
	    	    return ResponseEntity.status(HttpStatus.OK).body(images);
	  }
	
	
	  @GetMapping("{id}")
	  public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
	    ImageDB imageDB = storageService.getImage(id);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageDB.getName() + "\"")
	        .body(imageDB.getData());
	  }
	  
	  /*@GetMapping("user/{username}")
	  public ResponseEntity<?> getByUser(@PathVariable("username") String username) {
	    ImageDB imageDB = storageService.getByUser(username);
	    return ResponseEntity.ok(imageDB)
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageDB.getName() + "\"")
	        .body(imageDB.getData());
	  }*/
	  
	  @GetMapping("user/{username}")
	  public ImageDB getByUser(@PathVariable("username") String username) {
	    return storageService.getByUser(username);
	  }


}
