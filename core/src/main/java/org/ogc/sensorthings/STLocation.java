
package org.ogc.sensorthings;

import java.util.Date;

import org.geojson.Geometry;

// TODO: Auto-generated Javadoc
/**
 * The Class STLocation.
 *
 * @param <T> the generic type
 */
public class STLocation<T> {
 
 /** The date. */
 private Date date;
 
 /** The geometry. */
 private Geometry<T> geometry;

/**
 * Gets the date.
 *
 * @return the date
 */
public Date getDate() {
	return date;
}

/**
 * Gets the geometry.
 *
 * @return the geometry
 */
public Geometry<T> getGeometry() {
	return geometry;
}

/**
 * Sets the date.
 *
 * @param date the new date
 */
public void setDate(Date date) {
	this.date = date;
}

/**
 * Sets the geometry.
 *
 * @param geometry the new geometry
 */
public void setGeometry(Geometry<T> geometry) {
	this.geometry = geometry;
}
}
