package com.project.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.project.entities.ComplaintFile;
import com.project.repos.ComplaintFileRepository;

@Service
public class ComplaintFileStorageService {


	  @Autowired
	  private ComplaintFileRepository complaintFileRepository;
	  
	  @Autowired
	  private ReclamationService reclamationService;
	  
	  public ComplaintFile storet(MultipartFile file, Long id) throws IOException {
		    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		    ComplaintFile fileDB = new ComplaintFile(fileName, file.getContentType(),file.getBytes());
		    fileDB.setReclamation(reclamationService.findById(id));
		    return complaintFileRepository.save(fileDB);
	  }
	  
	  
	  public List<ComplaintFile> store(List<MultipartFile> files, Long id) throws IOException {
		  	List<ComplaintFile> tmp = new ArrayList<ComplaintFile>();
		  	files.forEach(file ->{
			    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			    ComplaintFile fileDB = null;
				try {
					fileDB = new ComplaintFile(fileName, file.getContentType(),file.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    fileDB.setReclamation(reclamationService.findById(id));
			    ComplaintFile f = complaintFileRepository.save(fileDB);
			    tmp.add(f);
		  	});

		    return tmp;
	  }
	  
	  public ComplaintFile getFile(String id) {
		  return complaintFileRepository.findById(id).get();
	  }
	  
	  
	  public List<ComplaintFile> getAll() {
		  return complaintFileRepository.findAll();
	  }
	  

	  
	  /*public Resource download(String id) {
	        try {
	        	ComplaintFile  file = complaintFileRepository.findById(id).get();
	            Resource resource = new UrlResource();

	            if (resource.exists() || resource.isReadable()) {
	                return resource;
	            } else {
	                throw new RuntimeException("Could not read the file!");
	            }
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("Error: " + e.getMessage());
	        }
	    }*/
}
