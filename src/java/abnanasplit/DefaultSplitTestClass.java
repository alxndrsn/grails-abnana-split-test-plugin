package abnanasplit;

import org.codehaus.groovy.grails.commons.AbstractGrailsClass;

public class DefaultSplitTestClass extends AbstractGrailsClass implements SplitTestClass {
	public DefaultSplitTestClass(Class clazz) {
		super(clazz, "SplitTest");
	}
}

