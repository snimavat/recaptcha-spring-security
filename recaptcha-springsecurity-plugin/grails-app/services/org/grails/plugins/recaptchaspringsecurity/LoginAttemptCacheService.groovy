package org.grails.plugins.recaptchaspringsecurity

import grails.config.Config
import grails.core.GrailsApplication
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

import java.util.concurrent.TimeUnit

import javax.annotation.PostConstruct

import org.springframework.web.context.request.RequestContextHolder

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache

/**
 * @author Grygoriy Mykhalyunyo
 * @modified by Roberto Perez
 */
@CompileStatic
class LoginAttemptCacheService {

    GrailsApplication grailsApplication

    private LoadingCache<String, Integer> attempts

    private Integer allowedNumberOfAttempts

    @PostConstruct
    @CompileDynamic
    void init() {
        Map config = grailsApplication.config.bruteforcedefender
        allowedNumberOfAttempts = config.containsKey("allowedNumberOfAttempts") ? config.allowedNumberOfAttempts : 3

        Integer time = config.containsKey("time") ? config.time : 5

        attempts = CacheBuilder.newBuilder()
                .expireAfterWrite(time, TimeUnit.MINUTES)
                .build({0} as CacheLoader)
    }

    /**
     * Triggers on each unsuccessful login attempt and increases number of attempts
     */
    void failLogin(String login) {
        int numberOfAttempts = attempts.get(login)
        numberOfAttempts++

        if (numberOfAttempts >= allowedNumberOfAttempts) {
            activateRecaptcha()
            //attempts.invalidate(login)
        } else {
            attempts.put(login, numberOfAttempts)
        }
    }

    /**
     * Triggers on each successful login attempt and resets number of attempts
     */
    void loginSuccess(String login) {
        attempts.invalidate(login)
        deactivateRecaptcha()
    }

    @CompileDynamic
    private activateRecaptcha() {
        RequestContextHolder.currentRequestAttributes().session.recaptchaForLogin = true
    }

    @CompileDynamic
    private deactivateRecaptcha() {
        RequestContextHolder.currentRequestAttributes().session.recaptchaForLogin = false
    }
}
