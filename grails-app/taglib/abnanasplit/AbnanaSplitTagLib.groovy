package abnanasplit

class AbnanaSplitTagLib {
	static namespace = 'ab'

	def test = { att, body ->
		// TODO check that test name has been set
		// TODO set page variable for the test name
		out << body()
		// TODO unset page variable for the test name
	}

	def option = { att, body ->
		// TODO check we are inside a test or that att.test is set
		// TODO check that the current test has the specified option
		// TODO check if requested option is selected for this user
		out << body()
	}

	def finished = { att ->
		// TODO check we are inside a test or that att.test is set
		// TODO check that att.goal is set orr that we are in an option tag
		// TODO mark the test as finished (if ifOption not set or fulfilled)
	}

	def convert = { att ->
		ab.finished(att + [goal:'convert'])
	}

	def fail = { att ->
		ab.finished(att + [goal:'fail'])
	}
}

