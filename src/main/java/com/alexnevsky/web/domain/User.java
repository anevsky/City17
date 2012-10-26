/**
 * 
 */
package com.alexnevsky.web.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This class represents a Data Transfer Object for the entity. This DTO can be
 * used thoroughout all layers, the data layer, the controller layer and the
 * view layer.
 * 
 * @author Alex Nevsky
 * 
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Properties
	// -------------------------------------------------------------------------------

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "age")
	private Integer age;

	@Column(name = "registered_date")
	private Date registeredDate;

	// Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Default constructor.
	 */
	public User() {
		// Always keep the default constructor alive in a Javabean class.
	}

	/**
	 * Minimal constructor. Contains required fields.
	 * 
	 * @param id
	 *            The ID of this User. Set it to null in case of a new and
	 *            unexisting user.
	 * @param login
	 *            The login of this User.
	 * @param password
	 *            The password of this User.
	 * @param registeredDate
	 *            The registered date of this User.
	 */
	public User(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	/**
	 * Full constructor. Contains required and optional fields.
	 * 
	 * @param id
	 *            The ID of this User. Set it to null in case of a new and
	 *            unexisting user.
	 * @param login
	 *            The login of this User.
	 * @param password
	 *            The password of this User.
	 * @param email
	 *            The email address of this User.
	 * @param displayName
	 *            The display name of this User.
	 * @param age
	 *            The age of this User.
	 * @param registeredDate
	 *            The registered date of this User.
	 */
	public User(Long id, String login, String password, String email, String displayName, Integer age,
			Date registeredDate) {
		this(id, login, password);
		this.email = email;
		this.displayName = displayName;
		this.age = age;
		this.registeredDate = registeredDate;
	}

	// Getters
	// -------------------------------------------------------------------------------

	public Long getId() {
		return this.id;
	}

	public String getLogin() {
		return this.login;
	}

	public String getPassword() {
		return this.password;
	}

	public Date getRegisteredDate() {
		return this.registeredDate;
	}

	public String getEmail() {
		return this.email;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public Integer getAge() {
		return this.age;
	}

	// Setters
	// -------------------------------------------------------------------------------

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	// Override
	// -------------------------------------------------------------------------------

	/**
	 * The entity ID is unique for each entity. So this should compare entity by ID
	 * only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof User) && (this.id != null) ? this.id.equals(((User) other).id) : (other == this);
	}

	/**
	 * The entity ID is unique for each entity. So entity with same ID should return
	 * same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.id != null) ? (this.getClass().hashCode() + this.id.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this entity.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(128);
		sb.append("User [id=");
		sb.append(this.id);
		sb.append(", login=");
		sb.append(this.login);
		sb.append(", password=");
		sb.append(this.password);
		sb.append(", email=");
		sb.append(this.email);
		sb.append(", displayName=");
		sb.append(this.displayName);
		sb.append(", age=");
		sb.append(this.age);
		sb.append(", registeredDate=");
		sb.append(this.registeredDate);
		sb.append("]");
		return sb.toString();
	}

}
