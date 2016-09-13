package com.zzj.appmarket.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bjh on 16/9/9.
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter mInnerAdapter;
    private RecyclerView mRecyclerView;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }


    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }
    public void addFooterView(View view){
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER,view);
    }
    public void removeFooterView(View view){
        mFooterViews.delete(mFooterViews.indexOfValue(view));
        notifyDataSetChanged();

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

        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}
