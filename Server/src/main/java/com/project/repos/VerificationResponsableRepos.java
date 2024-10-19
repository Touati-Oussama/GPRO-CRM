package com.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Societe;
import com.project.entities.User;
import com.project.entities.VerificationResponsable;

public interface VerificationResponsableRepos extends JpaRepository<VerificationResponsable, Long> {

	VerificationResponsable findBySociete(Societe societe);
	VerificationResponsable findByResponsable(User responsable);
}
