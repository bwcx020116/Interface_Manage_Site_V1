package iie.ac.cn.site.model;

public class Incident {
    private String id;
    private String start_time;
    private String time_pattern;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTime_pattern() {
        return time_pattern;
    }

    public void setTime_pattern(String time_pattern) {
        this.time_pattern = time_pattern;
    }

    public Incident() {
    }

    public Incident(String id, String start_time, String time_pattern) {
        this.id = id;
        this.start_time = start_time;
        this.time_pattern = time_pattern;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id='" + id + '\'' +
                ", start_time='" + start_time + '\'' +
                ", time_pattern='" + time_pattern + '\'' +
                '}';
    }
}
