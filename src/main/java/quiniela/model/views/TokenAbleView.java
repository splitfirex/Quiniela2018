package quiniela.model.views;

public abstract class  TokenAbleView  {

    private String token;

    public String getToken() {
        return token;
    }

    public <T extends TokenAbleView> T setToken(String token) {
        this.token = token;
        return (T) this;
    }

}
