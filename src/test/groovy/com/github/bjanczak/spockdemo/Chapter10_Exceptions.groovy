package com.github.bjanczak.spockdemo

import spock.lang.Specification

/**
 * Chapter 10
 * Dealing with exceptions.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter10_Exceptions extends Specification {

    def "should throw an exception"() {
        when:
            Integer.valueOf("exception")

        then:
            thrown(NumberFormatException)
    }

    def "should throw an exception with proper message"() {
        when:
            throw new RuntimeException("message")

        then:
            def exception = thrown(RuntimeException)
            exception.message == "message"
    }

    def "should throw no exception"() {
        when:
            Integer.MAX_VALUE

        then:
            noExceptionThrown()
    }

    def "should not throw given exception"() {
        when:
            Integer.MAX_VALUE

        then:
            notThrown(FileNotFoundException)
            // Only one exception condition is allowed per 'then' block.
            //thrown(RuntimeException)
        // A chain of 'then' blocks can have only a single exception condition.
        //then:
        //    thrown(RuntimeException)

    }
}
