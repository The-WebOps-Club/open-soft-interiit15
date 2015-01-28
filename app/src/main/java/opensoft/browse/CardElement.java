package opensoft.browse;

import opensoft.com.opensoft.R;

/**
 * Created by vigneshm on 26/01/15.
 */
public class CardElement {
    public String title;
    public String content;
    public int icon_ref;
    public CardElement(String title,String content){
        this.title=title;
        this.content=content;
        icon_ref= R.drawable.ic_launcher;
    }
    public CardElement(String title,String content,int icon_ref){
        this.title=title;
        this.content=content;
        this.icon_ref= icon_ref;
    }
}
