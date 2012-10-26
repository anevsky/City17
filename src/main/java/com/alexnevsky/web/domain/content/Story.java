/**
 * 
 */
package com.alexnevsky.web.domain.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import com.alexnevsky.web.domain.media.Gallery;
import com.alexnevsky.web.domain.meta.StoryTerm;

/**
 * This class represents a Data Transfer Object for the entity. This DTO can be
 * used thorough out all layers, the data layer, the controller layer and the
 * view layer.
 * 
 * @author Alex Nevsky
 * 
 */
@Entity
@Table(name = "story")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Story implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Properties
	// -------------------------------------------------------------------------------

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "source")
	private String source;

	@Column(name = "published_date")
	private Date publishedDate;

	@Column(name = "content")
	private String content;

	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinTable(name = "story_gallery", joinColumns = @JoinColumn(name = "story_id"), inverseJoinColumns = @JoinColumn(name = "gallery_id"))
	@Fetch(FetchMode.JOIN)
	private List<Gallery> galleries;

	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinTable(name = "story_term_relationship", joinColumns = @JoinColumn(name = "story_id"), inverseJoinColumns = @JoinColumn(name = "term_id"))
	@Fetch(FetchMode.JOIN)
	private Set<StoryTerm> terms = new HashSet<StoryTerm>();

	// Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Default constructor.
	 */
	public Story() {
		// Always keep the default constructor alive in a Javabean class.
	}

	/**
	 * Main constructor.
	 * 
	 * @param title
	 * @param content
	 * @param source
	 */
	public Story(String title, String content, String source) {
		this.title = title;
		this.content = content;
		this.source = source;

		this.setPublishedDate(new Date());
	}

	// Getters
	// -------------------------------------------------------------------------------

	public Long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getSource() {
		return this.source;
	}

	public Date getPublishedDate() {
		return this.publishedDate;
	}

	public String getContent() {
		return this.content;
	}

	public List<Gallery> getGalleries() {
		return this.galleries;
	}

	public TreeSet<StoryTerm> getTerms() {
		return new TreeSet<StoryTerm>(this.terms);
	}

	// Setters
	// -------------------------------------------------------------------------------

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setGalleries(List<Gallery> galleries) {
		this.galleries = galleries;
	}

	// More stuff
	// -------------------------------------------------------------------------------

	public void addTag(String tag) {
		if ((tag != null) && !tag.isEmpty()) {
			this.terms.add(new StoryTerm(tag));
		}
	}

	public void addTags(Set<String> tags) {
		for (String tag : tags) {
			this.addTag(tag);
		}
	}

	public void addCommaSeparatedTags(String tags) {
		if (tags != null) {
			String[] parts = tags.split(",");
			Set<String> tagsSet = new HashSet<String>(parts.length);

			for (String tag : parts) {
				tagsSet.add(tag);
			}

			this.addTags(tagsSet);
		}
	}

	public List<String> getTags() {
		TreeSet<StoryTerm> tagsSet = new TreeSet<StoryTerm>(this.terms);
		List<String> tagsList = new ArrayList<String>(tagsSet.size());

		for (StoryTerm term : tagsSet) {
			tagsList.add(term.getName());
		}

		return tagsList;
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
		return (other instanceof Story) && (this.id != null) ? this.id.equals(((Story) other).id) : (other == this);
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
