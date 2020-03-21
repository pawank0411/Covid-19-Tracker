package india.coronavirus.fight.model;

public class GuideData {
    private String title;
    private String link;
    private String desp;

    public GuideData(String title, String link, String desp) {
        this.title = title;
        this.link = link;
        this.desp = desp;
    }
    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
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

}
