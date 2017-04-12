package a3t.groupartapp.comp3717.artapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class FragmentActivity extends android.support.v4.app.FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0:
                        return CollectionPageActivity.newInstance("My Collection");
                    case 1:
                        return NearestArtActivity.newInstance("Nearest Art");
                    default:
                        return null;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal_fragment);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        //In order to obtain a customized drawable icon, instantiate as below.
        final Drawable ic_compass = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_compass)
                .color(Color.RED)
                .sizeDp(24);

        final Drawable ic_book = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_book)
                .color(Color.RED)
                .sizeDp(24);

        models.add(
                new NavigationTabBar.Model.Builder(
                        //Model drawable
                        ic_book,
                        //Model Color
                        Color.parseColor(colors[0]))
                        .title("Collection")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ic_compass,
                        Color.parseColor(colors[1]))
                        .title("Compass")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager);
        navigationTabBar.setBehaviorEnabled(true);


        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

    }


}
