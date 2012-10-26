package com.alexnevsky.web.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * We have this base abstract class for our GenericDAOs so that we don't have to override and
 * autowire the sessionFactory property for each one. That is the only reason for having this class.
 * 
 * The @Autowired annotation tells Spring to inject the sessionFactory bean into this setter method.
 * 
 * @author Alex Nevsky
 * 
 * @param <T>
 *            the type for the entity class
 * @param <ID>
 *            the type of the entity identifier
 * 
 */
public abstract class AbstractBaseDAO<T, ID extends Serializable> extends
		com.googlecode.genericdao.dao.hibernate.GenericDAOImpl<T, ID> {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
