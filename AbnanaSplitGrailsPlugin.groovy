import abnanasplit.AbnanaSplitGrailsPluginImpl

class AbnanaSplitGrailsPlugin {
	def version = '0.1-SNAPSHOT'
	def grailsVersion = '2.2 > *'
	def pluginExcludes = [
		'grails-app/views/error.gsp',
		'grails-app/conf/DataSource.groovy',
		'grails-app/controllers/test/'
	]
	def title = 'Abnana Split Plugin'
	def author = 'Alex Anderson'
	def description = 'A/B Split Testing for Grails'
	def documentation = 'https://github.com/alxndrsn/grails-abnana-split-test-plugin'
	def license = 'APACHE'
	def issueManagement = [system:'github', url:'https://github.com/alxndrsn/grails-abnana-split-test-plugin/issues']
	def scm = [url:'git@github.com:alxndrsn/grails-abnana-split-test-plugin.git']

	def artefacts = AbnanaSplitGrailsPluginImpl.artefacts
	def watchedResources = AbnanaSplitGrailsPluginImpl.watchedResources
	def doWithWebDescriptor = AbnanaSplitGrailsPluginImpl.doWithWebDescriptor
	def doWithSpring = AbnanaSplitGrailsPluginImpl.doWithSpring
	def doWithDynamicMethods = AbnanaSplitGrailsPluginImpl.doWithDynamicMethods
	def doWithApplicationContext = AbnanaSplitGrailsPluginImpl.doWithApplicationContext
	def onChange = AbnanaSplitGrailsPluginImpl.onChange
	def onConfigChange = AbnanaSplitGrailsPluginImpl.onConfigChange
	def onShutdown = AbnanaSplitGrailsPluginImpl.onShutdown
}

