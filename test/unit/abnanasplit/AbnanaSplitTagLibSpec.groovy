package abnanasplit

import grails.test.mixin.*
import spock.lang.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

@TestFor(AbnanaSplitTagLib)
class AbnanaSplitTagLibSpec extends Specification {
	AbnanaSplitService service

	def setup() {
		service = Mock()
		tagLib.abnanaSplitService = service
	}

//> TAG TESTS
	@Unroll
	def 'test should fail if no test name is been set'() {
		when:
			applyTemplate html
		then:
			thrown GrailsTagException
		where:
			html << ['<ab:test/>', '<ab:test name=""/>']
	}

	def 'should not be able to nest tests'() {
		when:
			applyTemplate '<ab:test name="one"><ab:test name="two"/></ab:test>'
		then:
			thrown GrailsTagException
	}

	def 'test should set page variable inside the tag'() {
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
			output == 'ok'
			1 * service.checkOption('my-test', 'option-a') >> true
		where:
			html << ['<ab:test name="my-test"><ab:option name="option-a">ok</ab:option></ab:test>',
				'<ab:option test="my-test" name="option-a">ok</ab:option>']
	}

	def 'option should throw an exception if test is set in both page and att_test'() {
		when:
			applyTemplate '<ab:test name="my-test"><ab:option test="my-test" name="option-a"/></ab:test>'
		then:
			thrown GrailsTagException
	}

	def 'option should set page variable inside the tag'() {
		given:
			service.checkOption('my-test', 'option-a') >> true
		expect:
			applyTemplate('<ab:option name="option-a" test="my-test">${abnanaSplitTest?.option}</ab:option>') == 'option-a'
	}

	def 'option should unset page variable at end of execution'() {
		given:
			service.checkOption('my-test', 'option-a') >> true
		expect:
			applyTemplate('<ab:option name="option-a" test="my-test"/>${abnanaSplitTest?.option}') == ''
	}

	def 'option should check that the requested option is valid'() {
		when:
			applyTemplate '<ab:option test="my-test" name="option-a"/>'
		then:
			1 * service.checkOption('my-test', 'option-a')
	}

	@Unroll
	def 'option should not render if selected option is not set option'() {
		setup:
			service.checkOption('my-test', 'option-b') >> false
		expect:
			applyTemplate(html) == ''
		where:
			html << ['<ab:option test="my-test" name="option-b">bad</ab:option>',
				'<ab:test name="my-test"><ab:option name="option-b">bad</ab:option></ab:test>']
	}

	def 'finished should check test name is set'() {
		when:
			applyTemplate '<ab:finished goal="all-done"/>'
		then:
			thrown GrailsTagException
	}

	def 'finished should check that goal is supplied'() {
		when:
			applyTemplate '<ab:finished test="my-test"/>'
		then:
			thrown GrailsTagException
	}

	@Unroll
	def 'finished should mark test complete if no ifOption specified'() {
		when:
			applyTemplate html
		then:
			1 * service.markFinished('my-test', 'all-done')
		where:
			html << ['<ab:test name="my-test"><ab:finished goal="all-done"/></ab:test>',
				'<ab:finished test="my-test" goal="all-done"/>']
	}

	@Unroll
	def 'finished should throw expcetion if ifOption is set AND we are inside an option tag'() {
		given:
			service.checkOption('my-test', 'option-a') >> true
		when:
			applyTemplate html
		then:
			thrown GrailsTagException
		where:
			html << ['<ab:option test="my-test" name="option-a"><ab:finished goal="all-done" ifOption="option-a"/></ab:option>',
				'<ab:test name="my-test"><ab:option name="option-a"><ab:finished goal="all-done" ifOption="option-a"/></ab:option></ab:test>']
	}

	@Unroll
	def 'finished should mark test complete if ifOption is fulfilled'() {
		given:
			service.checkOption('my-test', 'option-a') >> true
		when:
			applyTemplate html
		then:
			1 * service.markFinished('my-test', 'all-done')
		where:
			html << ['<ab:test name="my-test"><ab:finished goal="all-done" ifOption="option-a"/></ab:test>',
				'<ab:finished test="my-test" goal="all-done" ifOption="option-a"/>']
	}

	@Unroll
	def 'finished should not mark test complete if ifOption is not fulfilled'() {
		given:
			service.checkOption('my-test', 'option-b') >> false
		when:
			applyTemplate html
		then:
			0 * service.markFinished('my-test', 'all-done')
		where:
			html << ['<ab:test name="my-test"><ab:finished goal="all-done" ifOption="option-b"/></ab:test>',
				'<ab:finished test="my-test" goal="all-done" ifOption="option-b"/>']
	}

	def 'calling javascript tag without a test should throw an exception'() {
		when:
			applyTemplate("<ab:javascript/>")
		then:
			thrown GrailsTagException
		}

	def 'rawjavascript tag should include details of requested test'() {
		given:
			service.getOption('my-test') >> 'option-a'
		expect:
			applyTemplate('<ab:rawjavascript test="my-test"/>') == 'var abTest=abTest||{};abTest["my-test"]={"option-a":true};'
	}

	def 'javascript tag should include details of requested test'() {
		given:
			service.getOption('my-test') >> 'option-a'
		expect:
			applyTemplate('<ab:javascript test="my-test"/>') == '<script type="text/javascript>var abTest=abTest||{};abTest["my-test"]={"option-a":true};</script>'
	}

	def 'rawjavascript tag should include details of multiple requested tests'() {
		given:
			service.getOption('good-test-1') >> 'option-1'
			service.getOption('good-test-2') >> 'option-2'
			service.getOption('good-test-3') >> 'option-3'
		expect:
			applyTemplate('<ab:rawjavascript test="good-test-1, good-test-2,good-test-3 "/>') == 'var abTest=abTest||{};abTest["good-test-1"]={"option-1":true};abTest["good-test-2"]={"option-2":true};abTest["good-test-3"]={"option-3":true};'
	}

	def 'javascript tag should include details of multiple requested tests'() {
		given:
			service.getOption('good-test-1') >> 'option-1'
			service.getOption('good-test-2') >> 'option-2'
			service.getOption('good-test-3') >> 'option-3'
		expect:
			applyTemplate('<ab:javascript test="good-test-1, good-test-2,good-test-3 "/>') == '<script type="text/javascript>var abTest=abTest||{};abTest["good-test-1"]={"option-1":true};abTest["good-test-2"]={"option-2":true};abTest["good-test-3"]={"option-3":true};</script>'
	}

//> METHOD TESTS
	def 'option() should execute code if option is set'() {
		given:
			def closureExecuted = false
		when:
			tagLib.option('my-test', 'option-a') {
				closureExecuted = true
			}
		then:
			1 * service.checkOption('my-test', 'option-a') >> true
			closureExecuted
	}

	def 'option() should not execute code if option is not set'() {
		given:
			def closureExecuted = false
		when:
			tagLib.option('my-test', 'option-b') {
				closureExecuted = true
			}
		then:
			1 * service.checkOption('my-test', 'option-b') >> false
			!closureExecuted
	}

	def 'option() should throw exception if test does not exist'() {
		when:
			tagLib.option('not-my-test', 'option-x') {}
		then:
			1 * service.checkOption('not-my-test', 'option-x') >> { throw new AbnanaSplitException() }
			thrown AbnanaSplitException
	}

	private static TODO(message='not yet implemented') {
		assert false == message
	}
}

