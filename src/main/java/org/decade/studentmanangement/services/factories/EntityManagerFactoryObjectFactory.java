package org.decade.studentmanangement.services.factories;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;

/**
 * JNDI ObjectFactory that builds and returns a Jakarta EntityManagerFactory.
 *
 * Expected JNDI <Resource> attributes in context.xml:
 *  - name="services/EntityManagerFactory"
 *  - type="jakarta.persistence.EntityManagerFactory"
 *  - factory="org.decade.studentmanangement.services.factories.EntityManagerFactoryObjectFactory"
 *  - username, password (and optionally jdbcUrl)
 *
 * Notes:
 *  - Not a static singleton class. The container controls lifecycle and may cache value with singleton="true".
 */
public class EntityManagerFactoryObjectFactory implements ObjectFactory {

    private static final String PU_NAME = "StudentManangementPU";

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        if (!(obj instanceof Reference)) {
            return null;
        }
        Reference ref = (Reference) obj;

        String username = getRefContent(ref, "username", "root");
        String password = getRefContent(ref, "password", "root");
        String jdbcUrl = getRefContent(ref, "jdbcUrl", "jdbc:mysql://localhost:3306/QuanLySinhVien");

        Map<String, Object> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        props.put("jakarta.persistence.jdbc.url", jdbcUrl);
        props.put("jakarta.persistence.jdbc.user", username);
        props.put("jakarta.persistence.jdbc.password", password);

        // Hibernate-specific properties (safe defaults)
        props.put("hibernate.hbm2ddl.auto", "none");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.jdbc.time_zone", "UTC");

        try {
            return Persistence.createEntityManagerFactory(PU_NAME, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String getRefContent(Reference ref, String key, String def) {
        try {
            return ref.get(key) != null ? (String) ref.get(key).getContent() : def;
        } catch (Exception e) {
            return def;
        }
    }
}
