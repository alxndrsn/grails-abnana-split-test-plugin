# Grails Abnana Split Plugin

[![Build Status](https://travis-ci.org/alxndrsn/grails-abnana-split-test-plugin.png)](https://travis-ci.org/alxndrsn/grails-abnana-split-test-plugin)

## TASKS

TODO                    | IN PROGRESS               | DONE
                                                      create test domain class
                                                      create taglib for embedding test
                                                      create taglib for including javascript
                                                      create javascript test functionality
                                                      set up CI on travis as per http://blog.freeside.co/post/44061616547/grails-builds-on-travis-ci
inject ab object/methods into controllers
inject ab object/methods into services
detect split test config files at startup
admin screen to show active and inactive tests (starting with just scaffold)
add results to tests
allow finishing of tests and preferring one result over another (manual)
allow finishing of tests and preferring one result over another (automated)
allow finishing of tests and preferring one result over another (automated with custom configuration)
allow restriction of a test to a particular user or set of users
add support for choosing which option in a functional test
prevent concurrent tests for the same user

# Usage

in GSP:

	<ab:test name="my-test">
		<ab:option name="old style">
		</ab:option>
		<ab:option name="new style">
		</ab:option>
		<!-- Should be able to add as many options as possible -->
	</ab:test>

	<ab:finished name="my-test" goal="convert"/>
	<ab:convert name="my-test"/>
	<ab:fail name="my-test"/>

Configuration:

	class MyTestSplit {
		<!-- name and weight should be optional - name default to alphabetised indexes; weight default to 1 -->
		static options = ['old style':9, 'new-style':1]
		// Alternatives as follows:
		static options = ['old style', 'new style']
		static weightings = [9, 1]
		static weightings = { -> /* could e.g. write an algorithm here to change weightings over time */ }
	}

In Controller:

	def create() {
		o = new DomainObj()
		ab_option('my-test', 'b') {
			o.name = generateDefaultName()
		}
		[domainObjectInstance:o]
	}

	def save() {
		ab_finished 'my-test', 'convert'
		// or shorthand:
		ab_convert 'my_test'
	}

	def cancel() {
		ab_finished 'my-test', 'fail'
		// or shorthand:
		ab_fail 'my-test'
	}

In javascript:

	if(abTest['my_test']['a']) {
		
	} else { ... }

# Backend

## How are users differentiated?

* spring security user
* session info
* IP
* browser fingerprint

## Data model

test-name | user-identifier | option-shown | conversion-point | date-test-started | date-test-ended

# considerations

* should it be possible to run more than one test at once?
* what happens if you try to run a new test with the same name as an old test?
* should tests be re-runnable for different users?
* would it be possible to restrict only to certain users?

## restricting to certain users

File: `grails-app/split-tests/MySplit.groovy`

	class MySplit {
		def identity = 's2'
		def identify(request) {
			grailsApplication.springSecurityService.currentUser.id
			// or
			request.session.id
			// or
			grailsApplication.mainContext.currentTenant.id
		}

		/**
		 * Check if a user <i>may</i> be exposed to a particular test.  The response of
		 * this method has no direct bearing on which option the user will be exposed to.
		 * Should this method be deterministic?  Would it be wrong to randomly decide?
		 * If not defined, this is assumed to be true.
		 * @return <code>true</code> if the user <i>may</i> be exposed to this test.
		 */
		boolean allow(request) {
			true
		}

		/**
		 * Choose which option to give to this user.  Should return a name or index.
		 */
		def choose(request) {
			// default implementation:
			def value = chooseByWeighting()
			if(value instanceof String) {
				return value
			}
			if(value instanceof Integer) {
				def options = getOptions()
				if(options) return options[value]
				else return value;
			}
			throw new RuntimeException("Do not understand option identifier of type: ${value.getClass()} ('$value')")
		}

		int chooseByWeighting() {
			def options = getOptions()
			if(options instanceof Map) {
				return chooseByWeighting(options)
			} else if(weightings instanceof Closure) {
				etc.
		}
	}

# Implementation

	class TagLib {
		static namespace = 'ab'
		def test = { att ->
			checkNotInsideAnotherTest()
			setTestInPageScope(att.name)
		}
	}

# Admin

## Analytics

* weighting analysis should be generated from the saved data, rather than assumed from the properties
* should be able to sort test results in different ways, e.g. progressively over time (hourly/daily/weekly), by day of the week (i.e. how does A perform on Sundays?  Is B better *every* day of the week?)

## Completing a test

When you are happy with the results of a test and want to choose one option over the other, you can always go to the admin index and mark a test as completed.

A test can be re-started later, either with old weightings or new ones.

# Configuration

	grails.plugins.ab {
		allowConcurrentTests = true // allow concurrent tests to be run on the same user
		ident.default = ['tenant', 's2', 'session'] // 'session', 's2', 'project' - identify the user by a combination of his s2 username, and his session ID.  On login, session ID will be converted to s2 ID


# General queries

* what do functional tests do?

