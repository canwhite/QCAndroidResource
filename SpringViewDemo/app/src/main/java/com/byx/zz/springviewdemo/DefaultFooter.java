package com.byx.zz.springviewdemo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liaoinstan.springview.container.BaseFooter;

/**
 * Created by zb on 2018/7/31.
 */

public class DefaultFooter extends BaseFooter {

    private Context context;
    private int rotationSrc;
    private TextView footerTitle;
    private ProgressBar footerProgressbar;

    public DefaultFooter(Context context){
        this(context,R.drawable.progress_small);
    }

    public DefaultFooter(Context context,int rotationSrc){
        this.context = context;
        this.rotationSrc = rotationSrc;
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.default_footer, viewGroup, true);
        footerTitle = (TextView) view.findViewById(R.id.default_footer_title);
        footerProgressbar = (ProgressBar) view.findViewById(R.id.default_footer_progressbar);
        footerProgressbar.setIndeterminateDrawable(ContextCompat.getDrawable(context,rotationSrc));
        return view;
    }
    /**
     * 拖拽开始前回调
     * 设置头部的控件时间
     * @param rootView
     */
    @Override
    public void onPreDrag(View rootView) {
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
        if (upORdown) {
            footerTitle.setText("松开载入更多");
        } else {
            footerTitle.setText("查看更多");
        }
    }

    /***
     *  //拉动超过临界点后松开时回调
     */
    @Override
    public void onStartAnim() {
        footerTitle.setVisibility(View.INVISIBLE);
        footerProgressbar.setVisibility(View.VISIBLE);
    }
    /**
     *  //头部已经全部弹回时回调,也就是头部不可见的时候
     */
    @Override
    public void onFinishAnim() {
        footerTitle.setText("查看更多");
        footerTitle.setVisibility(View.VISIBLE);
        footerProgressbar.setVisibility(View.INVISIBLE);
    }
}
