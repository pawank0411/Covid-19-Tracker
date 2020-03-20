package india.coronavirus.fight.model;

public class NewData {
    private String title;
    private String link;
    private String time;

    public NewData(String title, String link, String time) {
        this.title = title;
        this.link = link;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
