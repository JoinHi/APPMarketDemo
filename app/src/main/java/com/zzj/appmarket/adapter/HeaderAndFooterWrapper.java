package com.zzj.appmarket.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bjh on 16/9/9.
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private static final int BASE_ITEM_TYPE_PROGRESS = 300000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter mInnerAdapter;
    private RecyclerView mRecyclerView;
    private int lastPosition;
    private ProgressBar progressBar;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }


    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }
    public void addFooterView(View view){
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
        Log.i("___", mFooterViews.size() + "");
    }
    public void removeFooterView(View view){
        mFooterViews.removeAt(mFooterViews.indexOfValue(view));
        Log.i("___",mFooterViews.size()+"");
        notifyItemChanged(getChangedPosition());

    }
    int getChangedPosition(){
        return getHeadersCount()+getRealItemCount()+getFootersCount();
    }
    public int getHeadersCount(){
        return mHeaderViews.size();
    }
    public int getFootersCount(){
        return mFooterViews.size();
    }
    private int getRealItemCount(){
        return mInnerAdapter.getItemCount();
    }
    private boolean isHeaderViewPos(int position){
        return position < getHeadersCount();
    }
    private boolean isFooterViewPos(int position){
        return position >= getHeadersCount() + getRealItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null){
            return ViewHolder.createViewHolder(parent.getContext(),mHeaderViews.get(viewType));
        }
        if (mFooterViews.get(viewType) != null){
            return ViewHolder.createViewHolder(parent.getContext(),mFooterViews.get(viewType));
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        lastPosition = position;
        if (isHeaderViewPos(position) || isFooterViewPos(position))
            return;
        mInnerAdapter.onBindViewHolder(holder,position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("++++", "" + position);
        if (isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }else if (isFooterViewPos(position)){
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        initEvent();
        if (!isLoadMore)
            initProgress();
    }

    private void initProgress() {
        progressBar = new ProgressBar(mRecyclerView.getContext());
        FrameLayout.LayoutParams params =null;
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } else if (layoutManager instanceof LinearLayoutManager) {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);

    }

    private void initEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int totalItem = layoutManager.getItemCount();
                int lastPosition = 0;
                if (layoutManager instanceof StaggeredGridLayoutManager) {

                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    lastPosition = findMax(lastPositions);
                } else if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
                if (lastPosition == totalItem - 1 && !isLoadMore) {
                    if (isShow){
                        isShow = false;
                    }else {
                        loadMore();
                    }
                }
            }
        });
    }
    boolean isLoadMore;
    boolean isShow;
    private void loadMore() {
        if (!isLoadMore){
            isLoadMore = true;
            isShow = true;
            addLoadingProgressbar();
            Observable.just("")
                    .delay(5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            removeLoadProgressbar();
                            isLoadMore = false;
                        }
                    });
        }

    }

    private void removeLoadProgressbar() {
        removeFooterView(progressBar);
    }

    private void addLoadingProgressbar() {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_PROGRESS, progressBar);
        notifyItemChanged(getChangedPosition());
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.i("__", "onViewRecycled");
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.i("__", "onViewDetachedFromWindow");
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.i("__", "onDetachedFromRecyclerView");
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value :lastPositions){
            if (value>max){
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        Log.i("__", "onViewAttachedToWindow" + holder.getAdapterPosition());
    }



    public interface OnLoadMoreLisenter{
        void loadMore();
    }

}
