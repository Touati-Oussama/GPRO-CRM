package com.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entities.ERole;
import com.project.entities.Role;
import com.project.entities.Societe;
import com.project.entities.User;
import com.project.repos.RoleRepository;
import com.project.repos.UserRepository;
import com.project.response.CustomerDTO;
import com.project.response.StaffDTO;


@Transactional
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRep;
	
	
	@Autowired
	private WebSocketService webSocketService;
	
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired 
	SocieteService societeService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRep.save(user);
	}
	
	@Override
	public User updateCompte(User user) {
		// TODO Auto-generated method stub
		return userRep.save(user);
	}

	
	@Override
	public User addRoleToUser(String username, String rolename) {
		User usr = userRep.findByUsername(username);
		Role r = roleRepository.findByRole(rolename);
		return usr;
	}
	
	@Override
	public Role addRole(Role role) {
		return roleRepository.save(role);
	}
	@Override
	public User findUserByUsername(String username) {
		return userRep.findByUsername(username);
	}
	@Override
	public User saveClient(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRep.save(user);
	}
	@Override
	public User getUser(Long id) {
		// TODO Auto-generated method stub
		return userRep.findById(id).get();
	}
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRep.findAll();
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRep.existsByEmail(email);
	}
	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRep.deleteById(id);
	}
	@Override
	public Boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return userRep.existsByUsername(username);
	}
	@Override
	public StaffDTO getStaff(Long id) {
		User s = userRep.findById(id).get();
		if (s == null)
			return null;
		StaffDTO staff = new StaffDTO();
		staff.setId(s.getUser_id());
		staff.setNom(s.getNom());
		staff.setPrenom(s.getPrenom());
		staff.setUsername(s.getUsername());
		staff.setTelephone(s.getTelephone());
		staff.setEmail(s.getEmail());
		staff.setPassword(s.getPassword());
		List<String> tmp = new ArrayList<>();
		s.getSpecialities().forEach(spec->{
			tmp.add(spec.getNom());
		});
		staff.setSpecialites(tmp);
		staff.setEnabled(s.getEnabled());
		return staff;
	}
	@Override
	public CustomerDTO getCustomer(Long id) {
		User s = userRep.findById(id).get();
		if (s == null)
			return null;
		CustomerDTO customer = new CustomerDTO();
		customer.setId(s.getUser_id());
		customer.setNom(s.getNom());
		customer.setPrenom(s.getPrenom());
		customer.setUsername(s.getUsername());
		customer.setTelephone(s.getTelephone());
		customer.setEmail(s.getEmail());
		customer.setPassword(s.getPassword());
		customer.setSociete(s.getSociete().getname());
		customer.setEnabled(s.getEnabled());
		return customer;
	}
	@Override
	public CustomerDTO getCustomerByUsername(String username) {
		User s = userRep.findByUsername(username);
		if (s == null)
			return null;
		CustomerDTO customer = new CustomerDTO();
		customer.setId(s.getUser_id());
		customer.setNom(s.getNom());
		customer.setPrenom(s.getPrenom());
		customer.setUsername(s.getUsername());
		customer.setTelephone(s.getTelephone());
		customer.setEmail(s.getEmail());
		customer.setPassword(s.getPassword());
		customer.setSociete(s.getSociete().getname());
		customer.setEnabled(s.getEnabled());
		return customer;
	}
	@Override
	public User updateStaff(User staff) {
		// TODO Auto-generated method stub
		return  userRep.save(staff);
	}
	@Override
	public List<User> findUserBySpecialityName(String specialite) {
		// TODO Auto-generated method stub
		return userRep.findUsersBySpecialitiesNom(specialite);
	}

	@Override
	public List<User> findBySocieteName(String name) {
		// TODO Auto-generated method stub
		Societe s = societeService.findSocieteByName(name);
		if (s== null)
			return null;
		return userRep.findBySociete(s);
	}
	@Override
	public List<User> findBySocieteNameAndFilter(String name, String keyword) {
		// TODO Auto-generated method stub
		return userRep.findBySocieteAndFilter(name, keyword);
	}
	@Override
	public List<User> findByCustomersByFilter(String keyword) {
		// TODO Auto-generated method stub
		return userRep.findByCustomersByFilter(keyword);
	}
	@Override
	public List<StaffDTO> getAllEmployee() {
		// TODO Auto-generated method stub
		List<User> oldUsers = this.getAllUsers();
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
	@Override
	public List<User> getAllStaffs() {
		
		List<User> oldUsers = this.getAllUsers();
		List<User> users = new ArrayList<User>();
		oldUsers.forEach(user->{
			user.getRoles().forEach(role->{
				if (role.getRole().equals(ERole.EMPLOYEE.name()))
					users.add(user);
					
			});
		});
		return users;
	}
	
	
	@Override
	public User updateUserStatus(String username, boolean status) {
		// TODO Auto-generated method stub
		User user = this.findUserByUsername(username);
		user.setConnected(status);
		User u = userRep.save(user);
		notifyFrontend("User Connection");
		return u;
	}
	
	
	private void notifyFrontend(String entityTopic) {
        webSocketService.sendMessage(entityTopic);
    }

}