package org.decade.studentmanangement.services.factories;

import org.decade.studentmanangement.services.ConnectionService;
import org.decade.studentmanangement.services.CourseService;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class CourseServiceFactory implements ObjectFactory {
      @Override
      public Object getObjectInstance(Object o, Name name, Context context, Hashtable<?, ?> hashtable) throws Exception {
            ConnectionService connectionService = (ConnectionService) context.lookup("ConnectionService");

            return new CourseService(connectionService);
      }
}
