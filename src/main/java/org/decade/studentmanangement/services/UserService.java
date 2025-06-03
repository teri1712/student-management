package org.decade.studentmanangement.services;

import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.StaffUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService implements UserDao {

      private ConnectionService connectionService;

      public UserService(ConnectionService connectionService) {
            this.connectionService = connectionService;
      }

      public StaffUser getUser(String username) throws SQLException {
            return connectionService.execute(new ConnectionCallback<StaffUser>() {
                  @Override
                  public StaffUser run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from StaffUser where username = ?");
                        stmt.setString(1, username);
                        ResultSet result = stmt.executeQuery();
                        if (!result.next()) {
                              return null;
                        }
                        return new StaffUser(result.getString("fullname"), result.getString("username"), result.getString("pw"));
                  }
            });
      }

      public void addUser(StaffUser user) throws SQLException {
            connectionService.execute(new ConnectionCallback<Void>() {
                  @Override
                  public Void run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("insert into StaffUser values(?,?,?)");
                        stmt.setString(1, user.getUserName());
                        stmt.setString(2, user.getPassword());
                        stmt.setString(3, user.getName());
                        stmt.execute();
                        return null;
                  }

            });
      }
}
