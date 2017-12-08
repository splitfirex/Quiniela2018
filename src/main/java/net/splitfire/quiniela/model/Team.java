package net.splitfire.quiniela.model;

public class Team {

    private String name;
    private String shotName;
    private String flagUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShotName() {
        return shotName;
    }

    public void setShotName(String shotName) {
        this.shotName = shotName;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public Team(String name, String shotName, String flagUrl) {
        this.name = name;
        this.shotName = shotName;
        this.flagUrl = flagUrl;
    }

    @Override
    public String toString() {
        return "Team {" +
                "name='" + name + '\'' +
                ", shotName='" + shotName + '\'' +
                ", flagUrl='" + flagUrl + '\'' +
                '}';
    }
}
