package me.nimavat.recaptcha.demo

class BootStrap {

    def init = { servletContext ->
        Role adminRole = new Role(authority: 'ROLE_ADMIN').save()
        Role userRole = new Role(authority: 'ROLE_USER').save()

        User testUser = new User(username: 'me', password: 'password').save()

        UserRole.create testUser, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 1
        assert Role.count() == 2
        assert UserRole.count() == 1
    }

    def destroy = {
    }
}
