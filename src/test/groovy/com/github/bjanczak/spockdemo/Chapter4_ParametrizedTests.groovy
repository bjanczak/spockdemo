package com.github.bjanczak.spockdemo

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Chapter4
 * Parametrized tests.
 *
 * @author Bartłomiej Jańczak
 */
class Chapter4_ParametrizedTests extends Specification {

    def "parametrized test demo"() {
        expect:
            Math.min(a, b) == c

        where:
            a << [1, 2, -1]
            b << [10, 12, 9]
            c << [1, 2, -1]
    }

    def "another parametrized test demo"() {
        expect:
           Math.min(a, b) == c

        where:
             a || b  || c
             1 || 10 || 1
             2 || 12 || 2
            -1 || 9  || -1
    }

    @Unroll
    def "unrolled parametrized test demo"() {
        expect:
            Math.min(a, b) == c

        where:
            a || b  || c
            1 || 10 || 1
            2 || 12 || 2
           -1 || 9  || -1
    }

    @Unroll
    def "unrolled parametrized test demo (a=#a, b=#b)"() {
        expect:
            Math.min(a, b) == c

        where:
            a || b  || c
            1 || 10 || 1
            2 || 12 || 2
           -1 || 9  || -1
    }
}
