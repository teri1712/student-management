package org.decade.studentmanangement.services.factories;

import jakarta.persistence.EntityManagerFactory;
import org.decade.studentmanangement.services.CourseService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class CourseServiceFactory implements ObjectFactory {
      @Override
      public Object getObjectInstance(Object o, Name name, Context context, Hashtable<?, ?> hashtable) throws Exception {
            EntityManagerFactory emf = null;
            try {
                  // Try common JNDI names used in this project/container
                  Object obj = context.lookup("services/EntityManagerFactory");
                  if (obj instanceof EntityManagerFactory) {
                        emf = (EntityManagerFactory) obj;
                  }
            } catch (Exception ignore) {}
            if (emf == null) {
                  try {
                        Object obj = context.lookup("EntityManagerFactory");
                        if (obj instanceof EntityManagerFactory) {
                              emf = (EntityManagerFactory) obj;
                        }
                  } catch (Exception ignore) {}
            }
            if (emf == null) {
                  try {
                        InitialContext ic = new InitialContext();
                        Object obj = ic.lookup("java:comp/env/services/EntityManagerFactory");
                        if (obj instanceof EntityManagerFactory) {
                              emf = (EntityManagerFactory) obj;
                        }
                  } catch (Exception ignore) {}
            }
            return emf == null ? null : new CourseService(emf);
      }
}
