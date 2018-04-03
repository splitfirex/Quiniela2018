package model;

import model.enums.TeamName;

public class Team extends MongoDBObject{

    private String name;
    private String shortName;
    private String group;
    private String flagUrl;

    public Team(String name, String shortName, String group ,String flagUrl) {
        this.name = name;
        this.shortName = shortName;
        this.flagUrl = flagUrl;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}

