package abnanasplit

class AbnanaSplitTagLib {
	static namespace = 'ab'

	def abnanaSplitService

	def test = { att, body ->
		if(!att.name) throw new AbSplitTestNotSpecifiedException('<ab:test> missing attribute: "name"')
		if(pageScope.abnanaSplitTest) throw new AbnanaSplitException("Cannot nest tests - $att.name inside $pageScope.abnanaSplitTest.name")
		pageScope.abnanaSplitTest = [name:att.name]
		out << body()
		pageScope.abnanaSplitTest = null
	}

	def option = { att, body ->
		def name = att.name
		if(!name) throw new AbnanaSplitException("No name specified for option in test $testName")
		withTest(att) { testName ->
			if(abnanaSplitService.checkOption(testName, name)) {
				pageScope.abnanaSplitTest.option = name
				out << body()
				pageScope.abnanaSplitTest.option = null
			}
		}
	}

	def finished = { att ->
		if(!att.goal) throw new AbnanaSplitException('No goal supplied for "finished" step')
		withTest(att) { testName ->
			if(att.ifOption && pageScope.abnanaSplitTest.option) {
				throw new AbnanaSplitException("finished.option specified twice: $pageScope.abnanaSplitTest.option, $att.ifOption")
			}
			if(!att.ifOption || abnanaSplitService.checkOption(testName, att.ifOption)) {
				abnanaSplitService.markFinished(testName, att.goal)
			}
		}
	}

	def convert = { att ->
		ab.finished(att + [goal:'convert'])
	}

	def fail = { att ->
		ab.finished(att + [goal:'fail'])
	}

	def rawjavascript = { att ->
		def tests = att.test.split(',')*.trim()
		if(!tests) throw new AbSplitTestNotSpecifiedException("No tests specified in <ab:*script> tag.")
		out << 'var abTest=abTest||{};'
		tests.each { test ->
			def option = abnanaSplitService.getOption(test)
			out << 'abTest["' + test + '"]={"' + option + '":true};'
		}
	}

	def javascript = { att ->
		out << '<script type="text/javascript>'
		out << rawjavascript(att)
		out << '</script>'
	}

	private void withTest(att, Closure c) {
		def testName = pageScope.abnanaSplitTest?.name
		if(testName && att.test) throw new AbnanaSplitException("test.name speicified twice: $name, $att.test")
		def temporaryName = false
		if(!testName) {
			temporaryName = true
			testName = att.test
			pageScope.abnanaSplitTest = [name:testName]
		}
		if(!testName) throw new AbSplitTestNotSpecifiedException()
		c.call(testName)
		if(temporaryName) {
			pageScope.abnanaSplitTest = null
		}
	}
}

