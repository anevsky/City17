/**
 * 
 */
package com.alexnevsky.web.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexnevsky.web.dao.IGeneralDAO;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

/**
 * @author Alex Nevsky
 * 
 */
@Service
public class GeneralServiceImpl implements IGeneralService {

	private IGeneralDAO dao;

	@Autowired
	public void setIGeneralDAO(IGeneralDAO dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public <T> T find(Class<T> type, Serializable id) {
		return this.dao.find(type, id);
	}

	@Override
	@Transactional
	public <T> T[] find(Class<T> type, Serializable... ids) {
		return this.dao.find(type, ids);
	}

	@Override
	@Transactional
	public <T> T getReference(Class<T> type, Serializable id) {
		return this.dao.getReference(type, id);
	}

	@Override
	@Transactional
	public <T> T[] getReferences(Class<T> type, Serializable... ids) {
		return this.dao.getReferences(type, ids);
	}

	@Override
	@Transactional
	public boolean save(Object entity) {
		return this.dao.save(entity);
	}

	@Override
	@Transactional
	public boolean[] save(Object... entities) {
		return this.dao.save(entities);
	}

	@Override
	@Transactional
	public boolean remove(Object entity) {
		return this.dao.remove(entity);
	}

	@Override
	@Transactional
	public void remove(Object... entities) {
		this.dao.remove(entities);
	}

	@Override
	@Transactional
	public boolean removeById(Class<?> type, Serializable id) {
		return this.dao.removeById(type, id);
	}

	@Override
	@Transactional
	public void removeByIds(Class<?> type, Serializable... ids) {
		this.dao.removeByIds(type, ids);
	}

	@Override
	@Transactional
	public <T> List<T> findAll(Class<T> type) {
		return this.dao.findAll(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public <T> List<T> search(ISearch search) {
		return this.dao.search(search);
	}

	@Override
	@Transactional
	public Object searchUnique(ISearch search) {
		return this.dao.searchUnique(search);
	}

	@Override
	@Transactional
	public int count(ISearch search) {
		return this.dao.count(search);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public <T> SearchResult<T> searchAndCount(ISearch search) {
		return this.dao.searchAndCount(search);
	}

	@Override
	@Transactional
	public boolean isAttached(Object entity) {
		return this.dao.isAttached(entity);
	}

	@Override
	@Transactional
	public void refresh(Object... entities) {
		this.dao.refresh(entities);
	}

	@Override
	@Transactional
	public void flush() {
		this.dao.flush();
	}

	@Override
	@Transactional
	public Filter getFilterFromExample(Object example) {
		return this.dao.getFilterFromExample(example);
	}

	@Override
	@Transactional
	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		return this.dao.getFilterFromExample(example, options);
	}

}
