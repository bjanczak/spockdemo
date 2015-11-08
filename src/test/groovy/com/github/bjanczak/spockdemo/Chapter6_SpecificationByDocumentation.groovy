package com.github.bjanczak.spockdemo

import spock.lang.Specification

/**
 * Chapter 6
 *
 * Specification by documentation.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter6_SpecificationByDocumentation extends Specification {

    def "sample feature method"() {
        setup: "setup sth 1"
            def value1 = 0
        and: "setup sth 2"
            def value2 = 0
        and: "setup sth 3"
            def value3 = 0

        when: "the max value is calculated"
            value3 = Math.max(value1, value2)

        then: "max value is 0"
            value3 == 0
    }
}
