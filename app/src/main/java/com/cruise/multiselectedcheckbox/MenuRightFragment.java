package com.cruise.multiselectedcheckbox;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cruise.multiselectedcheckbox.bean.ResourceItem;
import com.cruise.multiselectedcheckbox.views.MultiSelectCheckBox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by cruise on 2015/8/25.
 * 右滑抽屉，买衣服筛选的多选框
 */
public class MenuRightFragment extends Fragment {

    public static Map<Integer, ResourceItem> mSizeData = new HashMap<>();  //�������������
    public static Map<Integer, ResourceItem> mClothesLengthData = new HashMap<>();
    public static Map<Integer, Integer> option_chosen = new HashMap<>();
    public static Set<Integer> size_chosen = new HashSet<>();
    public static Set<Integer> clothes_length_chosen = new HashSet<>();
    private Context context;
    private TextView filtrate_clear_all,  size_clear, clothes_length_clear;
    private LinearLayout size_layout, clothes_length_layout;
    private MultiSelectCheckBox sscb_size, sscb_clothes_length;

    private LinearLayout size_choose_layout, clothes_length_choose_layout;
    private CallBack mCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        View mView = inflater.inflate(R.layout.fragment_menu_right, container, false);
        filtrate_clear_all = (TextView) mView.findViewById(R.id.filtrate_clear_all);
        size_clear = (TextView) mView.findViewById(R.id.size_clear);
        size_layout = (LinearLayout) mView.findViewById(R.id.size_layout);
        clothes_length_clear = (TextView) mView.findViewById(R.id.clothes_length_clear);
        clothes_length_layout = (LinearLayout) mView.findViewById(R.id.clothes_length_layout);
        size_choose_layout = (LinearLayout) mView.findViewById(R.id.size_choose_layout);
        clothes_length_choose_layout = (LinearLayout) mView.findViewById(R.id.clothes_length_choose_layout);

        filtrate_clear_all.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//�»���
        filtrate_clear_all.setTextColor(getResources().getColor(R.color.tip_text));
        size_clear.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//�»���
        size_clear.setTextColor(getResources().getColor(R.color.tip_text));
        size_clear.setVisibility(View.INVISIBLE);
        clothes_length_clear.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//�»���
        clothes_length_clear.setTextColor(getResources().getColor(R.color.tip_text));
        clothes_length_clear.setVisibility(View.INVISIBLE);

        initView(mView);
        initListener();

        return mView;

    }

    private void initListener() {

        filtrate_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllData();
            }
        });

        size_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sscb_size.resetData();
//                option_chosen.remove(0);
                size_clear.setVisibility(View.INVISIBLE);
                size_chosen.clear();
            }
        });

        clothes_length_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sscb_clothes_length.resetData();
                clothes_length_chosen.clear();
                clothes_length_clear.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void hidePart() {
        size_choose_layout.setVisibility(View.GONE);
        clothes_length_choose_layout.setVisibility(View.GONE);
    }


    public void showAll() {
        size_choose_layout.setVisibility(View.VISIBLE);
        clothes_length_choose_layout.setVisibility(View.VISIBLE);
    }


    public void clearAllData() {
        sscb_size.resetData();
        sscb_clothes_length.resetData();
        option_chosen.clear();
        size_chosen.clear();
        clothes_length_chosen.clear();
        size_clear.setVisibility(View.INVISIBLE);
        clothes_length_clear.setVisibility(View.INVISIBLE);
    }



    private void initView(View mView) {

        int checkBoxSize = 0, checkBoxClothesLength = 2; //��ѡ���type


        sscb_size = new MultiSelectCheckBox(context, checkBoxSize);
        ResourceItem ri = new ResourceItem();
        ri.setName("XS");
        ri.setId(0);
        mSizeData.put(0, ri);
        ResourceItem ri2 = new ResourceItem();
        ri2.setName("S");
        ri2.setId(1);
        mSizeData.put(1, ri2);
        ResourceItem ri3 = new ResourceItem();
        ri3.setName("M");
        ri3.setId(2);
        mSizeData.put(2, ri3);
        ResourceItem ri4 = new ResourceItem();
        ri4.setName("L");
        ri4.setId(3);
        mSizeData.put(3, ri4);
        ResourceItem ri5 = new ResourceItem();
        ri5.setName("XL");
        ri5.setId(4);
        mSizeData.put(4, ri5);
        sscb_size.setData(mSizeData);
        sscb_size.setOnSelectListener(new OnSSChkClickEvent());
        size_layout.addView(sscb_size);


        sscb_clothes_length = new MultiSelectCheckBox(context, checkBoxClothesLength);
        ResourceItem resourceItem = new ResourceItem();
        resourceItem.setName("short");
        resourceItem.setId(0);
        mClothesLengthData.put(0, resourceItem);
        ResourceItem resourceItem2 = new ResourceItem();
        resourceItem2.setName("middle");
        resourceItem2.setId(1);
        mClothesLengthData.put(1, resourceItem2);
        ResourceItem resourceItem3 = new ResourceItem();
        resourceItem3.setName("long");
        resourceItem3.setId(2);
        mClothesLengthData.put(2, resourceItem3);
        ResourceItem resourceItem4 = new ResourceItem();
        resourceItem4.setName("longer");
        resourceItem4.setId(3);
        mClothesLengthData.put(3, resourceItem4);
        sscb_clothes_length.setData(mClothesLengthData);
        sscb_clothes_length.setOnSelectListener(new OnSSChkClickEvent());
//        clothes_length_layout.setPadding(35, 10, 10, 10);
        clothes_length_layout.addView(sscb_clothes_length);

    }


    public void setJsonCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }



    public interface CallBack {
        public void setFilterData(HashMap<String, String> filterArg);
    }

    private class OnSSChkClickEvent implements MultiSelectCheckBox.OnSelectListener {
        @Override
        public void onSelect(View v, int position, int checkBoxType, boolean isChecked, int dataSize) {
            if (isChecked) {
                switch (checkBoxType) {
                    case 0:
                        size_chosen.add(position);               //��ѡ���checkbox�����ݼӵ�set��
                        size_clear.setVisibility(View.VISIBLE);  //ͬʱ����հ�ť��ʾ
                        break;
                    case 2:
                        clothes_length_chosen.add(position);
                        clothes_length_clear.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            } else {
                switch (checkBoxType) {
                    case 0:
                        size_chosen.remove(position);
                        if (dataSize == 0) size_clear.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        clothes_length_chosen.remove(position);
                        if (dataSize == 0) clothes_length_clear.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }

            }
        }
    }

}
