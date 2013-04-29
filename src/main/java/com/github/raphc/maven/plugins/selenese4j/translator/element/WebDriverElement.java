/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Raphael
 * The annotation used to flag a class as a WebDriver element
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebDriverElement {

}
