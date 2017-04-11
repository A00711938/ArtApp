package a3t.groupartapp.comp3717.artapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

import static android.R.attr.fragment;

public class NavigationTabBarActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_tab_bar);
        //For Whatever reason I'm getting a java.lang.IllegalStateException in
        //here. It doesn't affect the app but FYI.
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Screen 1");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    /**
     * The following method initializes the navigation tab at the bottom of the screen. Also,
     * it sets the recycler view.
     */
    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                                 @Override
                                 public Fragment getItem(int position) {
                                     if(position == 0){
                                         return CollectionPageActivity.newInstance("First Instance");
                                     }
                                     return null;
                                 }

                                 @Override
                                 public int getCount() {
                                     return 2;
                                 }
                             });
//        viewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return 2;
//            }
//
//            @Override
//            public boolean isViewFromObject(final View view, final Object object) {
//                return view.equals(object);
//            }
//
//            @Override
//            public void destroyItem(final View container, final int position, final Object object) {
//                ((ViewPager) container).removeView((View) object);
//            }
//
////            @Override
////            public Object instantiateItem(final ViewGroup container, final int position) {
////                final View view = LayoutInflater.from(
////                        getBaseContext()).inflate(R.layout.item_vp_list, null, false);
////
////                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
////                recyclerView.setHasFixedSize(true);
////                recyclerView.setLayoutManager(new LinearLayoutManager(
////                                getBaseContext(), LinearLayoutManager.VERTICAL, false
////                        )
////                );
////                recyclerView.setAdapter(new RecycleAdapter());
////
////                container.addView(view);
////                return view;
////            }
//        });

//        final String[] colors = getResources().getStringArray(R.array.default_preview);
//
//        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
//        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
//        //In order to obtain a customized drawable icon, instantiate as below.
//        final Drawable ic_android = new IconicsDrawable(this)
//                .icon(FontAwesome.Icon.faw_compass)
//                .color(Color.RED)
//                .sizeDp(24);
//
//        final Drawable ic_book = new IconicsDrawable(this)
//                .icon(FontAwesome.Icon.faw_book)
//                .color(Color.RED)
//                .sizeDp(24);
//
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        //Model drawable
//                        ic_android,
//                        //Model Color
//                        Color.parseColor(colors[0]))
//                        .title("Heart")
//                        .build()
//        );
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        ic_book,
//                        Color.parseColor(colors[1]))
//                        .title("Cup")
//                        .build()
//        );
//
//        navigationTabBar.setModels(models);
//        navigationTabBar.setViewPager(viewPager);
//
//        navigationTabBar.setBehaviorEnabled(true);
//
//        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
//            @Override
//            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
//            }
//
//            @Override
//            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
//                model.hideBadge();
//            }
//        });
//        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(final int position) {
//                if (position == 0){
//                    Log.d("POS 0: ", getSupportActionBar().getTitle().toString());
//                }
//                else if (position == 1) {
//                    Log.d("POS 1: ", getSupportActionBar().getTitle().toString());
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(final int state) {
//
//            }
//        });
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }

}


