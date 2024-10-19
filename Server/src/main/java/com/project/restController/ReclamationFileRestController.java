package com.project.restController;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.ComplaintFile;
import com.project.entities.Reclamation;
import com.project.repos.ReclamationRepository;
import com.project.response.JSONResponse;
import com.project.services.ComplaintFileStorageService;
import com.project.services.ReclamationService;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/files")
public class ReclamationFileRestController {

	@Autowired
	private ComplaintFileStorageService complaintFileStorageService;
	
	@Autowired
	private ReclamationRepository reclamationRepository;
	
	/*@PostMapping("/upload")
	  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	      return ResponseEntity.status(HttpStatus.OK).body(complaintFileStorageService.store(file));
	    } catch (Exception e) {
	      message = "Could not upload the image: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONResponse(message));
	    }
	  }*/

	@PostMapping("/upload/{id}")
	  public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files,@PathVariable("id") Long id) {
		 String message = "";
		    try {
		      return ResponseEntity.status(HttpStatus.OK).body(complaintFileStorageService.store(files,id));
		    } catch (Exception e) {
		      message = "Could not upload the image:!";
		      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JSONResponse(message));
		    }
	  }
	
	@PostMapping("/test/{id}")
	  public ResponseEntity<?> uploadFile(@RequestParam("files") List<MultipartFile> files,@PathVariable("id") Long id) {
	    files.forEach(file->{
	    	   try {
				ComplaintFile  f = complaintFileStorageService.storet(file,id);
			} catch (Exception e) {
				e.printStackTrace();
			  
			}
	    });
	    	
	    return ResponseEntity.status(HttpStatus.OK).body(new JSONResponse("Uploaded Files"));
	  
	  }
	
	 @GetMapping("/download/{id}")
	public void downloadFile(@PathVariable String id, HttpServletResponse res) throws Exception {
		ComplaintFile fileDB = complaintFileStorageService.getFile(id);
		res.setHeader("Content-Disposition", "attachment; filename=" + fileDB.getName());
		res.getOutputStream().write(fileDB.getData());
	}
	
	  @GetMapping("/complaint/{id}")
	  public List<ComplaintFile> getByComplaint(@PathVariable Long id) {
		Reclamation complaint = reclamationRepository.findById(id).get();
		 
		if(complaint == null) {
			return null;
		}
		if (complaint.getFile() == null)
			return null;
		return complaint.getFile();
	
	  }
	  
	  @GetMapping("{id}")
	  public ResponseEntity<?> getFile(@PathVariable String id) {
		  ComplaintFile file = complaintFileStorageService.getFile(id);
		  if (file == null)
			  return ResponseEntity.ok(new JSONResponse("False"));
		  return ResponseEntity.ok(file);
	  }
	  
	  @GetMapping("/all")
	  public List<ComplaintFile> getAll() {
		  return complaintFileStorageService.getAll();
	  }
}
