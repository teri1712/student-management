CDI SYNTAX LEARNING (Jakarta CDI 4 quick revision)

Goal: Keep a concise, practical reference for CDI used in this project (Tomcat + Weld + Narayana + Hibernate/JPA).

1) Core concepts
- Injection: @Inject on fields/ctors/setters to obtain beans discovered by CDI.
- Scopes (lifecycle / context):
  - @ApplicationScoped: one instance for the whole app.
  - @RequestScoped: one instance per HTTP request.
  - @SessionScoped: one instance per HTTP session (Serializable needed).
  - @Dependent: default; created and destroyed with the injection point.
- Qualifiers: custom annotations to distinguish multiple beans of same type.
- Producers/Disposers: create objects CDI doesn’t construct itself; cleanup with @Disposes.
- Alternatives & Priority: override beans or select implementations.
- Interceptors: cross-cutting (e.g., @Transactional). Enabled by beans.xml or annotations.

2) Weld on Tomcat (Servlet container)
- Tomcat is not a full EE server, so we add Weld and configure it in web.xml:
  - <listener-class>org.jboss.weld.environment.servlet.Listener</listener-class>
- beans.xml (WEB-INF/beans.xml) enables discovery and can include <scan> exclusions.
- CDI API and Weld implementation are bundled in WAR (no provided scope for CDI).

3) Transactions (@Transactional) with Narayana (JTA)
- Tomcat does not provide JTA. We bundle Narayana and expose JTA components via CDI producers.
- persistence.xml is JTA-based:
  - transaction-type="JTA"
  - hibernate.transaction.coordinator_class=jta
  - hibernate.transaction.jta.platform=org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform
- Our project avoids JNDI writes (Tomcat’s app context is read-only) and uses Narayana static accessors.

4) Project usage examples

4.1) Producing JPA EntityManager/Factory
- File: src/main/java/.../services/Resources.java
  - @ApplicationScoped class keeps a single EntityManagerFactory (EMF) per app.
  - @Produces @ApplicationScoped EntityManagerFactory produceEntityManagerFactory()
  - @Produces @RequestScoped EntityManager produceEntityManager(EntityManagerFactory emf)
  - void closeEntityManager(@Disposes EntityManager em) — safely close per-request EM
  - @PreDestroy shutdown() — closes EMF when app stops
- Why: In servlet apps, managing EM per request is typical; Hibernate integrates with JTA via persistence.xml.

4.2) Producing JTA components for @Transactional
- File: src/main/java/.../config/JtaProducers.java
  - @ApplicationScoped, @Alternative, @Priority(1)
  - @Produces TransactionManager -> com.arjuna.ats.jta.TransactionManager.transactionManager()
  - @Produces UserTransaction -> com.arjuna.ats.jta.UserTransaction.userTransaction()
  - @Produces TransactionSynchronizationRegistry -> new TransactionSynchronizationRegistryImple()
- beans.xml excludes Narayana’s CDI beans that expect JNDI binding, so our producers take precedence.
- Effect: CDI @Transactional interceptor (from Narayana) can start/commit/rollback transactions.

4.3) beans.xml fine-tuning
- File: src/main/webapp/WEB-INF/beans.xml
  - bean-discovery-mode="annotated"
  - <scan><exclude> com.arjuna.ats.jta.cdi.NarayanaTransactionManager, NarayanaUserTransaction, NarayanaTransactionSynchronizationRegistry </scan>
- Why: Prevent Narayana CDI beans that rely on JNDI from being selected; we provide our own producers.

4.4) Weld bootstrap
- File: src/main/webapp/WEB-INF/web.xml
  - Weld listener is registered to enable CDI in the webapp.

5) Common annotations quick view
- @Inject: inject bean instances.
- @Produces: method/field to create instances for injection.
- @Disposes: parameter on producer’s disposer method to release resources.
- @ApplicationScoped, @RequestScoped, @SessionScoped, @Dependent: bean scopes.
- @Alternative and @Priority: override bean choices; higher priority wins.
- @Transactional (jakarta.transaction.Transactional): demarcates transactions around methods/classes. Requires JTA and interceptor on classpath (Narayana provides it when bundled).

6) Typical patterns
- Service with transactional boundary:
  - Annotate service methods with @Transactional when they modify DB state.
  - Inject EntityManager; with JTA, you do NOT call getTransaction().begin(); EM joins JTA tx automatically.
- Request-scoped EntityManager:
  - Each request gets a fresh EM; dispose after request to free resources.
- Validation:
  - Use jakarta.validation annotations on entities; provider (Hibernate Validator) is on classpath.

7) Gotchas & tips
- Do not try to bind Narayana objects into Tomcat’s java:comp JNDI — it’s read-only in app context; use static accessors with producers (as we do).
- Ensure beans.xml exists; otherwise, CDI may not discover beans depending on mode.
- If multiple beans of the same type exist, use qualifiers or alternatives to disambiguate.
- When using @Transactional on CDI beans, ensure they are proxied (non-final class/methods) so interceptors can run.
- Keep DAO/Service methods non-static for CDI to intercept and inject.

8) Minimal recipes (copy/paste mental notes)
- Produce a per-request EntityManager:
  - @Produces @RequestScoped EntityManager em(EntityManagerFactory emf) { return emf.createEntityManager(); }
  - void close(@Disposes EntityManager em) { if (em.isOpen()) em.close(); }
- Produce Narayana JTA components:
  - @Produces TransactionManager tm() { return com.arjuna.ats.jta.TransactionManager.transactionManager(); }
  - @Produces UserTransaction utx() { return com.arjuna.ats.jta.UserTransaction.userTransaction(); }
- Mark method transactional:
  - @Transactional public void save(Entity e) { em.persist(e); }

9) References
- CDI: https://jakarta.ee/specifications/cdi/4.0/
- Weld: https://docs.jboss.org/weld/reference/latest/en-US/
- Narayana: https://narayana.io/
- Hibernate ORM 6: https://hibernate.org/orm/documentation/6.5/
