package net.splitfire.quiniela.service;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InitialContext;
import java.util.Iterator;

@Service("dbservice")
@Transactional
public class DBServiceImpl implements DBService {

    static MongoClient mongoClient;
    static MongoDatabase db;

    static {
        try {
            InitialContext initialContext = new InitialContext();
            mongoClient = (MongoClient) initialContext.lookup("java:global/MyMongoClient");
            db =  mongoClient.getDatabase("quiniela2018");
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public MongoDatabase getDBInstance() {
        return db;
    }

    public static boolean existsCollection(final String collectionName) {
        Iterator<String> collectionNames = db.listCollectionNames().iterator();
        while (collectionNames.hasNext()) {
            if (collectionNames.next().equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }
    private static void init(){

        if(!existsCollection("users")){
            db.createCollection("users");
        };

        if(!existsCollection("userMatches")){
            db.createCollection("userMatches");
        };

        if(!existsCollection("matches")){
            db.createCollection("matches");
        };

        if(!existsCollection("matchesResult")){
            db.createCollection("matchesResult");
        };
    }
}
