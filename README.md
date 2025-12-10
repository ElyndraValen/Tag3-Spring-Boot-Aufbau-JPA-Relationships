# Tag 3: JPA Relationships & Queries - OneToMany, ManyToOne & Custom Queries

**Spring Boot Aufbau-Kurs - Tag 3 von 10**  
*Von Elyndra Valen, Senior Entwicklerin bei Java Fleet Systems Consulting*

## 📋 Projekt-Übersicht

Dieses Projekt demonstriert fortgeschrittene JPA Relationships und Query Methods:

- ✅ **OneToMany** Relationships (Person → mehrere Adressen, Person → mehrere Orders)
- ✅ **ManyToOne** Relationships (Address → Person, Order → Person)
- ✅ **Bidirectionale Relationships** mit korrekter Synchronisation
- ✅ **Query Methods** nach Naming Convention (kein SQL schreiben!)
- ✅ **Custom Queries** mit JPQL und Fetch Joins
- ✅ **Lifecycle Callbacks** (@PrePersist)
- ✅ **LAZY vs EAGER Loading** verstehen
- ✅ **N+1 Query Problem** lösen mit Fetch Joins

## 🎯 Was du lernst

### Entities mit Relationships
- `Person` mit zwei OneToMany Relationships (Addresses, Orders)
- `Address` mit ManyToOne zu Person
- `Order` mit ManyToOne zu Person
- Helper-Methoden für bidirectionale Synchronisation
- @ToString.Exclude für StackOverflow-Vermeidung

### Query Methods (ohne SQL!)
- `findByLastname(String lastname)`
- `findByFirstnameContainingIgnoreCase(String firstname)`
- `findByAddresses_City(String city)` - Navigation durch Relationships!
- `findByStatus(OrderStatus status)`
- `findTop10ByOrderByOrderDateDesc()` - Top N mit Sorting

### Custom Queries mit JPQL
- `@Query` mit JPQL statt SQL
- `JOIN FETCH` für Performance-Optimierung
- Lösung des N+1 Query Problems

## 🚀 Projekt starten

### Voraussetzungen
- Java 21
- Maven 3.8+
- MariaDB 10.6+ (läuft auf localhost:3306)

### Installation

```bash
# Repository klonen oder ZIP entpacken
cd Tag3-Spring-Boot-Aufbau-JPA-Relationships

# Dependencies installieren
mvn clean install

# Projekt starten
mvn spring-boot:run
```

Die Anwendung läuft auf `http://localhost:8080`

### MariaDB Konfiguration

Standardmäßig verbindet sich die Anwendung mit:
- **Host:** localhost:3306
- **Database:** person_management (wird automatisch erstellt)
- **User:** root
- **Password:** (leer)

**Anpassen?** Editiere `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/person_management?createDatabaseIfNotExist=true
spring.datasource.username=dein-username
spring.datasource.password=dein-password
```

## 📚 API Endpoints

### Persons

| Method | Endpoint | Beschreibung |
|--------|----------|-------------|
| GET | `/api/persons` | Alle Personen |
| GET | `/api/persons/{id}` | Person nach ID |
| POST | `/api/persons` | Neue Person erstellen |
| POST | `/api/persons/{id}/addresses` | Adresse zu Person hinzufügen |
| GET | `/api/persons/search/by-lastname?lastname=...` | Suche nach Nachname |
| GET | `/api/persons/search/by-email?email=...` | Suche nach Email |
| GET | `/api/persons/search/by-city?city=...` | Personen mit Adresse in Stadt |
| GET | `/api/persons/search/by-address-type?type=HOME` | Personen mit Adresstyp |
| GET | `/api/persons/with-addresses` | Alle Personen MIT Adressen (Fetch Join) |

### Orders

