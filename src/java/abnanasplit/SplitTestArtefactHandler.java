package abnanasplit;

import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;

public class SplitTestArtefactHandler extends ArtefactHandlerAdapter {
	public SplitTestArtefactHandler() {
		super("SplitTest", SplitTestClass.class, DefaultSplitTestClass.class, "SplitTest");
	}
}

