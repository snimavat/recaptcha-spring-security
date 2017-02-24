<g:if test="${session.recaptchaForLogin}">
    <recaptcha:ifEnabled>
        <recaptcha:recaptcha theme="${theme}"/>
    </recaptcha:ifEnabled>
</g:if>