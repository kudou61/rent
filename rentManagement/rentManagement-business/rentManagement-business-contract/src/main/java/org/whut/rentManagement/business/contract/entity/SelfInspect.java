package org.whut.rentManagement.business.contract.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 上午8:46
 * To change this template use File | Settings | File Templates.
 */
public class SelfInspect {
    private long id;
    private long contractId;
    private long selfInspectDeviceId;
    private String selfInspectMan;
    private Date  selfInspectTime;
    private String selfInspectStatus;
    private long  appId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public long getSelfInspectDeviceId() {
        return selfInspectDeviceId;
    }

    public void setSelfInspectDeviceId(long selfInspectDeviceId) {
        this.selfInspectDeviceId = selfInspectDeviceId;
    }

    public String getSelfInspectMan() {
        return selfInspectMan;
    }

    public void setSelfInspectMan(String selfInspectMan) {
        this.selfInspectMan = selfInspectMan;
    }

    public Date getSelfInspectTime() {
        return selfInspectTime;
    }

    public void setSelfInspectTime(Date selfInspectTime) {
        this.selfInspectTime = selfInspectTime;
    }

    public String getSelfInspectStatus() {
        return selfInspectStatus;
    }

    public void setSelfInspectStatus(String selfInspectStatus) {
        this.selfInspectStatus = selfInspectStatus;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
