
package com.project.repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.Etat;
import com.project.entities.Projet;
import com.project.entities.Reclamation;
import com.project.entities.Societe;
import com.project.entities.TypeReclamation;
import com.project.entities.User;




public interface ReclamationRepository extends JpaRepository<Reclamation, Long>{

	
	Boolean existsByProjet(Projet p);
	Boolean existsByEtat(Etat etat);
	Boolean existsByType(TypeReclamation type);
	Boolean existsByDeveloppeur(User developpeur);
	Boolean existsByClient(User customer);
	
	@Query("SELECT r from Reclamation r join User u on (r.client = u)  where u.username = ?1 ")
	List<Reclamation> findByUser(String username); 
	List<Reclamation>findByEtat(Etat etat);
	List<Reclamation> findByProjet(Projet projet);
	List<Reclamation> findByDeveloppeurUsername(String username);
	List<Reclamation> findByType(TypeReclamation type);
	
	@Query("SELECT r from Reclamation r join User u on (r.developpeur = u)  where u.username = ?1 and r.etat = ?2 ")
	List<Reclamation> findByDeveloppeurAndStatus(String username, Etat etat);
	
	@Query("SELECT r from Reclamation r join User u on (r.client = u) Join Societe s on (u.societe = s) where s.name = ?1 and r.etat = ?2 ")
	List<Reclamation> findBySocieteAndEtat(String societe, Etat etat);
	
	
	@Query( value = "SELECT r from Reclamation r join Projet p on(r.projet = p) join Societe s on (p.societe =s) "
			+ "join TypeReclamation t on (r.type = t) join Sepicialite spec on (p.specialite = spec) "
			+ " where ((lower(r.sujet) like %:keyword%) or(lower(t.type) like %:keyword%) or (lower(s.name) like %:keyword%) "
			+ "or (lower(p.designation) like %:keyword%)  or(lower(r.etat) like %:keyword%)  or(lower(spec.nom) like %:keyword%))"
			)
	List<Reclamation> findByFilter(String keyword);
	
	@Query( value = "SELECT r from Reclamation r join Projet p on(r.projet = p) join Societe s on (p.societe =s) join TypeReclamation t on (r.type = t) "
			+ " where (((lower(r.sujet) like %:keyword%) or(lower(t.type) like %:keyword%) or (lower(s.name) like %:keyword%) "
			+ "or (lower(p.designation) like %:keyword%) )and (r.etat = :etat))"
			)
	List<Reclamation> findByFilterAndEtat(String keyword,Etat etat);
	
	@Query("SELECT r from Reclamation r   where r.etat IN(?1,?2)")
	List<Reclamation> findByListStatus(Etat etat1, Etat etat2);
	
	List<Reclamation> findByProjetSocieteName(String name);
	

	

    
	@Query("SELECT COUNT(r) FROM Reclamation r WHERE  r.etat = ?1")
    long totalByEtat(Etat etat);
	
	@Query("SELECT COUNT(r) FROM Reclamation r join Projet p on (r.projet = p) join Societe s on (p.societe = s) WHERE  s = ?1")
    long totalBySociete(Societe s);
	
	@Query("SELECT COUNT(r) FROM Reclamation r join Projet p on (r.projet = p) join Societe s on (p.societe = s) WHERE  (s = ?1 and r.dateCreation >= ?2 and r.dateCreation <= ?3)")
    long totalBySocieteAndDates(Societe s,LocalDateTime date1,LocalDateTime date2);
	
	@Query("SELECT COUNT(r) FROM Reclamation r join Projet p on (r.projet = p) join Societe s on (p.societe = s) WHERE  r.etat = ?1 and s = ?2")
    long totalByEtatAndSociete(Etat etat,Societe s);
	
	@Query("SELECT COUNT(r) FROM Reclamation r join Projet p on (r.projet = p) join Societe s on (p.societe = s) WHERE  (r.etat = ?1 and s = ?2 and r.dateCreation >= ?3 and r.dateCreation <= ?4) ")
    long totalByEtatAndSocieteAndDates(Etat etat,Societe s,LocalDateTime date1,LocalDateTime date2);
	
	///// Détails Par Projet
	@Query("SELECT r.projet.designation, COUNT(r.projet.designation) FROM Reclamation r GROUP BY r.projet.designation ORDER BY r.projet.designation")
	List<Object[]> totalByProjet();
	
	@Query("SELECT r.projet.designation, COUNT(r.projet.designation) FROM Reclamation r  where (r.dateCreation >= ?1 and r.dateCreation <= ?2) GROUP BY r.projet.designation ORDER BY r.projet.designation")
	List<Object[]> totalByProjetAndDates(LocalDateTime date1,LocalDateTime date2);
	

