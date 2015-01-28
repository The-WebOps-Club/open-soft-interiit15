package opensoft.browse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import opensoft.com.opensoft.R;

/**
 * Created by vigneshm on 26/01/15.
 */
public class AdapterCardElement extends RecyclerView.Adapter<AdapterCardElement.CardElementHolder>{
    private List<CardElement> list=new ArrayList<>();
    private List<CardElement> filtered_list=new ArrayList<>();
    public AdapterCardElement(List<CardElement> list) {
        this.filtered_list.addAll(list);
        this.list.addAll(list);
    }

    public int getItemCount() {
        return filtered_list.size();
    }

    public void refreshData(){
        System.out.println("refreshing");
        filtered_list.clear();
        filtered_list.addAll(list);
        for(CardElement c:filtered_list) System.out.println(c.title);
        for(CardElement c:list) System.out.println(c.title);
        notifyDataSetChanged();
    }
    public void removeFromList(int pos){
        filtered_list.remove(pos);
        notifyItemRemoved(pos);
    }
    public void onBindViewHolder(CardElementHolder contactViewHolder, int i) {
        CardElement cardElement = filtered_list.get(i);
        contactViewHolder.title.setText(cardElement.title);
        contactViewHolder.content.setText(cardElement.content);
        contactViewHolder.icon.setImageResource(cardElement.icon_ref);
    }

    public CardElementHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_element, viewGroup, false);

        CardElementHolder holder =new CardElementHolder(itemView);
        return holder;
    }
    public void swap(int pos1,int pos2){
        Collections.swap(filtered_list,pos1,pos2);
        notifyDataSetChanged();
    }
    public static class CardElementHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView content;
        protected ImageView icon;

        public CardElementHolder(View v) {
            super(v);
            title =  (TextView) v.findViewById(R.id.title_card);
            content = (TextView)  v.findViewById(R.id.content_card);
            icon = (ImageView)  v.findViewById(R.id.icon_card);
        }
    }
}
