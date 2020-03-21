package india.coronavirus.fight.model;

public class Data {
    private String heading;
    private int totalCase;
    private int newCase;
    private int hospitalized;
    private String subHeading1;
    private String subHeading2;
    private String subHeading3;


    public int getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(int hospitalized) {
        this.hospitalized = hospitalized;
    }

    public String getSubHeading3() {
        return subHeading3;
    }

    public void setSubHeading3(String subHeading3) {
        this.subHeading3 = subHeading3;
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(int totalCase) {
        this.totalCase = totalCase;
    }

    public int getNewCase() {
        return newCase;
    }

    public void setNewCase(int newCase) {
        this.newCase = newCase;
    }

    public String getSubHeading1() {
        return subHeading1;
    }

    public void setSubHeading1(String subHeading1) {
        this.subHeading1 = subHeading1;
    }

    public String getSubHeading2() {
        return subHeading2;
    }

    public void setSubHeading2(String subHeading2) {
        this.subHeading2 = subHeading2;
    }
}
