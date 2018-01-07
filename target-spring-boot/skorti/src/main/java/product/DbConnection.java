/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author sei
 */
public class DbConnection {
    //private final String connectionUri = "mongodb://localhost:27017";
    private final String DATABASE_NAME = "myretail";
    private final String PRICING_COLLECTION_NAME = "pricing";
    private final String TARGET_CASE_STUDY_CONNECT_STRING = "mongodb://sei:Rowenad0@cluster0-shard-00-00-ejrjn.mongodb.net:27017,cluster0-shard-00-01-ejrjn.mongodb.net:27017,cluster0-shard-00-02-ejrjn.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin";
    final private MongoClientURI connectionString;
    final private MongoClient mongoClient;
    final private  MongoDatabase database;
    private static DbConnection instance;
             
     private DbConnection(){
      connectionString = new MongoClientURI(TARGET_CASE_STUDY_CONNECT_STRING);
      mongoClient = new MongoClient(connectionString);
      database = mongoClient.getDatabase(DATABASE_NAME);
     }
     
     public static DbConnection getInstance(){
         if(instance == null){
             instance = new DbConnection();
         }
         return instance;
     }
     
     public MongoDatabase getDatabase(){
         return database;
     }
     
     public MongoCollection<Document> getCollection(String name){
         return database.getCollection(name);
     }
     
     public MongoCollection<Document> getPricingCollection(){
         return database.getCollection(PRICING_COLLECTION_NAME);
     }
     
}
