package opensoft.databases;

import org.json.JSONArray;

/**
 * Created by stejasvin on 1/28/2015.
 */
public class Content {

    int iLocalId, iServerId;
    String sTitle, sSummary, sContent, sRevId, sPageId, sServerTimeStamp, sLocalTimeStamp;
    JSONArray saImagePath;

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

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsSummary() {
        return sSummary;
    }

    public void setsSummary(String sSummary) {
        this.sSummary = sSummary;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public String getsRevId() {
        return sRevId;
    }

    public void setsRevId(String sRevId) {
        this.sRevId = sRevId;
    }

    public String getsPageId() {
        return sPageId;
    }

    public void setsPageId(String sPageId) {
        this.sPageId = sPageId;
    }

    public String getsServerTimeStamp() {
        return sServerTimeStamp;
    }

    public void setsServerTimeStamp(String sServerTimeStamp) {
        this.sServerTimeStamp = sServerTimeStamp;
    }

    public String getsLocalTimeStamp() {
        return sLocalTimeStamp;
    }

    public void setsLocalTimeStamp(String sLocalTimeStamp) {
        this.sLocalTimeStamp = sLocalTimeStamp;
    }

    public JSONArray getSaImagePath() {
        return saImagePath;
    }

    public void setSaImagePath(JSONArray saImagePath) {
        this.saImagePath = saImagePath;
    }
}