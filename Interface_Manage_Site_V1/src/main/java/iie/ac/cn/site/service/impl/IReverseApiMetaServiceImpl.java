package iie.ac.cn.site.service.impl;

import iie.ac.cn.site.dao.ReveapiMetaMapper;
import iie.ac.cn.site.model.*;
import iie.ac.cn.site.service.IMetadataService;
import iie.ac.cn.site.service.IReverseApiMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IReverseApiMetaServiceImpl implements IReverseApiMetaService {

    @Autowired
    private ReveapiMetaMapper reveapiMetaMapper;

    @Autowired
    private IMetadataService metadataService;

    public List<Metadata> queryByApiID(int id) {
        ReveapiMetaExample example = new ReveapiMetaExample();
        example.createCriteria().andApiIdEqualTo(id);
        List<ReveapiMeta> reveapiMetas = this.reveapiMetaMapper.selectByExample(example);
        List<Metadata> metadatas = new ArrayList<>();
        if(metadatas!=null){
            for(ReveapiMeta reveapiMeta: reveapiMetas){
                metadatas.add(this.metadataService.loadById(reveapiMeta.getMetadataId()));
            }
            return metadatas;
        }
        return null;
    }

    /**
     * 根据API ID删除
     * @param id
     */
    @Override
    public int delByApiID(int id) {
        ReveapiMetaExample example = new ReveapiMetaExample();
        example.createCriteria().andApiIdEqualTo(id);
        return this.reveapiMetaMapper.deleteByExample(example);
    }

    @Override
    public int insertByApiIDAndMetaID(int ApiID, int metadataID) {
        ReveapiMeta reveapiMeta = new ReveapiMeta();
        reveapiMeta.setApiId(ApiID);
        reveapiMeta.setMetadataId(metadataID);
        return this.reveapiMetaMapper.insert(reveapiMeta);
    }

}