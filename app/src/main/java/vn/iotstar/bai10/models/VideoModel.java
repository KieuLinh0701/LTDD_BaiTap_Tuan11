package vn.iotstar.bai10.models;

import java.io.Serializable;

public class VideoModel implements Serializable {
    private int id;
    private String title;
    private String description;
    private String url;

    public VideoModel() {

    }

    public VideoModel(String description, int id, String title, String url) {
        this.description = description;
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
