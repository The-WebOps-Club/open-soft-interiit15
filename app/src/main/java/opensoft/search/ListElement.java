package opensoft.search;

import opensoft.com.opensoft.R;

/**
 * Created by vigneshm on 24/01/15.
 */
public class ListElement {
    public int icon;
    public String title;
    public String info;
    public int id;
    public ListElement(String title,String info,int id){
        this.title=title;
        this.info=info;
        this.icon= R.drawable.ic_launcher;
        this.id=id;
    }
    public ListElement(String title,String info,int iconRef,int id){
        this.title=title;
        this.info=info;
        this.icon=iconRef;
        this.id=id;
    }
    public ListElement(String title,int id){
        this.title=title;
        this.info="";
        this.icon= R.drawable.ic_launcher;
        this.id=id;
    }
}
