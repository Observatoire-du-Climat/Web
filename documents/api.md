
# Documentation de l'API REST

Cette documentation présente les différents endpoints exposés par l'application ainsi que leur fonctionnement.

## Utilisateurs

### Création d'un utilisateur

```http
POST /api/regsiter
```

La requête nécessite un objet JSON avec les propriétés suivantes:
- `name` : le nom de l'utilisateur
- `email` : l'adresse email de l'utilisateur
- `password` : le mot de passe de l'utilisateur

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique de l'utilisateur
- `name` : le nom de l'utilisateur
- `email` : l'adresse email de l'utilisateur
- `isValid` : indique si l'utilisateur est validé

Codes de réponse :
- `201 Created` : l'utilisateur a été créé avec succès
- `400 Bad Request` : une erreur c'est produire lors de la création

### Authentification d'un utilisateur

```http
POST /api/login
```

La requête nécessite un objet JSON avec les propriétés suivantes:
- `email` : l'adresse email de l'utilisateur
- `password` : le mot de passe de l'utilisateur

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique de l'utilisateur
- `name` : le nom de l'utilisateur
- `email` : l'adresse email de l'utilisateur
- `isValid` : indique si l'utilisateur est validé

Codes de réponse :
- `200 OK` : authentification réussie
- `400 Bad Request` : une erreur c'est produire lors de l'authentification
- `401 Unauthorized` : les identifiant sont incorrects


### Récupérer les informations d'un utilisateur spécifique

```http
GET /api/users/{id}
```

La requête doit contenir l'identifiant de l'utilisateur dans son chemin.

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique de l'utilisateur
- `name` : le nom de l'utilisateur
- `email` : l'adresse email de l'utilisateur
- `isValid` : indique si l'utilisateur est validé

Codes de réponse :
- `200 OK` : les informations sont retournées avec succès
- `400 Bad Request` : une erreur c'est produire lors de la récupération
- `404 Not Found` : aucun utilisateur ne possède cet identifiant


### Modifier les informations d'un utilisateur

```http
PUT /api/users/{id}
```

La requête doit contenir l'identifiant de l'utilisateur dans son chemin et nécessite un objet JSON avec les propriétés suivantes:
- `name` : le (nouveau) nom de l'utilisateur
- `email` : la (nouvelle) adresse email de l'utilisateur
- `password` : le (nouveau) mot de passe de l'utilisateur

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique de l'utilisateur
- `name` : le nom de l'utilisateur
- `email` : l'adresse email de l'utilisateur
- `isValid` : indique si l'utilisateur est validé

Codes de réponse :
- `200 OK` : les informations sont retournées avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

### Valider un compte utilisateur

```http
PUT /api/users/{id}/validate
```

La requête doit contenir l'identifiant de l'utilisateur dans son chemin.

Codes de réponse :
- `200 OK` : l'utilisateur a été validé avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `404 Not Found` : aucun utilisateur ne possède cet identifiant


## Mesures

### Récupérer toutes les mesures d'un utilisateur

```http
GET /api/measures/user/{id}
```

La requête doit contenir l'identifiant de l'utilisateur dans son chemin.

En cas de réussite, le serveur répond avec une liste d'objets JSON qui contiennent :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé

Codes de réponse :
- `200 OK` : les mesures sont retournées avec succès
- `400 Bad Request` : une erreur c'est produire lors de la récupération
- `404 Not Found` : aucun utilisateur ne possède cet identifiant


### Récupérer une mesure en détails

```http
GET /api/measures/{id}
```

La requête doit contenir l'identifiant de la mesure dans son chemin.

En cas de réussite, le serveur répond avec une liste d'objets JSON qui contiennent :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
  Ainsi que les valeur spécifique du type de mesure :
- température :
    - `degree` : la température en degré Celsius du relevé
- hauteur des neiges :
    - `height` : la hauteur du manteau neigeux en centimètres
    - `weather` : la condition météorlogique lors du relevé
    - `precipitation` : les précipitations de la journée en millimètres
- migration des oiseaux :
    - `specie` : l'espèce d'oiseaux observée
    - `event` : l'événement observé (arrivée ou départ)
- relevé des pontes :
    - `number` : le nombre de pontes observés

Codes de réponse :
- `200 OK` : les informations détaillées de la mesure sont retournées avec succès
- `400 Bad Request` : une erreur c'est produire lors de la récupération
- `404 Not Found` : aucune mesure ne possède cet identifiant

### Supprimer une mesure

```http
DELETE /api/users/{id}
```

La requête doit contenir l'identifiant de la mesure.

Codes de réponse :
- `204 No Content` : la mesure a été supprimée avec succès
- `404 Not Found` : aucune mesure ne possède cet identifiant


## Température

### Création d'une mesure de température

```http
POST /api/measures/temperature
```

La requête est envoyée au format `multipart/form-data` et contient les éléments suivants :
- `request` : objet JSON contenant les informations du relevé
- `picture` : photo associée au relevé (optionnelle)
  L'objet `request` possède les propriétés suivantes :
- `userId` : identifiant de l'utilisateur
- `date` : date du relevé
- `location` : lieu de l'observation.
- `degree` : température relevée.

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `degree` : la température en degré Celsius observée

Codes de réponse :
- `201 Created` : la mesure a été créée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `403 Forbidden` : l'utilisateur n'a pas été validé.
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

### Modifier une mesure de température

```http
PUT /api/measures/temperature/{id}
```

La requête doit contenir l'identifiant de la mesure dans son chemin et nécessite un objet JSON avec les propriétés suivantes:
- `date` : la (nouvelle) date du relevé
- `location` : le (nouveau) lieu du relevé
- `degree` : la (nouvelle) température en degré Celsius observée

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `degree` : la température en degré Celsius observée

