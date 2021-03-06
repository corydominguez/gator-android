package com.corydominguez.gator.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.corydominguez.gator.R;
import com.corydominguez.gator.fragments.LinkDetailFragment;
import com.corydominguez.gator.models.Link;

import java.util.ArrayList;

public class DetailActivity extends FragmentActivity {
    public ArrayList<Link> linkList;
    public int pos;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getActionBar();
        assert (actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);

        linkList = getIntent().getParcelableArrayListExtra("linkList");
        pos  = getIntent().getExtras().getInt("pos");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("linkList", linkList);
        setResult(RESULT_OK, data);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("linkList", linkList);
        setResult(RESULT_OK, data);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LinkDetailFragment ldf = new LinkDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("link", linkList.get(position));
            ldf.setArguments(bundle);
            return ldf;
        }

        @Override
        public int getCount() {
            return linkList.size();
        }
    }
}
