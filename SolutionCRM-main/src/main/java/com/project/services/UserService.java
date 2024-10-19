package com.project.services;

import java.util.List;


import com.project.response.CustomerDTO;
import com.project.response.StaffDTO;
import com.project.entities.Role;
import com.project.entities.User;




public interface UserService {

	User saveUser(User user);
	User updateCompte(User user);
	User findUserByUsername (String username);
	Role addRole(Role role);
	User addRoleToUser(String username, String rolename);
	User saveClient(User user);
	User updateUser(User user);
	User updateUserStatus(String username, boolean status);
	//User findUserByEmail(String email);
	User getUser(Long id);
	List<User>getAllUsers();
	Boolean existsByEmail(String email);
	void deleteUser(Long id);
	Boolean existsByUsername(String username);
	StaffDTO getStaff(Long id);
	CustomerDTO getCustomer(Long id);
	CustomerDTO getCustomerByUsername(String username);
	User updateStaff(User staff);
	List<User> findUserBySpecialityName(String specialite);
	List<User> findBySocieteName(String name);
	List<User> findBySocieteNameAndFilter(String name,String keyword);
	List<User> findByCustomersByFilter(String keyword);
	List<StaffDTO> getAllEmployee();
	List<User> getAllStaffs();
	

	
}
