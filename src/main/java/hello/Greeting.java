package hello;

import java.util.List;

public class Greeting {

    private final long id;
    private final String content;
    private List<String> listado;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<String> getListado() {
        return listado;
    }

    public void setListado(List<String> listado) {
        this.listado = listado;
    }
}
