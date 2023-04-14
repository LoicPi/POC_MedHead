# POC_MedHead
[[![Pipeline CI/CD](https://github.com/LoicPi/POC_MedHead/actions/workflows/pipeline.yml/badge.svg)](https://github.com/LoicPi/POC_MedHead/actions/workflows/pipeline.yml)  [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=LoicPi_POC_MedHead&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=LoicPi_POC_MedHead)

Ce repository est une Proove Of Concept (POC) pour vérifier la faisabilité d'une création d'un service emergency pour trouver l'hôpital le plus proche d'une position donnée avec un lit disponible et une spécialité précise.

## Prérequis au projet
Afin de pouvoir exécuter l'application sur votre poste, vous devez d'abord installer les dépendances suivantes :
* JVM version 17
* Apache Maven 3.9.1
* Apache JMeter 5.5 (pour les tests de charge)

## Executer le projet
### Installation
Pour installer et utiliser le projet merci de mettre tous les documents dans le même dossier de votre choix.
1. Télécharger les jars des différents micro services :
   * Aller dans l'onglet **Actions**
   * Dans la barre de navigation gauche, cliquer sur **Pipeline CI/CD**
   * Dans la liste des workflow runs cliquer sur la dernière exécution
   * Dans la partie *Artifacts*, cliquer sur **github-actions-artifact**
2. Télécharger le jar de Graphhopper [ici](https://repo1.maven.org/maven2/com/graphhopper/graphhopper-web/7.0/graphhopper-web-7.0.jar)
3. Télécharger le fichier de config de Graphhopper [ici](https://raw.githubusercontent.com/graphhopper/graphhopper/6.x/config-example.yml)
4. Télécharger la dernière carte valide d'Angleterre de Geofabrik [ici](https://download.geofabrik.de/europe/great-britain/england-latest.osm.pbf)

### Execution
1. Dans le dossier où se trouvent l'ensemble des jars, ouvrir 5 terminaux (un pour chaque fichier jar) et lancer les commandes suivantes :
```bash
java -jar ms-emergency-0.0.1-SNAPSHOT.jar
java -jar ms-hospital-0.0.1-SNAPSHOT.jar
java -jar ms-bedavaible-0.0.1-SNAPSHOT.jar
java -jar ms-authorization-0.0.1-SNAPSHOT.jar
java -Ddw.graphhopper.datareader.file=england-latest.osm.pbf -jar *.jar server config-example.yml
``` 
2. Dans un autre terminal, lancer la requête suivant pour obtenir un token d'authentification :
```bash
curl --location --request POST 'http://localhost:9004/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "emergency",
    "password": "password"
}'
```
3. Lancer la requête suivante en veillant à mettre :
    * le token récupéré après `Bearer` dans le `header`
    * la latitude et la longitude de l'urgence et de la spécialité recherché ([tableau des spécialités](https://github.com/LoicPi/MedHead_Architecture/blob/main/External%20Reference/Donne%CC%81es_de_re%CC%81fe%CC%81rence_sur_les_spe%CC%81cialite%CC%81s_NHS.pdf)) dans le `data-raw`
```bash
curl --location --request GET 'http://localhost:9001/emergency' \
--header 'Authorization: Bearer token' \
--header 'Content-Type: application/json' \
--data-raw '{
    "latitude": "XX.XXXXXXX",
    "longitude": "X.XXXXXXX",
    "speciality":"Spécialité"
}'
```
Vous aurez alors les coordonnées et le nom de l'hôpital le plus proche.

## Tests
### Description
Des tests unitaires et d'intégration ont été produits pour le projet, ils permettent de s'assurer que l'ensemble des fonctionnalités du code soit testé.  
Des tests de charge ont été également fait pour vérifier la capacité de l'api d'urgence.

### Lancement des tests unitaires et d'intégrations
Pour le lancement des tests :
1. Cloner le repository git
2. A la racine du projet ouvrir un terminal
3. Lancer la commande suivante :
```bash
mvn surefire-report:report
```
4. Pour voir le rapport, aller dans le dossier POC_MedHead/target/site et cliquer sur le fichier **surefire-report.html**
### Lancement des tests de charge
Pour lancer les tests de charge :
1. Lancer JMeter sur votre poste
2. Installer les plugins suivants dans JMeter :  
   * *Throughput Shaping Timer*
   * *5 Additional Graphs*
3. Ouvrir le fichier `POC_MedHead/JMeter/Test Charge POC_MedHead.jmx`
4. Lancer les différents microservices (Voir la section : Executer le Projet/Executer/1.)
5. Démarrer le test


## Pipeline
### Description
La pipeline mise en place sur le repository permet de :
1. Lancer tous les tests unitaires et d'intégrations du projet
2. Lancer un scan du code via sonarcloud pour vérifier la qualité du code
3. Construire un jar de chacun des microservices téléchargeable dans l'artifacts
### Lancement de la pipeline
1. Aller à la page principal du repository
2. Cliquer sur **Actions**
3. Dans la barre de navigation gauche, cliquer sur **Pipeline CI/CD**
4. Dans la liste des workflow runs cliquer sur la dernière exécution
5. En haut à droite, cliquer sur le bouton **Re-Run all jobs**
6. Dans la pop-up qui s'ouvre cliquer sur le bouton vert **Re-run jobs**
### Les résultats
1. Pour voir les résultats des tests unitaires et d'intégration :
   * Aller dans l'onglet **Actions**
   * Dans la barre de navigation gauche, cliquer sur **Pipeline CI/CD**
   * Dans la liste des workflow runs cliquer sur la dernière exécution
   * Dans la partie *Artifacts*, cliquer sur **Tests**
2. Pour voir les résultats de la qualité du code :
   * [Sonar Cloud](https://sonarcloud.io/project/overview?id=LoicPi_POC_MedHead)  
3. Pour voir les artifacts générés par la pipeline :
   * Aller dans l'onglet **Actions**
   * Dans la barre de navigation gauche, cliquer sur **Pipeline CI/CD**
   * Dans la liste des workflow runs cliquer sur la dernière exécution
   * Dans la partie *Artifacts*, cliquer sur **github-actions-artifact**