| Method | Endpoint | Beschreibung |
|--------|----------|-------------|
| GET | `/api/orders` | Alle Orders |
| GET | `/api/orders/{id}` | Order nach ID |
| POST | `/api/orders` | Neue Order erstellen |
| GET | `/api/orders/search/by-status?status=PENDING` | Suche nach Status |
| GET | `/api/orders/search/by-person?personId=1` | Orders einer Person |
| GET | `/api/orders/search/by-email?email=...` | Orders nach Person Email |
| GET | `/api/orders/search/expensive?minPrice=100` | Teure Orders |
| GET | `/api/orders/recent` | Neueste 10 Orders |

## 🧪 Testen mit cURL

### Automatische Tests

```bash
# Alle Tests ausführen
./curl-tests.sh
```

### Manuelle Tests

#### Person mit mehreren Adressen erstellen

```bash
curl -X POST http://localhost:8080/api/persons \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Max",
    "lastname": "Mustermann",
    "email": "max@example.com",
    "addresses": [
      {
        "street": "Hauptstraße 1",
        "city": "Berlin",
        "zipCode": "10115",
        "country": "Deutschland",
        "type": "HOME"
      },
      {
        "street": "Geschäftsweg 42",
        "city": "München",
        "zipCode": "80333",
        "country": "Deutschland",
        "type": "WORK"
      }
    ]
  }'
```

#### Adresse zu Person hinzufügen

```bash
curl -X POST http://localhost:8080/api/persons/1/addresses \
  -H "Content-Type: application/json" \
  -d '{
    "street": "Lieferadresse 7",
    "city": "Hamburg",
    "zipCode": "20095",
    "country": "Deutschland",
    "type": "SHIPPING"
  }'
```

#### Personen nach Stadt suchen

```bash
curl "http://localhost:8080/api/persons/search/by-city?city=Berlin"
```

#### Alle Personen MIT Adressen laden (Fetch Join)

```bash
curl http://localhost:8080/api/persons/with-addresses
```

## 🏗️ Projekt-Struktur

```
Tag3-Spring-Boot-Aufbau-JPA-Relationships/
├── pom.xml
├── README.md
├── curl-tests.sh
├── src/
│   ├── main/
│   │   ├── java/com/javafleet/personmanagement/
│   │   │   ├── PersonManagementApplication.java
│   │   │   ├── entity/
│   │   │   │   ├── AddressType.java       # Enum: HOME, WORK, BILLING, SHIPPING
│   │   │   │   ├── OrderStatus.java       # Enum: PENDING, CONFIRMED, ...
│   │   │   │   ├── Person.java            # Entity mit 2x OneToMany
│   │   │   │   ├── Address.java           # Entity mit ManyToOne
│   │   │   │   └── Order.java             # Entity mit ManyToOne, @PrePersist
│   │   │   ├── repository/
│   │   │   │   ├── PersonRepository.java  # Query Methods + Custom Queries
│   │   │   │   └── OrderRepository.java   # Query Methods
│   │   │   └── controller/
│   │   │       ├── PersonController.java  # REST API
│   │   │       └── OrderController.java   # REST API
│   │   └── resources/
│   │       ├── application.properties     # Konfiguration
│   │       └── test-data.sql             # Test-Daten (optional)
│   └── test/
│       └── java/com/javafleet/personmanagement/
```

## 💡 Wichtige Konzepte

### 1. Bidirectionale Relationships synchron halten

❌ **Falsch:**
```java
person.getAddresses().add(address);
// → address.getPerson() ist noch NULL!
```

✅ **Richtig:**
```java
person.addAddress(address);  // Helper-Methode synchronisiert beide Seiten!
```

### 2. @ToString.Exclude bei bidirektionalen Relationships

Ohne `@ToString.Exclude`:
```
Person.toString() → Address.toString() → Person.toString() → StackOverflowError! 💥
```

Mit `@ToString.Exclude`:
```java
@OneToMany(...)
@ToString.Exclude  // Wichtig!
private List<Address> addresses;
```

### 3. LAZY vs EAGER Loading

