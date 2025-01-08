# Projet-Chat-Microservices

Ce projet est une application de chat basée sur une architecture de microservices. Chaque microservice a une responsabilité spécifique et communique avec les autres microservices pour fournir les fonctionnalités complètes de l'application.

## Structure du projet

- [`aller`](aller )
- [`auth`](auth )
- [`client`](client )
- [`message_channel`](message_channel )
- [`message_privee`](message_privee )
- [`retour`](retour )

### Microservices

#### 1. Auth Service ([`auth`](auth ))

Le service d'authentification gère l'enregistrement des utilisateurs, la connexion et la validation des tokens. Il fournit des endpoints REST pour ces opérations.

- **Endpoints principaux :**
  - [`POST /auth/register`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Enregistrer un nouvel utilisateur.
  - [`POST /auth/login`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Connecter un utilisateur existant.
  - [`GET /auth/validate`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/GET.class ) : Valider un token d'authentification.

#### 2. Message Service ([`aller`](aller ))

Le service de messages gère l'envoi de messages privés et de messages de canal. Il utilise le service d'authentification pour valider les tokens et les services de message pour envoyer les messages.

- **Endpoints principaux :**
  - [`POST /MessageService/sendPrivateMessage`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Envoyer un message privé.
  - [`POST /MessageService/sendChannelMessage`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Envoyer un message dans un canal.
  - [`POST /MessageService/joinChannel`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Rejoindre un canal.
  - [`POST /MessageService/leaveChannel`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Quitter un canal.

#### 3. Client ([`client`](client ))

Le client est une application qui permet aux utilisateurs d'interagir avec les services de chat via une interface utilisateur. Il utilise des appels SOAP pour communiquer avec le service de messages.

- **Fonctionnalités principales :**
  - S'inscrire et se connecter.
  - Envoyer des messages privés et de canal.
  - Rejoindre et quitter des canaux.

#### 4. Message Channel Service ([`message_channel`](message_channel ))

Le service de canal de messages gère les messages envoyés dans les canaux. Il utilise Kafka pour diffuser les messages aux membres du canal.

- **Endpoints principaux :**
  - [`POST /channel/join`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Rejoindre un canal.
  - [`POST /channel/leave`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Quitter un canal.
  - [`POST /channel/message`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Envoyer un message dans un canal.
  - [`GET /channel/members`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/GET.class ) : Obtenir la liste des membres d'un canal.

#### 5. Private Message Service ([`message_privee`](message_privee ))

Le service de messages privés gère l'envoi de messages privés entre utilisateurs. Il utilise Kafka pour envoyer les messages aux destinataires.

- **Endpoints principaux :**
  - [`POST /private-messages`](/jakarta.ws.rs-api-3.1.0.jar/jakarta.ws.rs/POST.class ) : Envoyer un message privé.

#### 6. Retour Service ([`retour`](retour ))

Le service de retour gère la réception des messages Kafka et les envoie aux utilisateurs connectés via des sockets TCP.

- **Fonctionnalités principales :**
  - Consommer les messages Kafka et les envoyer aux utilisateurs via des sockets.
  - Gérer les connexions des utilisateurs et la validation des tokens.

## Configuration de Kafka

Pour que les services fonctionnent correctement, vous devez créer les topics Kafka suivants :

- `private-messages` : Utilisé par le service de messages privés pour envoyer des messages privés.
- `channel-messages` : Utilisé par le service de canal de messages pour envoyer des messages de canal.

## Installation et Déploiement

### Prérequis

- Java 21
- Maven
- Kafka
- Un serveur d'application (Tomcat, par exemple)

### Instructions

1. Cloner le dépôt.
2. Naviguer dans chaque répertoire de microservice et exécuter `mvn clean install`.
3. Déployer les fichiers WAR générés sur votre serveur d'application.
4. Configurer Kafka et démarrer les services.

## Utilisation

### Enregistrement et Connexion

1. Utiliser le client pour s'inscrire et se connecter.
2. Obtenir un token d'authentification après la connexion.

### Envoi de Messages

1. Utiliser le token pour envoyer des messages privés ou de canal via le client.
2. Rejoindre ou quitter des canaux selon les besoins.

### Réception de Messages

1. Les messages envoyés seront reçus via les sockets TCP gérés par le service de retour.

## Contributions

Les contributions sont les bienvenues. Veuillez soumettre des pull requests ou ouvrir des issues pour toute amélioration ou bug.

## Licence

Ce projet est sous licence MIT. Voir le fichier [`LICENSE`](LICENSE ) pour plus de détails.

---