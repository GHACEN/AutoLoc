# Atelier 4 — IoC/DI et couche Service

## A. Injection par champ vs injection par constructeur

| Critère | Injection par champ `@Autowired` | Injection par constructeur `@RequiredArgsConstructor` |
|---|---|---|
| Immutabilité | Champ non `final`, modifiable | Champ `final`, obligation d'initialisation |
| Testabilité | Nécessite Spring ou reflection | Instance creable avec `new Impl(repository)` |
| Visibilité des dépendances | Dépendances cachees dans le corps de la classe | Dépendances visibles dans le constructeur |
| Échec au démarrage | Erreur possible seulement à l'usage | `NoSuchBeanDefinitionException` immédiat si bean manquant |
| Choix AutoLoc | Évité | Retenu pour les 9 services et les controllers |

Conclusion : l'injection par constructeur est utilisée partout dans la couche Service.

## B. Tableau des neuf services

| Interface | Implémentation | Repository injecté | Règle métier principale |
|---|---|---|---|
| `IClientService` | `ClientServiceImpl` | `IClientRepository` | `nom`, `email` obligatoires ; `dateInscription = LocalDate.now()` à la création. |
| `IVehiculeService` | `VehiculeServiceImpl` | `IVehiculeRepository` | `tarifJournalier >= 0`. |
| `IAgenceService` | `AgenceServiceImpl` | `IAgenceRepository` | `nom`, `ville`, `telephone` obligatoires. |
| `IEmployeService` | `EmployeServiceImpl` | `IEmployeRepository` | `nom` et `role` obligatoires. |
| `IEquipementService` | `EquipementServiceImpl` | `IEquipementRepository` | `libelle` obligatoire. |
| `IReservationService` | `ReservationServiceImpl` | `IReservationRepository` | `dateFin` pas avant `dateDebut`. |
| `IContratService` | `ContratServiceImpl` | `IContratRepository` | `montantTotal >= 0` ; cascade `ALL` vers `Paiement` laissée à l'entité. |
| `IPaiementService` | `PaiementServiceImpl` | `IPaiementRepository` | Lecture seule : `findById`, `findAll`. |
| `IMaintenanceService` | `MaintenanceServiceImpl` | `IMaintenanceRepository` | `dateFin` pas avant `dateDebut`. |

Contrat appliqué : `create`, `findById`, `findAll`, `update(Long, entité)`, `deleteById`.

## C. Erreurs DI rencontrées et corrections

| Erreur | Cause | Correction |
|---|---|---|
| `NoSuchBeanDefinitionException` | Classe d'implémentation sans `@Service`, ou mauvais nom d'interface/implémentation | Annoter chaque `...ServiceImpl` avec `@Service` et faire correspondre l'interface injectée |
| `NullPointerException` au lieu d'une erreur de création | Instanciation manuelle avec `new ...ServiceImpl()` ou dépendance non injectée | Laisser Spring créer le bean ; champ `private final` + `@RequiredArgsConstructor` |
| `BeanCurrentlyInCreationException` | Cycle d'injection entre deux services | Supprimer l'injection circulaire ; un service ne dépend que de son repository |

## D. Anomalies qualité corrigées

| Anomalie | Explication | Correction |
|---|---|---|
| `@AllArgsConstructor` dans les services | Constructeur trop large si la classe évolue | `@RequiredArgsConstructor` sur les champs `final` |
| `orElse(null)` dans `findById` | Renvoie `null` et provoque des `NullPointerException` en aval | `orElseThrow(() -> new ResourceNotFoundException("Ressource", id))` |
| `update`/`deleteById` sans contrôle d'existence | Mise à jour ou suppression silencieuse d'un id inexistant | `findById` puis `existsById` avant l'action |

Vérification prévue : `.\mvnw.cmd clean package` → `BUILD SUCCESS`, puis démarrage sans `APPLICATION FAILED TO START`.

## E. Questions de compréhension

**1. Différence entre IoC et DI ?**  
IoC = le conteneur Spring contrôle le cycle de vie des objets. DI = Spring injecte les dépendances dans ces objets.

**2. Pourquoi annoter l'implémentation avec `@Service` et non l'interface ?**  
Spring doit instancier une classe concrète. L'interface sert de contrat injecté dans les consommateurs.

**3. Pourquoi `@RequiredArgsConstructor` plutôt que `@Autowired` sur le champ ?**  
Parce que le repository est `final` : le constructeur garantit une dépendance non nulle et rend la classe testable sans Spring.

**4. Que se passe-t-il si deux implémentations de la même interface existent ?**  
Spring ne sait plus laquelle injecter : `NoUniqueBeanDefinitionException`. Il faut `@Primary` ou `@Qualifier`.

**5. Comment tester `ClientServiceImpl` sans contexte Spring ?**  
`new ClientServiceImpl(new IClientRepository() { ... })` ou un mock Mockito du repository, puis appel direct de `create/findById/update/deleteById`.
