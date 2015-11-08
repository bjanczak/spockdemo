package com.github.bjanczak.spockdemo

import spock.lang.Shared;
import spock.lang.Specification;

/**
 * Chapter 1
 * Working with fields in Spock.
 *
 * Class Specification instructs JUnit to run specification Sputnik, Spock's JUnit runner.
 *
 * @author Bartłomiej Jańczak
 */
public class Chapter1_Fields extends Specification {

    /*
     * Static fields, should only be used for constants.
     */
    static final PI = 3.141592654

    /*
     * @Shared shares object between feature methods - very expensive objects.
     * Please note that Spock does not guarantee that tests specified in a class
     * will be executed in the order they appear in the file.
     */
    @Shared
    def zeroValue = 0

    /* Objects stored into instance fields are NOT shared between feature methods.
     * Instead, every feature method gets its own object.
     */
    def maxValue = Integer.MAX_VALUE
    def minValue = Integer.MIN_VALUE


    def "should change instance fields"() {
        given:
            maxValue = 0
            minValue = 0
            zeroValue = Integer.MAX_VALUE
        expect:
            maxValue == 0
            minValue == 0
            zeroValue == Integer.MAX_VALUE
    }

    def "should not change instance fields"() {
        expect:
        maxValue == Integer.MAX_VALUE
        minValue == Integer.MIN_VALUE
    }

    def "should change shared instance fields"() {
        expect:
        zeroValue == Integer.MAX_VALUE
    }
}
