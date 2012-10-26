/**
 * 
 */
package com.alexnevsky.web.dao;

import org.springframework.stereotype.Repository;

/**
 * General Data Access Object that can be used for any type domain object. A single instance
 * implementing this class can be used for multiple types of domain objects.
 * 
 * Implementation of AbstractGeneralDAO.
 * 
 * @author Alex Nevsky
 * 
 */
@Repository
public class GeneralDAOImpl extends AbstractGeneralDAO implements IGeneralDAO {

}
