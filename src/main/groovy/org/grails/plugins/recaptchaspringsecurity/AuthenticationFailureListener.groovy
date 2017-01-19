package org.grails.plugins.recaptchaspringsecurity

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent

class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    LoginAttemptCacheService loginAttemptCacheService

    void onApplicationEvent(AbstractAuthenticationFailureEvent e) {
        loginAttemptCacheService.failLogin(e.authentication.name)
    }
}
