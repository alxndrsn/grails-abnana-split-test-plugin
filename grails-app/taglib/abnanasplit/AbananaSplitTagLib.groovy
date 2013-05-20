package abnanasplit

class AbnanaSplitTagLib {
	static namespace = 'ab'

	def test = { att, body ->
		// TODO set page variable for the test name
		out << body()
		// TODO unset page variable for the test name
	}

	def option = { att, body ->
		// TODO check we are inside a test or that att.test is set
		// TODO check that the current test has the specified option
		out << body()
	}

	def finished = { att ->
		// TODO check we are inside a test or that att.test is set
		// TODO check that goal is set
		// TODO mark the test as finished (if ifOption not set or fulfilled)
	}

	def convert = { att ->
		ab.finished(att + [goal:'convert'])
	}

	def fail = { att ->
		ab.finished(att + [goal:'fail'])
	}
}

