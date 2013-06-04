package abnanasplit

class AbnanaSplitGrailsPluginImpl {
	static artefacts = [SplitTestArtefactHandler]
	static watchedResources = [
		'file:./grails-app/split-tests/**/*SplitTest.groovy',
		'file:../../plugins/*/split-tests/**/*SplitTest.groovy'
	]
	static doWithWebDescriptor = { xml -> }
	static doWithSpring = {}
	static doWithDynamicMethods = { ctx -> }
	static doWithApplicationContext = { applicationContext -> }
	static onChange = { event -> }
	static onConfigChange = { event -> }
	static onShutdown = { event -> }
}

