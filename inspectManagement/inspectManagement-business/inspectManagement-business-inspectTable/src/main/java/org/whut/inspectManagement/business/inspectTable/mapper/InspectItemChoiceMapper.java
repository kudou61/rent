package org.whut.inspectManagement.business.inspectTable.mapper;

import org.whut.inspectManagement.business.inspectTable.entity.InspectItemChoice;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xjie
 * Date: 14-5-15
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
public interface InspectItemChoiceMapper extends AbstractMapper<InspectItemChoice> {
    public void addList(List<InspectItemChoice> inspectItemChoices);
    public List<Long> getChoiceIdByItemId(long id);
    public void deleteByInspectItemId(long id);
    public List<String> getChoicesByItemId(long itemId);
}
