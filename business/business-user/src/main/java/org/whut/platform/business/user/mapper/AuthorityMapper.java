package org.whut.platform.business.user.mapper;

import org.whut.platform.business.user.entity.Authority;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.Map;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: xiaozhujun
 * Date: 14-3-16
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public interface AuthorityMapper extends AbstractMapper<Authority> {
      public List<Authority> findByCondition(Map<String,Object> map);
      public long getIdByName(String name);
}
