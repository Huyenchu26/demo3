package com.example.admin.demo3.history;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;

public class HistoryContainerFragment extends Fragment {

    private static int TAB_COUNT = 2;


    @BindView(R.id.tabOption)
    TabLayout tabOption;
    @BindView(R.id.viewpagerOption)
    ViewPager viewpagerOption;
    @BindView(R.id.imageBack)
    ImageView imageBack;
    @BindView(R.id.imageRight)
    ImageView imageRight;

    private ViewPagerAdapter adapter;
    private View view;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setupHeader() {
        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                getActivity().getFragmentManager().popBackStack();
//                getActivity().onBackPressed();
            }
        });
        imageRight.setVisibility(View.VISIBLE);
        imageRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                openDateDialog();
            }
        });
    }

    private void openDateDialog() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history_container, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupViewPager();
        setupHeader();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder = null;
    }

    private void setupViewPager() {
        tabOption.setupWithViewPager(viewpagerOption);
        viewpagerOption.setOffscreenPageLimit(TAB_COUNT);
        viewpagerOption.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                // TODO: 4/18/2018 set text filter time
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addTab("Trunk");
        adapter.addTab("CPU time");
        viewpagerOption.setAdapter(adapter);

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) return new HistoryTrunkFragment();
            else return new HistoryCPUFragment();
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        void addTab(String title) {
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
