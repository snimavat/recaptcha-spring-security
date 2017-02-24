package org.grails.plugins.recaptchaspringsecurity

import groovy.transform.CompileStatic
import org.springframework.security.core.AuthenticationException

/**
 * Created by sudhir on 7/21/2015.
 */
@CompileStatic
class CaptchaVerificationFailedException extends AuthenticationException {

    CaptchaVerificationFailedException(String msg) {
        super(msg)
    }
}
