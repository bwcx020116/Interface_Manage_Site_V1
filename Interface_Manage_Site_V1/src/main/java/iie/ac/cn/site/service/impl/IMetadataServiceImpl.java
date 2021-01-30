package iie.ac.cn.site.service.impl;


import iie.ac.cn.site.dao.MetadataMapper;

import iie.ac.cn.site.model.Metadata;
import iie.ac.cn.site.model.MetadataExample;
import iie.ac.cn.site.service.IMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IMetadataServiceImpl implements IMetadataService {

    @Autowired
    private MetadataMapper metadataMapper;



    @Override
    public List<Metadata> queryAll() {
        MetadataExample example = new MetadataExample();
        List<Metadata> metadataList = this.metadataMapper.selectByExample(example);
        return metadataList;
    }

    @Override
    public List<Metadata> queryByName(String name) {
        MetadataExample example = new MetadataExample();
        example.createCriteria().andNameEqualTo(name);
        List<Metadata> metadataList = this.metadataMapper.selectByExample(example);
        return metadataList;
    }

    @Override
    public Metadata loadById(Integer id) {
        MetadataExample example = new MetadataExample();
        example.createCriteria().andIdEqualTo(id);
        List<Metadata> metadataList = this.metadataMapper.selectByExample(example);
        return metadataList.isEmpty()?null:metadataList.get(0);
    }
}