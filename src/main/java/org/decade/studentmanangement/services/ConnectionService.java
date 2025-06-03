package org.decade.studentmanangement.services;

import java.sql.Connection;
import java.sql.SQLException;

// Simple solution to ensure concurrency problems and ATOMIC.
public class ConnectionService {
      private final Connection connection;

      public ConnectionService(Connection connection) {
            this.connection = connection;
      }

      public synchronized <T> T execute(ConnectionCallback<T> callback) throws SQLException {
            boolean ownAutoCommit = connection.getAutoCommit();
            if (ownAutoCommit) {
                  connection.setAutoCommit(false);
            }
            T result = null;
            try {
                  result = callback.run(connection);
                  if (ownAutoCommit)
                        connection.commit();
            } catch (SQLException e) {
                  if (ownAutoCommit)
                        connection.rollback();
                  throw e; // Rethrow the exception after rollback
            } finally {
                  if (ownAutoCommit) {
                        connection.setAutoCommit(true);
                  }
            }
            return result;
      }
}
