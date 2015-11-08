package com.github.bjanczak.spockdemo

import com.github.bjanczak.spockdemo.data.order.Order
import com.github.bjanczak.spockdemo.data.order.OrderService
import com.github.bjanczak.spockdemo.data.order.OrderStatus
import com.github.bjanczak.spockdemo.data.order.User
import com.github.bjanczak.spockdemo.data.order.UserRole
import spock.lang.Specification

/**
 * Chapter 11
 * Good practices.
 *
 * @author Bartłomiej Jańczak 
 */
class Chapter11_GoodPractices extends Specification {

    /*
     * Using descriptive methods inside test.
     *
     * Given section tends to grow. Try using descriptive helper methods to keep it clean.
     */

    def "should throw exception if user is an admin"() {
        given:
            User tmpUser = Stub()
            tmpUser.getId() >> 123456
            tmpUser.getName() >> "James"
            tmpUser.getUserRole() >> UserRole.ADMIN
            // tmpUser.get...

            Order tmpOrder = Stub()
            tmpOrder.getId() >> 987654
            tmpOrder.getAmount() >> 1
            tmpOrder.getOrderStatus() >> OrderStatus.UNPAID
            tmpOrder.getValue() >> BigDecimal.valueOf(10.00d)
            // tmpOrder.get...

            // Object under test.
            OrderService tmpOrderService = new OrderService()

        when:
            tmpOrderService.placeAnOrder(tmpUser, tmpOrder)

        then:
            thrown(IllegalArgumentException)
    }

    private User user = Stub()
    private Order order = Stub()
    private OrderService orderService = new OrderService()

    def void userWithgivenRole(UserRole userRole) {
        User tmpUser = Stub()
        tmpUser.getId() >> 123456
        tmpUser.getName() >> "James"
        tmpUser.getUserRole() >> userRole
        // tmpUser.get...

        user = tmpUser
    }

    def void unpaidOrder() {
        Order tmpOrder = Stub()
        tmpOrder.getId() >> 987654
        tmpOrder.getAmount() >> 1
        tmpOrder.getOrderStatus() >> OrderStatus.UNPAID
        tmpOrder.getValue() >> BigDecimal.valueOf(10.00d)
        // tmpOrder.get...

        order = tmpOrder
    }

    def "should throw exception if user is an admin improved"() {
        given:
            userWithgivenRole UserRole.ADMIN
            unpaidOrder()

        when:
            orderService.placeAnOrder(user, order)

        then:
            thrown(IllegalArgumentException)
    }

    /*
     * Using 'and' label.
     */

    def userHasUserRole() {
        user.getUserRole() >> UserRole.USER
    }

    def userHasAdminRole() {
        user.getUserRole() >> UserRole.ADMIN
    }

    def orderHas10PLNValue() {
        order.getValue() >> BigDecimal.valueOf(10.00d)
    }

    def orderIsUnpaid() {
        order.getOrderStatus() >> OrderStatus.UNPAID
    }

    def "should throw exception if user is an admin not and used"() {
        given:
            userHasUserRole()
            orderHas10PLNValue()
            orderIsUnpaid()

        when:
            orderService.placeAnOrder(user, order)

        then:
            notThrown(IllegalArgumentException)
    }

    def "should throw exception if user is an admin and used"() {
        given: "order is eligible for place due to"
            orderHas10PLNValue()
            orderIsUnpaid()
        and: "not satisfied user role due to"
            userHasAdminRole()

        when:
            orderService.placeAnOrder(user, order)

        then:
            thrown(IllegalArgumentException)
    }

    /*
     * Using maps for method parameters.
     */

    def orderIsDoneForUser(Map userDetails) {
        user.getId() >> userDetails['id']
        user.getName() >> userDetails['name']
        user.getUserRole() >> userDetails['userRole']
    }

    def orderIsDoneForOrder(Map orderDetails) {
        order.getId() >> orderDetails['id']
        order.getAmount() >> orderDetails['amount']
        order.getOrderStatus() >> orderDetails['orderStatus']
        order.getValue() >> orderDetails['value']
    }

    def "maps for method parameters"() {
        given:
            orderIsDoneForUser id: 12345, name: "Some user name", userRole: UserRole.ADMIN
            orderIsDoneForOrder id: 987654, amount: 1, orderStatus: OrderStatus.UNPAID, value: BigDecimal.valueOf(10.00d)

        when:
            orderService.placeAnOrder(user, order)

        then:
            thrown(IllegalArgumentException)
    }
}
