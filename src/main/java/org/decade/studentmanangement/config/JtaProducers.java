package org.decade.studentmanangement.config;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionSynchronizationRegistryImple;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.TransactionSynchronizationRegistry;
import jakarta.transaction.UserTransaction;

/**
 * Provides JTA components for CDI without relying on JNDI writes in Tomcat.
 * Uses Narayana static accessors, avoiding the need to bind into java:comp.
 */
@ApplicationScoped
@Alternative
@Priority(1)
public class JtaProducers {

        @Produces
        @ApplicationScoped
        public TransactionManager produceTransactionManager() {
                return com.arjuna.ats.jta.TransactionManager.transactionManager();
        }

        @Produces
        @ApplicationScoped
        public UserTransaction produceUserTransaction() {
                return com.arjuna.ats.jta.UserTransaction.userTransaction();
        }

        @Produces
        @ApplicationScoped
        public TransactionSynchronizationRegistry produceTransactionSynchronizationRegistry() {
                return new TransactionSynchronizationRegistryImple();
        }
}
