package india.coronavirus.fight.model;

public class HeaderData {
    private String cases;
    private String header;
    private String subheader;
    private String newcase;

    public HeaderData(String cases, String header, String subheader, String new_case) {
        this.cases = cases;
        this.header = header;
        this.subheader = subheader;
        this.newcase = new_case;

    }

    public String getNewcase() {
        return newcase;
    }

    public void setNewcase(String newcase) {
        this.newcase = newcase;
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
