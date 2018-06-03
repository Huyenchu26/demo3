package com.example.admin.demo3.history;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.admin.demo3.data.ApiClient;
import com.example.admin.demo3.data.ApiHelper;
import com.example.admin.demo3.dialog.DateDialog;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.DateUtils;
import com.example.admin.demo3.util.LogUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static List<Vehicle> vehicleList = new ArrayList<>();

    Date dateCurrent = Calendar.getInstance().getTime();
    String imei;
    String startDate = "";
    String endDate = DateUtils.dateToStringSent(dateCurrent);

    public static HistoryContainerFragment newInstance(String imei) {
        return new HistoryContainerFragment().setImei(imei);
    }

    public HistoryContainerFragment setImei(String imei) {
        this.imei = imei;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupConnect();
            }
        }, 500);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal)); //2016/11/16 12:08:43
    }

    private void setupHeader() {
        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
//                getActivity().getFragmentManager().popBackStack();
                getActivity().onBackPressed();
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

    DateDialog dateDialog;
    private void openDateDialog() {
            if (dateDialog != null && dateDialog.isShowing()) return;
            dateDialog = new DateDialog(getContext());
            dateDialog.setCanceledOnTouchOutside(true);
            dateDialog.setOnChooseListener(new DateDialog.OnChooseListener() {
                @Override
                public void onDone(String startDate, String endDate) {
                    // TODO: 4/19/2018 some thing with dates
                }
            });
            dateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dateDialog.release();
                }
            });
            dateDialog.show();

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
            if (position == 0) return HistoryTrunkFragment.newInstance(imei, startDate, endDate);
            else return HistoryCPUFragment.newInstance(imei, startDate, endDate);
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

    private void setupConnect() {
        ApiClient client = ApiHelper.getClient().create(ApiClient.class);
        /** Call the method with parameter in the interface to get the notice data*/
        Call<List<Vehicle>> call = client.loadHistory(imei, startDate, endDate);
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if(response.isSuccessful()) {
                    vehicleList.addAll(response.body());
                    LogUtil.e("isSuccessful: " + response.toString());
                } else {
                    LogUtil.e("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                t.printStackTrace();
                LogUtil.e("onFailure History: " + t.getMessage());
            }
        });
    }
}
