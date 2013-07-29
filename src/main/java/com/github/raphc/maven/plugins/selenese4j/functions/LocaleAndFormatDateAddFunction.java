/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author Raphael
 * The function which convert 'dateadd' function as a date with a given pattern and locale
 * It will add the given number of day to the current date
 */
public class LocaleAndFormatDateAddFunction extends AbstractPreDefinedFunction {

	public LocaleAndFormatDateAddFunction(){
		this.functionName = "dateadd";
		this.functionArgsNumber = 3;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#process(java.lang.String)
	 */
	public String replaceByValue(String token) {
		if(!matches(token)){
			throw new RuntimeException("this instruction ["+token+"] doesn't match LocaleAndFormatDateAdd function");
		}
		
		//Extraction du 1er arg : correspond au nombre de jour a ajouter
		int nubmerOfDayToAdd = Integer.parseInt(getFunctionArgs()[0]); 
		//Extraction du 2eme arg : correspond à la Locale
		Locale locale = LocaleUtils.toLocale(getFunctionArgs()[1]);
		//Extraction du 3eme arg : correspond au format
		String format = getFunctionArgs()[2];
		
		// Calcul du prochain jour
		GregorianCalendar futureDay = (GregorianCalendar) GregorianCalendar.getInstance(locale);
		futureDay.add(GregorianCalendar.DATE, nubmerOfDayToAdd);
		
		return DateFormatUtils.format(futureDay, format, locale);
	}



}
