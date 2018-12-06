package com.yx.aircheck.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.yx.aircheck.base.BaseFragment;
import com.yx.aircheck.base.TabBasePager;
import com.yx.aircheck.pager.CheckPrePage;
<<<<<<< HEAD
import com.yx.aircheck.pager.DevicePage;
=======
import com.yx.aircheck.pager.NewDevicePage;
>>>>>>> add dynamic chart
import com.yx.aircheck.pager.MinePage;
import com.yx.aircheck.pager.HistoryPage;
import com.yx.aircheck.pager.ViewPage;
import com.yx.aircheck.view.NoScrollViewPager;
import com.yx.aircheck.R;
import java.util.ArrayList;
import java.util.List;


public class MainContentFragment extends BaseFragment implements OnCheckedChangeListener {

    public static final String TAG = "yanxin";
    public NoScrollViewPager mViewPager;


    public RadioGroup mRadioGroup;

    public List<TabBasePager> pagerList;


    @Override
    public View initView(LayoutInflater inflater) {
        //将layout布局文件转成View对象
        View view = inflater.inflate(R.layout.fragment_main_content, null);
        mViewPager = view.findViewById(R.id.vp_pagers);
        mRadioGroup = view.findViewById(R.id.rg_content_fragment);
        return view;
    }

    @Override
    public void initData() {
        pagerList = new ArrayList<TabBasePager>();
        pagerList.add(new HistoryPage(mActivity));
        pagerList.add(new CheckPrePage(mActivity));
        pagerList.add(new ViewPage(mActivity));
<<<<<<< HEAD
        pagerList.add(new DevicePage(mActivity));
=======
        pagerList.add(new NewDevicePage(mActivity));
>>>>>>> add dynamic chart
        pagerList.add(new MinePage(mActivity));

        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.setAdapter(new ContentPagerAdapter());
        mViewPager.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setCurrentItem(1);
    }


    /**
     * 主界面最下边RadioButton的条目的监听器
     */

    boolean flag = true;
    int index = -1;
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        switch (checkedId) {
            case R.id.rb_content_fragment_history:
                index = 0;
                break;
            case R.id.rb_content_fragment_check:
                index = 1;
                break;
            case R.id.rb_content_fragment_view:
                index = 2;
                break;
            case R.id.rb_content_fragment_device:
                index = 3;
                break;
            case R.id.rb_content_fragment_mine:
                index = 4;
                break;
            default:
                break;
        }
        Log.i(TAG, "onCheckedChanged: index = " + index);
        if (index >= 0 && index < pagerList.size()) {
            mViewPager.setCurrentItem(index);
        }
        //如果用户点击的是查询按钮
        /*if (index == 2) {
            mViewPager.setCurrentItem(index);
            ViewPage viewPage = (ViewPage)pagerList.get(2);
            View view = viewPage.getRootView();
            mViewPager.onClick(view);
            viewPage.loadData();
        }*/

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    class ContentPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, int position) {
            // 初始化每个pager view
            TabBasePager tabBasePager = pagerList.get(position);
            tabBasePager.initData();
            View view = tabBasePager.getRootView();
            container.addView(view);
           /* if(position == 2){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"点击了查看",Toast.LENGTH_LONG).show();
                    }
                });
            }*/
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
    class PageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            index = position;
            if(position == 2){
                ViewPage viewPage = (ViewPage)pagerList.get(position);
<<<<<<< HEAD
                viewPage.loadData();
            }
            if(position == 3){
                DevicePage devicePage = (DevicePage) pagerList.get(position);
=======
                Toast.makeText(getActivity(),"根据地点显示最近一次数据",Toast.LENGTH_LONG);
                viewPage.loadData();
            }
            if(position == 3){
                NewDevicePage devicePage = (NewDevicePage) pagerList.get(position);
>>>>>>> add dynamic chart
                Log.d("device_page", "onPageSelected: "+position);
                devicePage.loadData();
            }
            if(position == 0){
                HistoryPage historyPage = (HistoryPage) pagerList.get(position);
                historyPage.loadData();
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
