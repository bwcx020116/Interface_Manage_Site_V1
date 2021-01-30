package iie.ac.cn.site.service;


import iie.ac.cn.site.model.Permit;

public interface IPermitService {


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Permit loadById(int id);


}
