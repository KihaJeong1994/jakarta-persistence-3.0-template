# JPA Template

---

Jakarta Persistence API 3.0 template

JPA
- standard for management of **persistence** and **object/relational mapping**

## 1. Entity

### Entity 

a lightweight persistent domain object

```java
@Entity
@NoArgsConstructor // must have no-arg constructor
@Getter
@Setter
public class User{
    
}
```

### Entity Relationships

https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html#a516

**Bidirectional vs Unidirectional**

- Bidirectional : has both an owning side & inverse(non-owning) side
- Unidirectional : has only an owning side

**Relationships**

- one-to-one
- one-to-many
- many-to-one
- many-to-many

---

## 2. EntityManager

### Entity Instance's life cycle

- new : entity with no persistent identity. not yet associated with a persistence context
- managed : entity with persistent identity & associated with a persistence context
- detached : entity with persistent identity, but not associated with a persistence context
- removed : entity with persistent identity & associated with a persistence context, but will be removed from the database upon transaction commit

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