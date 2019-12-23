package sk.tsystems.gamestudio.service.ScoreService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;


import sk.tsystems.gamestudio.entity.Raiting;

@Component
@Transactional
public class RaitngServiceJPA implements RaitingService {

	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	public List<Raiting> getRaiting(String game) {
		return (List<Raiting>) entityManager
				.createQuery("select c from Raiting c where c.game = :game")
				.setParameter("game", game).getResultList();
	}

	@Override
	public void setRaiting(Raiting raiting) {
		try {
		Raiting dbRaiting = (Raiting)entityManager.createQuery("select r from Raiting r where r.username = :username and r.game = :game ")
				.setParameter("username", raiting.getUsername())
				.setParameter("game", raiting.getGame())
				.getSingleResult();
				dbRaiting.setValue(raiting.getValue());
				}
		catch(NoResultException e) {
			entityManager.persist(raiting);
		}}

	@Override
	public double getAverageRaiting() {
		// TODO Auto-generated method stub
		return 0;
	}
		


	}

	

