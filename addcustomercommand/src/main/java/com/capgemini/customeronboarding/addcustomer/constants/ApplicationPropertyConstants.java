package com.capgemini.customeronboarding.addcustomer.constants;

public final class ApplicationPropertyConstants {

	private ApplicationPropertyConstants(){
		
	}
	
	/*** axon mongodb Properties **********/
	public static final String AXON_MONGO_DB_HOST = "axon.event.mongodb.host";
	public static final String AXON_MONGO_DB_HOST_DEFAULT = "localhost";

	public static final String AXON_MONGO_DB_PORT = "axon.event.mongodb.port";
	public static final int AXON_MONGO_DB_PORT_DEFAULT = 27017;

	public static final String AXON_MONGO_DB_DATABASE = "axon.event.mongodb.database";
	public static final String AXON_MONGO_DB_DATABASE_DEFAULT = "axonframework";

	public static final String AXON_MONGO_DB_DOMAIN_EVENT_STORE = "axon.event.mongodb.domaineventstore";
	public static final String AXON_MONGO_DB_DOMAIN_EVENT_STORE_DEFAULT = "domainevents";

	public static final String AXON_MONGO_DB_SNAPSHOT_EVENT_STORE = "axon.event.mongodb.snapshoteventstore";
	public static final String AXON_MONGO_DB_SNAPSHOT_EVENT_STORE_DEFAULT = "snapshotevents";
	
	public static final String AXON_MONGO_DB_USERNAME = "axon.event.mongodb.usernamme";
	public static final String AXON_MONGO_DB_PASSWORD = "axon.event.mongodb.password";
	
	/*** JGROUP Properties **********/
	public static final String JGROUP_CLUSTER_NAME = "jgroup.cluster.name";
	public static final String JGROUP_CONFIGURATION = "jgroup.configuration";
}
