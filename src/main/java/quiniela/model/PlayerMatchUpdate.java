package quiniela.model;

public class PlayerMatchUpdate {

    private long id;
    private String username;
    private String password;
    private Long hScore;
    private Long vScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long gethScore() {
        return hScore;
    }

    public void sethScore(Long hScore) {
        this.hScore = hScore;
    }

    public Long getvScore() {
        return vScore;
    }

    public void setvScore(Long vScore) {
        this.vScore = vScore;
    }
}
