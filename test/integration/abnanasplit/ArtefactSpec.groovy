package abnanasplit

import grails.plugin.spock.IntegrationSpec

import org.codehaus.groovy.grails.commons.*

class ArtefactSpec extends IntegrationSpec {
	def grailsApplication

	def 'artefact handler should be listed by plugin descriptor'() {
		expect:
			AbnanaSplitGrailsPluginImpl.artefacts == [SplitTestArtefactHandler]
	}

	def 'split tests should be watchedResources'() {
		expect:
			AbnanaSplitGrailsPluginImpl.watchedResources.containsAll([
				'file:./grails-app/split-tests/**/*SplitTest.groovy',
				'file:../../plugins/*/split-tests/**/*SplitTest.groovy'
			] as Object[])
	}

	def 'onChange listener should be available for split tests'() {
		expect:
			AbnanaSplitGrailsPluginImpl.onChange
	}

	def 'artefact handler adapter should extend ArtefactHandlerAdapter'() {
		expect:
			ArtefactHandlerAdapter.isAssignableFrom(SplitTestArtefactHandler)
	}

	def 'split test class should extend GrailsClass'() {
		expect:
			GrailsClass.isAssignableFrom(SplitTestClass)
	}

	def 'default split test class should implement SplitTestClass'() {
		expect:
			SplitTestClass.isAssignableFrom(DefaultSplitTestClass)
	}

	def 'default split test class should extend AbstractGrailsClass'() {
		expect:
			AbstractGrailsClass.isAssignableFrom(DefaultSplitTestClass)
	}

	def 'grailsApplication should have a list of split test classes'() {
		expect:
			grailsApplication.splitTestClasses*.clazz == [abnanasplit.test.MySplitTest]
	}
}

