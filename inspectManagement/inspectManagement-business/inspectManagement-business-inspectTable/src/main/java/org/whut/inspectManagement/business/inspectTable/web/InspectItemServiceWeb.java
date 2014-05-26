package org.whut.inspectManagement.business.inspectTable.web;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.inspectManagement.business.device.service.InspectAreaService;
import org.whut.inspectManagement.business.inspectTable.entity.InspectItem;
import org.whut.inspectManagement.business.inspectTable.entity.SubInspectItem;

import org.whut.inspectManagement.business.inspectTable.entity.InspectItemChoice;
import org.whut.inspectManagement.business.inspectTable.service.InspectChoiceService;
import org.whut.inspectManagement.business.inspectTable.service.InspectItemChoiceService;
import org.whut.inspectManagement.business.inspectTable.service.InspectItemService;
import org.whut.inspectManagement.business.inspectTable.service.InspectTableService;

import org.whut.platform.business.user.security.UserContext;
import org.whut.platform.business.user.service.UserService;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: choumiaoer
 * Date: 14-5-11
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/inspectItem")
public class InspectItemServiceWeb {
    @Autowired
    InspectTableService inspectTableService;
    @Autowired
    InspectItemService inspectItemService;
    @Autowired
    InspectItemChoiceService inspectItemChoiceService;
    @Autowired
    InspectChoiceService inspectChoiceService;
    @Autowired
    UserService userService;
    @Autowired
    InspectAreaService inspectAreaService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("description") String description,@FormParam("inspectTableId") String inspectTableId,
    @FormParam("inspectAreaId") long inspectAreaId,@FormParam("number") String number,@FormParam("isInput") int isInput,@FormParam("inspectChoiceId") String inspectChoiceId){
        if(name==null||inspectAreaId==0||inspectTableId.equals("")||number==null||name.equals("")||inspectTableId.equals("null")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能为空!");
        }
        Date date=new Date();
        String[] inspectTableArray=inspectTableId.split(";");
        List<InspectItem> inspectItemList=new ArrayList<InspectItem>();
        for(int i=0;i<inspectTableArray.length;i++){
            InspectItem inspectItem=new InspectItem();
            inspectItem.setName(name);
            inspectItem.setDescription(description);
            inspectItem.setCreatetime(date);
            inspectItem.setInspectAreaId(inspectAreaId);
            inspectItem.setNumber(number);
            inspectItem.setInput(isInput);
            inspectItem.setInspectTableId(Integer.parseInt(inspectTableArray[i]));
            inspectItemList.add(inspectItem);
        }
        inspectItemService.addList(inspectItemList);
        if(inspectChoiceId!=""||inspectChoiceId.equals("")){
        }else {
            String [] inspectItemChoiceList=inspectChoiceId.split(";");
            List<InspectItemChoice> inspectItemChoices=new ArrayList<InspectItemChoice>();
            if (isInput==0){
                for(String choice:inspectItemChoiceList){
                    InspectItemChoice inspectItemChoice=new InspectItemChoice();
                    inspectItemChoice.setInspectItemId(inspectItemService.getInspectItemIdByNameAndNumber(name,number));
                    inspectItemChoice.setInspectChoiceId(Integer.parseInt(choice));
                    inspectItemChoices.add(inspectItemChoice);
                }
                inspectItemChoiceService.addList(inspectItemChoices);
            }
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"操作成功");
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/addList")
    @POST
    public String addList(@FormParam("jsonStringList") String jsonStringList){
        long appId=userService.getMyUserDetailFromSession().getAppId();
        System.out.println(appId);
        long a=UserContext.currentUserAppId();

        List<InspectItem> inspectItemList=new ArrayList<InspectItem>();
        List<InspectItemChoice> inspectItemChoiceList=new ArrayList<InspectItemChoice>();
        Date date=new Date();
        try {
            JSONArray jsonArray=new JSONArray(jsonStringList);
            System.out.println(jsonArray.get(0));
            for(int i=0;i<jsonArray.length();i++){
                String jsonString= jsonArray.get(i).toString();
                SubInspectItem subInspectItem=JsonMapper.buildNonDefaultMapper().fromJson(jsonString,SubInspectItem.class);
                InspectItem inspectItem=new InspectItem();
                inspectItem.setName(subInspectItem.getName());
                inspectItem.setNumber(subInspectItem.getNumber());
                inspectItem.setCreatetime(date);
                System.out.println("jjjjjjjjjjjjjjjjj");
                System.out.println(subInspectItem.getInput());
                inspectItem.setInput(0);
                inspectItem.setInspectTableId(inspectTableService.getIdByName(subInspectItem.getInspectTable()));
                inspectItem.setInspectAreaId(inspectAreaService.getInspectAreaIdByNames(subInspectItem.getInspectArea(),subInspectItem.getDeviceType()));
                inspectItem.setDescription(subInspectItem.getDescription());
                inspectItem.setAppId(appId);
                inspectItemList.add(inspectItem);


                String choices=subInspectItem.getChoiceValue();
                String [] choicesList=choices.split(";");
                for (String choice:choicesList){
                    InspectItemChoice inspectItemChoice=new InspectItemChoice();
                    inspectItemChoice.setInspectChoiceId(inspectChoiceService.getIdByChoiceValue(choice));
                    inspectItemChoice.setInspectItemId(inspectItemService.getInspectItemIdByNameAndNumber(subInspectItem.getName(),subInspectItem.getNumber()));
                    inspectItemChoice.setAppId(appId);
                    inspectItemChoiceList.add(inspectItemChoice);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        inspectItemChoiceService.addList(inspectItemChoiceList);
        inspectItemService.addList(inspectItemList);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"操作成功");
    }
    @Produces(MediaType.APPLICATION_JSON +";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(){
        List<InspectItem> list=inspectItemService.list();
        List<SubInspectItem> subList=new ArrayList<SubInspectItem>();
        for(InspectItem a:list){
            SubInspectItem subInspectItem=new SubInspectItem();
            subInspectItem.setId(a.getId());
            subInspectItem.setName(a.getName());
            subInspectItem.setNumber(a.getNumber());
            subInspectItem.setCreatetime(a.getCreatetime());
            subInspectItem.setDescription(a.getDescription());
            subInspectItem.setInspectArea("qq");
            subInspectItem.setInspectTable(inspectTableService.getNameById(a.getInspectTableId()));
            if(a.getInput()==0){
                subInspectItem.setInput("否");
            }
            else{
                  subInspectItem.setInput("是");
                }
            subInspectItem.setChoiceValue(inspectItemChoiceService.getChoiceValueByItemId(a.getId()));
            subList.add(subInspectItem);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(subList, JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON +";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
        SubInspectItem subInspectItem = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,SubInspectItem.class);
        if(subInspectItem.getName()==null||subInspectItem.getInspectArea()==null||subInspectItem.getInspectTable()==null||subInspectItem.getNumber()==null||subInspectItem.getName().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能为空!");
        }
     //更新inspectItem表
       int isInput;
        if(subInspectItem.getInput().equals("否")){
            isInput=0;
        }
        else {
            isInput=1;
        }
            InspectItem inspectItem=new InspectItem();
        inspectItem.setId(subInspectItem.getId());
            inspectItem.setName(subInspectItem.getName());
            inspectItem.setDescription(subInspectItem.getDescription());
            inspectItem.setCreatetime(subInspectItem.getCreatetime());
            //inspectItem.setInspectAreaId(subInspectItem.getInspectArea());
            //inspectItem.setInspectAreaId(subInspectItem.getInspectAreaId());
            inspectItem.setNumber(subInspectItem.getNumber());
            inspectItem.setInput(isInput);
            inspectItem.setInspectTableId(inspectTableService.getIdByName(subInspectItem.getInspectTable()));


        inspectItemService.update(inspectItem);

       //更新inspectItem_choice表

        List<InspectItemChoice> inspectItemChoicesList=new ArrayList<InspectItemChoice>();
        String[] choiceValueArray=subInspectItem.getChoiceValue().split(";");
        inspectItemChoiceService.deleteByInspectItemId(subInspectItem.getId());
            for(String a:choiceValueArray){
                InspectItemChoice inspectItemChoice=new InspectItemChoice();
                inspectItemChoice.setInspectItemId(subInspectItem.getId());
                 inspectItemChoice.setInspectChoiceId(inspectChoiceService.getIdByChoiceValue(a));
                 inspectItemChoicesList.add(inspectItemChoice);
            }

        if(!inspectItemChoicesList.isEmpty()) {
        inspectItemChoiceService.addList(inspectItemChoicesList);
        }

        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }
    @Produces(MediaType.APPLICATION_JSON +";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){
        InspectItem inspectItem = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,InspectItem.class);
        inspectItemChoiceService.deleteByInspectItemId(inspectItem.getId());
        int result=inspectItemService.delete(inspectItem);
        if(result>=0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"操作成功");
        }
        else
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"操作失败");
    }
  }
