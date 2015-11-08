package com.github.bjanczak.spockdemo

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Chapter 3
 * Defining feature methods.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter3_FeatureMethods extends Specification {
    /*
     * Feature method consists of four phases.
     * 1) Set up the feature's fixture.
     * 2) Provide stimulus to the system under specification.
     * 3) Describe the response expected from the system.
     * 4) Cleanup the feature's fixture.
     */

    /*
     * Block is what makes method a feature method.
     */
    def "not a feature method"() {
        System.out.println("not a feature method")
    }


    /*
     * There are six kinds of blocks: setup, when, then, expect, cleanup, and where blocks.
     * Blocks can not exist in free order.
     */


    /*
     * 1) setup(given) - is where you do any setup work for the feature that you are describing,
     *                   it may not be preceded by other blocks, and may not be repeated
     *
     *
     * 2) when/then    - when and then always occurs together, they describe stimulus
     *                   and expected response; when blocks may contain arbitrary code bu then
     *                   block is restricted to conditions, interactions and variable definitions
     */
    def "simple condition demo"() {
        setup:
            def maxValue = Integer.MAX_VALUE
            def minvalue = Integer.MIN_VALUE

        when:
            def result = Math.max(minvalue, maxValue);

        then:
            result == Integer.MAX_VALUE
            result > 0
    }

    def "exception condition demo"() {
        setup:
            def stack = new Stack()
            assert stack.empty()

        when:
            stack.pop()

        then:
            EmptyStackException e = thrown()
            e.cause == null
            stack.empty()
    }

    def "no exception condition demo"() {
        setup:
            def map = new HashMap()

        when:
            map.put(null, "value")

        then:
            notThrown(NullPointerException)
    }

    /*
     * 3) expect       - is like then block but limited to conditions and variable definitions;
     *                   use when-then to describe methods with side effects,
     *                   and expect to describe purely functional methods
     */
    def "expect block demo"() {
        expect:
            Math.min(-2, 10) == -2
    }

    /*
     * 4) cleanup      - may only be followed by a where block, and may not be repeated;
     *                   is run even if (a previous part of) the feature method has produced an exception
     */
    def "cleanup block demo"() {
        setup:
            def file = new File("/tmp/somefile")
            file.createNewFile()

        //...

        cleanup:
            file.delete()
    }

    /*
     * 5) where        - always comes last in a method and can not be repeated, it is used to write data driven
     *                   feature methods - it's a parametrization feature. @Unroll
     */
    def "where block demo"() {

        expect:
            Math.min(a, b) == c

        where:
            a << [1, 2, -1]
            b << [10, 12, 9]
            c << [1, 2, -1]
    }
}
