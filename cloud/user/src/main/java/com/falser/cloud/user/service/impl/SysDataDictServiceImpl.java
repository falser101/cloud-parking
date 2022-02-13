package com.falser.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.falser.cloud.common.enums.StatusEnum;
import com.falser.cloud.user.dao.SysDataDictDao;
import com.falser.cloud.user.dto.DataDictDTO;
import com.falser.cloud.user.dto.DataDictLevelDTO;
import com.falser.cloud.user.entity.SysDataDict;
import com.falser.cloud.user.service.SysDataDictService;
import com.falser.cloud.user.vo.DataDictModifyVO;
import com.falser.cloud.user.vo.DataDictQueryVO;
import com.falser.cloud.user.vo.DataDictSaveVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 数据字典表(SysDataDict)表服务实现类
 *
 * @author falser
 * @since 2022-02-13 11:30:52
 */
@Service
public class SysDataDictServiceImpl extends ServiceImpl<SysDataDictDao, SysDataDict> implements SysDataDictService {
    @Override
    public List<DataDictLevelDTO> findLevel(DataDictQueryVO vo) {
        List<SysDataDict> dataDicts;
        QueryWrapper<SysDataDict> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getParentCode())) {
            LambdaQueryWrapper<SysDataDict> lambdaQuery = queryWrapper.lambda().eq(SysDataDict::getDictCode, vo.getParentCode())
                    .or().eq(SysDataDict::getParentCode, vo.getParentCode());
            if (vo.getStatus() != null) {
                lambdaQuery.eq(SysDataDict::getStatus, vo.getStatus());
            }
        } else {
            if (vo.getStatus() != null) {
                queryWrapper.lambda().eq(SysDataDict::getStatus, vo.getStatus());
            }
        }
        dataDicts = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(dataDicts)) {
            List<DataDictLevelDTO> list = new ArrayList<>();
            if (StringUtils.isEmpty(vo.getParentCode())) {
                for (SysDataDict dataDict : dataDicts) {
                    if (StringUtils.isEmpty(dataDict.getParentCode())) {
                        wrap(list, dataDicts, dataDict);
                    }
                }
            } else {
                for (SysDataDict dataDict : dataDicts) {
                    if (vo.getParentCode().equals(dataDict.getDictCode())) {
                        wrap(list, dataDicts, dataDict);
                    }
                }
            }
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public void saveDataDict(DataDictSaveVO vo) {
        SysDataDict dataDict = new SysDataDict();
        BeanUtils.copyProperties(vo, dataDict);
        dataDict.setSortNumber(0);
        dataDict.setStatus(StatusEnum.ENABLE);
        save(dataDict);
    }

    @Override
    public void modifyDataDict(DataDictModifyVO vo) {
        UpdateWrapper<SysDataDict> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysDataDict::getUpdateTime, new Date())
                .set(SysDataDict::getDictName, vo.getDictName())
                .set(SysDataDict::getParentId, vo.getParentId())
                .set(SysDataDict::getParentCode, vo.getParentCode())
                .set(SysDataDict::getDictRemark, vo.getDictRemark())
                .eq(SysDataDict::getId, vo.getId());
        update(updateWrapper);
    }

    @Override
    public DataDictDTO getDataDict(Long id) {
        SysDataDict sysDataDict = getById(id);
        DataDictDTO dataDictDTO = new DataDictDTO();
        BeanUtils.copyProperties(sysDataDict, dataDictDTO);
        if (Objects.nonNull(sysDataDict.getParentId()) && sysDataDict.getParentId() != 0L) {
            SysDataDict parentDataDice = getById(sysDataDict.getParentId());
            dataDictDTO.setParentName(parentDataDice.getDictName());
        }
        return dataDictDTO;
    }

    private void wrap(List<DataDictLevelDTO> list, List<SysDataDict> dataDicts, SysDataDict dataDict) {
        DataDictLevelDTO dto = new DataDictLevelDTO();
        BeanUtils.copyProperties(dataDict, dto);
        dto.setChildNodes(childNodes(dataDict.getDictCode(), dataDicts));
        list.add(dto);
    }

    private List<DataDictLevelDTO> childNodes(String parentCode, List<SysDataDict> dataDicts) {
        List<DataDictLevelDTO> list = new ArrayList<>();
        // 针对parentCode == null 的情况需要特殊处理
        if (StringUtils.isEmpty(parentCode)) {
            dataDicts.stream().filter(r -> StringUtils.isEmpty(parentCode))
                    .forEach(r1 -> wrap(list, dataDicts, r1));
        } else {
            dataDicts.stream().filter(r -> parentCode.equals(r.getParentCode()))
                    .forEach(r1 -> wrap(list, dataDicts, r1));
        }
        return list;
    }
}

