package net.splitfire.quiniela.model;

import com.mongodb.BasicDBObject;

import java.util.List;

public class User {

    private String publicName;
    private String userName;
    private String id;
    private String password;

    public User(String publicName, String userName, String id, String password) {
        this.userName = userName;
        this.id = id;
        this.password = password;
        this.publicName = publicName;
    }

    @Override
    public String toString() {
        return "User{" +
                "publicName='" + publicName + '\'' +
                ", userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public BasicDBObject getDBObject(){
        BasicDBObject doc =
                new BasicDBObject("publicName", publicName)
                .append("userName", userName)
                .append("id", id)
                .append("password", password);
        return doc;
    }
}
