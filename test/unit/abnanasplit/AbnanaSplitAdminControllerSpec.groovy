package abnanasplit

import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import spock.lang.*

@TestFor(AbnanaSplitAdminController)
class AbnanaSplitAdminControllerSpec extends Specification {
	def 'index should provide a list of tests'() {
		given:
			DefaultGrailsApplication.metaClass.getSplitTestClasses = { -> [[clazz:'my-pretend-test']] }
		when:
			def model = controller.index()
		then:
			model.splitTests == ['my-pretend-test']
	}
}

