# JPA Template

---

Jakarta Persistence API 3.0 template

JPA
- standard for management of **persistence** and **object/relational mapping**

## 1. Entity

---

## 2. EntityManager

---

## 3. Persistence Context

https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html#a11432

> A persistence context is a set of managed entity instances in which for any persistent entity identity there is a unique entity instance. Within the persistence context, the entity instances and their lifecycle are managed by the entity manager.

- Persistence Context is a group of `Entity` instance that is persisted by `EntityManager`
- Container-managed Persistence Context vs Application-Managed Persistence Context
  - Container-managed Persistence Context : different entity manager instances are mapped to same Persistence Context by JTA transaction(propagation of persistence context). For EJB environment & Jakarta EE Web containers.
  ```
  @PersistenceContext
  EntityManager em;
  ```
  - Application-Managed Persistence Context : a new isolated persistence context per an entity manager. For Java SE environments & Jakarta EE application containers.
  
  ```
  EntityManagerFactory emf =
    jakarta.persistence.Persistence.createEntityManagerFactory("Order");
  EntityManager em = emf.createEntityManager();
  ```



---