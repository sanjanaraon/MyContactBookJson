package com.myContactBookJson.repository;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MyMongoTemplate {
    static MongoOperations mongoOperations;
    static MongoClient mongo;
    static String DataBase = "contactBook";

    public static MongoOperations getMongoConnection() {
        mongo = new MongoClient("localhost", 27017);
        mongoOperations = new MongoTemplate(mongo, DataBase);
        return mongoOperations;
    }
}
