package com.project.restController;

import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Sepicialite;
import com.project.entities.Societe;
import com.project.request.ClientModel;
import com.project.request.StaffModel;
import com.project.response.CustomerDTO;
import com.project.response.JSONResponse;
import com.project.response.StaffDTO;
import com.project.security.MyUserDetailsService;
import com.project.services.EmailSenderService;
import com.project.services.ProjetService;
import com.project.services.ReclamationService;
import com.project.services.SocieteService;
import com.project.services.SpecialiteService;
import com.project.entities.ERole;
import com.project.entities.Role;
import com.project.entities.User;
import com.project.entities.VerificationResponsable;
import com.project.repos.RoleRepository;
import com.project.repos.VerificationResponsableRepos;
import com.project.services.ImageStorageService;
import com.project.services.UserService;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/users")
public class userRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	SpecialiteService specialiteService;
	
	@Autowired
	VerificationResponsableRepos verificationResponsableRepos;
	@Autowired 
	ReclamationService reclamationService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	ProjetService projetService;
	
	@Autowired
	SocieteService societeService;
	@Autowired 
	RoleRepository roleRepository;
	@Autowired
	MyUserDetailsService detailsService;
	
	@Autowired 
	ImageStorageService imageStorageService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ImageStorageService storageService;
	
	@RequestMapping(path="add/admin",method = RequestMethod.POST)
	public ResponseEntity<?> addAdmin(@RequestBody User user) {
		if(userService.existsByUsername(user.getUsername()))
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Error: Username is already taken!"));
		if(userService.existsByEmail(user.getEmail()))
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Error: Email is already taken!"));
		List<Role> roles = new ArrayList<>();
		Role role = roleRepository.findByRole(ERole.ADMIN.name());
		roles.add(role);
		user.setRoles(roles);

		return ResponseEntity.ok(userService.saveUser(user));
	}
	

	
	@RequestMapping(path = "all",method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(path = "{id}",method = RequestMethod.GET)
	public User geUser(@PathVariable("id") Long id) {
		return userService.getUser(id);
	}

	@RequestMapping(path = "",method = RequestMethod.GET)
	public User geUserByUsername(@RequestParam("username") String username) {
		return userService.findUserByUsername(username);
	}
	

	
	@RequestMapping(path = "",method = RequestMethod.POST)
	public ResponseEntity<?> updateStatus(@RequestParam("enabled") Boolean enabled, @RequestParam("id") Long id){
		User user = userService.getUser(id);
		System.out.println("#### le mot de passe est: " + user.getPassword() + " #######################");
		if(user == null)
			return null;
		user.setEnabled(enabled);
		if(user.getSociete() != null) {
			if (!enabled) {
				Societe s = societeService.findByResponsable(user);
				s.setResponsable(null);
				societeService.saveSociete(s);
				VerificationResponsable verif = verificationResponsableRepos.findByResponsable(user);
				if (verif == null)
					verif =  new VerificationResponsable();
				verif.setResponsable(user);
				verif.setSociete(s);
				verificationResponsableRepos.save(verif);
				
			}
			else {
				VerificationResponsable verif = verificationResponsableRepos.findByResponsable(user);
				Societe s = societeService.findSocieteByName(verif.getSociete().getname());
				if (s.getResponsable() == null)
					s.setResponsable(user);
				else 
					return ResponseEntity
							.badRequest()
							.body(new JSONResponse("Cette Soicete à déja un Responsable"));
			}
		}

		User usr = userService.updateCompte(user);
		return  ResponseEntity.ok(usr);
	}
	
	
	@RequestMapping(path = "/user",method = RequestMethod.POST)
	public ResponseEntity<?> updateUserStatus(@RequestParam("connected") Boolean connected, @RequestParam("username") String username){
		return ResponseEntity.ok(userService.updateUserStatus(username, connected));
	}
	
	/**********************************************Customers*****************************************************/
	@RequestMapping(path="add/customers",method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody ClientModel user) throws SendFailedException {
		if(userService.existsByUsername(user.getUsername()))
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Erreur: Username  est déja utilisé !"));
		if(userService.existsByEmail(user.getEmail()))
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Error: Email est déja utilisé !"));
			    
		User newUser = new User();
		newUser.setNom(user.getNom());
		newUser.setPrenom(user.getPrenom());
		newUser.setTelephone(user.getTelephone());
		newUser.setEmail(user.getEmail());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setImage(imageStorageService.getImage(user.getImage()));
		newUser.setSociete(societeService.getSociete(user.getSociete()));
		List<Role> roles = new ArrayList<>();
		Role r = roleRepository.findByRole(user.getRole());
		roles.add(r);
		if (user.getRole().equals(ERole.RESPONSABLE_SOCIETE.name())) {
			roles.add(roleRepository.findByRole(ERole.PERSONNEL_SOCIETE.name()));
		}
			
		newUser.setRoles(roles);
		
		/*String msg = "Your username is : " + user.getUsername() + "\n" + " Your password is: " + user.getPassword();
		emailSenderService.sendEmail(user.getEmail(),"Solution CRM",msg);*/
		User usr = userService.saveUser(newUser);
		if (user.getRole().equals(ERole.RESPONSABLE_SOCIETE.name())) {
			Societe s = societeService.getSociete(user.getSociete());
			s.setResponsable(usr);
			societeService.updateSociete(s);
		}
		return ResponseEntity.ok(usr);
	}
	
	@RequestMapping(path="customers/update/{id}",method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@RequestBody ClientModel user,@PathVariable("id") Long id) {
		User u = userService.getUser(id);
		Boolean res = true;
		if (u.getUsername().equals(user.getUsername()) == false)
		{
			if(userService.existsByUsername(user.getUsername()))
				return ResponseEntity
						.badRequest()
						.body(new JSONResponse("Error: Username est déja utilisé !"));
		}
		
		if(u.getEmail().equals(user.getEmail()) == false)
		{
			if(userService.existsByEmail(user.getEmail()))
				return ResponseEntity
						.badRequest()
						.body(new JSONResponse("Error: Email est déja utilisé !"));
		}
		if (u.getPassword().equals(user.getPassword()))
			res = false;
		u.setNom(user.getNom());
		u.setPrenom(user.getPrenom());
		u.setUsername(user.getUsername());
		u.setTelephone(user.getTelephone());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		if(user.getImage() != null)
			u.setImage(imageStorageService.getImage(user.getImage()));
		//u.setSociete(societeService.getSociete(user.getSociete()));
		String message = "Your new username is : " + user.getUsername() + "\n" + " Your neww password is: " + user.getPassword();
		emailSenderService.sendEmail(user.getEmail(),"Solution CRM",message);
		User usr;
		if (!res)
			usr = userService.updateCompte(u);
		else
			usr = userService.saveUser(u);
		return ResponseEntity.ok(usr);
	}
	
	@RequestMapping(path="customers/delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?>delete(@PathVariable("id")Long id){
		Boolean res = reclamationService.existsByCustomer(id);	
		if (res) {
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Impossible de supprimer cette user !. le client a des réclamations !"));
		}
		userService.deleteUser(id);
		JSONResponse response = new JSONResponse("Deleted User !");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(path = "customers/all",method = RequestMethod.GET)
	public List<CustomerDTO> getAllCustomers() {
		List<User> oldUsers = userService.getAllUsers();
		List<User> users = new ArrayList<User>();
		oldUsers.forEach(user->{
			UserDetails details = detailsService.loadUserByUsername(user.getUsername(),"test");
		    
		    if ( 
		    		details != null &&
		    		details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ERole.PERSONNEL_SOCIETE.name()))
		    	) {
		    	users.add(user);
		    }
		});

		List<CustomerDTO> customers = new ArrayList<>();
		users.forEach(u ->{
			CustomerDTO customer = new CustomerDTO();
			customer.setId(u.getUser_id());
			customer.setNom(u.getNom());
			customer.setPrenom(u.getPrenom());
			customer.setTelephone(u.getTelephone());
			customer.setEmail(u.getEmail());
			customer.setUsername(u.getUsername());
			customer.setPassword(u.getPassword());
			customer.setSociete(u.getSociete().getname());
			customer.setEnabled(u.getEnabled());
			List<String> roles = new ArrayList<>();
			u.getRoles().forEach(role->{
				roles.add(role.getRole());
			});
			customer.setRoles(roles);
			customers.add(customer);
		
		});
		
		return customers;
	}
	
	

	
	
	@RequestMapping(path = "customers/all/filter",method = RequestMethod.GET)
	public List<CustomerDTO> getAllCustomers(@RequestParam("keyword") String keyword) {
		List<User> oldUsers = userService.findByCustomersByFilter(keyword);
		List<User> users = new ArrayList<User>();
		oldUsers.forEach(user->{
			UserDetails details = detailsService.loadUserByUsername(user.getUsername());
		    
		    if ( 
		    		details != null &&
		    		details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ERole.PERSONNEL_SOCIETE.name()))
		    	) {
		    	users.add(user);
		    }
		});

		List<CustomerDTO> customers = new ArrayList<>();
		users.forEach(u ->{
			CustomerDTO customer = new CustomerDTO();
			customer.setId(u.getUser_id());
			customer.setNom(u.getNom());
			customer.setPrenom(u.getPrenom());
			customer.setTelephone(u.getTelephone());
			customer.setEmail(u.getEmail());
			customer.setUsername(u.getUsername());
			customer.setPassword(u.getPassword());
			customer.setSociete(u.getSociete().getname());
			customer.setEnabled(u.getEnabled());
			List<String> roles = new ArrayList<>();
			u.getRoles().forEach(role->{
				roles.add(role.getRole());
			});
			customer.setRoles(roles);
			customers.add(customer);
		
		});
		
		return customers;
	}
	

	

	
	
	@RequestMapping(path = "customers",method = RequestMethod.GET)
	public List<CustomerDTO> getAllBySocietes(@RequestParam("societe")String societe) {
		List<User> users = userService.findBySocieteName(societe);
		if (users == null)
			return null; 
		List<CustomerDTO> customers = new ArrayList<>();
		users.forEach(u ->{
			CustomerDTO customer = new CustomerDTO();
			customer.setId(u.getUser_id());
			customer.setNom(u.getNom());
			customer.setPrenom(u.getPrenom());
			customer.setTelephone(u.getTelephone());
			customer.setEmail(u.getEmail());
			customer.setUsername(u.getUsername());
			customer.setPassword(u.getPassword());
			customer.setSociete(u.getSociete().getname());
			customer.setEnabled(u.getEnabled());
			List<String> roles = new ArrayList<>();
			u.getRoles().forEach(role->{
				roles.add(role.getRole());
			});
			customer.setRoles(roles);
			customers.add(customer);
		});
		
		return customers;
	}
	
	@RequestMapping(path = "customers/filter",method = RequestMethod.GET)
	public List<CustomerDTO> getAllBySocieteAndFilter(@RequestParam("societe")String societe,@RequestParam("keyword")String keyword ) {
		List<User> users = userService.findBySocieteNameAndFilter(societe,keyword);
		if (users == null)
			return null; 
		List<CustomerDTO> customers = new ArrayList<>();
		users.forEach(u ->{
			CustomerDTO customer = new CustomerDTO();
			customer.setId(u.getUser_id());
			customer.setNom(u.getNom());
			customer.setPrenom(u.getPrenom());
			customer.setTelephone(u.getTelephone());
			customer.setEmail(u.getEmail());
			customer.setUsername(u.getUsername());
			customer.setPassword(u.getPassword());
			customer.setSociete(u.getSociete().getname());
			customer.setEnabled(u.getEnabled());
			List<String> roles = new ArrayList<>();
			u.getRoles().forEach(role->{
				roles.add(role.getRole());
			});
			customer.setRoles(roles);
			customers.add(customer);
		});
		
		return customers;
	}
	
	
	@RequestMapping(path = "customers/get/{id}",method = RequestMethod.GET)
	public CustomerDTO geCustomer(@PathVariable("id") Long id) {
		return userService.getCustomer(id);
	}
	
	@RequestMapping(path = "customers/{username}",method = RequestMethod.GET)
	public CustomerDTO geCustomerByUsername(@PathVariable("username") String username) {
		return userService.getCustomerByUsername(username);
	}
	
	/*******************************************************************Teams**************************************/
	
	@RequestMapping(path="add/staff",method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody StaffModel user) throws SendFailedException {
		if(userService.existsByUsername(user.getUsername()))
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Erreur: Username est déja utilisé !"));
		if(userService.existsByEmail(user.getEmail()))
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Erreur: Email est déja utilisé "));
		User newUser = new User();
		newUser.setNom(user.getNom());
		newUser.setPrenom(user.getPrenom());
		newUser.setTelephone(user.getTelephone());
		newUser.setEmail(user.getEmail());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setImage(imageStorageService.getImage(user.getImage()));
		List<Role> roles = new ArrayList<>();
		Role r = roleRepository.findByRole(ERole.EMPLOYEE.name());
		roles.add(r);
		newUser.setRoles(roles);
		List<String> specs = user.getSpecialites();
		List<Sepicialite> specialities = new ArrayList<>();
		specs.forEach(spec ->{
			Sepicialite s = specialiteService.getSpecialiteByNom(spec);
			specialities.add(s);
		});
		newUser.setSpecialities(specialities);
		String message = "Your username is : " + user.getUsername() + "\n" + " Your password is: " + user.getPassword();
		
		//emailSenderService.sendEmail(user.getEmail(),"Solution CRM",message);
		return ResponseEntity.ok(userService.saveUser(newUser));
	}
	
	
	@RequestMapping(path="teams/delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?>deleteTeams(@PathVariable("id")Long id){
		
		User developpeur = userService.getUser(id);
		Boolean verifSpec = specialiteService.existsByStaffId(id);
		Boolean verifRec = reclamationService.existsByDeveloppeurId(id);
		
		if (/*verifSpec ||*/ verifRec) {
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Impossible de supprimer ce developpeur !"));
		}
		userService.deleteUser(id);
		JSONResponse response = new JSONResponse("Deleted User !");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(path="teams/update/{id}",method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@RequestBody StaffModel newStaff,@PathVariable("id") Long id) {
		User oldStaff = userService.getUser(id);
		Boolean res = true;
		if (oldStaff.getUsername().equals(newStaff.getUsername()) == false)
		{
			if(userService.existsByUsername(newStaff.getUsername()))
				return ResponseEntity
						.badRequest()
						.body(new JSONResponse("Erreur: Username est déja utilisé !"));
		}
		if(oldStaff.getEmail().equals(newStaff.getEmail()) == false)
		{
			if(userService.existsByEmail(newStaff.getEmail()))
				return ResponseEntity
						.badRequest()
						.body(new JSONResponse("Error: Email est déja utilisé !"));
		}
		if (oldStaff.getPassword().equals(newStaff.getPassword()))
			res = false;
		oldStaff.setNom(newStaff.getNom());
		oldStaff.setPrenom(newStaff.getPrenom());
		oldStaff.setUsername(newStaff.getUsername());
		oldStaff.setTelephone(newStaff.getTelephone());
		oldStaff.setPassword(newStaff.getPassword());
		oldStaff.setEmail(newStaff.getEmail());
		if(newStaff.getImage() != null)
			oldStaff.setImage(imageStorageService.getImage(newStaff.getImage()));
		List<Sepicialite> tmp = new ArrayList<>();
		newStaff.getSpecialites().forEach(spec ->{
			Sepicialite s = specialiteService.getSpecialiteByNom(spec);
			tmp.add(s);
		});
		oldStaff.setSpecialities(tmp);
		String message = "Your new username is : " + newStaff.getUsername() + "\n" + " Your neww password is: " + newStaff.getPassword();
		emailSenderService.sendEmail(newStaff.getEmail(),"Solution CRM",message);
		User usr;
		if (!res)
			usr = userService.updateCompte(oldStaff);
		else
			usr = userService.saveUser(oldStaff);
		return ResponseEntity.ok(usr);
	}
	
	@RequestMapping(path = "all/teams",method = RequestMethod.GET)
	public List<StaffDTO> getAllStaff() {
		List<User> oldUsers = userService.getAllUsers();
		List<User> users = new ArrayList<User>();
		oldUsers.forEach(user->{
			user.getRoles().forEach(role->{
				if (role.getRole().equals(ERole.EMPLOYEE.name()))
					users.add(user);
					
			});
		});
		List<StaffDTO> staffs = new ArrayList<>();
		users.forEach(u ->{
			StaffDTO staff = new StaffDTO();
			staff.setId(u.getUser_id());
			staff.setNom(u.getNom());
			staff.setPrenom(u.getPrenom());
			staff.setTelephone(u.getTelephone());
			staff.setEmail(u.getEmail());
			staff.setUsername(u.getUsername());
			staff.setPassword(u.getPassword());
			List<String> tmp = new ArrayList<>();
			u.getSpecialities().forEach(spec->{
				tmp.add(spec.getNom());
			});
			staff.setSpecialites(tmp);
			staff.setEnabled(u.getEnabled());
			staffs.add(staff);
		});
		
		return staffs;
	}
	
	@RequestMapping(path = "all/teams/{specialite}",method = RequestMethod.GET)
	public List<StaffDTO> getAllStaffs(@PathVariable String specialite) {
		List<User> users = userService.findUserBySpecialityName(specialite);
		List<StaffDTO> staffs = new ArrayList<>();
		users.forEach(u ->{
			StaffDTO staff = new StaffDTO();
			staff.setId(u.getUser_id());
			staff.setNom(u.getNom());
			staff.setPrenom(u.getPrenom());
			staff.setTelephone(u.getTelephone());
			staff.setEmail(u.getEmail());
			staff.setUsername(u.getUsername());
			staff.setPassword(u.getPassword());
			List<String> tmp = new ArrayList<>();
			u.getSpecialities().forEach(spec->{
				tmp.add(spec.getNom());
			});
			staff.setSpecialites(tmp);
			staffs.add(staff);
			staff.setEnabled(u.getEnabled());
		});
		
		return staffs;
	}
	
	
	

	
	@RequestMapping(path = "teams/get/{id}",method = RequestMethod.GET)
	public StaffDTO geStaff(@PathVariable("id") Long id) {
		return userService.getStaff(id);
	}

	


}
