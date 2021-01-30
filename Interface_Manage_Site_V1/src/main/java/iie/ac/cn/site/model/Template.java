package iie.ac.cn.site.model;

public class Template {
    private Integer id;

    private String name;

    private String type;

    private String src;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSrc() {
        return src;
    }

    public Template(Integer id, String name, String type, String src) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.src = src;
    }

    public Template() {
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}