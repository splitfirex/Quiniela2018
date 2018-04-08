package quiniela.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "tournament")
public class Tournament {

    private long id;
    private String name;
    private String idAdmin;

    private List<String> listPlayers = new ArrayList<String>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public List<String> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(List<String> listPlayers) {
        this.listPlayers = listPlayers;
    }
}
