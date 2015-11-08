package com.github.bjanczak.spockdemo

import com.github.bjanczak.spockdemo.data.transfer.Transfer
import com.github.bjanczak.spockdemo.data.transfer.TransferService
import spock.lang.Specification

/**
 * Chapter 8
 * Working with Mocks.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter8_Mocks extends Specification {

    def "creating example mock"() {
        given:
            Map map1 = Mock(Map)
            def map2 = Mock(Map)
            Map map3 = Mock() // preferred way
    }

    def "check interaction with mock"() {
        given:
            List list

        when:
            list = Mock()

        then:
            0 * list.size()
            //1 * list.size()
    }

    def "matching invocations with mock"() {
        given:
            List<Integer> list = Mock()
            Set<Transfer> set = Mock()
            Map map = Mock()
            Queue<Integer> queue = Mock()

        when:
            list.add(Integer.valueOf(1))
            set.add(new Transfer(value: BigDecimal.ONE))
            map.size()
            queue.add(Integer.valueOf(1))

        then:
            /*
             * Verification similar to stubbing wildcards.
             */

            // Method taking no parameter.
            1 * map.size()

            // Explicit parameter value.
            1 * list.add(Integer.valueOf(1))

            // Wildcards as parameters.
            1 * queue.add(_ as Number)

            // Detailed check.
            1 * set.add({Transfer transfer -> transfer.value == BigDecimal.ONE})
    }

    def "funny verification semantic"() {
        given:
            List<Integer> list = Mock()

        when:
            list.add(Integer.valueOf(1))
            list.add(Integer.valueOf(1))

        then:
            /*
             * Each verification 'burns' call numbers.
             */
            2 * list.add(Integer.valueOf(1))
            //1 * list.add(_)
            //1 * list.add(!null)
            ////1 * list.add(!null)
    }

    def "matching invocations cardinality with mock"() {
        given:
            List<Integer> list = Mock()
            Set<Transfer> set = Mock()
            Map map = Mock()
            Queue<Integer> queue = Mock()

        when:
            set.size()
            set.size()
            map.size()

        then:
            // Should not be invoked.
            0 * list.size()

            // Should be invoked at least one time.
            (1.._) * set.size()

            // Should be invoked at most one time.
            (_..1) * map.size()

            // Any number of calls.
            _* queue.size()
    }

    def "checking the order of execution"() {
        given:
            TransferService transferService = Mock()

        when:
            transferService.save(new Transfer(value: BigDecimal.ONE))
            transferService.commit()

        then:
            1 * transferService.save(_ as Transfer)
        then:
            transferService.commit()
    }
}
