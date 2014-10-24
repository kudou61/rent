package org.whut.rentManagement.business.badDebt.web;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.platform.business.user.security.UserContext;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;
import org.whut.rentManagement.business.badDebt.entity.BadDebtDevice;
import org.whut.rentManagement.business.badDebt.entity.BadDebtSheet;
import org.whut.rentManagement.business.badDebt.entity.SubBadDebtSheet;
import org.whut.rentManagement.business.badDebt.service.BadDebtDeviceService;
import org.whut.rentManagement.business.badDebt.service.BadDebtSheetService;
import org.whut.rentManagement.business.device.service.DeviceService;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cwb
 * Date: 14-10-9
 * Time: 下午7:05
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/bad_Debt_Sheet")
public class BadDebtSheetServiceWeb {
    @Autowired
    BadDebtSheetService badDebtSheetService;
    @Autowired
    BadDebtDeviceService badDebtDeviceService;
    @Autowired
    DeviceService deviceService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    /*public String add(@FormParam("number") String number,@FormParam("carNumber") String carNumber,@FormParam("customerId")long customerId,
                      @FormParam("contractId")long contractId,@FormParam("handler")String handler,@FormParam("storehouseId")long storehouseId,
                      @FormParam("description")String description,@FormParam("creator")String creator){*/
    public String add(@FormParam("jsonStringList") String jsonStringList) throws JSONException, ParseException{
        long appId = UserContext.currentUserAppId();
        try {
            //JSONArray jsonArray = new JSONArray(jsonStringList);
            JSONObject jsonObject=new JSONObject(jsonStringList);
            if(jsonObject==null){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能是空");
            }
                SubBadDebtSheet subBadDebtSheet = JsonMapper.buildNonDefaultMapper().fromJson(jsonStringList, SubBadDebtSheet.class);
                BadDebtSheet badDebtSheet = new BadDebtSheet();
                badDebtSheet.setNumber(subBadDebtSheet.getNumber());
                badDebtSheet.setCarNumber(subBadDebtSheet.getCarNumber());
                badDebtSheet.setCustomerId(subBadDebtSheet.getCustomerId());
                badDebtSheet.setContractId(subBadDebtSheet.getContractId());
                badDebtSheet.setHandler(subBadDebtSheet.getHandler());
                badDebtSheet.setStorehouseId(subBadDebtSheet.getStorehouseId());
                badDebtSheet.setDescription(subBadDebtSheet.getDescription());
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //定义时间格式
                badDebtSheet.setCreateTime(sdf.parse(subBadDebtSheet.getCreateTime()));  //String搞成date类型
                badDebtSheet.setCreator(subBadDebtSheet.getCreator());
                badDebtSheet.setAppId(appId);
                badDebtSheetService.add(badDebtSheet);

            long deviceId = deviceService.getIdByNumber(subBadDebtSheet.getDeviceNumber(),appId);
            long badDebtId = badDebtSheetService.getBadDebtSheetId(jsonObject.getString("number"),jsonObject.getString("carNumber"),jsonObject.getLong("customerId"),jsonObject.getLong("contractId"),jsonObject.getLong("storehouseId"),jsonObject.getLong("appId"));
            BadDebtDevice badDebtDevice = new BadDebtDevice();
            badDebtDevice.setBadDebtId(badDebtId);
            badDebtDevice.setDeviceId(deviceId);
            badDebtDeviceService.add(badDebtDevice);   //对关联表bad_debt_device进行插入操作

        }catch (JSONException e){
            e.printStackTrace();
        }
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
        BadDebtSheet badDebtSheet = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,BadDebtSheet.class);
        badDebtSheetService.update(badDebtSheet);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){
        BadDebtSheet badDebtSheet = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,BadDebtSheet.class);
        badDebtSheetService.delete(badDebtSheet);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(){
        long appId = UserContext.currentUserAppId();
        List<Map<String,Object>> list = badDebtSheetService.getListByAppId(appId); //mapper.xml中用的left join 实现的所需字段的组合,此时是resultType=map
        List<SubBadDebtSheet> subBadDebtSheetsList = new ArrayList<SubBadDebtSheet>();
        for(Map<String,Object> badDebtSheet:list){
            SubBadDebtSheet subBadDebtSheet = new SubBadDebtSheet();
            subBadDebtSheet.setNumber((String)badDebtSheet.get("number"));
            subBadDebtSheet.setCarNumber((String) badDebtSheet.get("carNumber"));
            subBadDebtSheet.setId((Long) badDebtSheet.get("id"));
            subBadDebtSheet.setCustomerId((Long) badDebtSheet.get("customerId"));
            subBadDebtSheet.setContractId((Long) badDebtSheet.get("contractId"));
            subBadDebtSheet.setHandler((String) badDebtSheet.get("handler"));
            subBadDebtSheet.setStorehouseId((Long) badDebtSheet.get("storehouseId"));
            subBadDebtSheet.setDescription((String) badDebtSheet.get("description"));
            Date date=(Date)badDebtSheet.get("createTime");  //获取到日期
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = sdf.format(date);      //日期转化成字符串
            subBadDebtSheet.setCreateTime(str);
            subBadDebtSheet.setCreator((String) badDebtSheet.get("creator"));
            subBadDebtSheet.setAppId((Long)badDebtSheet.get("appId"));
            subBadDebtSheet.setDeviceNumber((String) badDebtSheet.get("deviceNumber"));
            //System.out.println(subBadDebtSheet);
            subBadDebtSheetsList.add(subBadDebtSheet);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(subBadDebtSheetsList, JsonResultUtils.Code.SUCCESS);
    }

    /*@Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getBadDebtSheetId")
    @POST
    public String getBadDebtSheetId(@FormParam("number") String number,@FormParam("carNumber") String carNumber,@FormParam("customerId") String customerId){
       return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }*/
}