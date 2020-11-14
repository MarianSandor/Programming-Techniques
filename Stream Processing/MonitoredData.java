package task1;

public class MonitoredData {

    private String startTime;
    private String endTime;
    private String activityLabel;

    public MonitoredData(String startTime, String endTime, String activityLabel) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityLabel = activityLabel;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    @Override
    public String toString() {
        return "Start Time: " + this.startTime + " End Time: " + this.endTime + " Activity Label: " + this.activityLabel;
    }
}
