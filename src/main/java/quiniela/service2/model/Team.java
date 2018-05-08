package quiniela.service2.model;

public class Team {
    Long id;
    String name;
    String code;
    String urlFlag;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getUrlFlag() {
        return urlFlag;
    }

    public void setUrlFlag(String urlFlag) {
        this.urlFlag = urlFlag;
    }
}
