package com.github.bjanczak.spockdemo.data.transfer

/**
 * Implementation of {@link TransferService}.
 *
 * @author Bartłomiej Jańczak 
 */
class TransferServiceImpl implements TransferService {

    TransferServiceImpl(Transaction transaction) { }

    @Override
    void save(Transfer transfer) {
        println "Saving transfer ${transfer.value} - TransferServiceImpl"
    }

    @Override
    void commit() {
        println "commit"
    }

    @Override
    boolean isServiceUp() {
        return false
    }
}
