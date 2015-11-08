package com.github.bjanczak.spockdemo

import com.github.bjanczak.spockdemo.data.transfer.Transaction
import com.github.bjanczak.spockdemo.data.transfer.Transfer
import com.github.bjanczak.spockdemo.data.transfer.TransferService
import com.github.bjanczak.spockdemo.data.transfer.TransferServiceImpl
import spock.lang.Specification

/**
 * Chapter 9
 * Working with Spy object.
 *
 * Spy is not a dummy object like Mock or Stub. It is rather a wrapper to an object.
 * Whenever you want to verify that a method of some particular class has been invoked
 * and you cannot use Mock as for some reason you want the original implementation of this method to be executed,
 * use Spy instead of Mock.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter9_Spies extends Specification {

/*    def "creating spy from interface"() {
        // Not a good idea - spy object should by called on an implementation
        given:
            TransferService transferService = Spy(TransferService)

        expect:
            transferService.save(new Transfer(value: BigDecimal.ONE))
    }*/

    def "creating spy"() {
        // You need CGLIB in classpath to work this way.
        given:
            Transaction transaction = Stub()
            TransferService transferService = Spy(TransferServiceImpl, constructorArgs: [transaction])

        expect:
            transferService.save(new Transfer(value: BigDecimal.ONE))
    }

    /*
     * How does it differ from Mock object? We did not change the save method behaviour what is visible in console.
     */

    def "verifying interaction with spy"() {
        given:
            Transaction transaction = Stub()
            TransferService transferService = Spy(TransferServiceImpl, constructorArgs: [transaction])

        when:
            transferService.save(new Transfer(value: BigDecimal.ONE))

        then:
            1 * transferService.save(_)
    }

    /*
     * Unlike Mock and Stub Spy will override only a method that it has been explicitly told to override,
     * the implementation of other methods will be unaffected.
     */

    def "specifying behaviour in spy"() {
        given:
            Transaction transaction = Stub()
            TransferService transferService = Spy(TransferServiceImpl, constructorArgs: [transaction])
            //transferService.isServiceUp() >> true

        expect:
            if (transferService.isServiceUp()) {
                transferService.save(new Transfer(value: BigDecimal.ONE))
            }
    }
}
