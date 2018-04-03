package service;

import com.mongodb.*;
import model.MongoDBObject;

import java.net.UnknownHostException;

public class DBServiceImpl implements DBService {
    private static MongoClient mongoClient;
    private static  DB db;
    static{

        try {
            mongoClient = new MongoClient( "localhost" , 27017 );
            db = mongoClient.getDB("database name");
            boolean auth = db.authenticate("username", "password".toCharArray());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Class<T extends MongoDBObject> insertObject(Class<T> obj) {
        return null;
    }

    @Override
    public Class<T> deleteObjectById(int id) {
        return null;
    }

    @Override
    public Class<T> selectObjectById(int id) {
        return null;
    }

    @Override
    public int updateObject(int id, Class<T> obj) {
        return 0;
    }
}
