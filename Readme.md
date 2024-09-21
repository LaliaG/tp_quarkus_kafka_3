# TP QUARKUS KAFKA 3 19/09/2024 LALIA SY

## Documentation
[documentation](./documentation/)


## microservices
[department-service](./department/)
[organization-service](./organization-service/)
[employee-service](./employee-service/)

## Objectif de ce projet
Répondre au sujet du [tp_quarkus_kafka_3](./tp_quarkus_kafka_3.md/)

### Explication détaillée du projet en Quarkus avec Kafka

Voici un résumé détaillé du code fourni et de son fonctionnement.

#### 1. **ProductKafkaConsumer.java** (Consommateur Kafka pour la mise à jour du stock des produits)
- **Classe ProductKafkaConsumer** : Cette classe consomme les événements Kafka pour mettre à jour les quantités de produits en stock.
- **@ApplicationScoped** : Définit la portée de l'application pour cette classe, signifiant qu'elle vivra pendant toute la durée de vie de l'application.
- **@Inject ProductRepository** : Injecte un dépôt de produits (repository) pour les opérations de base de données.
- **@Incoming("stock-increase")** : Méthode annotée pour recevoir des messages Kafka depuis le topic nommé "stock-increase".
- **@Blocking** : Exécute la méthode de manière synchrone dans un thread bloquant, car la mise à jour de la base de données nécessite un traitement synchrone.
- **@Transactional** : Assure que les modifications sur la base de données sont réalisées dans le cadre d'une transaction.
- **updateProductStock** : Récupère l'ID du produit et la nouvelle quantité à partir du message Kafka, trouve le produit dans la base de données, puis met à jour son stock.
- **parseProductIdFromMessage / parseQuantityFromMessage** : Ces méthodes parsèment les informations du message pour en extraire l'ID du produit et la quantité.

#### 2. **Product.java** (Entité JPA représentant un produit)
- **@Entity** : Représente une entité de base de données.
- **@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder** : Annotations Lombok pour générer automatiquement les getters, setters, constructeurs et le pattern builder pour l'objet `Product`.
- **Attributs** : Chaque produit a un `id`, un `name`, une `description`, un `price`, un `available` (disponibilité) et un `quantity` (quantité).

#### 3. **ProductRepository.java** (Dépôt de produits)
- **PanacheRepository<Product>** : Utilisation de Panache, une bibliothèque de Quarkus simplifiant l'accès à la base de données pour les entités JPA.
  
#### 4. **ProductResource.java** (API REST pour les produits)
- **ProductResource** : Classe exposant une API REST pour gérer les produits via des méthodes CRUD.
- **@Path("/products")** : Définit la route API à laquelle cette ressource répond.
- **getAllProducts, getProductById, createProduct, updateProduct, deleteProduct** : Ces méthodes gèrent respectivement la récupération de tous les produits, un produit spécifique, la création, la mise à jour et la suppression de produits via des appels HTTP GET, POST, PUT, DELETE.

#### 5. **ProductService.java** (Service métier pour les produits)
- **ProductService** : Cette classe contient la logique métier pour la gestion des produits, comme appeler le dépôt pour persister, mettre à jour ou supprimer des produits.
- **@Transactional** : Cette annotation sur les méthodes de création, de mise à jour et de suppression garantit que ces opérations sont effectuées dans une transaction de base de données.

#### 6. **application.properties** (Fichier de configuration Quarkus)
- Configuration de Quarkus pour utiliser une base de données H2 en mémoire avec Hibernate ORM pour gérer la persistance des entités.
- Kafka est configuré avec SmallRye Reactive Messaging, et les événements sont consommés à partir du topic `stock-increase`.

#### 7. **import.sql** (Script SQL d'import de données)
- Insertion de produits initiaux dans la base de données avec un redémarrage de la séquence d'ID à 4 pour les futures insertions.

#### 8. **docker-compose.yml** (Configuration Docker Compose pour Kafka et Kong)
- **Services Kafka et Zookeeper** : Kafka est configuré pour gérer la messagerie asynchrone avec Zookeeper comme système de coordination.
- **Service Kong** : Utilisé comme API Gateway pour gérer l'authentification, le routage et la gestion de services.

---

### Prochain projet avec Quarkus et Kafka : Gestion des Organisations

#### Conception d'un nouveau projet microservices

**Objectifs** :
- 3 microservices : Un pour la gestion des employés, un pour les départements, et un pour les organisations.

#### Exemple d'architecture avec Quarkus et Kafka :

1. **Employee Service** :
   - **Entité Employee** : contient `name`, `age`, `position`, `organization`, `department`, et un flag `isDepartmentless`.
   - **API CRUD** : pour gérer les employés.
   - **Kafka Consumer** : écoute les événements de suppression de département ou d'organisation pour mettre à jour le statut `isDepartmentless` et `isOrganizationless`.

2. **Department Service** :
   - **Entité Department** : contient `name`, `organization`, et un flag `isOrganizationless`.
   - **API CRUD** : pour gérer les départements.
   - **Kafka Producer** : Envoie des événements lorsque des départements sont supprimés pour mettre à jour les employés.

3. **Organization Service** :
   - **Entité Organization** : contient `name`, `address`, `numberOfEmployees`, `lastModified`.
   - **API CRUD** : pour gérer les organisations et récupérer les employés d'une organisation.
   - **Kafka Producer** : Envoie des événements lors de la suppression d'une organisation.

Ces microservices utiliseront Quarkus pour l'API, Kafka pour la communication entre services, et une base de données relationnelle pour persister les entités.

---

En résumé, cette nouvelle application utilisera un pattern microservices basé sur Quarkus et Kafka pour assurer la gestion des employés, des départements, et des organisations avec des opérations CRUD et des mises à jour synchronisées grâce à Kafka.


# Organization Service

## Prérequis

- Java 11
- Maven
- Docker
- Kafka et Zookeeper (pour Kafka)

## Compilation du projet

```bash
mvn clean package

Configuration de Kafka
Le service Kafka est configuré pour publier et consommer des événements concernant les organisations sur le topic organization-topic. Vous pouvez modifier les configurations Kafka dans le fichier application.properties.

Points d'API
GET /organizations : Récupère la liste des organisations.
POST /organizations : Crée une nouvelle organisation.
PUT /organizations/{id} : Met à jour une organisation.
DELETE /organizations/{id} : Supprime une organisation.

# Department Service

## Prérequis

- Java 11
- Maven
- Docker
- Kafka et Zookeeper (pour Kafka)

## Compilation du projet

```bash
mvn clean package

Configuration de Kafka
Le service Kafka est configuré pour publier et consommer des événements concernant les départements sur le topic department-topic. Vous pouvez modifier les configurations Kafka dans le fichier application.properties.

Points d'API
GET /departments : Récupère la liste des départements.
POST /departments : Crée un nouveau département.
PUT /departments/{id} : Met à jour un département.
DELETE /departments/{id} : Supprime un département.

