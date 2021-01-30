package iie.ac.cn.site.vo;

public class InverseTrackParam {
    //fieldType   fieldValue   goalType termOrFuzzy
    private String fieldType;
    private String fieldValue;
    private String goalType;
    private String termOrFuzzy;

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

    public String getTermOrFuzzy() {
        return termOrFuzzy;
    }

    public void setTermOrFuzzy(String termOrFuzzy) {
        this.termOrFuzzy = termOrFuzzy;
    }

    public InverseTrackParam(String fieldType, String fieldValue, String goalType, String termOrFuzzy) {
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
        this.goalType = goalType;
        this.termOrFuzzy = termOrFuzzy;
    }

    public InverseTrackParam() {
    }
}
