package com.example.disilin.selection.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.disilin.selection.R;
import com.example.disilin.selection.info.SubSelectionInfo;
import com.example.disilin.selection.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by disilin on 2017/5/10.
 */
public class SelectionView extends ViewGroup {

    private Context mContext;
    private int mScreenWidth;
    private List<View> mViews = new ArrayList<>();
    private ArrayList<SubSelectionInfo> mSubs = new ArrayList<>();
    private int subWidth = 0;
    private int subHeight = 0;
    private int subTopMargin = 0;
    private int maxSizeInLine = 3;

    public int getSubWidth() {
        return subWidth;
    }

    public void setSubWidth(int subWidth) {
        this.subWidth = subWidth;
    }

    public int getSubHeight() {
        return subHeight;
    }

    public void setSubHeight(int subHeight) {
        this.subHeight = subHeight;
    }

    public int getSubTopMargin() {
        return subTopMargin;
    }

    public void setSubTopMargin(int subTopMargin) {
        this.subTopMargin = subTopMargin;
    }

    public int getMaxSizeInLine() {
        return maxSizeInLine;
    }

    public void setMaxSizeInLine(int maxSizeInLine) {
        this.maxSizeInLine = maxSizeInLine;
    }

    public SelectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SelectionView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        //获取屏幕宽度
        mScreenWidth = DeviceUtils.getScreenWidth(context);

        subWidth = DeviceUtils.dp2px(getContext(), 320 / (getMaxSizeInLine() + 1));
        subHeight = subWidth + DeviceUtils.dp2px(getContext(), 25);
        subTopMargin = DeviceUtils.dp2px(getContext(), 20);
    }

    public SelectionView(Context context) {
        super(context, null, 0);
    }

    public void initDatas(List<SubSelectionInfo> subs, OnMenuItemClickListener listener) {
        if (subs != null){
            mSubs.clear();
            mSubs.addAll(subs);
            mOnMenuItemClickListener = listener;
            initViews();
            requestLayout();

        }
    }

    private void initViews() {
        for (int i = 0; i < mSubs.size(); i++){
            SubSelectionInfo sub = mSubs.get(i);
            View subView = LayoutInflater.from(getContext()).inflate(R.layout.view_sub, null);
            ImageView iv  = (ImageView) subView.findViewById(R.id.iv_sub_icon);
            TextView tv = (TextView) subView.findViewById(R.id.tv_sub_name);
            //加载图标
            iv.setImageResource(sub.getIconId());
            tv.setText(sub.getIconName());
            subView.setTag(i);
            subView.setOnClickListener(onMenuItemClickListener);
            mViews.add(subView);
            addView(subView);
        }
    }

    private OnMenuItemClickListener mOnMenuItemClickListener;
    View.OnClickListener onMenuItemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Object tagValue = v.getTag();
            if (tagValue instanceof Integer) {
                int position = (int)tagValue;
                if (mOnMenuItemClickListener != null) {
                    mOnMenuItemClickListener.clickPosition(position);
                }
            }
        }
    };

    public interface OnMenuItemClickListener{
        void clickPosition(int position);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftMargin = (mScreenWidth - subWidth * 3) / 4;
        for (int i = 0; i < mViews.size(); i++){
            View view = mViews.get(i);
            int left = subWidth * (i % 3) + leftMargin * (i % 3 + 1);
            int top = (subHeight + subTopMargin) * (i / 3);
            int right = left + subWidth;
            int bottom = top + subHeight;
            view.layout(left, top, right, bottom);
        }
    }

    public int getRelativeHeitht(int size) {
        int h = subHeight + (subHeight + subTopMargin) * ((size - 1) / 3);
        return h;
    }


    /**
     * 设置布局的宽高，并策略menu item宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultWidth(), getRelativeHeitht(mSubs.size()));
        // menu item数量
        final int count = mSubs.size();
        // menu item尺寸
        int childSize = subHeight;
        // menu item测量模式
        int childMode = MeasureSpec.EXACTLY;

        // 迭代测量
        for (int i = 0; i < count; i++)
        {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
            {
                continue;
            }
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;

            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
                    childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private int getDefaultWidth()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }
}
