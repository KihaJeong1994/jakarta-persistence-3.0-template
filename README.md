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

**Relationships**

- one-to-one
- one-to-many
- many-to-one
- many-to-many

**Bidirectional vs Unidirectional**

- Bidirectional : has both an owning side & inverse(non-owning) side
  - the inverse side of a bidirectional relationship must refer to its owning side by using `mappedBy` element
  - owning side : determines the updates to the relationship in the database
  - one-to-one
    - owning side : contains foreign key
    - inverse side : mappedBy
    - `orphanRemoval` option is possible
  - one-to-many
    - owning side : `Many` side must be owning side -> no mappedBy
    - inverse side : `One`. mappedBy
    - `orphanRemoval` option is possible
  - many-to-one
    - owning side : `Many` side must be owning side -> no mappedBy
    - inverse side : `One`. mappedBy
    - *cascade=REMOVE* should not be applied
  - many-to-many
    - *cascade=REMOVE* should not be applied 
- Unidirectional : has only an owning side
- `cascade` : propagate the cascadable operation to the associated entity
  - persist
  - merge
  - remove
  - refresh
  - detach
- `orphanRemoval` 
  - the entity is removed when it is removed from relationship(one-to-one, one-to-many)
  - in hibernate, `CascadeType.PERSIST` is necessary to use orphanRemoval=true option



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