package org.decade.studentmanangement.services.factories;

import jakarta.persistence.EntityManagerFactory;
import org.decade.studentmanangement.services.AssessmentService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class AssessmentServiceFactory implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context context, Hashtable<?, ?> env) throws Exception {
        EntityManagerFactory emf = null;
        try {
            Object o = context.lookup("services/EntityManagerFactory");
            if (o instanceof EntityManagerFactory) emf = (EntityManagerFactory) o;
        } catch (Exception ignore) {}
        if (emf == null) {
            try {
                Object o = context.lookup("EntityManagerFactory");
                if (o instanceof EntityManagerFactory) emf = (EntityManagerFactory) o;
            } catch (Exception ignore) {}
        }
        if (emf == null) {
            try {
                InitialContext ic = new InitialContext();
                Object o = ic.lookup("java:comp/env/services/EntityManagerFactory");
                if (o instanceof EntityManagerFactory) emf = (EntityManagerFactory) o;
            } catch (Exception ignore) {}
        }
        return emf == null ? null : new AssessmentService(emf);
    }
}
