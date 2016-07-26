package org.ogc.sensorthings.tasking;

// TODO: Auto-generated Javadoc
/**
 * The Class STParameter.
 */
public class STParameter {

/** The id. */
private String id;

/** The description. */
private String description;

/** The use. */
private STUse use;

/** The definition. */
private STDefinition definition;

/**
 * Gets the id.
 *
 * @return the id
 */
public String getId() {
	return id;
}

/**
 * Gets the description.
 *
 * @return the description
 */
public String getDescription() {
	return description;
}

/**
 * Gets the use.
 *
 * @return the use
 */
public STUse getUse() {
	return use;
}

/**
 * Gets the definition.
 *
 * @return the definition
 */
public STDefinition getDefinition() {
	return definition;
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
 * Sets the description.
 *
 * @param description the new description
 */
public void setDescription(String description) {
	this.description = description;
}

/**
 * Sets the use.
 *
 * @param use the new use
 */
public void setUse(STUse use) {
	this.use = use;
}

/**
 * Sets the definition.
 *
 * @param definition the new definition
 */
public void setDefinition(STDefinition definition) {
	this.definition = definition;
}
}
