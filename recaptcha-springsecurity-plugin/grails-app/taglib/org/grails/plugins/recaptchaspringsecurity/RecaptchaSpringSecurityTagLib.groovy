package org.grails.plugins.recaptchaspringsecurity

class RecaptchaSpringSecurityTagLib {

    Closure recaptchaLogin = { attrs ->
        def theme = attrs.theme ?: 'light'
        out << render(template: "/recaptchaLogin", plugin: 'recaptcha-spring-security', model: [theme: theme])
    }
}
