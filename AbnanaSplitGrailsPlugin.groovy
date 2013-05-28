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

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
