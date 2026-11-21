# Atelier 5 — Spring MVC, tests Insomnia et documentation Swagger

Base URL : `http://localhost:8080/api` (`server.port=8080`, `server.servlet.context-path=/api`).

Swagger UI : `http://localhost:8080/api/swagger-ui.html`  
OpenAPI JSON : `http://localhost:8080/api/v3/api-docs`

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| `AgenceController` | `GET /api/agences` | `findAll()` | 200 | — |
| `AgenceController` | `GET /api/agences/{id}` | `findById(id)` | 200 | 404 |
| `AgenceController` | `POST /api/agences` | `create(...)` | 201 | 400 |
| `AgenceController` | `PUT /api/agences/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `AgenceController` | `DELETE /api/agences/{id}` | `deleteById(id)` | 204 | 404 |
| `EmployeController` | `GET /api/employes` | `findAll()` | 200 | — |
| `EmployeController` | `GET /api/employes/{id}` | `findById(id)` | 200 | 404 |
| `EmployeController` | `POST /api/employes` | `create(...)` | 201 | 400 |
| `EmployeController` | `PUT /api/employes/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `EmployeController` | `DELETE /api/employes/{id}` | `deleteById(id)` | 204 | 404 |
| `VehiculeController` | `GET /api/vehicules` | `findAll()` | 200 | — |
| `VehiculeController` | `GET /api/vehicules/{id}` | `findById(id)` | 200 | 404 |
| `VehiculeController` | `POST /api/vehicules` | `create(...)` | 201 | 400 |
| `VehiculeController` | `PUT /api/vehicules/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `VehiculeController` | `DELETE /api/vehicules/{id}` | `deleteById(id)` | 204 | 404 |
| `EquipementController` | `GET /api/equipements` | `findAll()` | 200 | — |
| `EquipementController` | `GET /api/equipements/{id}` | `findById(id)` | 200 | 404 |
| `EquipementController` | `POST /api/equipements` | `create(...)` | 201 | 400 |
| `EquipementController` | `PUT /api/equipements/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `EquipementController` | `DELETE /api/equipements/{id}` | `deleteById(id)` | 204 | 404 |
| `ClientController` | `GET /api/clients` | `findAll()` | 200 | — |
| `ClientController` | `GET /api/clients/{id}` | `findById(id)` | 200 | 404 |
| `ClientController` | `POST /api/clients` | `create(...)` | 201 | 400 |
| `ClientController` | `PUT /api/clients/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `ClientController` | `DELETE /api/clients/{id}` | `deleteById(id)` | 204 | 404 |
| `ReservationController` | `GET /api/reservations` | `findAll()` | 200 | — |
| `ReservationController` | `GET /api/reservations/{id}` | `findById(id)` | 200 | 404 |
| `ReservationController` | `POST /api/reservations` | `create(...)` | 201 | 400 |
| `ReservationController` | `PUT /api/reservations/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `ReservationController` | `DELETE /api/reservations/{id}` | `deleteById(id)` | 204 | 404 |
| `ContratController` | `GET /api/contrats` | `findAll()` | 200 | — |
| `ContratController` | `GET /api/contrats/{id}` | `findById(id)` | 200 | 404 |
| `ContratController` | `POST /api/contrats` | `create(...)` | 201 | 400 |
| `ContratController` | `PUT /api/contrats/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `ContratController` | `DELETE /api/contrats/{id}` | `deleteById(id)` | 204 | 404 |
| `MaintenanceController` | `GET /api/maintenances` | `findAll()` | 200 | — |
| `MaintenanceController` | `GET /api/maintenances/{id}` | `findById(id)` | 200 | 404 |
| `MaintenanceController` | `POST /api/maintenances` | `create(...)` | 201 | 400 |
| `MaintenanceController` | `PUT /api/maintenances/{id}` | `update(id, ...)` | 200 | 404, 400 |
| `MaintenanceController` | `DELETE /api/maintenances/{id}` | `deleteById(id)` | 204 | 404 |
| `PaiementController` | `GET /api/paiements` | `findAll()` | 200 | — |
| `PaiementController` | `GET /api/paiements/{id}` | `findById(id)` | 200 | 404 |
| `PaiementController` | `POST /api/paiements` | aucun | 405 | — |

Les 404 proviennent de `ResourceNotFoundException`, les 400 de `IllegalArgumentException`, tous deux traduits par `GlobalExceptionHandler` avec un corps JSON `statut`, `message`, `horodatage`.

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| `Contrat.paiements` | `@JsonManagedReference("contrat-paiements")` | Vraie composition : la liste des enfants est sérialisée et liée au parent. |
| `Paiement.contrat` | `@JsonBackReference("contrat-paiements")` | Évite la boucle `Contrat → Paiement → Contrat` ; le parent n'apparaît pas dans le JSON du paiement. |
| `Agence.vehicules`, `Agence.employes` | `@JsonIgnore` | Inverses non utiles dans la réponse ; le véhicule/employé porte déjà son `agence`. |
| `Client.reservations` | `@JsonIgnore` | Inverse non utile ; la réservation porte déjà son `client`. |
| `Equipement.vehicules` | `@JsonIgnore` | Inverse de la relation many-to-many ; le véhicule porte ses équipements. |
| `Reservation.contrat` | `@JsonIgnore` | Inverse de la relation one-to-one ; le contrat porte déjà sa réservation. |

Différence : `@JsonBackReference` est associé à un `@JsonManagedReference` de même nom et reste piloté par le parent (liaison à la désérialisation), alors que `@JsonIgnore` ignore purement et simplement la propriété en lecture et en écriture.

`orphanRemoval = true` sur `Contrat.paiements` permet la cascade `REMOVE` quand le contrat est supprimé.

## C. Insomnia et Swagger

Insomnia sert à tester : requêtes rejouables dans `insomnia/AutoLoc - Atelier 5.yaml`, environnement `local` avec `baseUrl=http://localhost:8080/api`, et scripts `After-response`.

