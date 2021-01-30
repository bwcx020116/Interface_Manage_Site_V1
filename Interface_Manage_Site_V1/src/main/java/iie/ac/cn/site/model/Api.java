package iie.ac.cn.site.model;

public class Api {
    private Integer id;

    private String name;

    private String description;

    private String metadata;

    private String type;

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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata == null ? null : metadata.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Api(String name, String description, String metadata, String type) {
        this.name = name;
        this.description = description;
        this.metadata = metadata;
        this.type = type;
    }

    public Api() {
    }

    public Api(Integer id, String name, String description, String metadata, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.metadata = metadata;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Api{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", metadata='" + metadata + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}