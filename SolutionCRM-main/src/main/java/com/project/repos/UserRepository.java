package com.project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.Role;
import com.project.entities.Societe;
import com.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findUserByUsername (String username);
	//User findByEmail(String email);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
	//List<User> findByRole(ERole role);
	
	//List<User> findByRoles(List<Role> roles);
	
	List<User> findUsersBySpecialitiesNom(String nom);
	//List<User> findBySociete(Societe societe);

	@Query("SELECT u from User u join Societe s on (u.societe = s) where s=?1 and u.enabled = true")
	List<User> findBySociete(Societe societe);
	
	@Query(
			"SELECT u from User u join Societe s on (u.societe = s) "
			+ "where ((s.name = :name) and"
			+ "( (lower(u.nom) like %:keyword%) or  (lower(u.prenom) like %:keyword%) or (lower(u.email) like %:keyword%) ))"
			)
	List<User> findBySocieteAndFilter(String name,String keyword);
	
	@Query(
			"SELECT u from User u join Societe s on (u.societe = s) "
			+ "where ("
			+ " (lower(u.nom) like %:keyword%) or  (lower(u.prenom) like %:keyword%) or (lower(u.email) like %:keyword%) or (lower(s.name) like %:keyword%) )"
			)
	List<User> findByCustomersByFilter(String keyword);
	
	/*@Query("SELECT u from User u join Societe s on (u.societe = s) where s.name = ?1 and u.role = ?2")
	List<User> findBySocieteName(String name,ERole role);*/
	
	/*@Query("SELECT u from User u  where u.role IN (?1,?2)")
	List<User> findSocietiesHR(ERole role1 ,ERole role2);*/
}
