package org.decade.studentmanangement.services;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallback<T> {
      T run(Connection connection) throws SQLException;
}
