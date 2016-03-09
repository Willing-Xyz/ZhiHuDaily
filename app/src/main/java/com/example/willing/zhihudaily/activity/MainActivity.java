package com.example.willing.zhihudaily.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.fragment.MainFragment;
import com.example.willing.zhihudaily.fragment.SubjectFragment;
import com.example.willing.zhihudaily.model.SubjectsEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Willing on 2/28/2016/028.
 */
public class MainActivity extends BaseActivity implements MainFragment.OnPageChangedListener
{
    private static final String LASTEST_NEWS_URL = "http://news-at.zhihu.com/api/4/news/latest";
    private static final String SUBJECT_LIST_URL = "http://news-at.zhihu.com/api/4/themes";


    private Toolbar mToolBar;
    private NavigationView mNavigation;
    private DrawerLayout mDrawer;
    private DrawerLayout.DrawerListener mDrawerListener;
    private SubjectsEntity mSubjects;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if (getSupportFragmentManager().findFragmentById(R.id.story_list) == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.story_list, new MainFragment()).commit();
        }

        initView();
        setupListener();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDrawer.removeDrawerListener(mDrawerListener);
    }

    private void setupListener() {

        mDrawerListener = new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {

                OkHttpClient client = HttpUtils.getInstance();

                Request request = new Request.Builder().url(SUBJECT_LIST_URL).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        mSubjects = gson.fromJson(response.body().string(), SubjectsEntity.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Menu menu = mNavigation.getMenu();
                                menu.clear();
                                menu.add(0, 0, 0, "首页");
                                for (int i = 0; i < mSubjects.getOthers().size(); ++i) {
                                    String name = mSubjects.getOthers().get(i).getName();
                                    menu.add(0, i + 1, i + 1, name);

                                }
                            }
                        });

                    }
                });
            }
        };
        mDrawer.addDrawerListener(mDrawerListener);
        mDrawer.addDrawerListener(mDrawerToggle);

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                Fragment frag = null;

                if (id == 0)
                {
                    frag = new MainFragment();

                }
                else {

                    frag = SubjectFragment.newInstance(mSubjects.getOthers().get(id - 1).getId());

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.story_list, frag).commit();

                mDrawer.closeDrawer(GravityCompat.START);


                return true;
            }
        });
    }

    private void initView() {

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mNavigation = (NavigationView) findViewById(R.id.navigation);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(mToolBar);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolBar, 0, 0);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_items, menu);
//
//        return true;
//    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mDrawerToggle.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }

    @Override
    public void onPageChanged(String title) {
        mToolBar.setTitle(title);
    }
}
