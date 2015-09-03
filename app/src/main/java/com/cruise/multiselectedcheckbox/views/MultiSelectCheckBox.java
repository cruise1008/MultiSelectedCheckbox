package com.cruise.multiselectedcheckbox.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.cruise.multiselectedcheckbox.R;
import com.cruise.multiselectedcheckbox.bean.ResourceItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cruise on 2015/2/16.
 */
public class MultiSelectCheckBox extends LinearLayout {
    public OnSelectListener mOnSelectListener;
    private Map<Integer, ResourceItem> mData = new HashMap<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mSrcW;
    private WindowManager wm;
    private int maxItemW;
    private List<View> mItemViews = new ArrayList<>();
    private LinearLayout mLinearLayout;
    private int maxOneRowItemCount = -1;
    private View view;
    private int checkBoxType;
    private int select_count = 0;

    public MultiSelectCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        this.mSrcW = wm.getDefaultDisplay().getWidth();
        this.mSrcW = dip2px(mContext, 240);

    }

    public MultiSelectCheckBox(Context context, int type) {
        super(context);
        this.mContext = context;
        this.checkBoxType = type;
        this.mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        this.mSrcW = wm.getDefaultDisplay().getWidth();
        this.mSrcW = dip2px(mContext, 240);
    }

    /**
     * 初始化视图，根据计算的宽去绘制布局
     */
    private void initView() {
        view = mLayoutInflater.inflate(R.layout.view_single_select_chk, this);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.single_select_root);
        if (null == mData || 0 == mData.size()) {
            return;
        }
        switch (checkBoxType) {
            case 0:
                for (int i = 0; i < mData.size(); i++) {
                    View itemView = mLayoutInflater.inflate(
                            R.layout.item_single_select, null);
                    CheckBox chk = (CheckBox) itemView.findViewById(R.id.single_select_chk);
                    chk.setOnClickListener(new OnChkClickEvent());
                    chk.setText(mData.get(i).getName());
                    chk.setTextColor(getResources().getColor(R.color.black));
                    chk.setTag(i);
                    mItemViews.add(itemView);
                    measureView(itemView);
                    int width = itemView.getMeasuredWidth();
                    if (width > maxItemW) {
                        maxItemW = width;
                    }
                }
                break;
            default:
                for (int i = 0; i < mData.size(); i++) {
                    View itemView = mLayoutInflater.inflate(
                            R.layout.item_single_select_rec, null);
                    CheckBox chk = (CheckBox) itemView.findViewById(R.id.single_select_chk_rec);
                    chk.setOnClickListener(new OnChkClickEvent());
                    chk.setText(mData.get(i).getName());
                    chk.setTextColor(getResources().getColor(R.color.black));
                    chk.setTag(i);
                    mItemViews.add(itemView);
                    measureView(itemView);
                    int width = itemView.getMeasuredWidth();
                    if (width > maxItemW) {
                        maxItemW = width;
                    }
                }
                break;
        }
        maxOneRowItemCount = (mSrcW - dip2px(mContext, 10)) / maxItemW;
        if (maxOneRowItemCount > 4) maxOneRowItemCount = 4;
        if (maxOneRowItemCount == 0) {
            maxOneRowItemCount = 1;
        }
        addItem();


    }

    private void addItem() {
        int rowCount = mItemViews.size() / maxOneRowItemCount;
        int lastRowItemCount = mItemViews.size() % maxOneRowItemCount;

        int itemW = (mSrcW - dip2px(mContext, 10)) / maxOneRowItemCount;
        for (int i = 0; i < rowCount; i++) {

            LinearLayout llytRow = getNewRow();
            for (int j = 0; j < maxOneRowItemCount; j++) {
                View itemView = mItemViews.get(i * maxOneRowItemCount + j);
                resetItemWidth(itemView, itemW);
                llytRow.addView(itemView);
            }
            mLinearLayout.addView(llytRow);
        }
        if (lastRowItemCount != 0 && rowCount > 0) {
            LinearLayout llytRow = getNewRow();
            for (int k = 0; k < lastRowItemCount; k++) {
                View itemView = mItemViews.get(maxOneRowItemCount * rowCount
                        + k);
                resetItemWidth(itemView, itemW);
                llytRow.addView(itemView);
            }
            mLinearLayout.addView(llytRow);
        } else if (rowCount == 0) {
            LinearLayout llytRow = getNewRow();
            for (int j = 0; j < mData.size(); j++) {
                View itemView = mItemViews.get(j);
                itemW = (mSrcW - dip2px(mContext, 10)) / mData.size();
                resetItemWidth(itemView, itemW);
                llytRow.addView(itemView);
            }
            mLinearLayout.addView(llytRow);
        }
    }

    private void resetItemWidth(View itemView, int itemW) {

        switch (checkBoxType) {
            case 0:
                LinearLayout itemViewRoot = (LinearLayout) itemView
                        .findViewById(R.id.item_single_select_root);
                LinearLayout.LayoutParams lp = new LayoutParams(itemW,
                        LayoutParams.MATCH_PARENT);
                itemViewRoot.setLayoutParams(lp);
                break;
            default:
                LinearLayout itemViewRoot3 = (LinearLayout) itemView.findViewById(R.id.item_single_select_root_rec);
                LinearLayout.LayoutParams lp3 = new LayoutParams(itemW,
                        LayoutParams.MATCH_PARENT);
                itemViewRoot3.setLayoutParams(lp3);
                break;
        }

    }

    private LinearLayout getNewRow() {
        LinearLayout llytRow = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llytRow.setOrientation(LinearLayout.HORIZONTAL);
        lp.setMargins(30, 10, 5, 10);
        llytRow.setLayoutParams(lp);
        return llytRow;
    }

    public void setData(Map<Integer, ResourceItem> data) {
        this.mData = data;
        initView();
    }

    public void resetData() {
        switch (checkBoxType) {
            case 0: //���� Բ��
                for (View itemView : mItemViews) {
                    CheckBox chk = (CheckBox) itemView.findViewById(R.id.single_select_chk);
                    chk.setChecked(false);
                    chk.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            default: //��������
                for (View itemView : mItemViews) {
                    CheckBox chk = (CheckBox) itemView.findViewById(R.id.single_select_chk_rec);
                    chk.setChecked(false);
                    chk.setTextColor(getResources().getColor(R.color.black));
                }
                break;
        }

    }

    private void refreshData(int selectPosition) {

        switch (checkBoxType) {
            case 0:
                for (View itemView : mItemViews) {
                    CheckBox chk = (CheckBox) itemView
                            .findViewById(R.id.single_select_chk);
                    if ((Integer) chk.getTag() != selectPosition) {
                        chk.setChecked(false);
                        chk.setTextColor(getResources().getColor(R.color.black));
                    }
                }
                break;

            default:
                for (View itemView : mItemViews) {
                    CheckBox chk = (CheckBox) itemView
                            .findViewById(R.id.single_select_chk_rec);
                    if ((Integer) chk.getTag() != selectPosition) {
                        chk.setChecked(false);
                        chk.setTextColor(getResources().getColor(R.color.black));
                    }
                }
                break;
        }
    }

    public void setOnSelectListener(OnSelectListener l) {
        this.mOnSelectListener = l;
    }

    public void measureView(View v) {
        if (v == null) {
            return;
        }
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }

    /**
     */
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     */
    public interface OnSelectListener {
        // v:����ͼ  position: cb��λ�ã���0��ʼ��  type: ���ͣ���ɫ�����룬�䳤�ȣ�  isCheck:�Ƿ�ѡ��
        void onSelect(View v, int position, int checkBoxType, boolean isCheck, int selectedCount);
    }

    /**
     */
    private class OnChkClickEvent implements OnClickListener {
        @Override
        public void onClick(View v) {
                CheckBox chk = (CheckBox) v;
                if (chk.isChecked()) {
                    chk.setTextColor(getResources().getColor(R.color.white));
                    select_count++;
                } else {
                    //ȡ��ѡ����һ��item
                    chk.setTextColor(getResources().getColor(R.color.black));
                    select_count--;
                }
            int selectPosition = (Integer) chk.getTag();
//                boolean isChecked = true;
//                if (select_count == 0)
//                    isChecked = false;
                Boolean isChecked = chk.isChecked();
                mOnSelectListener.onSelect(view, selectPosition, checkBoxType, isChecked, select_count);

        }
    }

}
