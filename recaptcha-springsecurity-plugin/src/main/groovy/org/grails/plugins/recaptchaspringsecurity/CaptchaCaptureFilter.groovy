package org.grails.plugins.recaptchaspringsecurity

import com.megatome.grails.RecaptchaService
import grails.plugin.springsecurity.web.authentication.FilterProcessUrlRequestMatcher
import org.apache.log4j.Logger
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Filter for capturing Captcha fields and validate them using recaptchaService.
 */
public class CaptchaCaptureFilter extends OncePerRequestFilter {

    private final Logger log = Logger.getLogger(getClass());

    private String failureUrl;
    private RecaptchaService recaptchaService
    FilterProcessUrlRequestMatcher requiresAuthenticationRequestMatcher

    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((GrailsWebRequest) RequestContextHolder.currentRequestAttributes()).getSession();

        try {

            if(req.isPost() && requiresAuthenticationRequestMatcher.matches(req) && session.getAttribute("recaptchaForLogin")) {
                // Assign values only when user has submitted a Captcha value.
                String recaptcha_response = req.getParameter("g-recaptcha-response");
                String remoteAddr = req.getRemoteAddr();

                Map params = [:]
                params["g-recaptcha-response"] = recaptcha_response ?: ''
                boolean verified = (Boolean) recaptchaService.verifyAnswer(session, remoteAddr, params);

                if(!verified) {
                    // Redirect user to login page
                    failureHandler.setDefaultFailureUrl(failureUrl);
                    failureHandler.onAuthenticationFailure(req, res, new CaptchaVerificationFailedException("Captcha invalid"));
                    return;
                }
                recaptchaService.cleanUp(session);

            }

            chain.doFilter(req, res);

        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    protected String getFailureUrl() {
        return failureUrl;
    }

    void setRecaptchaService(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }
}
