package iie.ac.cn.site.model;

public class Reverseapi {
    private Integer id;

    private String name;

    private String description;

    private String attribution;

    private String apitype;

    private String searchtype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution == null ? null : attribution.trim();
    }

    public String getApitype() {
        return apitype;
    }

    public void setApitype(String apitype) {
        this.apitype = apitype == null ? null : apitype.trim();
    }

    public String getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype == null ? null : searchtype.trim();
    }

    public Reverseapi(String name, String description, String attribution, String apitype, String searchtype) {
        this.name = name;
        this.description = description;
        this.attribution = attribution;
        this.apitype = apitype;
        this.searchtype = searchtype;
    }

    public Reverseapi(Integer id, String name, String description, String attribution, String apitype, String searchtype) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attribution = attribution;
        this.apitype = apitype;
        this.searchtype = searchtype;
    }

    public Reverseapi() {
    }
}