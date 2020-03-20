package india.coronavirus.fight.model;

public class HeaderData {
    private String cases;
    private String header;
    private String subheader;

    public HeaderData(String cases, String header, String subheader) {
        this.cases = cases;
        this.header = header;
        this.subheader = subheader;
    }

    public String getSubheader() {
        return subheader;
    }

    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

}
