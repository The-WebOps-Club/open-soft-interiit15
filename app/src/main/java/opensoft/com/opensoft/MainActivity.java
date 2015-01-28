package opensoft.com.opensoft;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import opensoft.browse.AdapterCardElement;
import opensoft.browse.CardElement;
import opensoft.databases.Content;
import opensoft.databases.ContentDatabaseHandler;
import opensoft.databases.User;
import opensoft.databases.UserDatabaseHandler;
import opensoft.search.AdapterListElement;
import opensoft.search.ListElement;
import opensoft.util.DragSortRecycler;
import opensoft.util.SwipeableRecyclerViewTouchListener;


public class MainActivity extends ActionBarActivity {
    private URL = "192.168.1.4:8000/";
    private CharSequence mTitle="OpenSoft";
    private List<NavDrawerItem> datalist;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

        SharedPreferences pref = getSharedPreferences("mpowered",0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(pref.getBoolean("register",false)) {
            fragmentTransaction.add(R.id.frame_container, new RegisterFragment());
        }
        else {
            fragmentTransaction.add(R.id.frame_container, new BrowseFragment());
        }
        fragmentTransaction.commit();
        datalist = data.getNavDrawerItems();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        // Set the adapter for the list view
        drawerList.setAdapter(new NavDrawerListAdapter(getApplicationContext(),datalist));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.string.app_name,  /* "open drawer" description */
                R.string.app_name /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(mTitle);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            System.out.println(position);
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(position==0) {
            fragmentTransaction.replace(R.id.frame_container, new BrowseFragment());
            setTitle("Browse");
        }
        if(position==1) {
            fragmentTransaction.replace(R.id.frame_container, new SearchFragment());
            setTitle("Search");
        }
        if(position==2){
            fragmentTransaction.replace(R.id.frame_container, new DemandFragment());
            setTitle("Demand");
        }
        fragmentTransaction.commit();

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle=title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ShowDataFragment extends Fragment {

        public String id;
        public String caption;
        public String content;
        public JSONArray imgpath;

        public ShowDataFragment(int id) {
            //get object - fills caption, content
            Content info = ContentDatabaseHandler.getContent(id);
            caption = info.getsTitle();
            content =info.getsContent();
            imgpath=info.getSaImagePath();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_show_content, container, false);
            TextView txtcaption = (TextView) findViewById(R.id.content_title);
            TextView txtcontent = (TextView) findViewById(R.id.content_data);

            txtcaption.setText(caption);
            txtcontent.setText(content);

            LinearLayout image_holder = (LinearLayout) findViewById(R.id.customisable_layout);

            for(String p:imgpath) {
                ImageView img = new ImageView(rootView.getContext());
                img.setImageResource(R.drawable.ic_launcher);
                image_holder.addView(img);
            }
            return rootView;
        }
        }
    }

    public static class RegisterFragment extends Fragment {
        public RegisterFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_register, container, false);
            Button submit_register = (Button) findViewById(R.id.but_register);

            final EditText edit_name = (EditText) findViewById(R.id.edit_name);
            final EditText edit_phone = (EditText) findViewById(R.id.edit_phone);
            final EditText edit_place = (EditText) findViewById(R.id.edit_place);
            final EditText edit_email = (EditText) findViewById(R.id.edit_email);
            final EditText edit_occupation = (EditText) findViewById(R.id.edit_occupation);

            submit_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO : send data to server

                    SharedPreferences pref;
                    SharedPreferences.Editor editor;
                    pref = rootView.getContext().getSharedPreferences("mpowered",0);
                    editor = pref.edit();
                    editor.putBoolean("register",true);
                    Calendar c = Calendar.getInstance();
                    new AsyncGet(rootView.getContext(), URL + "user/create?name="+edit_name.getText()+"&phone="+edit_phone.getText()+"&email="+edit_email.getText()+"&occupation="+edit_occupation.getText()+"&region="+edit_place.getText(), new AsyncGet.AsyncResult() {
                        @Override
                        public void gotResult(String s) {
                            JSONObject obj = new JSONObject(s);
                            if(obj.result) {
                                Toast.makeText(rootView.getContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    UserDatabaseHandler.addUser(new User(edit_name.getText(),edit_phone.getText(),edit_email.getText(),edit_occupation.getText(),edit_place.getText(),c.getTime().toString(),c.getTime().toString()));
                    editor.putInt("user_id",0);
                    editor.commit();
                }
            });
            return rootView;
        }

    }

    public static class SearchFragment extends Fragment {
        AdapterListElement adapter;
        public SearchFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listmain);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);

            List<ListElement> data= new ArrayList<ListElement>();
            ArrayList<Content> allinfo = ContentDatabaseHandler.getAllContent();

            for(Content inf:allinfo) {
                data.add(new ListElement(inf.getsTitle(),inf.getsSummary()));
            }
            final AdapterListElement adapterListElement = new AdapterListElement(data,rootView.getContext());
            recyclerView.setAdapter(adapterListElement);
            this.adapter = adapterListElement;
            EditText searchbar=(EditText) rootView.findViewById(R.id.search_bar);
            searchbar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    ;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.filter(s, rootView.getContext());
                    //adapterListElement.notifyDataSetChanged();
                    recyclerView.swapAdapter(adapter, false);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    ;
                }
            });
            return rootView;
        }
    }

    public static class BrowseFragment extends Fragment {
        AdapterCardElement adapter ;
        public BrowseFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_browse, container, false);
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.card_list);
            final List<CardElement> list=new ArrayList<>();

            ArrayList<Content> allinfo = ContentDatabaseHandler.getAllContent();
            for(Content inf:allinfo) list.add(new CardElement(inf.getsTitle(), inf.getsContent()));

            final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.browse_swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    adapter.refreshData();
                    recyclerView.swapAdapter(adapter,false);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            adapter=new AdapterCardElement(list);
            recyclerView.setAdapter(adapter);
            SwipeableRecyclerViewTouchListener swipeTouchListener =
                    new SwipeableRecyclerViewTouchListener(recyclerView,
                            new SwipeableRecyclerViewTouchListener.SwipeListener() {
                                @Override
                                public boolean canSwipe(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        adapter.removeFromList(position);
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        adapter.removeFromList(position);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            });
            recyclerView.addOnItemTouchListener(swipeTouchListener);
            return rootView;
        }
    }
    public static class DemandFragment extends Fragment{
        public DemandFragment(){
            ;
        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_demand, container, false);
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.demand_list);
            final List<CardElement> list=new ArrayList<>();

            SharedPreferences pref = Context.getSharedPreferences("mpowered",0);
            int id = pref.getInt("user_id",0);

            JSONArray priority_list = UserDatabaseHandler.getUser(id).getSaPriority();

            for(int i=0;i<priority_list.length();i++) {
                list.add(new CardElement(p.getJSONObject(i).getString("str")));
            }

            final AdapterCardElement adapter=new AdapterCardElement(list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
//            final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.demand_swipe_refresh_layout);
//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    swipeRefreshLayout.setRefreshing(true);
//                    adapter.refreshData();
//                    recyclerView.swapAdapter(adapter,false);
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            });
            DragSortRecycler dragSortRecycler = new DragSortRecycler();
            //dragSortRecycler.setViewHandleId(R.drawable.ic_launcher); //View you wish to use as the handle
            dragSortRecycler.setLeftDragArea(160);
            dragSortRecycler.setOnItemMovedListener(new DragSortRecycler.OnItemMovedListener() {
                @Override
                public void onItemMoved(int from, int to) {
                    System.out.println("onItemMoved " + from + " to " + to);
                    adapter.swap(from,to);
                }
            });
            SwipeableRecyclerViewTouchListener swipeTouchListener =
                    new SwipeableRecyclerViewTouchListener(recyclerView,
                            new SwipeableRecyclerViewTouchListener.SwipeListener() {
                                @Override
                                public boolean canSwipe(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        adapter.removeFromList(position);
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        adapter.removeFromList(position);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            });
            recyclerView.addOnItemTouchListener(swipeTouchListener);
            recyclerView.addItemDecoration(dragSortRecycler);
            recyclerView.addOnItemTouchListener(dragSortRecycler);
            recyclerView.setOnScrollListener(dragSortRecycler.getScrollListener());
            return rootView;
        }
    }
}

