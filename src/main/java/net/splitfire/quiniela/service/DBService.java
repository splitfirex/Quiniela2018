package net.splitfire.quiniela.service;

import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;

public interface DBService {

    MongoDatabase getDBInstance();

}
