package quiniela.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@Document(collection = "playerGroup")
public class PlayerGroup {

    @Id
    private Long id;
    private Long idGroup;
    private Long idPlayer;
    private Long idLadder;

    private LinkedList<PlayerGroupDetail> details = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Long getIdLadder() {
        return idLadder;
    }

    public void setIdLadder(Long idLadder) {
        this.idLadder = idLadder;
    }


    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public LinkedList<PlayerGroupDetail> getDetails() {
        return details;
    }

    public void setDetails(LinkedList<PlayerGroupDetail> details) {
        this.details = details;
    }
}
