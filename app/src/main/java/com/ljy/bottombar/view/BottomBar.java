package com.ljy.bottombar.view;

/**
 * Created by lijunyan on 2017/1/16.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljy.bottombar.R;
import com.ljy.bottombar.bean.BottomBarBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BottomBar extends LinearLayout {

    private static final String TAG = "BottomBar";
    private int parentWidth;
    private int parentHeight;
    private int count = 0;
    private Context context;
    private boolean isFirst = true;
    private boolean isFirstClick = true;
    private BottomBarClickListener mListener;
    private List<BottomBarBean> bottomElementList = new ArrayList<>();
    private HashMap<Integer, RelativeLayout> layoutHashMap = new HashMap<>();
    private int selectionIndex = -1;
    private List<TextView> countTexts = new ArrayList<>();
    private int baseSize;
    private BottomBarBean lastBean;
    private boolean fisrtUpdateFlag = false;
    private int index = 0;
    private RelativeLayout relativeLayout;
    private LayoutParams linearParams;
    private RelativeLayout.LayoutParams relativeParam;

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "BottomBar: ");
        this.context = context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: ");
        if (count > 0) {
            //得到xml中的传递过来的经处理的px值
            parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            Log.d(TAG, "onMeasure: parentWidth = "+parentWidth+ "  parentHeight = "+parentHeight);

            //baseSize为每个单元控件长宽中比较少的那个值
            baseSize = parentWidth / count;
            if (baseSize > parentHeight) {
                baseSize = parentHeight;
            }

            setMeasuredDimension(parentWidth, parentHeight);
        }
    }

    private void updateView() {
        int childCount = getChildCount();
        Log.d(TAG, "updateView: childCount = "+childCount);
        if (parentHeight == 0 || parentWidth == 0) {
            return;
        }
        if (childCount > 0) {
            removeAllViews();
            countTexts.clear();
        }
        index = 0;
        final View imageView = new View(context);
        final View titleView = new View(context);

        for (final BottomBarBean bean : bottomElementList) {
            relativeLayout = new RelativeLayout(context);
            linearParams = new LayoutParams(baseSize, baseSize);
            bean.setIndex(index++);
            linearParams.gravity = Gravity.CENTER;
            linearParams.weight = 1;
            relativeParam = new RelativeLayout.LayoutParams(parentWidth / count, parentHeight);

            //添加图标
            final ImageView imageIv = new ImageView(context);
            imageIv.setImageResource(bean.getImage());
            RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(baseSize / 2, baseSize / 2);
            imageParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imageParam.setMargins(0, baseSize / 6, 0, 0);

            //添加标题
            final TextView titleTv = new TextView(context);
            titleTv.setText(bean.getTitle());
            titleTv.setTextColor(Color.parseColor("#6A6F74"));
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, baseSize / 5 );
            titleTv.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams tvParam = new RelativeLayout.LayoutParams(parentWidth / count, baseSize / 3);
            tvParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            if (bean.isCheck() && isFirst) {
                Log.i(TAG, "updateView: bean.isCheck() && isFirst");
                imageView.setTag(imageIv);
                titleView.setTag(titleTv);
                //设置为选中状态
                imageIv.setImageResource(bean.getImageHover());
                titleTv.setTextColor(Color.parseColor("#14B7F5"));
                lastBean = bean;
                if (isFirstClick) {
                    isFirstClick = false;
                    Log.i(TAG, "updateView isFirstClick click");
                    mListener.click(bean.getIndex());
                }
            }

            if (lastBean != null) {
                if (bean.getTitle().equals(lastBean.getTitle())) {
                    Log.i(TAG, "updateView: lastBean != null bean.getTitle().equals(lastBean.getTitle())");
                    imageView.setTag(imageIv);
                    titleView.setTag(titleTv);
                    imageIv.setImageResource(bean.getImageHover());
                    titleTv.setTextColor(Color.parseColor("#14B7F5"));
                    lastBean = bean;
                }
            }

            relativeLayout.addView(imageIv, imageParam);
            relativeLayout.addView(titleTv, tvParam);


            //添加圆点
            RelativeLayout.LayoutParams messageParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            messageParams.addRule(RelativeLayout.ALIGN_RIGHT);
            messageParams.setMargins((baseSize / 6) + parentWidth / (2 * count) - 5, baseSize / 7 - 3, 0, 0);

            //添加消息条数
            TextView messageTV = new TextView(context);
            countTexts.add(messageTV) ;
            messageTV.setBackgroundResource(R.drawable.circle_shape);
            messageTV.setGravity(Gravity.CENTER);
            messageTV.setTextColor(Color.WHITE);
            //粗体
            TextPaint textPaint = messageTV.getPaint();
            textPaint.setFakeBoldText(true);

            messageTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, baseSize / 5);
            messageTV.setText("" + bean.getMsgCount());
            if (bean.getMsgCount() <= 0 ){
                messageTV.setVisibility(GONE);
            }
            if (bean.getMsgCount() > 99) {
                messageTV.setText("…");
            }

            relativeLayout.addView(messageTV, messageParams);
            relativeLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Log.d(TAG, "onClick: " + lastBean);
                        ImageView lastImage = (ImageView) imageView.getTag();
                        if (lastBean != null && lastImage != null) {
                            //上一次点击的恢复
                            lastImage.setImageResource(lastBean.getImage());
                        }
                        imageIv.setImageResource(bean.getImageHover());
                        imageView.setTag(imageIv);

                        if (titleView != null) {
                            TextView lastText = (TextView) titleView.getTag();
                            if (lastText != null) {
                                lastText.setTextColor(Color.parseColor("#6A6F74"));
                            }
                            titleTv.setTextColor(Color.parseColor("#14B7F5"));
                            titleView.setTag(titleTv);
                        }

                        lastBean = bean;
                        if (mListener != null) {
                            mListener.click(bean.getIndex());
                        }
                        isFirst = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            layoutHashMap.put(bean.getIndex(), relativeLayout);
            addView(relativeLayout, relativeParam);
        }

        if (selectionIndex != -1) {
            perform();
        }
    }

    public void update(List<BottomBarBean> list) {
        Log.d(TAG, "update: " + list.size());
        this.bottomElementList = list;
        count = list.size();
        fisrtUpdateFlag = false;
        requestLayout();
    }


    public void updateMsgCount(List<BottomBarBean> list){
        if (countTexts.isEmpty()){
            update(list);
            return;
        }
        this.bottomElementList = list;
        for (int i = 0 ; i < list.size() ; i++){
            int count = list.get(i).getMsgCount() ;
            TextView textView = countTexts.get(i) ;
            if (count > 0 ){
                textView.setVisibility(VISIBLE);
                textView.setText(count +"");
            }else {
                textView.setVisibility(GONE);
            }
            if (count > 99){
                textView.setText("…");
            }
        }
    }


    //底栏监听事件和接口
    public void setOnBottomClickListener(BottomBarClickListener bottomClickListener) {
        this.mListener = bottomClickListener;
    }

    public interface BottomBarClickListener {
        void click(int i);
    }


    //供外部调用的，可以直接跳转到指定页面
    public void setSelectionPage(int index) {
        this.selectionIndex = index;
        invalidate();
    }

    private void perform() {
        layoutHashMap.get(selectionIndex).performClick();
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        if (!fisrtUpdateFlag) {
            updateView();
            fisrtUpdateFlag = true;
        }
    }

}
