package com.example.admin.demo3.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseAdapter<IVH extends BaseAdapter.BaseItemViewHolder, IL extends BaseAdapter.BaseItemListener, DT> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING_MORE = 1;
    private static final int VIEW_TYPE_LOADING = 2;
    private static final int VIEW_TYPE_LIST_ERROR = 3;
    private static final int VIEW_TYPE_LIST_EMPTY = 4;
    private static final int VIEW_TYPE_LIST_LOAD_MORE_ERROR = 5;

    protected IL itemListener;
    private boolean showLoadingMore = false;
    private boolean showLoading = true;
    private boolean isError = false;

    protected List<DT> data = new ArrayList<>();

    public void addData(List<DT> materials) {
        int startPosition = data.size();
        int endPosition = startPosition;
        if (materials != null && !materials.isEmpty()) {
            this.data.addAll(materials);
            endPosition = data.size();
        }
        loadingOff(false);
        if (endPosition != startPosition) notifyItemRangeChanged(startPosition, endPosition);
        else notifyDataSetChanged();

    }

    public void clearData() {
        data.clear();
        loadingOff(false);
        showLoading = true;
        notifyDataSetChanged();
    }

    public void loadingOff(boolean isError) {
        showLoadingMore = false;
        showLoading = false;
        if (isError) errorOn();
    }

    private void errorOn() {
        isError = true;
        notifyItemRemoved(data.size());
        notifyItemRangeChanged(data.size(), data.size() + 1);
    }

    public void setItemListener(IL itemListener) {
        this.itemListener = itemListener;
    }

    public interface BaseItemListener {
        void onRetryClick();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return getCustomItemViewHolder(parent);
        else if (viewType == VIEW_TYPE_LOADING_MORE)
            return new EmptyViewHolder(createView(parent, R.layout.x_item_loading_more_linear));
        else if (viewType == VIEW_TYPE_LOADING)
            return new EmptyViewHolder(createView(parent, R.layout.x_layout_list_loading));
        else if (viewType == VIEW_TYPE_LIST_ERROR)
            return new ErrorViewHolder(createView(parent, R.layout.x_layout_list_error));
        else if (viewType == VIEW_TYPE_LIST_LOAD_MORE_ERROR)
            return new ErrorViewHolder(createView(parent, R.layout.x_layout_list_load_more_error));
        else
            return new EmptyViewHolder(createView(parent, R.layout.x_layout_list_empty));
    }

    protected abstract IVH getCustomItemViewHolder(ViewGroup parent);

    protected View createView(ViewGroup parent, int layoutResource) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (showLoading || isError || data.size() == 0) return data.size() + 1;
        return showLoadingMore ? data.size() + 1 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoading) return VIEW_TYPE_LOADING;
        if (isError) {
            if (data.size() == 0)
                return VIEW_TYPE_LIST_ERROR;
            else if (position == data.size()) return VIEW_TYPE_LIST_LOAD_MORE_ERROR;
        }
        if (data.size() == 0) return VIEW_TYPE_LIST_EMPTY;
        if (position == data.size()) return VIEW_TYPE_LOADING_MORE;
        return VIEW_TYPE_ITEM;
    }

    public static abstract class BaseItemViewHolder extends RecyclerView.ViewHolder {

        public String code = "";
        public boolean needUpdate = false;
        public int position = -1;
        protected boolean isBindData = true;

        public BaseItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setupView();
        }

        protected abstract void setupView();

        protected void bindData(int position) {
            this.position = position;
            needUpdate = false;
            isBindData = true;
        }

    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ErrorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buttonRetry)
        Button buttonRetry;

        ErrorViewHolder(View itemView) {
            super(itemView);
            setupView();
        }

        private void setupView() {
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onDelayedClick(View v) {
                    if (itemListener != null) itemListener.onRetryClick();
                }
            });
        }
    }
}
