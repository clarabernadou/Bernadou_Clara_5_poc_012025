# Your Car Your Way

## Cloner le projet

Clonez ce dépôt GitHub pour obtenir une copie locale du projet.

```bash
git clone https://github.com/clarabernadou/Bernadou_Clara_5_poc_012025.git
```

---

## Base de données

### Créer et configurer la base de données

1. **Créer la base de données** :

   Pour commencer, vous devez créer la base de données `your_car_your_way` dans votre serveur MySQL. Exécutez les commandes SQL suivantes :

   ```sql
   CREATE DATABASE your_car_your_way;
   USE your_car_your_way;
   ```

2. **Créer les tables** :

   Exécutez les commandes SQL suivantes pour créer les tables nécessaires à l'application :

   ```sql
   -- Table des utilisateurs
   CREATE TABLE users (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       first_name VARCHAR(255) NOT NULL,
       last_name VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );

   -- Table des conversations
   CREATE TABLE conversations (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       user1_id BIGINT NOT NULL,
       user2_id BIGINT NOT NULL,
       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       CONSTRAINT fk_user1 FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE,
       CONSTRAINT fk_user2 FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE,
       CONSTRAINT unique_conversation UNIQUE (user1_id, user2_id)
   );

   -- Table des messages
   CREATE TABLE messages (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       user_id BIGINT NOT NULL,
       conversation_id BIGINT NOT NULL,
       content TEXT NOT NULL,
       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       CONSTRAINT fk_message_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
       CONSTRAINT fk_message_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
   );
   ```
   
---

## Configuration de la base de données dans l'application

Assurez-vous de mettre à jour la configuration de la base de données dans votre application backend en fonction des paramètres de connexion appropriés pour votre serveur MySQL.

Dans le fichier `application.properties`, configurez les propriétés de connexion comme suit :

```properties
spring.application.name=ycyw
spring.datasource.url=jdbc:mysql:/<your_host>/your_car_your_way?useSSL=true&requireSSL=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## Frontend

### Étapes pour démarrer le frontend

1. **Accédez au répertoire `frontend`** :
   
   ```bash
   cd frontend/
   ```

2. **Installez `ngx-cookie-service` version 16.0.0** :

   ```bash
   sudo npm install ngx-cookie-service@16.0.0
   ```

3. **Installez les autres dépendances** :

   ```bash
   sudo npm install
   ```

4. **Lancez le serveur de développement** :

   ```bash
   ng serve
   ```

   L'application sera disponible sur [http://localhost:4200](http://localhost:4200).

---

## Backend

### Étapes pour démarrer le backend

1. **Accédez au répertoire `backend`** :

   ```bash
   cd backend/
   ```

2. **Lancez le serveur Spring Boot** :

   ```bash
   mvn spring-boot:run
   ```

   Le backend sera disponible par défaut sur [http://localhost:8080](http://localhost:8080).

---

## Technologies utilisées

- **Frontend** : Angular
- **Backend** : Spring Boot, Maven
- **Base de données** : MySQL

---

## Notes

- Assurez-vous d'avoir installé [Node.js](https://nodejs.org/) et [npm](https://www.npmjs.com/) pour le frontend.
- Pour le backend, vous devez avoir [Maven](https://maven.apache.org/) installé.
- Si vous rencontrez des erreurs lors de l'installation des dépendances, essayez de vider le cache de npm avec `npm cache clean --force`.
