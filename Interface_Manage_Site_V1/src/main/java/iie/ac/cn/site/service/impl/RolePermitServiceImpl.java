package iie.ac.cn.site.service.impl;


import iie.ac.cn.site.dao.RolePermitMapper;
import iie.ac.cn.site.model.Permit;
import iie.ac.cn.site.model.RolePermit;
import iie.ac.cn.site.model.RolePermitExample;
import iie.ac.cn.site.service.IPermitService;
import iie.ac.cn.site.service.IRolePermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RolePermitServiceImpl implements IRolePermitService {

    @Autowired
    private RolePermitMapper rolePermitMapper;


    @Autowired
    private IPermitService permitService;


    /**
     * 根据角色ID获取权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permit> queryByRoleId(int roleId) {
        RolePermitExample example = new RolePermitExample();

        example.createCriteria().andRoleIdEqualTo(roleId);


        List<RolePermit> rolePermitList = this.rolePermitMapper.selectByExample(example);

        List<Permit> permitList = new ArrayList<Permit>();

        for (RolePermit rolePermit : rolePermitList) {

            permitList.add(this.permitService.loadById(rolePermit.getPermitId()));
        }

        return permitList;
    }
}