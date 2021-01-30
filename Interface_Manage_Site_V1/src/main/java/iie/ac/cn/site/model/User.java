package iie.ac.cn.site.model;

public class User {
    private Integer id;

    private String loginName;

    private String loginPwd;

    private Integer roleId;

    public User(String loginName, String loginPwd, Integer roleId) {
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.roleId = roleId;
    }

    public User(String loginName, String loginPwd) {
        this.loginName = loginName;
        this.loginPwd = loginPwd;
    }

    public User(Integer id, String loginName, String loginPwd, Integer roleId) {
        this.id = id;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.roleId = roleId;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}