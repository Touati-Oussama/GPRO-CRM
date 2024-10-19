package com.project.services;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.project.entities.ImageDB;
import com.project.entities.User;
import com.project.repos.ImageRepository;

@Service
public class ImageStorageService {

	  @Autowired
	  private ImageRepository imageDBRepository;
	  
	  @Autowired
	  private UserService userService;
	  

	  
	  public ImageDB store(MultipartFile image) throws IOException {
	    String imageName = StringUtils.cleanPath(image.getOriginalFilename());
	    ImageDB imageDB = new ImageDB(imageName, image.getContentType(),compressBytes(image.getBytes()));
	    return imageDBRepository.save(imageDB);
	  }
	  
	  public ImageDB update(MultipartFile image,Long id) throws IOException {
		    String newImageName = StringUtils.cleanPath(image.getOriginalFilename());
		    User u = userService.getUser(id);
		    System.out.println("###########################" + u.getImage() +"###################");
		    ImageDB imageDB = u.getImage();
		    imageDB.setName(newImageName);
		    imageDB.setType(image.getContentType());
		    imageDB.setData(compressBytes(image.getBytes()));
		    return imageDBRepository.save(imageDB);
	  }
	  

	  /*public void deleteImageByUserUsername(String username) {
		  ImageDB img = imageDBRepository.findByUserUsername(username);
		  imageDBRepository.delete(img);
	  }
	  
	  public void deleteImage(User user) {
		  ImageDB img = imageDBRepository.findByUser(user);
		  imageDBRepository.delete(img);
	  }*/
	  
	  public ImageDB getImage(String id) {
	    return imageDBRepository.findById(id).get();
	  }
	  
	  public Stream<ImageDB> getAllImages() {
	    return imageDBRepository.findAll().stream();
	  }
	  
	  public ImageDB getByUser(String username) {
		  User u = userService.findUserByUsername(username);
		  if (u == null)
			  return null;
		  ImageDB image =  u.getImage();
		  if (image == null)
			  return null;
		  return new ImageDB(image.getName(), image.getType(), decompressBytes(image.getData()));
	  }
	  	  
	  
		// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

			return outputStream.toByteArray();
		}
		
		// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}
}
