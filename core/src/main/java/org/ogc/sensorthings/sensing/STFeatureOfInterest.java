
package org.ogc.sensorthings.sensing;

import org.geojson.Geometry;

// TODO: Auto-generated Javadoc
/**
 * The Class STFeatureOfInterest.
 *
 * @param <T> the generic type
 */
public class STFeatureOfInterest<T> {

/** The geometry. */
private Geometry<T> geometry;

/**
 * Gets the geometry.
 *
 * @return the geometry
 */
public Geometry<T> getGeometry() {
	return geometry;
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
