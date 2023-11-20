# Group 7

CITY DISASTER RESPONSE APP

## Project structure: (These are only thoughts/ideas at the moment)
docker-compose.yml is for local deployment for development purposes. This way changes can be tested 
quickly without setting up an entire kubernetes cluster. No docker internal ips or ports should be used. 
(expose all ports and use localhost and exposed ports instead of docker hostnames) This makes it possible 
for individual microservices in the docker-compose stack to be shut down and executed via the IDE for 
debugging instead.<br/>
<br/>
infrastructure contains terraform to set up the cloud infrastructure and helm charts for all microservices, as
well as external services. It makes sense to have all Helm charts in this location, as infrastructure and 
application logic should be separated from each other. Changes that affect the ports of an application/database, 
for example, and therefore affect the infrastructure, should therefore be made centrally.  <br/>
<br/>
frontend contains our react native frontend application<br/>
<br/>
external content is described in its own README in the folder. It is relevant for databases, grafana, 
kafka and zookeeper.<br/>
<br/>
Every microservice is a submodule of the services folder/module.<br/>
<br/>
Ports and ips of databases, other services as well as other configuration parameters should be stored in
environment variables. This way, the application can be easily deployed both to the cloud and locally.
### Idea behind using gradle for all modules:
Using gradle should make ci significantly easier, since running simple gradle commands should be sufficient
for each phase. (./gradlew test) Using gradle should make ci significantly easier, since running simple gradle 
commands should be sufficient for each phase. (./gradlew test) It also ensures that Modules are built in the 
correct order, which might be helpful if modules depend on each other during the build process in any way. 
(shared module for common data classes etc.) It also offers to easily share versions e.g. node versions between
different modules.
