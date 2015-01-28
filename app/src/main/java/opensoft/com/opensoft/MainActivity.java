package opensoft.com.opensoft;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.widget.AdapterViewCompat;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import opensoft.browse.AdapterCardElement;
import opensoft.browse.CardElement;
import opensoft.search.AdapterListElement;
import opensoft.search.ListElement;
import opensoft.util.SwipeableRecyclerViewTouchListener;


public class MainActivity extends ActionBarActivity {
    private CharSequence mTitle="OpenSoft";
    private NavDrawerItem[] datalist;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, new BrowseFragment());
        fragmentTransaction.commit();
        datalist = data.navtitles;
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        // Set the adapter for the list view
        drawerList.setAdapter(new NavDrawerListAdapter(getApplicationContext(), Arrays.asList(datalist)));
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
        public String imgpath[];

        public ShowDataFragment(String id) {
            //get object - fills caption, content
            caption = "caption";
            content = "This is the list of machines that have at some point in the past authenticated against your account. The time up to which the authentication is valid and the amount of data that has been downloaded on each machine is shown against the respective machine ID. Note that this information is updated here only once in 5 minutes, so it may not be completely up to date.\n" +
                    "\n" +
                    "The download column is showing only your usage for today: 28 Jan, 2015.\n" +
                    "\n" +
                    "If you find any machines here that you feel you have not used, someone may have got your password and misused it. Remember that you are responsible for all activity related to your account. If this happens, you should immediately change your password (use the links provided at http://cc.iitm.ac.in/ for this purpose).";
            imgpath=["path1","path2","path3"];
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

    public static class SearchFragment extends Fragment {
        AdapterListElement adapter;
        public SearchFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listmain);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);

            List<ListElement> data= new ArrayList<ListElement>();
            data.add(new ListElement("first","caption"));
            data.add(new ListElement("second"));

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
            list.add(new CardElement("first","content"));
            list.add(new CardElement("second","no content"));
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
}