Exemple concret : `GET {{ _.baseUrl }}/contrats/9999` renvoie 404 avec :

```json
{
  "statut": 404,
  "message": "Contrat introuvable avec l'identifiant 9999",
  "horodatage": "2026-11-21T23:10:00"
}
```

Script correspondant :

```js
insomnia.test('Statut 404', () => {
  insomnia.expect(insomnia.response.code).to.eql(404);
});
```

Swagger UI sert à documenter et explorer : le groupe `Contrats` apparaît grâce à `@Tag`, le résumé « Consulter un contrat » grâce à `@Operation`, et les codes 200/404 grâce à `@ApiResponse`. Les deux outils sont complémentaires : Insomnia vérifie le comportement, Swagger reflète le contrat généré depuis le code.

## D. Anomalies SonarQube

| Règle | Explication | Correction |
|---|---|---|
| `@AllArgsConstructor` sur un service à champ unique | Constructeur plus large que nécessaire | Remplacé par `@RequiredArgsConstructor` dans les 9 `...ServiceImpl` (Atelier 4) |
| Import inutile `IAgenceRepository` dans `EmployeServiceImpl` | Import non utilisé après refactorisation | Supprimé |
| Exposition directe de l'entité `Contrat` dans `ContratController` | Couplage API/base et champs internes visibles | Volontaire et provisoire : DTO prévu au chapitre 10 |

## E. Questions

**1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur ?**  
Le `DispatcherServlet`. Il consulte les mapping handlers (`@RequestMapping`, `@GetMapping`, etc.) et la méthode annotée la plus spécifique.

**2. Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?**  
Inversion de dépendance : le contrôleur dépend d'un contrat, pas d'une implémentation ni de la persistance. Cela respecte la séparation en couches et facilite le test.

**3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?**  
201 indique une ressource créée ; 204 indique un succès sans corps. Une absence est un cas fonctionnel normal, traduit par `ResourceNotFoundException` en 404, pas par une erreur serveur.

**4. Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un POST `/api/paiements` ?**  
`IPaiementService` est en lecture seule : les paiements sont créés par cascade depuis `Contrat`. Un POST reçoit 405 Method Not Allowed.

**5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?**  
Risques : couplage API/base, fuite de champs internes, boucles de sérialisation. `Contrat.paiements` est une vraie composition, donc la paire managed/back lie les deux côtés ; `Agence.vehicules` est une inverse déjà représentée par `Vehicule.agence`, donc `@JsonIgnore` suffit.
