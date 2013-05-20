package abnanasplit

import grails.test.mixin.*
import spock.lang.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

@TestFor(AbnanaSplitTagLib)
class AbnanaSplitTagLibSpec extends Specification {
	private static TODO(message='not yet implemented') { 
		assert false == message
	}

	def 'test should fail if no page variable has been set'() {
		when:
			applyTemplate '<ab:test/>'
		then:
			thrown GrailsTagException
	}

	def 'test should set page variable inside the test'() {
		expect:
			applyTemplate('<ab:test name="my-test">${abnanaSplitTest?.name}</ab:test>') == 'my-test'
	}

	def 'test should unset page variable at the end of execution'() {
		expect:
			applyTemplate('<ab:test name="my-test"></ab:test>${abnanaSplitTest==null}') == 'true'
	}

	def 'option should fail if no name is set'() {
		when:
			applyTemplate '<ab:test name="my-test"><ab:option/></ab:test>'
		then:
			thrown GrailsTagException
	}

	def 'option should fail if test is not set in page or att'() {
		when:
			applyTemplate '<ab:test name="my-test"><ab:option/></ab:test>'
		then:
			thrown GrailsTagException
	}

	@Unroll
	def 'option should work if test variable is set in page or att'() {
		when:
			def output = applyTemplate html
		then:
			output == ''
		where:
			html << ['<ab:test name="my-test"><ab:option name="option-a"/></ab:test>',
				'<ab:option test="my-test" name="option-a"/>']
	}

	def 'option should throw an exception if test is set in both page and att_test'() {
		when:
			applyTemplate '<ab:test name="my-test"><ab:option test="my-test" name="option-a"/></ab:test>'
		then:
			thrown GrailsTagException
	}

	def 'option should set page variable inside the tag'() {
		expect:
			applyTemplate('<ab:option name="option-a" test="my-test">${abnanaSplitTest?.option}</ab:test>') == 'option-a'
	}

	def 'option should unset page variable at end of execution'() {
		expect:
			applyTemplate('<ab:option name="option-a" test="my-test"/>${abnanaSplitTest?.option}') == 'option-a'
	}

	def 'option should check that the requested option is valid'() { expect: TODO() }
	def 'option should only render if selected option is set option'() { expect: TODO() }

	def 'finished should check test name is set'() { expect: TODO() }
	def 'finished should check that goal is supplied'() { expect: TODO() }
	def 'finished should mark test complete if no ifOption specified'() { expect: TODO() }
	def 'finished should throw expcetion if ifOption is set AND we are inside an option tag'() { expect: TODO() }
	def 'finished should mark test complete if ifOption is fulfilled'() { expect: TODO() }
	def 'finished should not mark test complete if ifOption is not fulfilled'() { expect: TODO() }
}

