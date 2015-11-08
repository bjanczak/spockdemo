package com.github.bjanczak.spockdemo

import spock.lang.Specification

/**
 * Chapter 2
 * Defining fixture methods.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter2_FixtureMethods extends Specification {

    /*
     * Run before every feature method.
     */
    def setup() {
        System.out.println("setup")
    }

    /*
     * Run after every feature method.
     */
    def cleanup() {
        System.out.println("cleanup")
    }

    /*
     * Run before the first feature method.
     */
    def setupSpec() {
        System.out.println("setupSpec")
    }

    /*
     * Run after the last feature method.
     */
    def cleanupSpec() {
        System.out.println("cleanupSpec")
    }

    def "first feature method"() {
        given:
            System.out.println("first feature method")

        expect:
            true == Boolean.TRUE
    }

    def "last feature method"() {
        given:
            System.out.println("last feature method")
        expect:
            true == Boolean.TRUE
    }
}
