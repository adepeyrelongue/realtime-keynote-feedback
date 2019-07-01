# realtime-keynote-feedback-2

Realtime keynote feedback est une application Web qui permet d'analyser l'attention d'un auditoire à partir de vidéos.
L'application propose un reporting détaillée de l'évolution de l'attention de l'auditoire.

Décomposition technique du projet :
- Un client (Vue.js)
- Une API serveur (Spring, OpenCV)

Comment installer le projet : 
- Installer IntelliJ
- Installer Wampserver 
- Wamp --> PHPMyAdmin --> Créer une nouvelle base nommée realtime_keynote
- Créez un dossier sur votre système --> "C:\data-video" c'est dans ce dossier que les vidéos sont uploadées, traitées, puis supprimées (sinon modifiez la valeur de ce champs dans la variable statique correspondante qui se situe dans [ce fichier](https://github.com/Miage-Paris-Ouest/realtime-keynote-feedback-2/blob/master/src/main/java/miage/nanterre/m1app/realtimekeynote/Service/UploadService.java)
- Installer node et npm

Pour accéder à la base de donnée : 
- localhost --> PhpMyAdmin 

Lancez le backend :
- Ouvrez le projet à la racine dans IntelliJ
- Installez les dépendances Maven
- Modifiez les propriétées de base de donnée selon votre configuration de serveur MySQL dans "application.properties" : 
- spring.datasource.url=jdbc:mysql://localhost:3306/realtime_keynote?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC
- spring.datasource.username=root
- spring.datasource.password=root
- Lancez le serveur ce dernier écoute sur le port 8082

Lancez le frontend :
- Allez dans src/frontend
- Dans un terminal --> *npm install* (installez les dépendances)
- Pour lancer l'application client -->  *npm run serve*
- Le port d'écoute s'affiche dans le terminal (généralement 8080)

En dev : Après avoir exécuté le projet, vous devrez aller dans le fichier [application.properties](https://github.com/Miage-Paris-Ouest/realtime-keynote-feedback-2/tree/master/src/main/resources/application.properties) qui est dans *ressources* et :
- commenter : spring.jpa.hibernate.ddl-auto = create
- Décommenter: spring.jpa.hibernate.ddl-auto=update

