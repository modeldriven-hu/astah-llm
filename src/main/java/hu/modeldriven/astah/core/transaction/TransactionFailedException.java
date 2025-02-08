package hu.modeldriven.astah.core.transaction;

public class TransactionFailedException extends Exception {

    public TransactionFailedException(Exception e) {
        super(e);
    }

}
