package abnanasplit

import spock.lang.*

@TestFor(SplitTestData)
class SplitTestDataSpec extends Specification {
	def d

	def setup() {
		d = new SplitTestData()
	}

	def 'data should know the name of their test'() {
		expect:
			!d.testName
	}

	def 'data should know user ID'() {
		expect:
			!d.userId
	}

	def 'data should know option to show'() {
		expect:
			!d.option
	}

	def 'data should know  date started'() {
		expect:
			!d.dateCreated
	}

	def 'data should know date completed'() {
		expect:
			!d.dateCompleted
	}
}

