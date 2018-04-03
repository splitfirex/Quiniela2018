package service;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public interface DBService {

   static DBService instance = new DBServiceImpl();

   DB getDBConnection();

   DBCollection getDBCollection(String name);

}
