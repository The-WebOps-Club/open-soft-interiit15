package opensoft.search;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;

import opensoft.com.opensoft.MainActivity;
import opensoft.com.opensoft.R;
/**
 * Created by vigneshm on 25/01/15.
 */
/**
 * Created by Arvind on 24-01-2015.
 */
public class AdapterListElement extends RecyclerView.Adapter<AdapterListElement.ListItemViewHolder>{
        private List<ListElement> items;
        public List<ListElement> filtered_items;
        static Context c;

    public AdapterListElement(List<ListElement> modelData, Context c) {
            if (modelData == null) {
                throw new IllegalArgumentException(
                        "modelData must not be null");
            }
            this.items = modelData;
            this.filtered_items=new ArrayList<>();
            this.c = c;
        }

        @Override
        public ListItemViewHolder onCreateViewHolder(
                ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.list_element,
                            viewGroup,
                            false);
            return new ListItemViewHolder(itemView);
        }



        @Override
        public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
            viewHolder.currentItem = filtered_items.get(position);
            ListElement model = filtered_items.get(position);
            viewHolder.txtTitle.setText(model.title);
            viewHolder.txtInfo.setText(model.info);
            viewHolder.imgViewIcon.setImageResource(R.drawable.ic_launcher);
        }

        @Override
        public int getItemCount() {
            return filtered_items.size();
        }

        public List<ListElement> getList() {
            return filtered_items;
        }

        public final static class ListItemViewHolder
                extends RecyclerView.ViewHolder {
            ImageView imgViewIcon;
            TextView txtTitle;
            TextView txtInfo;

            public ListElement currentItem;

            public ListItemViewHolder(final View itemView) {
                super(itemView);
                imgViewIcon = (ImageView) itemView.findViewById(R.id.icon);
                txtInfo = (TextView) itemView.findViewById(R.id.info);
                txtTitle = (TextView) itemView.findViewById(R.id.title);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_container, new MainActivity.ShowDataFragment("id"));
                        getSupportActionBar().setTitle("Browse");
                    }
                });
            }
        }
    public void filter(CharSequence charSequence, Context c) {
        //System.out.println(charSequence);
        filtered_items = new ArrayList<>();
        if(charSequence.length()>0){
        for(ListElement l : items) {
            //System.out.println(l.info+" "+l.title+" "+(l.info.contains(charSequence) || l.info.contains(charSequence)));
            if(l.title.contains(charSequence) || l.info.contains(charSequence)) {
                filtered_items.add(l);
                System.out.println(l.title);
            }
        }}
        notifyDataSetChanged();
    }

}