	@Query("SELECT r.projet.designation, COUNT(r.projet.designation) FROM Reclamation r WHERE r.etat =?1  GROUP BY r.projet.designation  ORDER BY r.projet.designation ")
	List<Object[]> totalByProjetAndEtat(Etat etat);
	
	@Query("SELECT r.projet.designation, COUNT(r.projet.designation) FROM Reclamation r WHERE (r.etat =?1 and r.dateCreation >= ?2 and r.dateCreation <= ?3) GROUP BY r.projet.designation  ORDER BY r.projet.designation ")
	List<Object[]> totalByProjetAndEtatAndDates(Etat etat,LocalDateTime date1,LocalDateTime date2);
	
	
	//Détails Par client
	@Query("SELECT COUNT(r) FROM Reclamation r where r.client = ?1")
	long totalByClient(User client);
	
	@Query("SELECT COUNT(r) FROM Reclamation r WHERE r.client =?1 and r.etat = ?2")
    long totalByClientByEtat(User client,Etat etat);
	
	////Détails Par Type
	@Query("SELECT r.type.type, COUNT(r.type.type) FROM Reclamation r GROUP BY r.type.type")
	List<Object[]> totalByType();
	
	@Query("SELECT r.type.type, COUNT(r.type.type) FROM Reclamation r where (r.dateCreation >= ?1 and r.dateCreation <= ?2)  GROUP BY r.type.type")
	List<Object[]> totalByTypeAndDates(LocalDateTime date1,LocalDateTime date2);
	
	@Query("SELECT r.type.type, COUNT(r.type.type) FROM Reclamation r WHERE r.etat =?1 GROUP BY r.type.type ORDER BY r.type.type")
	List<Object[]> totalByTypeAndEtat(Etat etat);
	
	@Query("SELECT r.type.type, COUNT(r.type.type) FROM Reclamation r WHERE (r.etat =?1 and r.dateCreation >= ?2 and r.dateCreation <= ?3) GROUP BY r.type.type ORDER BY r.type.type")
	List<Object[]> totalByTypeAndEtatAndDates(Etat etat,LocalDateTime date1,LocalDateTime date2);
	
	
	
	
	/// Détails Par Developpeur
	
	@Query("SELECT COUNT(r) FROM Reclamation r WHERE (r.developpeur=?1 and r.dateCreation >= ?2 and r.dateCreation <= ?3)")
    long totalByUserAndDates(User developpeur,LocalDateTime date1,LocalDateTime date2);
	
	@Query("SELECT COUNT(r) FROM Reclamation r WHERE r.developpeur=?1 and r.etat = ?2")
    long totalByUserByEtat(User developpeur,Etat etat);
	
	@Query("SELECT COUNT(r) FROM Reclamation r WHERE (r.developpeur=?1 and r.etat = ?2 and r.dateCreation >= ?3 and r.dateCreation <= ?4) ")
    long totalByUserByEtatAndDates(User developpeur,Etat etat,LocalDateTime date1,LocalDateTime date2);
	
	@Query("SELECT COUNT(r) FROM Reclamation r WHERE r.developpeur=?1")
    long totalByUser(User developpeur);
	

	
	
	@Query("SELECT CONCAT(r.developpeur.nom), COUNT(r.developpeur.nom) FROM Reclamation r  GROUP BY r.developpeur.nom")
	List<Object[]> totalByDeveloppeur();
	
	
	@Query("SELECT CONCAT(r.developpeur.nom), COUNT(r.developpeur.nom) FROM Reclamation r  where (r.dateCreation >= ?1 and r.dateCreation <= ?2) GROUP BY r.developpeur.nom")
	List<Object[]> totalByDeveloppeurAndDates(LocalDateTime date1,LocalDateTime date2);
	
	@Query("SELECT CONCAT(r.developpeur.nom), COUNT(r.developpeur.nom) FROM Reclamation r WHERE r.etat =?1 GROUP BY r.developpeur.nom ORDER BY r.developpeur.nom")
	List<Object[]> totalByDeveloppeurAndEtat(Etat etat);
	
	@Query("SELECT CONCAT(r.developpeur.nom), COUNT(r.developpeur.nom) FROM Reclamation r WHERE (r.etat =?1 and r.dateCreation >= ?2 and r.dateCreation <= ?3) GROUP BY r.developpeur.nom ORDER BY r.developpeur.nom")
	List<Object[]> totalByDeveloppeurAndEtatAndDates(Etat etat,LocalDateTime date1,LocalDateTime date2);
	
}
