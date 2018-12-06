package com.yx.aircheck.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.aircheck.MainUI;
import com.yx.aircheck.R;

import com.yx.aircheck.adapter.CityListAdapter;
import com.yx.aircheck.adapter.ResultListAdapter;
import com.yx.aircheck.base.BaseFragment;
import com.yx.aircheck.bean.City;
import com.yx.aircheck.bean.LocateState;
import com.yx.aircheck.utils.DBManager;
import com.yx.aircheck.view.SideLetterBar;

import java.util.List;

import static android.app.Activity.RESULT_OK;
public class CityPickerFragment extends BaseFragment implements View.OnClickListener{

    public static final String KEY_PICKED_CITY = "picked_city";

    //城市选择
    public static final int REQUEST_CODE_PICK_CITY = 2;

    private ListView mListView;

    private ListView mResultListView;

    private SideLetterBar mLetterBar;

    private EditText searchBox;

    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;

    private ResultListAdapter mResultAdapter;

    private List<City> mAllCities;

    private CityListAdapter mCityAdapter;
    private TextView overlay;
    private DBManager dbManager;
    View mRootView;

    /**
     * 更新定位状态

     */
    public void updateLocateState(int state, String city) {
        mCityAdapter.updateLocateState(state, city);
    }
    @Override
    public View initView(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.cp_fragment_city_list, null);
        mListView = (ListView) mRootView.findViewById(R.id.listview_all_city);
        backBtn = (ImageView)mRootView.findViewById(R.id.back);
        overlay = (TextView) mRootView.findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) mRootView.findViewById(R.id.side_letter_bar);
        searchBox = (EditText) mRootView.findViewById(R.id.et_search);
        emptyView = (ViewGroup) mRootView.findViewById(R.id.empty_view);
        mResultListView = (ListView) mRootView.findViewById(R.id.listview_search_result);
        clearBtn = (ImageView) mRootView.findViewById(R.id.iv_search_clear);
        return mRootView;
    }
    public void initData() {
        dbManager = new DBManager(getActivity());
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(getActivity(), mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
            }
        });
        mResultAdapter = new ResultListAdapter(getActivity(), null);
        mListView.setAdapter(mCityAdapter);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });
        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });
    }
    private void back(String city) {
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch(i){
            case R.id.iv_search_clear:
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
                break;
            case R.id.back:
                getActivity().finish();
                break;
        }
    }
}
