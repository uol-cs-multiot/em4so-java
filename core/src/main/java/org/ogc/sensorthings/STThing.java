package org.ogc.sensorthings;

import java.util.List;

import org.ogc.sensorthings.sensing.STDataStream;
import org.ogc.sensorthings.tasking.STTaskingCapabilities;

// TODO: Auto-generated Javadoc
/**
 * The Class STThing.
 */
public class STThing extends STEntity{
	
	/** The description. */
	private String description;
	
	/** The location. */
	private List<STLocation> location;
	
	/** The data stream. */
	private List<STDataStream> dataStream;
	
	/** The tasking capabilities. */
	private List<STTaskingCapabilities> taskingCapabilities;
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public List<STLocation> getLocation() {
		return location;
	}
	
	/**
	 * Gets the data stream.
	 *
	 * @return the data stream
	 */
	public List<STDataStream> getDataStream() {
		return dataStream;
	}
	
	/**
	 * Gets the tasking capabilities.
	 *
	 * @return the tasking capabilities
	 */
	public List<STTaskingCapabilities> getTaskingCapabilities() {
		return taskingCapabilities;
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
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(List<STLocation> location) {
		this.location = location;
	}
	
	/**
	 * Sets the data stream.
	 *
	 * @param dataStream the new data stream
	 */
	public void setDataStream(List<STDataStream> dataStream) {
		this.dataStream = dataStream;
	}
	
	/**
	 * Sets the tasking capabilities.
	 *
	 * @param taskingCapabilities the new tasking capabilities
	 */
	public void setTaskingCapabilities(List<STTaskingCapabilities> taskingCapabilities) {
		this.taskingCapabilities = taskingCapabilities;
	}
}
