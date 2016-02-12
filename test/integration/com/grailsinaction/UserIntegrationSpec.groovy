package com.grailsinaction

import spock.lang.*

/**
 *
 */
class UserIntegrationSpec extends Specification {

    /*
     * Exercise 3.2.1
     */
    def "Saving our first user to the database"() {

        given: "A brand new user"
        def joe = new User(loginId: 'joe', password: 'secret',
                        homepage: 'http://www.grailsinaction.com')

        when: "The user is saved"
        joe.save()

        then: "It saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }

    /*
     * Exercise 3.2.2
     */
    def "Updating a saved user changes its properties"() {

        given: "An existing user"
        def existingUser = new User(loginId: 'joe', password: 'secret',
                        homepage: 'http://www.grailsinaction.com')
        existingUser.save(failOnError: true)

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.password = 'sesame'
        foundUser.save(failOnError: true)

        then: "The change is reflected in the database"
        User.get(existingUser.id).password == 'sesame'
    }

    /*
     * Exercise 3.2.3
     */
    def "Deleting an existing user removes it from the database"() {

        given: "An existing user"
        def user = new User(loginId: 'joe', password: 'secret',
                        homepage: 'http://www.grailsinaction.com')
        user.save(failOnError: true)

        when: "The user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush: true)

        then: "The user is removed from the database"
        !User.exists(foundUser.id)
    }
}
