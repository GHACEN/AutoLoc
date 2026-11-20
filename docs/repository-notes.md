# Atelier 3 — Spring Data JPA : couche Repository

## A. Choix de l'interface

Toutes les interfaces du package `tn.esprit.autoloce1.repository` étendent
`JpaRepository<Entité, Long>` : CRUD complet, `findAll()` qui renvoie une `List`,
tri/pagination et méthodes propres à JPA (`saveAndFlush`, `flush`, `getReferenceById`).

| Interface | Étend | Justification |
|---|---|---|
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD complet sur Agence. |
| `IClientRepository` | `JpaRepository<Client, Long>` | CRUD complet sur Client. |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD complet ; la cascade vers Paiement est gérée par l'entité. |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | CRUD complet sur Employe. |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | CRUD complet sur Equipement. |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | CRUD complet sur Maintenance. |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Créée pour la lecture des paiements ; la création passe par Contrat (cascade `ALL`). |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | CRUD complet sur Reservation. |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | CRUD complet sur Vehicule. |

## B. Conventions appliquées

- Préfixe `I` pour les neuf interfaces, conformément à la convention AutoLoc.
- Aucune classe d'implémentation écrite : le proxy Spring Data est généré au démarrage.
- Aucune annotation `@Repository` nécessaire sur une interface Spring Data.
- Type d'identifiant `Long` aligné avec les `@Id` des entités.

## C. Vérifications

- `.\mvnw.cmd clean package` → `BUILD SUCCESS`.
- Démarrage de l'application → log :
  `Finished Spring Data repository scanning ... Found 9 JPA repository interfaces.`
- `ddl-auto=update`, `show-sql=true` et `logging.level.org.hibernate.SQL=DEBUG` actifs
  dans `application.properties`.

## D. Anomalies qualité corrigées

| Anomalie | Explication | Correction |
|---|---|---|
| Interfaces repository sans préfixe `I` | Non-conformité à la convention de nommage AutoLoc | Renommage des neuf interfaces en `I...Repository` |
| Caractère parasite `s` dans `pom.xml` | POM non parseable, build impossible | Suppression du caractère |
| Logger `tn.esprit.autoloc` | Paquetage inexistant, logs de l'application muets | Remplacé par `tn.esprit.autoloce1` |

À compléter après analyse du projet avec SonarQube for IDE dans IntelliJ (règles exactes et identifiants).
