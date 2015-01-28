package opensoft.databases;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by stejasvin on 1/28/2015.
 */
public class User {

    int iLocalId, iServerId;
    String sName, sPhone,sEmail,sOccupation,sPlace,sCreatedTime,sLastSyncedTime,sDataSize;
    JSONArray saPriority;

    public int getiLocalId() {
        return iLocalId;
    }

    public void setiLocalId(int iLocalId) {
        this.iLocalId = iLocalId;
    }

    public int getiServerId() {
        return iServerId;
    }

    public void setiServerId(int iServerId) {
        this.iServerId = iServerId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsOccupation() {
        return sOccupation;
    }

    public void setsOccupation(String sOccupation) {
        this.sOccupation = sOccupation;
    }

    public String getsPlace() {
        return sPlace;
    }

    public void setsPlace(String sPlace) {
        this.sPlace = sPlace;
    }

    public String getsCreatedTime() {
        return sCreatedTime;
    }

    public void setsCreatedTime(String sCreatedTime) {
        this.sCreatedTime = sCreatedTime;
    }

    public String getsLastSyncedTime() {
        return sLastSyncedTime;
    }

    public void setsLastSyncedTime(String sLastSyncedTime) {
        this.sLastSyncedTime = sLastSyncedTime;
    }

    public String getsDataSize() {
        return sDataSize;
    }

    public void setsDataSize(String sDataSize) {
        this.sDataSize = sDataSize;
    }

    public JSONArray getSaPriority() {
        return saPriority;
    }

    public void setSaPriority(JSONArray saPriority) {
        this.saPriority = saPriority;
    }
}
