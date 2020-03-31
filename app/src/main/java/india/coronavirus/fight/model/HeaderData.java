package india.coronavirus.fight.model;

public class HeaderData {
    private String cases;
    private String header;
    private String subheader;
    private String newcase;
    private String color;
    private String column;
    private String info;

    public HeaderData(String cases, String header, String subheader, String new_case, String color, String info, String column) {
        this.cases = cases;
        this.header = header;
        this.subheader = subheader;
        this.newcase = new_case;
        this.color = color;
        this.column = column;
        this.info = info;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
