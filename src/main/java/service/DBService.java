package service;

import model.MongoDBObject;
import model.Player;

public interface DBService {

    <T extends MongoDBObject> insertObject(Class<T> obj);

    <T extends MongoDBObject> deleteObjectById(int id);

    <T extends MongoDBObject> selectObjectById(int id);

    int updateObject(int id, Class<T> obj);
}
