package org.grails.plugins.recaptchaspringsecurity

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent

class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    LoginAttemptCacheService loginAttemptCacheService

    void onApplicationEvent(AuthenticationSuccessEvent e) {
        loginAttemptCacheService.loginSuccess(e.authentication.name)
    }
}