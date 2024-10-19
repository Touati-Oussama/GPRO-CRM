package com.project.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.ComplaintFile;
import com.project.entities.ERole;
import com.project.entities.Etat;
import com.project.entities.Projet;
import com.project.entities.Reclamation;
import com.project.entities.Societe;
import com.project.entities.TypeReclamation;
import com.project.entities.User;
import com.project.repos.ReclamationRepository;
import com.project.request.ReclamationModel;
import com.project.response.ProjetDTO;
import com.project.response.ReclamationDTO;
import com.project.response.ReclamationDetails;
import com.project.response.ReclamationDetailsClient;
import com.project.response.ReclamationDetailsDev;
import com.project.response.ReclamationDetailsParDev;
import com.project.response.ReclamationDetailsType;
import com.project.response.StaffDTO;
import com.project.response.TestProjet;


@Service
public class ReclamationServiceImpl implements ReclamationService {

	@Autowired
	ReclamationRepository reclamationRepository;
	
	@Autowired
	ProjetService projetService;
	
	@Autowired
	SocieteService societeService;
	
	@Autowired
	ComplaintFileStorageService complaintFileStorageService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	private WebSocketService webSocketService;
	   
	@Autowired
	UserService userService;
	
	private int x = 0;
	private int typeTest = 0;
	private int devTest = 0;
	
	//protected abstract String getEntityTopic();
	
	@Override
	public List<ReclamationDTO> findByProjetId(Long id) {
		
		Projet p = projetService.getProjet(id);
		if (p == null)
			return null;
		List<Reclamation> tmp = reclamationRepository.findByProjet(p);
		if (tmp.size() == 0)
			return null;
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}
	
	@Override
	public List<ReclamationDTO> findByTypeId(Long id) {

		TypeReclamation t = typeService.getTypeReclamation(id);
		if (t == null)
			return null;
		List<Reclamation> tmp = reclamationRepository.findByType(t);
		if (tmp.size() == 0)
			return null;
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}

	@Override
	public Boolean existsByProjet(Long id) {
		
		Projet p = projetService.getProjet(id);
		if (p == null)
			return null;
		return reclamationRepository.existsByProjet(p);
	}
	
	@Override
	public Boolean existsByType(Long type_id) {
		TypeReclamation t = typeService.getTypeReclamation(type_id);
		if (t == null)
			return null;
		return reclamationRepository.existsByType(t);
	}

	@Override
	public Boolean existsByCustomer(Long id) {
		User u = userService.getUser(id);
		if (u == null)
			return null;
		return reclamationRepository.existsByClient(u);
	}
	
	@Override
	public Boolean existsByDeveloppeurId(Long id) {
		// TODO Auto-generated method stub
		User dev = userService.getUser(id);
		if(dev == null)
			return null;
		return reclamationRepository.existsByDeveloppeur(dev);
	}

	@Override
	public Reclamation add(ReclamationModel reclamationModel) {
		
		Reclamation reclamation = new Reclamation();
		reclamation.setSujet(reclamationModel.getSujet());
		reclamation.setDetails(reclamationModel.getDetails());
		//reclamation.setDeveloppeur(userService.getUser(reclamationModel.getDeveloper()));
		reclamation.setType(typeService.getTypeReclamation(reclamationModel.getType()));
		reclamation.setProjet(projetService.getProjet(reclamationModel.getProjet()));
		reclamation.setClient(userService.findUserByUsername(reclamationModel.getClient()));
		/*if (reclamationModel.getFiles() != null)
		{
			List<String> tmp = reclamationModel.getFiles();
			List<ComplaintFile> files = new ArrayList<>();
			tmp.forEach(f->{
				ComplaintFile file = complaintFileStorageService.getFile(f);
				files.add(file);
			});
			reclamation.setFile(files);
		}*/
		Reclamation res = reclamationRepository.save(reclamation);
		notifyFrontend("New Complaint");
		return res;
	}

