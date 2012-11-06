/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Raphael
 *
 */
public final class StringSplittingUtils {

	/**
	 * 
	 */
	public static String[] split(String compactedString, char startBoundary, char endBoundary) {
		String[] result = new String[0];
		int i = 0;
		char[] element = null; 
		char[] plainStringToChar = StringUtils.trim(compactedString).toCharArray();
		for(char currentChar : plainStringToChar){
			// On verifie si il s'agit du char qui delimite le debut (char de debut sans escape \ )
			// Si le delimiteur de debut et de fin sont identiques on verifie qu'on ne traite pas deja un element
			if(((currentChar == startBoundary && startBoundary != endBoundary) || 
					(currentChar == startBoundary && startBoundary == endBoundary && element == null)
				) && (i == 0 || i-1 >=0 && plainStringToChar[i-1] != '\\')
			){
				// On initialise la tableau de char
				element = new char[0];
			// On verifie si il s'agit du char qui delimite le fin (char de fin sans escape \ )
			} else if (currentChar == endBoundary && (i == 0 || i-1 >=0 && plainStringToChar[i-1] != '\\')) {
				//On ajoute l'arg au tableau de resultat
				result = (String[]) ArrayUtils.add(result, String.valueOf(element));
				//On nettoie l'element
				element = null;
			} else if(element != null) {
				element = ArrayUtils.add(element, currentChar);
			}
			i++;
		}
		
		return result;
	}

}
