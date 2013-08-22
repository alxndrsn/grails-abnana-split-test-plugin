package abnanasplit

class AbnanaSplitAdminController {
	def index() {
		[splitTests:grailsApplication.splitTestClasses*.clazz]
	}
}

