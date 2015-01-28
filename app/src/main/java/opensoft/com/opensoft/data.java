package opensoft.com.opensoft;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vigneshm on 27/01/15.
 */
public class data {
    private static NavDrawerItem[] navtitles={new NavDrawerItem("Browse",R.drawable.ic_launcher),
            new NavDrawerItem("Search",R.drawable.ic_launcher),
            new NavDrawerItem("Demand",R.drawable.ic_launcher)};
    public static List<NavDrawerItem> getNavDrawerItems(){
        return Arrays.asList(navtitles);
    }
}
