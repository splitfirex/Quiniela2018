package quinielas.utils.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "domTeam")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DOMTeam {

    public DOMTeam(){

    }

    public DOMTeam(JSONObject object) throws JSONException {
        this.id = object.getLong("id");
        this.name = object.getString("name");
        this.shortCode = object.getString("fifaCode");
        this.flagUrl = object.getString("iso2");
    }

    @Id
    Long id;
    String name;
    String shortCode;
    String flagUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
