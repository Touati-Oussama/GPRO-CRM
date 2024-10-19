package com.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Projet;
import com.project.entities.TrelloBoard;

public interface TrelloBoardRepository extends JpaRepository<TrelloBoard,String> {

	TrelloBoard findByProjet(Projet p);
}
