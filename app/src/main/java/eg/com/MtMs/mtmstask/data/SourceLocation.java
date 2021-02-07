package eg.com.MtMs.mtmstask.data;

public class SourceLocation {


    String name;
    int latitude;
    int lantitude;

    public SourceLocation() {
    }

    public SourceLocation(String name, int latitude, int lantitude) {
        this.name = name;
        this.latitude = latitude;
        this.lantitude = lantitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLantitude() {
        return lantitude;
    }

    public void setLantitude(int lantitude) {
        this.lantitude = lantitude;
    }
}
