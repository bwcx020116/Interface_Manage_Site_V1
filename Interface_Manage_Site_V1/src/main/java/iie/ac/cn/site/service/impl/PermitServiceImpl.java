package iie.ac.cn.site.service.impl;


import iie.ac.cn.site.dao.PermitMapper;
import iie.ac.cn.site.model.Permit;
import iie.ac.cn.site.model.PermitExample;
import iie.ac.cn.site.service.IPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermitServiceImpl implements IPermitService {

    @Autowired
    private PermitMapper permitMapper;


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Permit loadById(int id) {

        //select * from permit where id=?

        PermitExample example = new PermitExample();
        example.createCriteria().andIdEqualTo(id);

        List<Permit> siteList = this.permitMapper.selectByExample(example);

        return siteList.isEmpty() ? null : siteList.get(0);

    }


}