package com.falser.cloud.parking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.falser.cloud.parking.entity.ParkingParkingSpace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 停车位(ParkingParkingSpace)表数据库访问层
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
public interface ParkingParkingSpaceDao extends BaseMapper<ParkingParkingSpace> {

/**
* 批量新增数据（MyBatis原生foreach方法）
*
* @param entities List<ParkingParkingSpace> 实例对象列表
* @return 影响行数
*/
int insertBatch(@Param("entities") List<ParkingParkingSpace> entities);

/**
* 批量新增或按主键更新数据（MyBatis原生foreach方法）
*
* @param entities List<ParkingParkingSpace> 实例对象列表
* @return 影响行数
* @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
*/
int insertOrUpdateBatch(@Param("entities") List<ParkingParkingSpace> entities);

}

