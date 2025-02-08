package hu.modeldriven.astah.core.transaction;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.ITransactionManager;

public class AstahTransaction {

    public void execute(Runnable command) throws TransactionFailedException {

        ITransactionManager transactionManager;

        try {
            transactionManager = AstahAPI.getAstahAPI().getProjectAccessor().getTransactionManager();
        } catch (ClassNotFoundException e) {
            throw new TransactionFailedException(e);
        }

        try {
            transactionManager.beginTransaction();

            command.run();

            transactionManager.endTransaction();

        } catch (Exception e) {
            transactionManager.abortTransaction();
            throw new TransactionFailedException(e);
        }
    }

}
