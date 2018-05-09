package quinielas.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerGroup {


    private String groupName;
    private Long idPlayer;
    private Long idLadder;

    private LinkedList<PlayerGroupDetail> details = new LinkedList<>();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public LinkedList<PlayerGroupDetail> getDetails() {
        return details;
    }

    public void setDetails(LinkedList<PlayerGroupDetail> details) {
        this.details = details;
    }
}
