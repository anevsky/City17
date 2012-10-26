/**
 * 
 */
package com.alexnevsky.web.domain.media;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * This class represents a Data Transfer Object for the entity. This DTO can be
 * used thoroughout all layers, the data layer, the controller layer and the
 * view layer.
 * 
 * @author Alex Nevsky
 * 
 */
@Entity
@Table(name = "gallery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gallery implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Properties
	// -------------------------------------------------------------------------------

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinTable(name = "image_gallery", joinColumns = @JoinColumn(name = "gallery_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	@Fetch(FetchMode.JOIN)
	private Set<Image> images = new TreeSet<Image>();

	/**
	 * Default constructor.
	 */
	public Gallery() {
		// Always keep the default constructor alive in a Javabean class.
	}

	/**
	 * Main constructor.
	 * 
	 * @param name
	 *            The name of this gallery.
	 * @param description
	 *            The description of this gallery.
	 */
	public Gallery(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// Getters
	// -------------------------------------------------------------------------------

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public TreeSet<Image> getImages() {
		return new TreeSet<Image>(this.images);
	}

	// Setters
	// -------------------------------------------------------------------------------

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	// More stuff
	// -------------------------------------------------------------------------------

	public void addImage(Image image) {
		if (image != null) {
			if (!this.images.contains(image)) {
				this.images.add(image);
			}
		}
	}

	public void addImages(Set<Image> images) {
		for (Image i : images) {
			if (!this.images.contains(i)) {
				this.images.add(i);
			}
		}
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
		return (other instanceof Gallery) && (this.id != null) ? this.id.equals(((Gallery) other).id) : (other == this);
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
}
