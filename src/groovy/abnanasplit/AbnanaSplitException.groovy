package abnanasplit

class AbnanaSplitException extends RuntimeException {
	AbnanaSplitException(GString message) {
		super(message.toString())
	}
}

