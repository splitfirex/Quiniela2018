package quiniela.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "team")
public class Team{

    private long id;
    private String name;
    private String shortName;
    private String group;
    private String flagUrl;

    public Team(int id, String name, String shortName, String group ,String flagUrl) {
        this.name = name;
        this.shortName = shortName;
        this.flagUrl = flagUrl;
        this.id = id;
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

