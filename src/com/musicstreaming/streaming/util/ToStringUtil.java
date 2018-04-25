package com.musicstreaming.streaming.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 *
 * @author Alberto Taboada Varela
 * @version 0.2
 */
public class ToStringUtil {
	
	public static String toString(Object o) {
		return ToStringBuilder.reflectionToString(o);
	}
}
