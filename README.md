1.	Pre-requisite

a.	Mong DB
Mongo db Server should be running on the machine. The default URK is “localhost:27071” then no need to do anything. Otherwise change the values in application properties file. 

1.	/customerquery/src/main/resources/application.properties
2.	/addcustomercommand/src/main/resources/application.properties

Following properties needs to be changed
#mongodb
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=onboarding_query

#mongodb for eventstore
axon.event.mongodb.host=localhost
axon.event.mongodb.port=27017
axon.event.mongodb.database=onboarding
axon.event.mongodb.domaineventstore=domainevents
axon.event.mongodb.snapshoteventstore=snapshotevents

b.	Active MQ
Active MQ broker should be running on the machine. The default URL  is tcp://localhost:61616 then no need to do anything. Otherwise change the values in application properties file. 

3.	/customerquery/src/main/resources/application.properties
4.	/addcustomercommand/src/main/resources/application.properties

Following properties needs to be changed
#Active MQ
activemq.queue.name=onboarding.queue
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=admin
spring.activemq.in-memory=true
spring.activemq.pool.enabled=false

Running the application
Command Component
To run the command component execute the com.capgemini.customeronboarding.CreatecustomerApplication class.
If everything goes fine the application will be published on http://localhost:8080/.
Open this URL to add/update a customer.
Query Component
To run the query component execute the com.capgemini.customeronboarding.CreatecustomerApplication class.
If everything goes fine the application will be published on http://localhost:8180.
Open this URL to search a customer. Firstname, lastname and mobileno are mandatory and case sensitive.

