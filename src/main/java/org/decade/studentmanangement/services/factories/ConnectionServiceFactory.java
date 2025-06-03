package org.decade.studentmanangement.services.factories;

import org.decade.studentmanangement.services.ConnectionService;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Hashtable;

public class ConnectionServiceFactory implements ObjectFactory {
      @Override
      public Object getObjectInstance(Object obj, Name name, Context context, Hashtable<?, ?> env) throws Exception {
            if (!(obj instanceof Reference)) {
                  return null;
            }
            Reference ref = (Reference) obj;

            String username = (String) ref.get("username").getContent();
            String password = (String) ref.get("password").getContent();
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLySinhVien", username, password);
                  return new ConnectionService(connection);
            } catch (Exception ex) {
                  ex.printStackTrace();
            }
            return null;
      }
}
