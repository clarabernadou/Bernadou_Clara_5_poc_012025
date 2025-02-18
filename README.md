# Your Car Your Way

## Cloner le projet

Clonez ce dépôt GitHub pour obtenir une copie locale du projet.

```bash
git clone https://github.com/clarabernadou/Bernadou_Clara_5_poc_012025.git
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