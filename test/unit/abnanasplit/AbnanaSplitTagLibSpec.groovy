package abnanasplit

import spock.lang.*

class AbnanaSplitTagLibSpec extends Specification {
	private static TODO(message='not yet implemented') { 
		assert false == message
	}

	def 'test should fail if no page variable has been set'() { expect: TODO() }
	def 'test should set page variable inside the test'() { expect: TODO() }
	def 'test should unset page variable at the end of execution'() { expect: TODO() }
	def 'option should check that the test variable is set in page or att'() { expect: TODO() }
	def 'option should throw an exception if test is set in both page and att_test'() { expect: TODO() }
	def 'option should set page variable inside the tag'() { expect: TODO() }
	def 'option should unset page variable at end of execution'() { expect: TODO() }
	def 'option should check that the requested option is valid'() { expect: TODO() }
	def 'option should only render if selected option is set option'() { expect: TODO() }
	def 'finished should check test name is set'() { expect: TODO() }
	def 'finished should check that goal is supplied'() { expect: TODO() }
	def 'finished should mark test complete if no ifOption specified'() { expect: TODO() }
	def 'finished should throw expcetion if ifOption is set AND we are inside an option tag'() { expect: TODO() }
	def 'finished should mark test complete if ifOption is fulfilled'() { expect: TODO() }
	def 'finished should not mark test complete if ifOption is not fulfilled'() { expect: TODO() }
}