	@Override
	public List<ReclamationDTO> findByClient(String username) {
		// TODO Auto-generated method stub
		List<Reclamation> tmp =  reclamationRepository.findByUser(username);
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());			
			res.add(rec);
		});
		return res;
	}
	
	@Override
	public List<ReclamationDTO> findByDeveloppeur(String username) {
		List<Reclamation> tmp =  reclamationRepository.findByDeveloppeurUsername(username);
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}

	@Override
	public ReclamationDTO getReclamation(Long id) {
		Reclamation r = reclamationRepository.findById(id).get();
		if (r == null)
			return null;
		ReclamationDTO rec = new ReclamationDTO();
		rec.setId(r.getId());
		rec.setSujet(r.getSujet());
		rec.setDetails(r.getDetails());
		rec.setDateCreation(r.getDateCreation());
		rec.setType(r.getType().getType());
		rec.setStatus(r.getEtat().name());
		rec.setProjet(r.getProjet().getDesignation());
		if(r.getDeveloppeur() != null)
			rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
		rec.setSociete(r.getProjet().getSociete().getname());
		rec.setSpeciality(r.getProjet().getSpecialite().getNom());
		if(r.getFile() != null)
			rec.setFiles(r.getFile());
		rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
		return rec;
	}

	@Override
	public List<ReclamationDTO> findAll() {
		List<Reclamation> tmp =  reclamationRepository.findAll();
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			if(r.getDeveloppeur() != null)
			rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}

	@Override
	public Reclamation findById(Long id) {
		// TODO Auto-generated method stub
		return reclamationRepository.findById(id).get();
	}

	@Override
	public Reclamation update(Reclamation reclamation) {
		// TODO Auto-generated method stub
		Reclamation r = reclamationRepository.save(reclamation);
		if (r.getEtat().equals(Etat.EN_COURS))
			notifyFrontend("Forward Complaint");
		return r;
		
	}

	@Override
	public List<ReclamationDTO> findByEtat(String etat) {
		List<Reclamation> tmp;
		if (etat.equals(Etat.EN_ATTENTE.name()))
			 tmp =  reclamationRepository.findByEtat(Etat.EN_ATTENTE);
		else if (etat.equals(Etat.EN_COURS.name()))
			 tmp =  reclamationRepository.findByEtat(Etat.EN_COURS);
		else if (etat.equals(Etat.ClOTURE.name()))
			 tmp =  reclamationRepository.findByEtat(Etat.ClOTURE);
		else
			return null;
		
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}

	@Override
	public List<ReclamationDTO> findByDeveloppeurAndEtat(String username, String etat) {
		List<Reclamation> tmp;
		if (etat.equals(Etat.EN_COURS.name()))
			 tmp =  reclamationRepository.findByDeveloppeurAndStatus(username,Etat.EN_COURS);
		else if (etat.equals(Etat.ClOTURE.name()))
			 tmp =  reclamationRepository.findByDeveloppeurAndStatus(username,Etat.ClOTURE);
		else
			return null;
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}

	@Override
	public List<ReclamationDTO> findByFilterAndEtat(String keyword, String etat) {
		List<Reclamation> tmp;
		if (etat.equals(Etat.EN_COURS.name()))
			 tmp =  reclamationRepository.findByFilterAndEtat(keyword,Etat.EN_COURS);
		else if (etat.equals(Etat.ClOTURE.name()))
			 tmp =  reclamationRepository.findByFilterAndEtat(keyword,Etat.ClOTURE);
		else if (etat.equals(Etat.EN_ATTENTE.name()))
			 tmp =  reclamationRepository.findByFilterAndEtat(keyword,Etat.EN_ATTENTE);
		else
			return null;
		System.out.println(tmp.size());
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		return res;
	}
	
	@Override
	public List<ReclamationDTO> findByFilter(String keyword) {
		 List<Reclamation> tmp = reclamationRepository.findByFilter(keyword);
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		
		return res;
	}
	@Override
	public List<ReclamationDTO> findBySocieteAndEtat(String societe, String etat) {
		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name()))
			 e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name()))
			 e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name()))
			 e = Etat.EN_ATTENTE;
		else
			return null;
		List<Reclamation> tmp = reclamationRepository.findBySocieteAndEtat(societe, e);
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			rec.setClient(r.getClient().getNom()+ " " + r.getClient().getPrenom());
			res.add(rec);
		
		});
		return res;
	}

	@Override
	public List<ReclamationDTO> findByStatusList(/*String etat1, String etat2*/) {
		List<Reclamation> tmp= reclamationRepository.findByListStatus(Etat.EN_COURS, Etat.ClOTURE);
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		
		return res;
	}
	
	@Override
	public List<ReclamationDTO> findBySociete(String name) {
		List<Reclamation> tmp= reclamationRepository.findByProjetSocieteName(name);
		List<ReclamationDTO> res = new ArrayList<>(tmp.size());
		if (tmp.size() == 0)
			return null;
		tmp.forEach(r ->{
			ReclamationDTO rec = new ReclamationDTO();
			rec.setId(r.getId());
			rec.setSujet(r.getSujet());
			rec.setDetails(r.getDetails());
			rec.setDateCreation(r.getDateCreation());
			rec.setType(r.getType().getType());
			rec.setClient(r.getClient().getNom() + " " + r.getClient().getPrenom());
			rec.setStatus(r.getEtat().name());
			if(r.getDeveloppeur() != null)
				rec.setDevelopper(r.getDeveloppeur().getNom() + " " + r.getDeveloppeur().getPrenom());
			rec.setProjet(r.getProjet().getDesignation());	
			rec.setSociete(r.getProjet().getSociete().getname());
			rec.setSpeciality(r.getProjet().getSpecialite().getNom());
			res.add(rec);
		});
		
		return res;
	}


	
	@Override
	public void deleteReclamation(Reclamation reclamation) {
		reclamationRepository.delete(reclamation);
		
	}

	@Override
	public void deleteReclamation(Long id) {
		// TODO Auto-generated method stub
		reclamationRepository.deleteById(id);
	}






	private void notifyFrontend(String entityTopic) {
        webSocketService.sendMessage(entityTopic);
    }

	@Override
	public ReclamationDetails getDétailsByUser(String username) {
		// TODO Auto-generated method stub
		ReclamationDetails details = new ReclamationDetails();
		User user = userService.findUserByUsername(username);
		details.setTotal(reclamationRepository.totalByUser(user));
		details.setEncours(reclamationRepository.totalByUserByEtat(user, Etat.EN_COURS));
		details.setCloture(reclamationRepository.totalByUserByEtat(user, Etat.ClOTURE));
		return details;
	}

	@Override
	public ReclamationDetails getDétailsByUserAndDates(String username, LocalDateTime date1, LocalDateTime date2) {
		ReclamationDetails details = new ReclamationDetails();
		User user = userService.findUserByUsername(username);
		details.setTotal(reclamationRepository.totalByUserAndDates(user,date1,date2));
		details.setEncours(reclamationRepository.totalByUserByEtatAndDates(user, Etat.EN_COURS,date1,date2));
		details.setCloture(reclamationRepository.totalByUserByEtatAndDates(user, Etat.ClOTURE,date1,date2));
		return details;
	}

	
	@Override
	public ReclamationDetails getDétailsByEtat() {
		// TODO Auto-generated method stub
		ReclamationDetails details = new ReclamationDetails();
		details.setTotal(reclamationRepository.count());
		details.setNouv(reclamationRepository.totalByEtat(Etat.EN_ATTENTE));
		details.setEncours(reclamationRepository.totalByEtat(Etat.EN_COURS));
		details.setCloture(reclamationRepository.totalByEtat(Etat.ClOTURE));
		return details;
	}
	

	@Override
	public ReclamationDetails getDétailsByEtatAndSoicety(String  societe) {
		Societe s = societeService.findSocieteByName(societe);
		ReclamationDetails details = new ReclamationDetails();
		details.setTotal(reclamationRepository.totalBySociete(s));
		details.setNouv(reclamationRepository.totalByEtatAndSociete(Etat.EN_ATTENTE,s));
		details.setEncours(reclamationRepository.totalByEtatAndSociete(Etat.EN_COURS,s));
		details.setCloture(reclamationRepository.totalByEtatAndSociete(Etat.ClOTURE,s));
		return details;
	}

	@Override
	public ReclamationDetails getDétailsByEtatAndSoicetyAndDates(String societe, LocalDateTime date1, LocalDateTime date2) {
		Societe s = societeService.findSocieteByName(societe);
		ReclamationDetails details = new ReclamationDetails();
		details.setTotal(reclamationRepository.totalBySocieteAndDates(s,date1,date2));
		details.setNouv(reclamationRepository.totalByEtatAndSocieteAndDates(Etat.EN_ATTENTE,s,date1,date1));
		details.setEncours(reclamationRepository.totalByEtatAndSocieteAndDates(Etat.EN_COURS,s,date1,date2));
		details.setCloture(reclamationRepository.totalByEtatAndSocieteAndDates(Etat.ClOTURE,s,date1,date2));
		return details;
	}
	
	@Override
	public List<TestProjet> totalByProjet() {
		List<Object[]> tmp = reclamationRepository.totalByProjet();
		List<ProjetDTO>projets = projetService.findAll();
		List<TestProjet> res = new ArrayList<>();
		projets.forEach(p->{
			x = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getDesignation())))
				{
					x = Integer.parseInt(t[1].toString());
				}
			});
			if (x > 0)
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(x);
				res.add(t);
			}
			else
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		x = 0;
		return res;
	}

	@Override
	public List<TestProjet> totalByProjetAndates(LocalDateTime date1, LocalDateTime date2) {

		List<Object[]> tmp = reclamationRepository.totalByProjetAndDates(date1,date2);
		List<ProjetDTO>projets = projetService.findAll();
		List<TestProjet> res = new ArrayList<>();
		projets.forEach(p->{
			x = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getDesignation())))
				{
					x = Integer.parseInt(t[1].toString());
				}
			});
			if (x > 0)
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(x);
				res.add(t);
			}
			else
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		x = 0;
		return res;
	}
		
	@Override
	public List<TestProjet> totalByProjetAndEtat(String etat) {
		// TODO Auto-generated method stub
		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name())) 
			e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name())) 
			e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name())) 
			 e = Etat.EN_ATTENTE;
		
		List<Object[]> tmp = reclamationRepository.totalByProjetAndEtat(e);
		List<ProjetDTO>projets = projetService.findAll();
		List<TestProjet> res = new ArrayList<>();
		projets.forEach(p->{
			x = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getDesignation())))
				{
					x = Integer.parseInt(t[1].toString());
				}
			});
			if (x > 0)
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(x);
				res.add(t);
			}
			else
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		return res;
	}
	
	@Override
	public List<TestProjet> totalByProjetAndEtatAndDates(String etat, LocalDateTime date1, LocalDateTime date2) {
		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name())) 
			e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name())) 
			e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name())) 
			 e = Etat.EN_ATTENTE;
		
		List<Object[]> tmp = reclamationRepository.totalByProjetAndEtatAndDates(e,date1,date2);
		List<ProjetDTO>projets = projetService.findAll();
		List<TestProjet> res = new ArrayList<>();
		projets.forEach(p->{
			x = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getDesignation())))
				{
					x = Integer.parseInt(t[1].toString());
				}
			});
			if (x > 0)
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(x);
				res.add(t);
			}
			else
			{
				TestProjet t = new TestProjet();
				t.setProjet(p.getDesignation());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		return res;
	}
	
	@Override
	public List<ReclamationDetailsType> totalByType() {

		List<Object[]> tmp = reclamationRepository.totalByType();
		List<TypeReclamation>types = typeService.getAll();
		List<ReclamationDetailsType> res = new ArrayList<>();
		types.forEach(p->{
			typeTest = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getType())))
				{
					typeTest = Integer.parseInt(t[1].toString());
				}
			});
			if (typeTest > 0)
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(typeTest);
				res.add(t);
			}
			else
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		return res;
	}
	

	@Override
	public List<ReclamationDetailsType> totalByTypeAndDates(LocalDateTime date1, LocalDateTime date2) {
		List<Object[]> tmp = reclamationRepository.totalByTypeAndDates(date1,date2);
		List<TypeReclamation>types = typeService.getAll();
		List<ReclamationDetailsType> res = new ArrayList<>();
		types.forEach(p->{
			typeTest = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getType())))
				{
					typeTest = Integer.parseInt(t[1].toString());
				}
			});
			if (typeTest > 0)
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(typeTest);
				res.add(t);
			}
			else
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		return res;
	}

	@Override
	public List<ReclamationDetailsType> totalByTypeAndEtatAndDates(String etat, LocalDateTime date1,
			LocalDateTime date2) {
		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name())) 
			e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name())) 
			e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name())) 
			 e = Etat.EN_ATTENTE;
		List<Object[]> tmp = reclamationRepository.totalByTypeAndEtatAndDates(e,date1,date2);
		List<TypeReclamation>types = typeService.getAll();
		List<ReclamationDetailsType> res = new ArrayList<>();
		types.forEach(p->{
			typeTest = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getType())))
				{
					System.out.println(Integer.parseInt(t[1].toString()));
					typeTest = Integer.parseInt(t[1].toString());
				}
			});
			if (typeTest > 0)
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(typeTest);
				res.add(t);
			}
			else
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		return res;
	}

	

	@Override
	public List<ReclamationDetailsType> totalByTypeAndEtat(String etat) {

		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name())) 
			e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name())) 
			e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name())) 
			 e = Etat.EN_ATTENTE;
		List<Object[]> tmp = reclamationRepository.totalByTypeAndEtat(e);
		List<TypeReclamation>types = typeService.getAll();
		List<ReclamationDetailsType> res = new ArrayList<>();
		types.forEach(p->{
			typeTest = 0;
			tmp.forEach(t->{
				if ((t[0].equals(p.getType())))
				{
					System.out.println(Integer.parseInt(t[1].toString()));
					typeTest = Integer.parseInt(t[1].toString());
				}
			});
			if (typeTest > 0)
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(typeTest);
				res.add(t);
			}
			else
			{
				ReclamationDetailsType t = new ReclamationDetailsType();
				t.settype(p.getType());
				t.setNbReclamation(0);
				res.add(t);
			}
			
		});
		return res;
	}
	
	@Override
	public List<ReclamationDetailsDev> totalByDeveloppeurs() {
		// TODO Auto-generated method stub
		
		 List <Object[]> tmp =  reclamationRepository.totalByDeveloppeur();
		 List<StaffDTO>users = userService.getAllEmployee();
			List<ReclamationDetailsDev> res = new ArrayList<>();
			users.forEach(u->{
				devTest = 0;
				tmp.forEach(t->{
					if ((t[0].equals(u.getNom())))
					{
						devTest = Integer.parseInt(t[1].toString());
					}
				});
				if (devTest > 0)
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(devTest);
					res.add(t);
				}
				else
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(0);
					res.add(t);
				}
				
			});
			return res;
	}

	@Override
	public List<ReclamationDetailsDev> totalByDeveloppeursAndDates(LocalDateTime date1, LocalDateTime date2) {
		 List <Object[]> tmp =  reclamationRepository.totalByDeveloppeurAndDates(date1,date2);
		 List<StaffDTO>users = userService.getAllEmployee();
			List<ReclamationDetailsDev> res = new ArrayList<>();
			users.forEach(u->{
				devTest = 0;
				tmp.forEach(t->{
					if ((t[0].equals(u.getNom())))
					{
						devTest = Integer.parseInt(t[1].toString());
					}
				});
				if (devTest > 0)
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(devTest);
					res.add(t);
				}
				else
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(0);
					res.add(t);
				}
				
			});
			return res;
	}
	@Override
	
	
	public List<ReclamationDetailsDev> totalByDeveloppeursAndEtat(String etat) {
		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name())) 
			e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name())) 
			e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name())) 
			 e = Etat.EN_ATTENTE;
		List <Object[]> tmp =  reclamationRepository.totalByDeveloppeurAndEtat(e);
		 List<StaffDTO>users = userService.getAllEmployee();
			List<ReclamationDetailsDev> res = new ArrayList<>();
			users.forEach(u->{
				devTest = 0;
				tmp.forEach(t->{
					if ((t[0].equals(u.getNom())))
					{
						devTest = Integer.parseInt(t[1].toString());
					}
				});
				if (devTest > 0)
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(devTest);
					res.add(t);
				}
				else
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(0);
					res.add(t);
				}
				
			});
			return res;
	}

	
	
	@Override
	public List<ReclamationDetailsDev> totalByDeveloppeursAndEtatAndDates(String etat, LocalDateTime date1,
			LocalDateTime date2) {
		Etat e = null;
		if (etat.equals(Etat.EN_COURS.name())) 
			e = Etat.EN_COURS;
		else if (etat.equals(Etat.ClOTURE.name())) 
			e = Etat.ClOTURE;
		else if (etat.equals(Etat.EN_ATTENTE.name())) 
			 e = Etat.EN_ATTENTE;
		List <Object[]> tmp =  reclamationRepository.totalByDeveloppeurAndEtatAndDates(e,date1,date2);
		 List<StaffDTO>users = userService.getAllEmployee();
			List<ReclamationDetailsDev> res = new ArrayList<>();
			users.forEach(u->{
				devTest = 0;
				tmp.forEach(t->{
					if ((t[0].equals(u.getNom())))
					{
						devTest = Integer.parseInt(t[1].toString());
					}
				});
				if (devTest > 0)
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(devTest);
					res.add(t);
				}
				else
				{
					ReclamationDetailsDev t = new ReclamationDetailsDev();
					t.setUsername(u.getNom()+" "+u.getPrenom());
					t.setNbReclamation(0);
					res.add(t);
				}
				
			});
			return res;
	}

	@Override
	public ReclamationDetailsClient detailsByClient(User client) {
		// TODO Auto-generated method stub
		ReclamationDetailsClient d = new ReclamationDetailsClient();
		long total = reclamationRepository.totalByClient(client);
		long attente = reclamationRepository.totalByClientByEtat(client, Etat.EN_ATTENTE);
		long encours = reclamationRepository.totalByClientByEtat(client, Etat.EN_COURS);
		long cloture = reclamationRepository.totalByClientByEtat(client, Etat.ClOTURE);
		d.setTotal(total);
		d.setAttente(attente);
		d.setEncours(encours);
		d.setCloture(cloture);
		return d;
	}























}
