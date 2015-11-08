package com.github.bjanczak.spockdemo

import spock.lang.Specification

/**
 * Chapter 5
 * Helper methods.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter5_HelperMethods extends Specification {

    /*
     * Helper methods, help apply DRY principle.
     */

    def "simple feature method with expended then block"() {
        when:
            def someValue = 10
        then:
            someValue > Integer.MIN_VALUE
            someValue < Integer.MAX_VALUE
            someValue == 10
    }

    def "simple feature method with expended then block using helper method"() {
        when:
            def someValue = 10
        then:
            helperThenBlock(someValue)
    }

    def helperThenBlock(someValue) {
         someValue > Integer.MIN_VALUE && someValue < Integer.MAX_VALUE && someValue == 10
    }

    def "simple feature method with expended then block using improved helper method"() {
        when:
            def someValue = 10
        then:
            improvedHelperThenBlock(someValue)
    }

    void improvedHelperThenBlock(someValue) {
        assert someValue > Integer.MIN_VALUE
        assert someValue < Integer.MAX_VALUE
        assert someValue == 10
    }
}
