package com.github.bjanczak.spockdemo

import com.github.bjanczak.spockdemo.data.transfer.Transfer
import com.github.bjanczak.spockdemo.data.transfer.TransferService
import spock.lang.Specification

/**
 * Chapter 7
 * Working with Stubs.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter7_Stubs extends Specification {

    def "creating example stub"() {
        given:
            Map map1 = Stub(Map)
            def map2 = Stub(Map)
            Map map3 = Stub() // preferred way
    }

    def "defining stub behaviour"() {
        given:
            Map map = Stub()
            map.size() >> 10

        expect:
            map.size() == 10
    }

    def "defining stub side effects"() {
        given:
            Map map = Stub()
            map.size() >> {println "Size method has been invoked"
                           return 10}

        expect:
            map.size() == 10
    }

/*    def "throwing an exception as the result of the method"() {
        given:
            Map map = Stub()
            map.size() >> {throw new IllegalStateException()}

        expect:
            map.size()
    }*/

    def "defining stub behaviour based on execution order"() {
        given:
            Map map = Stub()
            map.size() >> 2 >> 10 >> 15
            // Other ways of defining return value
            //map.size() >>> 2 >>> 10 >>> 15
            //map.size() >>> [2, 10, 15]

        expect:
            map.size() == 2
            map.size() == 10
            map.size() == 15
            // Any subsequent call returns last value
            map.size() == 15
    }

    def "defining stub mixed behaviour based on execution order"() {
        given:
            Map map = Stub()
            map.size() >> {println "returning 10"
                           return 10} >> {println "returning 20"
                                          return 20} >> {println "returning 30"
                                                         return 30} >> {throw new IllegalStateException()}

        expect:
            map.size() == 10
            map.size() == 20
            map.size() == 30
            // Here and any subsequent call we should expect exception
            //map.size() == 40
    }

    /*
     * Stubbing methods that have parameters.
     */

/*    def "inappropriate stubbing method that has a parameter"() {
        given:
            Map map = Stub()
            // Lack of parameter -> problems
            map.get() >> 10

        expect:
            map.get(10) == 10
    }*/

    def "stubbing method that has a parameter"() {
        given:
            Map map = Stub()
            map.get(10) >> 10
            map.get(20) >> 20
            map.get(30) >> {throw new IllegalStateException()}

        expect:
            map.get(10) == 10
            map.get(20) == 20
            // Throwin an exception
            //map.get(30) == 30
    }

    def "stubbing method that has a parameter with parameter wildcards"() {
        given:
            // Any argument.
            List list = Stub()
            list.contains(_) >> {println "List contains any element"
                                 return true}

            // Any argument of given type.
            Map map = Stub()
            map.containsKey(_ as Number) >> {println "Map contains any Number Key"
                                             return true}

            // Any non null argument.
            Set set = Stub()
            set.contains(!null) >> {println "Set contains any nut null value"
                                    return true}

            // Any argument different than given.
            Queue queue = Stub()
            queue.contains(!Integer.MAX_VALUE) >> {println "Queue contains any not Integer.MAX_VALUE value"
                                                   return true}

        expect:
            list.contains(null) == true
            list.contains(BigDecimal.ZERO) == true

            map.containsKey(Integer.MAX_VALUE) == true
            map.containsKey(BigDecimal.ONE) == true
            //map.containsKey(null) == true

            set.contains("?") == true
            set.contains(BigDecimal.ZERO) == true
            //set.contains(null) == true

            queue.contains(Integer.MIN_VALUE) == true
            //queue.contains(Integer.MAX_VALUE) == true
    }

    def "stubbing method with conditional behaviour"() {
        given:
            Set<Integer> set = Stub()
            set.contains(_) >> {Integer object ->
                                if (object == Integer.MAX_VALUE)
                                    throw new IllegalArgumentException()
                                else
                                    return false;
                                }
        when:
            set.contains(Integer.MAX_VALUE)
        then:
            thrown(IllegalArgumentException)

        when:
            set.contains(Integer.MIN_VALUE)
        then:
            notThrown(IllegalArgumentException)
    }

    def "stubbing method with advanced parameter match"() {
        given:
            TransferService transferService = Stub()
            transferService.save({Transfer transfer -> BigDecimal.valueOf(1_000_000.00d) > transfer.value }) >> {
                println "Ordinary transfer."}
            transferService.save({Transfer transfer -> BigDecimal.valueOf(1_000_000.00d) <= transfer.value }) >> {
                throw new IllegalArgumentException("Over 1 000 000 PLN transfer should be accepted manually!")}

        when:
            Transfer transfer = new Transfer(value: BigDecimal.ONE)
            transferService.save(transfer)
        then:
            notThrown(IllegalArgumentException)

        when:
            Transfer bigTransfer = new Transfer(value: BigDecimal.valueOf(1_000_000.00d))
            transferService.save(bigTransfer)
        then:
            thrown(IllegalArgumentException)
    }
}
