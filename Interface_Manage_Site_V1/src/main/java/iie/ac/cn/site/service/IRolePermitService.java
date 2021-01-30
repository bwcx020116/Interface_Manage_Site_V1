package iie.ac.cn.site.service;




import iie.ac.cn.site.model.Permit;

import java.util.List;

public interface IRolePermitService {

    /**
     * 根据角色ID获取权限
     *
     * @param roleId
     * @return
     */
    List<Permit> queryByRoleId(int roleId);
}
