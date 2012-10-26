/**
 * 
 */
package com.alexnevsky.web.domain.media;

import java.awt.Dimension;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alexnevsky.web.util.FileUtil;
import com.alexnevsky.web.util.GlobalConstants;
import com.alexnevsky.web.util.ImageUtil;

/**
 * This class represents a Data Transfer Object for the entity. This DTO can be
 * used thoroughout all layers, the data layer, the controller layer and the
 * view layer.
 * 
 * @author Alex Nevsky
 * 
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image implements java.io.Serializable, Comparable<Image> {

	private static final long serialVersionUID = 1L;

	// Properties
	// -------------------------------------------------------------------------------

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "extension")
	private String extension;

	@Column(name = "alt")
	private String alt;

	@Column(name = "path")
	private String path;

	@Column(name = "thumb_path")
	private String thumbPath;

	@Column(name = "width")
	private Double width;

	@Column(name = "height")
	private Double height;

	@Column(name = "upload_date")
	private Date uploadDate;

	@Column(name = "size")
	private Integer size;

	@Column(name = "category")
	private String category;

	@ManyToMany(mappedBy = "images")
	private List<Gallery> galleries;

	// Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Default constructor.
	 */
	public Image() {
		// Always keep the default constructor alive in a Javabean class.
	}

	/**
	 * Main constructor. Automatically calculates the values ​​of other fields.
	 * 
	 * @param name
	 *            The name of this image.
	 * @param alt
	 *            The alt text of this image.
	 * @param path
	 *            The path in the system to this image.
	 */
	public Image(String name, String alt, String path) {
		this.name = name;
		this.alt = alt;
		this.path = path;
		this.setMetaFields();
	}

	// Getters
	// -------------------------------------------------------------------------------

	public Long getId() {
		return this.id;
	}

	public Double getWidth() {
		return this.width;
	}

	public Double getHeight() {
		return this.height;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public String getName() {
		return this.name;
	}

	public String getExtension() {
		return this.extension;
	}

	public String getAlt() {
		return this.alt;
	}

	public String getPath() {
		return this.path;
	}

	public String getThumbPath() {
		return this.thumbPath;
	}

	public Integer getSize() {
		return this.size;
	}

	public String getCategory() {
		return this.category;
	}

	public List<Gallery> getGalleries() {
		return this.galleries;
	}

	// Setters
	// -------------------------------------------------------------------------------

	public void setId(Long id) {
		this.id = id;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setGalleries(List<Gallery> galleries) {
		this.galleries = galleries;
	}

	// More stuff
	// -------------------------------------------------------------------------------

	private void setMetaFields() {
		File imageFile = new File(GlobalConstants.DOMAIN_DIR_PATH + this.path);

		Dimension dimension = ImageUtil.getImageDimension(imageFile);
		if (dimension != null) {
			this.setWidth(dimension.getWidth());
			this.setHeight(dimension.getHeight());
		}

		if (imageFile != null) {
			int dotIndex = this.path.lastIndexOf(".");
			if (dotIndex > -1) {
				this.setExtension(this.path.substring(dotIndex + 1));
			}
		}

		this.setUploadDate(new Date());
		this.setSize((int) FileUtil.getFileSize(imageFile));
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
		return (other instanceof Image) && (this.id != null) ? this.id.equals(((Image) other).id) : (other == this);
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
	 * Sort entities. Newest first.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Image o) {
		if ((this.id != null) && (o.getId() != null)) {
			return (int) (o.getId() - this.id);
		} else {
			return -1;
		}
	}

}
