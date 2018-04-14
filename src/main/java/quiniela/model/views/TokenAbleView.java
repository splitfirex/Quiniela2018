package quiniela.model.views;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class  TokenAbleView  {

    private String token;

    public String getToken() {
        return token;
    }

    public <T extends TokenAbleView> T setToken(String token) {
        this.token = token;
        return (T) this;
    }

}
