package org.decade.studentmanangement.services.factories;

import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.services.ConnectionService;
import org.decade.studentmanangement.services.CourseStudentService;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class StudentCourseServiceFactory implements ObjectFactory {
      @Override
      public Object getObjectInstance(Object obj, Name name, Context context, Hashtable<?, ?> env) throws Exception {

            ConnectionService connectionService = (ConnectionService) context.lookup("ConnectionService");
            StudentDao studentDao = (StudentDao) context.lookup("StudentDao");
            CourseDao courseDao = (CourseDao) context.lookup("CourseDao");
            return new CourseStudentService(connectionService, courseDao, studentDao);
      }
}
