package abnanasplit

import spock.lang.*

@TestFor(AbnanaSplitService)
class AbnanaSplitServiceSpec extends Specification {
	def 'getOption should findOrCreate SplitTestData'() {
		expect:
			false
	}

	def 'checkOption should findOrCreate SplitTestData'() {
		expect:
			false
	}

	def 'markFinished should findOrCreate SplitTestData'() {
		expect:
			false
	}

	def 'getOption should choose an option if one is not set'() {
		expect:
			false
		// TODO use the splitTestOptionChooser service??
	}
}

