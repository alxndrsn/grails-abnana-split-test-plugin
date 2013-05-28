package abnanasplit

import grails.plugin.spock.IntegrationSpec
import abnanasplit.test.TestController

class ControllerInjectionSpec extends IntegrationSpec {
	def controller

	def setup() {
		controller = new TestController()
	}

	def 'taglib methods should be available'() {
		expect:
			controller.ab
	}
}

