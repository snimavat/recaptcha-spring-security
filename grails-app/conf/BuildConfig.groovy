grails.project.work.dir = 'target'
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        compile "com.google.guava:guava:14.0-rc1"
        compile 'net.sf.ehcache:ehcache-core:2.6.9' //spring security needs it, cane be removed when updated to spring-security-core:2.0-RC5
    }

    plugins {
        compile ":recaptcha:1.2.0"
        compile ":spring-security-core:2.0-RC4"
        build (":release:3.1.1", ":rest-client-builder:2.1.1") { export = false }
    }
}
