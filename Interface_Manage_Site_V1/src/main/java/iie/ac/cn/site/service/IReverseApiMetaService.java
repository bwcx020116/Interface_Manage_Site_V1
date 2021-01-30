package iie.ac.cn.site.service;


import iie.ac.cn.site.model.Metadata;


import java.util.List;

public interface IReverseApiMetaService {
    /**
     * 根据API id查询所有的template id
     * @return
     */
    List<Metadata> queryByApiID(int id);

    /**
     * 根据API id删除对应的关系
     * @return
     */
    int delByApiID(int id);

    /**
     * 根据API ID和metadata ID插入表中
     * @param ApiID
     * @param metadataID
     * @return
     */
    int insertByApiIDAndMetaID(int ApiID, int metadataID);

}
