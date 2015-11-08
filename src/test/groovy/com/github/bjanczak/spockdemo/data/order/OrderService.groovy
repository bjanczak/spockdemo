package com.github.bjanczak.spockdemo.data.order

/**
 * Order service.
 *
 * @author Bartłomiej Jańczak 
 */
class OrderService {

    /**
     * Places an order.
     *
     * @param user user placing an order
     * @param order order placed
     */
    void placeAnOrder(User user, Order order) {
        if (user.getUserRole() == UserRole.ADMIN) {
            throw new IllegalArgumentException("Admin can not place an order!");
        }

        println "Order placed"
    }
}
