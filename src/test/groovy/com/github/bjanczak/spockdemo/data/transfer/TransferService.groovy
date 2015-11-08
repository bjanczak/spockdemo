package com.github.bjanczak.spockdemo.data.transfer

/**
 * Transfer service.
 *
 * @author Bartłomiej Jańczak 
 */
interface TransferService {
    /**
     * Saves given transfer.
     *
     * @param transfer transfer to be saved
     */
    void save(Transfer transfer)

    /**
     * Commits transaction.
     */
    void commit()

    /**
     * Predicate verifying wheter service is up.
     *
     * @return returns true if service is up
     */
    boolean isServiceUp()
}