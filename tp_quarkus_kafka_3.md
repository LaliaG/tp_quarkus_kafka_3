### Petit projet en quarkus et kafka : 

#### Ce qu'il faut faire : 

- Une application faite avec Quarkus qui fonctionnera sous forme d'API.
- Elle permettra de gérer des organisations composées de departements qui sont elles-même composées d'employée.
- L'information concerant l'organisation est aussi contenue dans les données employées.
- Il faut créer une application composé de 3 microservices quarkus.
- Il y aura un microservice qui gerera chaque entité.

Un employée -> nom, age, position (poste), organisation et departement.
Un departement -> nom et organisation.
Une organisation -> nom, adress, nombre de salarié et date de d'arrivée ou départ d'un salarié.

- En plus d'un CRUD pour chacun des microservices, il faudra rajouter les options suivantes : 
  - Je veux être capable de récupérer les employées d'un departement ou d'une organisation.
  - Si je supprime une organisation, les departements de l'organisation basculent avec un status sans organisation (isOrganisationless à true, par exemple.), idem pour les employees.
  - Même chose quand c'est un departement qui est supprimé, l'employeé a toujours l'organisation mais n'a pas departement (isDepartmentLess).
  - Je veux être capable de récupérer les salariés sans departement ou sans organisation.
  - Quand un employée est rajouté ou s'il quitte une organisation (delete), le total des salariés de cette organisation est modifié et aussi la date de cette modification doit evoluée.