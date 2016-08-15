/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

// TODO: Auto-generated Javadoc
/** The Class Element. */
public class Element
{

	/** The id. */
	private String id;

	/** The scope. */
	private String scope;

	/** The name. */
	private String name;

	/** The attr names. */
	private String[] attrNames;

	/** The attribute name. */
	private String attributeName;

	/** The attributes. */
	private HashMap<String, String> attributes;

	/** The kind. */
	private String kind;

	/** The effect. */
	private String effect;

	/** The value. */
	private String value;

	/** The to. */
	private MessagePeer to;

	/** The from. */
	private MessagePeer from;

	/** The received date. */
	private Date receivedDate;

	/** Instantiates a new element. */
	public Element()
	{

	}

	/** Instantiates a new element.
	 *
	 * @param scope the scope
	 * @param kind the kind
	 * @param name the name */
	public Element(String scope, String kind, String name)
	{
		this.scope = scope;
		this.kind = kind;
		this.name = name;
	}

	/** Instantiates a new element.
	 *
	 * @param scope the scope
	 * @param name the name */
	public Element(String scope, String name)
	{
		this.scope = scope;
		this.name = name;
	}

	/** Gets the scope.
	 *
	 * @return the scope */
	public String getScope()
	{
		return scope;
	}

	/** Sets the scope.
	 *
	 * @param scope the new scope */
	public void setScope(String scope)
	{
		this.scope = scope;
	}

	/** Gets the attr names.
	 *
	 * @return the attr names */
	public String[] getAttrNames()
	{
		return attrNames;
	}

	/** Sets the attr names.
	 *
	 * @param attrNames the new attr names */
	public void setAttrNames(String[] attrNames)
	{
		this.attrNames = attrNames;
	}

	public void addAttribute(String key, String value)
	{
		if (attributes == null)
			attributes = new HashMap<String, String>(1);
		attributes.put(key, value);
	}

	/** Gets the attribute name.
	 *
	 * @return the attribute name */
	public String getAttributeName()
	{
		return attributeName;
	}

	/** Sets the attribute name.
	 *
	 * @param attributeName the new attribute name */
	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	/** Gets the attributes.
	 *
	 * @return the attributes */
	public HashMap<String, String> getAttributes()
	{
		return attributes;
	}

	/** Sets the attributes.
	 *
	 * @param attributes the attributes */
	public void setAttributes(HashMap<String, String> attributes)
	{
		this.attributes = attributes;
	}

	/** Gets the kind.
	 *
	 * @return the kind */
	public String getKind()
	{
		return kind;
	}

	/** Sets the kind.
	 *
	 * @param kind the new kind */
	public void setKind(String kind)
	{
		this.kind = kind;
	}

	/** Gets the effect.
	 *
	 * @return the effect */
	public String getEffect()
	{
		return effect;
	}

	/** Sets the effect.
	 *
	 * @param effect the new effect */
	public void setEffect(String effect)
	{
		this.effect = effect;
	}

	/** Gets the value.
	 *
	 * @return the value */
	public String getValue()
	{
		return value;
	}

	/** Sets the value.
	 *
	 * @param value the new value */
	public void setValue(String value)
	{
		this.value = value;
	}

	/** Gets the to.
	 *
	 * @return the to */
	public MessagePeer getTo()
	{
		return to;
	}

	/** Sets the to.
	 *
	 * @param to the new to */
	public void setTo(MessagePeer to)
	{
		this.to = to;
	}

	/** Gets the from.
	 *
	 * @return the from */
	public MessagePeer getFrom()
	{
		return from;
	}

	/** Sets the from.
	 *
	 * @param from the new from */
	public void setFrom(MessagePeer from)
	{
		this.from = from;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString()
	{
		String s = "";
		s += "";

		s += "scope:";
		if (scope != null)
			s += scope;
		s += "\n";

		s += "name: ";
		if (name != null)
			s += name;
		s += "\n";

		s += "attrNames:";
		if (attrNames != null)
		{
			s += " [";
			for (String st : attrNames)
			{

				s += st + ",";
			}
			s = s.substring(0, s.length());
			s += "]";
		}
		s += " \n";

		s += " attributeName:";
		if (attributeName != null)
			s += attributeName;
		s += "\n";
		s += "attributes:";
		if (attributes != null)
		{
			s += " [";
			for (Entry<String, String> e : attributes.entrySet())
			{
				s += "{" + e.getKey() + "-" + e.getValue() + "},";
			}
			s = s.substring(0, s.length());
			s += "]";
		}
		s += " \n";

		s += " kind:";
		if (kind != null)
			s += kind;
		s += "\n";
		s += " effect:";
		if (effect != null)
			s += effect;
		s += "\n";
		s += " value:";
		if (value != null)
			s += value;
		s += "\n";
		s += "to:";
		if (to != null)
			s += to;
		s += "\n";
		s += " from:";
		if (from != null)
			s += from;

		return s;
	}

	/** Gets the id.
	 *
	 * @return the id */
	public String getId()
	{
		return id;
	}

	/** Sets the id.
	 *
	 * @param id the new id */
	public void setId(String id)
	{
		this.id = id;
	}

	/** Gets the received date.
	 *
	 * @return the received date */
	public Date getReceivedDate()
	{
		return receivedDate;
	}

	/** Sets the received date.
	 *
	 * @param receivedDate the new received date */
	public void setReceivedDate(Date receivedDate)
	{
		this.receivedDate = receivedDate;
	}

	/** Gets the name.
	 *
	 * @return the name */
	public String getName()
	{
		return name;
	}

	/** Sets the name.
	 *
	 * @param name the new name */
	public void setName(String name)
	{
		this.name = name;
	}

}
