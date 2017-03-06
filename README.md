recaptcha-spring-security
=========================

Grails: Using recaptcha with spring security core for brute force defender

based in: [Brute Force Defender](https://github.com/grygoriy/bruteforcedefender)

Requirements
========================

1. Grails 2.3.2+
2. [Spring Security Core Plugin](http://grails.org/plugin/spring-security-core) (2.0-RC3+)
3. [ReCaptcha Plugin](http://grails.org/plugin/recaptcha) (0.6.9+)

Installation
========================

1. clone the repository: git clone https://github.com/rpalcolea/recaptcha-spring-security.git

2. Enter to the repository via command line.

3. package the plugin: grails package-plugin

Configuration
========================

## Config.groovy

Configure [recaptcha plugin](http://plugins.grails.org/plugin/iamthechad/recaptcha#configuration)

And then Add the following lines to your Grails Config.groovy file:

	bruteforcedefender {
    	time = 5
    	allowedNumberOfAttempts = 3
	}

time = minutes mantaining failed attempts allowed
NumberOfAttempts = number of failed attempts before showing the recaptcha widget.

Adding the recaptcha to your auth view
======================== 

Open your auth.gsp (/grails-app/views/login/auth.gsp) and add the next line wherever you want to render the recaptcha after the attempts.

          <g:recaptchaLogin/>


Note: The plugin depends on spring security authentication events and so it sets grails.plugin.springsecurity.useSecurityEventListener = true

Extras
========================

Thanks to [Vinco Orbis](http://www.vincoorbis.com) and [Burt Beckwith](https://github.com/burtbeckwith)

Thanks to JetBrains for Awesome IntelliJ Idea

<img src="https://cdn.rawgit.com/snimavat/repo-bin/master/assets/jetbrains/icon_IntelliJIDEA.svg" width="48">

