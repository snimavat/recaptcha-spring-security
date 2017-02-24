package org.grails.plugins.recaptchaspringsecurity

import groovy.transform.CompileStatic
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent

@CompileStatic
class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    LoginAttemptCacheService loginAttemptCacheService

    void onApplicationEvent(AbstractAuthenticationFailureEvent e) {
        loginAttemptCacheService.failLogin(e.authentication.name)
    }
}
