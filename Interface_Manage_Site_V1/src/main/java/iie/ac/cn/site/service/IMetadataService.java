package iie.ac.cn.site.service;


import iie.ac.cn.site.model.Metadata;

import java.util.List;

public interface IMetadataService {


    List<Metadata> queryAll();


    List<Metadata> queryByName(String o);

    Metadata  loadById(Integer id);
}
