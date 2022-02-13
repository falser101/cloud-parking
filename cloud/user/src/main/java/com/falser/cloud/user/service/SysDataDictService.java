package com.falser.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.user.dto.DataDictDTO;
import com.falser.cloud.user.dto.DataDictLevelDTO;
import com.falser.cloud.user.entity.SysDataDict;
import com.falser.cloud.user.vo.DataDictModifyVO;
import com.falser.cloud.user.vo.DataDictQueryVO;
import com.falser.cloud.user.vo.DataDictSaveVO;

import java.util.List;

/**
 * 数据字典表(SysDataDict)表服务接口
 *
 * @author falser
 * @since 2022-02-13 11:30:52
 */
public interface SysDataDictService extends IService<SysDataDict> {
    /**
     * 发现水平
     *
     * @param vo 签证官
     * @return {@link List}<{@link DataDictLevelDTO}>
     */
    List<DataDictLevelDTO> findLevel(DataDictQueryVO vo);

    /**
     * 保存数据dict类型
     *
     * @param vo 签证官
     */
    void saveDataDict(DataDictSaveVO vo);

    /**
     * 修改数据dict类型
     *
     * @param vo 签证官
     */
    void modifyDataDict(DataDictModifyVO vo);

    /**
     * 获取数据dict类型
     *
     * @param id id
     * @return {@link DataDictDTO}
     */
    DataDictDTO getDataDict(Long id);
}

