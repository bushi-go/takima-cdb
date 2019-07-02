# takima-cdb
Takima Computer Database REST application

This app is an attempt at an api-driven application, using Spring HATEOAS (spring-boot-starter-hateoas) v2.2.0M1 and Angular V8

Download the code, and run with the following : docker-compose -f docker-compose-dev.yml
Then go to localhost:3000 to see the app :)

Beware of the following : 

 - you need docker
 - if you are on Windows, you'll need to share your drive with docker ; i use volumes on ui container for hot-reload and db server for persistence
 - the first install will be long. Grab a coffee, and chill out while maven download the world. Following build have most of maven dependencies cached by docker, so it get way faster
