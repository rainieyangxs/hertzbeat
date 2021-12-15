package com.usthe.manager.dao;

import com.usthe.manager.pojo.dto.AppCount;
import com.usthe.manager.pojo.entity.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * AuthResources 数据库操作
 *
 *
 */
public interface MonitorDao extends JpaRepository<Monitor, Long>, JpaSpecificationExecutor<Monitor> {


    /**
     * 根据监控ID列表删除监控
     * @param monitorIds 监控ID列表
     */
    void deleteAllByIdIn(Set<Long> monitorIds);

    /**
     * 根据监控ID列表查询监控
     * @param monitorIds 监控ID列表
     * @return 监控列表
     */
    List<Monitor> findMonitorsByIdIn(Set<Long> monitorIds);

    /**
     * 根据监控类型查询监控
     * @param app 监控类型
     * @return 监控列表
     */
    List<Monitor> findMonitorsByAppEquals(String app);

    /**
     * 查询监控类别及其对应的监控数量
     * @return 监控类别与监控数量映射
     */
    @Query("select new com.usthe.manager.pojo.dto.AppCount(mo.app, COUNT(mo.id)) from Monitor mo group by mo.app")
    List<AppCount> findAppsCount();

    /**
     * 更新指定监控的状态
     * @param id 监控ID
     * @param status 监控状态
     */
    @Modifying
    @Query("update Monitor set status = :status where id = :id")
    void updateMonitorStatus(@Param(value = "id") Long id, @Param(value = "status") byte status);
}
