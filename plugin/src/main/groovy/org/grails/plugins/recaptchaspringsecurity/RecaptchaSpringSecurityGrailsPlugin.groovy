package org.grails.plugins.recaptchaspringsecurity

import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugins.Plugin

class RecaptchaSpringSecurityGrailsPlugin extends Plugin {

	def version = "3.0.0.1"
	def grailsVersion = "3.0.0 > *"

	def title = "Recaptcha Spring Security Plugin"

	def author = "Sudhir Nimavat, Roberto Pérez Alcolea"
	def authorEmail = "sudhir@nimavat.me"

	def description = 'Prevents brute force attack with Spring security and Recaptcha.'
	def documentation = "http://grails.org/plugin/recaptcha-spring-security"

	def pluginExcludes = ["grails-app/conf/RecaptchaConfig.groovy"]
	def loadAfter = ['springSecurityCore']

	def issueManagement = [system: "Github", url: "https://github.com/snimavat/recaptcha-spring-security/issues"]
	def scm = [url: "https://github.com/snimavat/recaptcha-spring-security"]
	def license = "APACHE"

	@Override
	Closure doWithSpring() { { ->

			authenticationFailureListener(AuthenticationFailureListener) {
				loginAttemptCacheService = ref('loginAttemptCacheService')
			}

			authenticationSuccessEventListener(AuthenticationSuccessListener) {
				loginAttemptCacheService = ref('loginAttemptCacheService')
			}

			captchaCaptureFilter(CaptchaCaptureFilter) {
				failureUrl = SpringSecurityUtils.securityConfig.failureHandler.defaultFailureUrl
				recaptchaService = ref('recaptchaService')
			}

			SpringSecurityUtils.registerFilter 'captchaCaptureFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10
		}
	}
}
