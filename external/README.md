This folder contains configuration files for all external software deployed and used by us. 
This includes databases, to ensure vendor neutrality and avoid costs.
Dockerfiles can be used to adjust exposed ports, and configurations .... 
this might not be needed for all external software we use but having this structure ensures 
that we can quickly make changes even on the Docker image level without making any changes anywhere else.
<br/><br/>
It also ensures that we are automatically using the same images for both local testing and production.
