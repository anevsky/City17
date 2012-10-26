/**
 * 
 */
package com.alexnevsky.web.domain.meta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alexnevsky.web.util.StringUtil;

/**
 * @author Alex Nevsky
 * 
 */
@Entity
@Table(name = "story_term")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StoryTerm implements java.io.Serializable, Comparable<StoryTerm> {

	private static final long serialVersionUID = 1L;

	// Properties
	// -------------------------------------------------------------------------------

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	// Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Default constructor.
	 */
	public StoryTerm() {
		// Always keep the default constructor alive in a Javabean class.
	}

	/**
	 * Main constructor.
	 * 
	 * @param name
	 */
	public StoryTerm(String name) {
		this.setName(name);
	}

	// Getters
	// -------------------------------------------------------------------------------

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	// Setters
	// -------------------------------------------------------------------------------

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = StringUtil.toAlphanumericHyphen(name).toLowerCase();
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
		return (other instanceof StoryTerm) && (this.id != null) ? this.name.equals(((StoryTerm) other).id)
				: (other == this);
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
	 * Sort by name.
	 */
	@Override
	public int compareTo(StoryTerm other) {
		return this.name.compareTo(other.getName());
	}
}