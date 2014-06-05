package org.whut.inspectManagement.business.device.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.whut.inspectManagement.business.device.entity.Device;
import org.whut.inspectManagement.business.device.entity.DeviceTypeTag;
import org.whut.inspectManagement.business.device.mapper.DeviceMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-15
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    public void add(Device device){
        deviceMapper.add(device);
    }

    public List<Device> list(){
      return  deviceMapper.findByCondition(new HashMap<String, Object>());
    }

    public List<Device> getListByAppId(long appId)
    {
        return deviceMapper.getListByAppId(appId);
    }

    public void update(Device device){
        deviceMapper.update(device);
    }

    public void delete(Device device){
        deviceMapper.delete(device);
    }
    public long getIdByNumber(String number,long appId) {
        return deviceMapper.getIdByNumber(number,appId);
    }
    public List<Map<String,String>> getListByCondition(String deviceType,String deviceNumber,String tagName,long appId){
       return deviceMapper.getListByCondition(deviceType,deviceNumber,tagName,appId);
    }
    public List<Map<String,String>> getListByTagId(long tagId){
        return deviceMapper.getListByTagId(tagId);
    }

    public String getNameById(long id)
    {
        return deviceMapper.getNameById(id);
    }

    public long getIdByName(String name,long appId)
    {
        return deviceMapper.getIdByName(name,appId);
    }
    public List<Device> getInfoByCondition(String name,String number,long deviceTypeId,long appId){
        return  deviceMapper.getInfoByCondition(name,number,deviceTypeId,appId);
    }
}
