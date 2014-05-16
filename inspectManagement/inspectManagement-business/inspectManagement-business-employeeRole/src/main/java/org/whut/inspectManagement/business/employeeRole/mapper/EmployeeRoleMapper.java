package org.whut.inspectManagement.business.employeeRole.mapper;

import org.whut.inspectManagement.business.employeeRole.entity.EmployeeRole;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chenqw
 * Date: 14-5-15
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public interface EmployeeRoleMapper extends AbstractMapper{
    public List<EmployeeRole> findByCondition(Map<String,Object> map);
}
