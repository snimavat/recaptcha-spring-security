import org.codehaus.groovy.grails.plugins.recaptchaspringsecurity.AuthenticationFailureListener
import org.codehaus.groovy.grails.plugins.recaptchaspringsecurity.AuthenticationSuccessListener
import org.codehaus.groovy.grails.plugins.recaptchaspringsecurity.CaptchaCaptureFilter
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class RecaptchaSpringSecurityGrailsPlugin {

    def version = "1.0"
    def grailsVersion = "2.3.2 > *"
    def pluginExcludes = ["grails-app/conf/RecaptchaConfig.groovy"]
    def loadAfter = ['springSecurityCore']

    def title = "Recaptcha Spring Security Plugin"

    def author = "Sudhir Nimavat, Roberto Pérez Alcolea"
    def authorEmail = "sudhir@nimavat.me"

    def description = 'Prevents brute force attack with Spring security and Recaptcha.'
    def documentation = "http://grails.org/plugin/recaptcha-spring-security"

    def issueManagement = [system: "Github", url: "https://github.com/snimavat/recaptcha-spring-security/issues"]
    def scm = [url: "https://github.com/snimavat/recaptcha-spring-security"]
    def license = "APACHE"

    def doWithSpring = {

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
