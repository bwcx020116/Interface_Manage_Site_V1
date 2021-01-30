package iie.ac.cn.site.vo;

public class InverseTrackVo {
    //fieldType   fieldValue   goalType termOrFuzzy
    private String fieldType;
    private String fieldValue;
    private String goalType;
    private String goalValue;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getGoalValue() {
        return goalValue;
    }

    public void setGoalValue(String goalValue) {
        this.goalValue = goalValue;
    }

    public InverseTrackVo(String fieldType, String fieldValue, String goalType, String goalValue) {
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
        this.goalType = goalType;
        this.goalValue = goalValue;
    }

    public InverseTrackVo() {
    }
}
