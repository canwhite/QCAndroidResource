package com.byx.zz.springviewdemo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liaoinstan.springview.container.BaseHeader;

/**
 * Created by zb on 2018/7/31.
 */

public class DefaultHeader extends BaseHeader {
    //传进来的上下文;
    private Context context;
    //旋转动画的int
    private int rotationSrc;
    //箭头
    private int arrowSrc;
    //定义刷新时间的标记;
    private long freshTime;
    //动画执行时间180mm
    private final int ROTATE_ANIM_DURATION = 180;
    //箭头朝上的动画;
    private RotateAnimation mRotateUpAnim;
    //箭头朝下的动画;
    private RotateAnimation mRotateDownAnim;

    private TextView headerTitle;
    private TextView headerTime;
    private ImageView headerArrow;
    private ProgressBar headerProgressbar;

    /**
     * 构造方法中传进来上下文,然后传递给三个参数的构造方法;
     * @param context
     */
    public DefaultHeader(Context context){
        //第一个参数是上下文,第二个参数是加载的圆圈,其中是加载一个旋转的动画,第三个参数是箭头;
        this(context, R.drawable.progress_small,R.drawable.arrow);
    }

    public DefaultHeader(Context context,int rotationSrc,int arrowSrc){
        this.context = context;
        this.rotationSrc = rotationSrc;
        this.arrowSrc = arrowSrc;
        //添加一个逆时针旋转动画使朝下的箭头转动到朝上;
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        //动画执行完停在当前位置;
        mRotateUpAnim.setFillAfter(true);
        //添加一个顺时针旋转动画使朝上的箭头转动到朝下;
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    /***
     * //获取Header;
     * @param inflater
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        //添加头部的布局view;
        View view = inflater.inflate(R.layout.default_header, viewGroup, true);
        //头部标题
        headerTitle = (TextView) view.findViewById(R.id.default_header_title);
        //记录刷新的时间;
        headerTime = (TextView) view.findViewById(R.id.default_header_time);
        //箭头;
        headerArrow = (ImageView) view.findViewById(R.id.default_header_arrow);
        //加载的圆圈;
        headerProgressbar = (ProgressBar) view.findViewById(R.id.default_header_progressbar);
        //将int加入到控件上;
        headerProgressbar.setIndeterminateDrawable(ContextCompat.getDrawable(context, rotationSrc));

        headerArrow.setImageResource(arrowSrc);
        return view;
    }

    /**
     * 拖拽开始前回调
     * 设置头部的控件时间
     * @param rootView
     */
    @Override
    public void onPreDrag(View rootView) {
        //如果刷新时间为零.表示没有刷新过;
        if (freshTime==0){
            //获取到当前的运行时间;
            freshTime = System.currentTimeMillis();
        }else {
            //如果不是第一次刷新,获取当前刷新时间然后减掉-上次一刷新的时间
            int m = (int) ((System.currentTimeMillis()-freshTime)/1000/60);
            if(m>=1 && m<60){
                headerTime.setText( m +"分钟前");
            }else if (m>=60){
                int h = m/60;
                headerTime.setText( h +"小时前");
            }else if(m>60*24){
                int d = m/(60*24);
                headerTime.setText( d +"天前");
            }else if(m==0){
                headerTime.setText("刚刚");
            }
        }
    }

    /***
     * //手指拖拽过程中不断回调，dy为拖拽的距离，可以根据拖动的距离添加拖动过程动画
     * @param rootView
     * @param dy 拖动距离，下拉为+，上拉为-
     */
    @Override
    public void onDropAnim(View rootView, int dy) {
    }

    /***
     * //手指拖拽过程中每次经过临界点时回调，upORdown是向上经过还是向下经过
     * @param rootView
     * @param upORdown 是上拉还是下拉
     */
    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        //upORdown 为false时表示过了临界点
        if (!upORdown){
            //修改标题信息;
            headerTitle.setText("松开刷新数据");
            //判断箭头可见
            if (headerArrow.getVisibility()==View.VISIBLE)
                //加入向上的动画;
                headerArrow.startAnimation(mRotateUpAnim);
        }
        else {
            //没过临界点,还是下拉刷新
            headerTitle.setText("下拉刷新");
            if (headerArrow.getVisibility()== View.VISIBLE)
                //动画转为向下;
                headerArrow.startAnimation(mRotateDownAnim);
        }
    }

    /***
     *  //拉动超过临界点后松开时回调
     */
    @Override
    public void onStartAnim() {
        //再一次记录刷新的时间;
        freshTime = System.currentTimeMillis();
        //设置标题信息是正在刷新;
        headerTitle.setText("正在刷新");
        //隐藏箭头;
        headerArrow.setVisibility(View.INVISIBLE);
        //清除箭头动画;
        headerArrow.clearAnimation();
        //设置加载圈进行显示;
        headerProgressbar.setVisibility(View.VISIBLE);
    }

    /**
     *  //头部已经全部弹回时回调,也就是头部不可见的时候
     */
    @Override
    public void onFinishAnim() {
        //显示箭头
        headerArrow.setVisibility(View.VISIBLE);
        //隐藏圆圈的加载动画;
        headerProgressbar.setVisibility(View.INVISIBLE);
    }

}
