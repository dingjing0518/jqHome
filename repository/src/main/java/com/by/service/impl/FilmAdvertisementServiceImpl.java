package com.by.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.FilmAdvertisement;
import com.by.model.FilmContent;
import com.by.model.ShoppingMall;
import com.by.repository.FilmAdvertisementRepository;
import com.by.service.FilmAdvertisementService;
import com.by.typeEnum.ShowOnIndex;

/**
 * Created by yagamai on 16-3-30.
 */
@Service
@Transactional
public class FilmAdvertisementServiceImpl implements FilmAdvertisementService {
	@Autowired
	private FilmAdvertisementRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	public List<FilmAdvertisement> findByMall(ShoppingMall mall) {
		return repository.findByMall(mall);
	}

	@Override
	public Page<FilmAdvertisement> findByMall(ShoppingMall mall, Pageable pageable) {
		return repository.findByMall(mall, pageable);
	}

	@Override
	public FilmAdvertisement findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public FilmAdvertisement update(FilmAdvertisement adv) {
		FilmAdvertisement a = repository.findOne(adv.getId());
		a.setCoverImg(adv.getCoverImg());
		a.setDescription(adv.getDescription());
		FilmContent content = a.getContent();
		content.setSummary(adv.getContent().getSummary());
		return a;
	}

	@Override
	public FilmAdvertisement save(FilmAdvertisement adv) {
		return repository.save(adv);
	}

	@Override
	public List<FilmAdvertisement> findOnIndex() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FilmAdvertisement> c = cb.createQuery(FilmAdvertisement.class);
		Root<FilmAdvertisement> root = c.from(FilmAdvertisement.class);
		c.select(root);
		c.where(cb.equal(root.get("isShowOnIndex"), ShowOnIndex.SHOWONINDEX));
		c.orderBy(cb.desc(root.get("createdTime")));
		List<FilmAdvertisement> films = em.createQuery(c).setFirstResult(0).setMaxResults(4).getResultList();
		return films;
	}
}
