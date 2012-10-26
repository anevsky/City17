/**
 * 
 */
package com.alexnevsky.web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * We have this base abstract class for our GeneralDAOs so that we don't have to override and
 * autowire the sessionFactory property for each one. That is the only reason for having this class.
 * 
 * The @Autowired annotation tells Spring to inject the sessionFactory bean into this setter method.
 * 
 * @author Alex Nevsky
 * 
 */
public abstract class AbstractGeneralDAO extends com.googlecode.genericdao.dao.hibernate.GeneralDAOImpl implements
		com.googlecode.genericdao.dao.hibernate.GeneralDAO {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
