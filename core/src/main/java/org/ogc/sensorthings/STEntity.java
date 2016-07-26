
package org.ogc.sensorthings;

// TODO: Auto-generated Javadoc
/**
 * The Class STEntity.
 */
public abstract class STEntity {
	
	/** The id. */
	protected String id;
	
	/** The self link. */
	protected String selfLink;
	
	/** The association link. */
	protected String associationLink;
	
	/** The navigation link. */
	protected String navigationLink;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the self link.
	 *
	 * @return the self link
	 */
	public String getSelfLink() {
		return selfLink;
	}
	
	/**
	 * Gets the association link.
	 *
	 * @return the association link
	 */
	public String getAssociationLink() {
		return associationLink;
	}
	
	/**
	 * Gets the navigation link.
	 *
	 * @return the navigation link
	 */
	public String getNavigationLink() {
		return navigationLink;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Sets the self link.
	 *
	 * @param selfLink the new self link
	 */
	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}
	
	/**
	 * Sets the association link.
	 *
	 * @param associationLink the new association link
	 */
	public void setAssociationLink(String associationLink) {
		this.associationLink = associationLink;
	}
	
	/**
	 * Sets the navigation link.
	 *
	 * @param navigationLink the new navigation link
	 */
	public void setNavigationLink(String navigationLink) {
		this.navigationLink = navigationLink;
	}
}
