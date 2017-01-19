package org.grails.plugins.recaptchaspringsecurity

import org.springframework.security.core.AuthenticationException

/**
 * Created by sudhir on 7/21/2015.
 */
class CaptchaVerificationFailedException extends AuthenticationException {

    CaptchaVerificationFailedException(String msg) {
        super(msg)
    }
}