Codes de réponse :
- `200 OK` : la mesure est modifiée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

## Hauteur des neiges

### Création d'une mesure de hauteur des neiges

```http
POST /api/measures/snow-height
```

La requête est envoyée au format `multipart/form-data` et contient les éléments suivants :
- `request` : objet JSON contenant les informations du relevé
- `picture` : photo associée au relevé (optionnelle)
  L'objet `request` possède les propriétés suivantes :
- `userId` : identifiant de l'utilisateur
- `date` : date du relevé
- `location` : lieu de l'observation.
- `height` : la hauteur du manteau neigeux en centimètres
- `weather` : la condition météorologique lors du relevé
- `precipitation` : les précipitations de la journée en millimètres

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `height` : la hauteur du manteau neigeux en centimètres
- `weather` : la condition météorologique lors du relevé
- `precipitation` : les précipitations de la journée en millimètres

Codes de réponse :
- `201 Created` : la mesure a été créée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `403 Forbidden` : l'utilisateur n'a pas été validé.
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

### Modifier une mesure de hauteur de neiges

```http
PUT /api/measures/snow-height/{id}
```

La requête doit contenir l'identifiant de la mesure dans son chemin et nécessite un objet JSON avec les propriétés suivantes:
- `date` : la (nouvelle) date du relevé
- `location` : le (nouveau) lieu du relevé
- `height` : la (nouvelle) hauteur du manteau neigeux en centimètres
- `weather` : la (nouvelle) condition météorologique lors du relevé
- `precipitation` : les (nouvelles) précipitations de la journée en millimètres

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `height` : la hauteur du manteau neigeux en centimètres
- `weather` : la condition météorologique lors du relevé
- `precipitation` : les précipitations de la journée en millimètres

Codes de réponse :
- `200 OK` : la mesure est modifiée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

## Migration des oiseaux

### Création d'une mesure de migration des oiseaux

```http
POST /api/measures/bird-migration
```

La requête est envoyée au format `multipart/form-data` et contient les éléments suivants :
- `request` : objet JSON contenant les informations du relevé
- `picture` : photo associée au relevé (optionnelle)
  L'objet `request` possède les propriétés suivantes :
- `userId` : identifiant de l'utilisateur
- `date` : date du relevé
- `location` : lieu de l'observation.
- `height` : la hauteur du manteau neigeux en centimètres
- `specie` : l'espèce d'oiseaux observée
- `event` : l'événement observé (arrivée ou départ)

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `specie` : l'espèce d'oiseaux observée
- `event` : l'événement observé (arrivée ou départ)

Codes de réponse :
- `201 Created` : la mesure a été créée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `403 Forbidden` : l'utilisateur n'a pas été validé.
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

### Modifier une mesure de migration des oiseaux

```http
PUT /api/measures/bird-migration/{id}
```

La requête doit contenir l'identifiant de la mesure dans son chemin et nécessite un objet JSON avec les propriétés suivantes:
- `date` : la (nouvelle) date du relevé
- `location` : le (nouveau) lieu du relevé
- `specie` : la (nouvelle) espèce d'oiseaux observée
- `event` : le (nouvel) événement observé (arrivée ou départ)

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `specie` : l'espèce d'oiseaux observée
- `event` : l'événement observé (arrivée ou départ)

Codes de réponse :
- `200 OK` : la mesure est modifiée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

Migration des oiseaux

## Relevé des pontes

### Création d'une mesure de relevé des pontes

```http
POST /api/measures/eggs-laying
```

La requête est envoyée au format `multipart/form-data` et contient les éléments suivants :
- `request` : objet JSON contenant les informations du relevé
- `picture` : photo associée au relevé (optionnelle)
  L'objet `request` possède les propriétés suivantes :
- `userId` : identifiant de l'utilisateur
- `date` : date du relevé
- `location` : lieu de l'observation.
- `height` : la hauteur du manteau neigeux en centimètres
- `number` : le nombre de pontes observés

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `author` : le nom de l'auteur du relevé
- `number` : le nombre de pontes observés

Codes de réponse :
- `201 Created` : la mesure a été créée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `403 Forbidden` : l'utilisateur n'a pas été validé.
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

### Modifier une mesure de relevé des pontes

```http
PUT /api/measures/eggs-laying/{id}
```

La requête doit contenir l'identifiant de la mesure dans son chemin et nécessite un objet JSON avec les propriétés suivantes:
- `date` : la (nouvelle) date du relevé
- `location` : le (nouveau) lieu du relevé
- `specie` : la (nouvelle) espèce d'oiseaux observée
- `number` : le (nouveau) nombre de pontes observés

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `id` : l'identifiant unique du relevé
- `date` : la date du relevé
- `location` : le lieu du relevé
- `type` : le type d'indicateur
- `number` : le nombre de pontes observés

Codes de réponse :
- `200 OK` : la mesure est modifiée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la modification
- `404 Not Found` : aucun utilisateur ne possède cet identifiant

## Photos

Migration des oiseaux

### Récupérer la photo d'une mesure

```http
GET /api/measures/{id}/picture
```

La requête doit contenir l'identifiant de la mesure dans son chemin.

En cas de réussite, le serveur répond avec un objet JSON qui contient :
- `path` : l'adresse de stockage de la photo

Codes de réponse :
- `200 OK` : la récupération a été effectuée avec succès
- `400 Bad Request` : une erreur c'est produire lors de la récupération
- `404 Not Found` : aucune photo n'existe pour cette mesure