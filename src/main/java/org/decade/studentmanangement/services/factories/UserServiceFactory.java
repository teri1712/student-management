package org.decade.studentmanangement.services.factories;

import org.decade.studentmanangement.services.ConnectionService;
import org.decade.studentmanangement.services.UserService;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class UserServiceFactory implements ObjectFactory {
      @Override
      public Object getObjectInstance(Object obj, Name name, Context context, Hashtable<?, ?> env) throws Exception {
            ConnectionService connectionService = (ConnectionService) context.lookup("ConnectionService");
            return connectionService == null ? null : new UserService(connectionService);
      }
}
