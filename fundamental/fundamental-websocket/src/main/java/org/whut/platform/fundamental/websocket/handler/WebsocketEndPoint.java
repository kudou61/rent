package org.whut.platform.fundamental.websocket.handler;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.whut.platform.business.user.security.MyUserDetail;
import org.whut.platform.fundamental.communication.api.MessageDispatcher;
import org.springframework.security.core.context.SecurityContext;
import org.whut.platform.fundamental.logger.PlatformLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tgq
 * Date: 14-8-19
 * Time: 下午8:07
 * To change this template use File | Settings | File Templates.
 */
public class WebsocketEndPoint extends TextWebSocketHandler {
    private static final PlatformLogger logger = PlatformLogger.getLogger(WebsocketEndPoint.class);
    private static List<WebSocketSession> wssList;   // ReceivedMessage对应的 WebSocketSession列表
    private static Map<WebSocketSession,String> map=new HashMap<WebSocketSession, String>(); // 一个session对应一个message 维护 session和message的关系
    private static Map<String,List<WebSocketSession>> wsImpMap=new HashMap<String, List<WebSocketSession>>(); //一个message对应多个session，供通过websocket向前台发送消息使用
    private static Map<WebSocketSession,Long> sessionAndAppIdMap= new HashMap<WebSocketSession,Long>();  //维护session和appId的关系，
    private static Map<String,WebSocketSession> wsuImpMap=new HashMap<String,WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //以下三行获得此session对应的租户
        Map<String,Object> sessionAttributesMap= session.getHandshakeAttributes();
        Object credential=((SecurityContext) sessionAttributesMap.values().toArray()[0]).getAuthentication().getPrincipal();
        Long appId=((MyUserDetail) credential).getAppId();
        Long userId=((MyUserDetail) credential).getId();
        logger.info("appId= " + appId);
        logger.info("userId= " + userId);
        //维护起session和appId的关系，一个session可以对应多个appId
        sessionAndAppIdMap.put(session,appId);
        //得到前台发来的消息，里面可能包含多个sNum
        String  ReceivedMessage = message.getPayload();
        map.put(session,ReceivedMessage);
        JSONObject dataJson=new JSONObject(ReceivedMessage);
        //得到sNum的数组
        JSONArray sNum= dataJson.getJSONArray("sensors");
        //得到前台发来的命令：取消订阅、订阅
        String command=dataJson.getString("c");
        if (command.equals("cancelSubscribe")){    //取消订阅
                logger.info("进行取消订阅！");
                logger.info("wsImpMap1为"+wsImpMap);
                cancelSubscribe(ReceivedMessage,appId,userId);
                logger.info("wsImpMap1为"+wsImpMap);
        } else{
            if (command.equals("Subscribe")){    //订阅
                 for(int i=0;i<sNum.length();i++){
                    wssList=wsImpMap.get(appId+":"+sNum.get(i).toString());
                   if(wssList==null){
                       wssList=new ArrayList<WebSocketSession>();
                   }
                   wssList.add(session);
                   wsImpMap.put(appId+":"+sNum.get(i).toString(),wssList);
                   wsuImpMap.put(appId+":"+userId+":"+sNum.get(i).toString(),session);
                }
        }}
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        cancelSubscribe(session);
        logger.info("连接关闭！");
        super.afterConnectionClosed(session, status);
    }

    public void cancelSubscribe(WebSocketSession session) throws JSONException {
        String msg= map.get(session);     //获得接收到的sNum 数组
        Long appId=sessionAndAppIdMap.get(session);
        JSONObject dataJson=new JSONObject(msg);
        JSONArray sNum= dataJson.getJSONArray("sensors");
        for(int i=0;i<sNum.length();i++){
            logger.info("wsImpMap为"+wsImpMap);
            List<WebSocketSession> webSocketSessionList=wsImpMap.get(appId+":"+sNum.get(i).toString());
            webSocketSessionList.remove(session);
            if(webSocketSessionList.isEmpty()){
                wsImpMap.remove(appId+":"+sNum.get(i).toString());
            }
            logger.info("wsImpMap为"+wsImpMap);
        }
        map.remove(session);
        sessionAndAppIdMap.remove(session);
    }

    public void cancelSubscribe(String msg,Long appId,Long userId) throws JSONException {
        JSONObject dataJson=new JSONObject(msg);
        JSONArray sNum= dataJson.getJSONArray("sensors");
        for(int i=0;i<sNum.length();i++){
           WebSocketSession webSocketSession=wsuImpMap.get(appId+":"+userId+":"+sNum.get(i).toString());
           List<WebSocketSession> webSocketSessionList=wsImpMap.get(appId+":"+sNum.get(i).toString());
           webSocketSessionList.remove(webSocketSession);
            if(webSocketSessionList.isEmpty()){
                wsImpMap.remove(appId+":"+sNum.get(i).toString());
            }
           wsuImpMap.remove(appId+":"+userId+":"+sNum.get(i).toString());
        }
    }

    public static Map<String,List<WebSocketSession>> getWsImpMap(){
        return wsImpMap;
    }
}