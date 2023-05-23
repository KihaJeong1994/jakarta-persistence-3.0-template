# JPA Template

---

Jakarta Persistence API 3.0 template

https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html#introduction

Hibernate 6.2

https://docs.jboss.org/hibernate/orm/6.2/userguide/html_single/Hibernate_User_Guide.html#preface

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
  - owning side : <span style="background-color:#ffdce0">**determines the updates to the relationship in the database**</span>
  - Relationship Mapping Defaults is below
  - one-to-one
    - owning side : contains foreign key
    - inverse side : mappedBy
    - `orphanRemoval` option is possible
  - one-to-many / many-to-one
    - owning side : `Many` side must be owning side -> no mappedBy. contains foreign key.
    - inverse side : `One`. mappedBy
    - *cascade=REMOVE* should not be applied
    - `orphanRemoval` option is possible for one-to-many
  - many-to-many
    - there is a join table that is named A_B(owner name first) 
    - *cascade=REMOVE* should not be applied 
    - setting relationship on inverse side does not work!
    - hibernate : when remove relationship, the whole relationship is removed and insert rest of relationship(delete all relationship & insert again) -> many-to-many with a link entity! 
    >When an entity is removed from the @ManyToMany collection, Hibernate simply deletes the joining record in the link table. Unfortunately, this operation requires removing all entries associated with a given parent and recreating the ones that are listed in the current running persistent context.
- Unidirectional : has only an owning side
  - one-to-one
    - owning side : contains foreign key
  - one-to-many
    - owning side : `One` 
    - there is a join table that is named A_B(owner name first)
  - many-to-one
    - owning side : contains foreign key
  - many-to-many
    - there is a join table that is named A_B(owner name first)
- `cascade` : propagate the cascadable operation to the associated entity
  - persist
  - merge
  - remove
  - refresh
  - detach
- `orphanRemoval` 
  - the entity is removed when it is removed from relationship(one-to-one, one-to-many)
  - in hibernate, `CascadeType.PERSIST` is necessary to use orphanRemoval=true option

**JPA itself does not ensure the consistency of runtime relationships**
>Note that it is the application that bears responsibility for maintaining the consistency of runtime relationships—for example, for insuring that the “one” and the “many” sides of a bidirectional relationship are consistent with one another when the application updates the relationship at runtime.

```java
@OneToMany(mappedBy = "customer"
        ,cascade = CascadeType.PERSIST // hibernate need CascadeType.PERSIST option for orphanRemoval since persist on Customer will propagate the persist operation to the Account
        , orphanRemoval = true)
private List<Account> accounts ;


// method to manage bidirectional relationship between entity
public void addAccount(Account account){
    this.accounts.add(account);
    account.setCustomer(this);
}

// method to manage bidirectional relationship between entity
public void removeAccount(Account account){
    this.accounts.remove(account);
    account.setCustomer(null);
}
```


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