**LAZY (Standard für @OneToMany):**
```java
Person person = repository.findById(1L).get();
// SQL: SELECT * FROM persons WHERE id = 1
// Adressen werden NICHT geladen!

person.getAddresses().size();
// SQL: SELECT * FROM addresses WHERE person_id = 1
// Jetzt werden Adressen geladen!
```

**Problem: N+1 Queries**
```java
List<Person> persons = repository.findAll();  // 1 Query
for (Person p : persons) {
    p.getAddresses().size();  // N Queries (eine pro Person!)
}
// = N+1 Queries! 😱
```

**Lösung: Fetch Joins**
```java
@Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.addresses")
List<Person> findAllWithAddresses();
// Nur 1 Query für alles!
```

### 4. Query Methods - Spring Data Magic!

Methodenname → SQL:
```java
// Methodenname
List<Person> findByLastname(String lastname);

// Wird zu SQL:
// SELECT * FROM persons WHERE lastname = ?
```

Navigation durch Relationships:
```java
// Methodenname
List<Person> findByAddresses_City(String city);

// Wird zu SQL:
// SELECT DISTINCT p.* FROM persons p 
// INNER JOIN addresses a ON p.id = a.person_id 
// WHERE a.city = ?
```

**Kein SQL schreiben - Spring Data JPA generiert es automatisch!** 🎉

### 5. Lifecycle Callbacks

```java
@PrePersist
protected void onCreate() {
    if (orderDate == null) {
        orderDate = LocalDateTime.now();
    }
}
```

Wird automatisch VOR dem ersten Speichern aufgerufen!

## 🐛 Troubleshooting

### "LazyInitializationException"

**Problem:** Du greifst auf LAZY-geladene Entities außerhalb einer Transaction zu.

**Lösung:**
1. Nutze `@Transactional` auf Service-Methoden
2. Oder nutze Fetch Joins für explizites Laden

### "StackOverflowError beim toString()"

**Problem:** Bidirectionale Relationship ohne `@ToString.Exclude`.

**Lösung:**
```java
@ToString.Exclude
private Person person;
```

### "N+1 Query Problem"

**Problem:** Zu viele SQL-Queries beim Laden von Relationships.

**Lösung:**
```java
@Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.addresses")
List<Person> findAllWithAddresses();
```

## 📖 Weiterführende Ressourcen

- [Spring Data JPA - Query Methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)
- [Hibernate - Entity Relationships](https://docs.jboss.org/hibernate/orm/6.3/userguide/html_single/Hibernate_User_Guide.html#associations)
- [Blogbeitrag zu diesem Projekt](https://java-developer.online/tag-3-jpa-relationships)

## 🎓 Was du gelernt hast

Nach diesem Projekt kannst du:

- ✅ OneToMany und ManyToOne Relationships implementieren
- ✅ Bidirectionale Relationships korrekt synchronisieren
- ✅ Helper-Methoden für Relationship-Management schreiben
- ✅ @ToString.Exclude für Infinite Loops einsetzen
- ✅ Query Methods ohne SQL schreiben
- ✅ @PrePersist für automatische Defaults nutzen
- ✅ Komplexe Datenmodelle mit mehreren Relationships bauen
- ✅ Das N+1 Problem erkennen und mit Fetch Joins lösen
- ✅ LAZY vs EAGER Loading verstehen und richtig einsetzen
- ✅ Custom Queries mit JPQL und @Query schreiben

## 👤 Autor

**Elyndra Valen**  
Senior Entwicklerin bei Java Fleet Systems Consulting  
elyndra@java-developer.online

## 📄 Lizenz

Dieses Projekt ist Teil des Spring Boot Aufbau-Kurses und steht unter der MIT-Lizenz.

---

**"Relationships sind das Herzstück jeder Datenbank - genau wie im echten Leben!"**  
*- Elyndra Valen*

---

**Tags:** #SpringBoot #JPA #Relationships #OneToMany #ManyToOne #QueryMethods #Hibernate #Tutorial #Tag3
