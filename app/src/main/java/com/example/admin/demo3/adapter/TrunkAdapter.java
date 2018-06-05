package com.example.admin.demo3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.util.HistoryUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrunkAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING_MORE = 1;
    private static final int VIEW_TYPE_LOADING = 2;
    private static final int VIEW_TYPE_LIST_ERROR = 3;
    private static final int VIEW_TYPE_LIST_EMPTY = 4;
    private static final int VIEW_TYPE_LIST_LOAD_MORE_ERROR = 5;

    protected List<HistoryUtil.ItemTrunk> itemTrunksList = new ArrayList<>();
    protected ItemListener itemListener;
    private boolean showLoadingMore = false;
    private boolean showLoading = true;
    private boolean isError = false;

    public void addData(List<HistoryUtil.ItemTrunk> itemTrunks) {
        int startPosition = itemTrunksList.size();
        int endPosition = startPosition;
        if (itemTrunks != null && !itemTrunks.isEmpty()) {
            this.itemTrunksList.addAll(itemTrunks);
            endPosition = itemTrunksList.size();
        }
        loadingOff(false);
        if (endPosition != startPosition) notifyItemRangeChanged(startPosition, endPosition);
        else notifyDataSetChanged();
    }

    public void addData(HistoryUtil.ItemTrunk itemTrunk) {
        if (itemTrunk != null) {
            this.itemTrunksList.add(itemTrunk);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        itemTrunksList.clear();
        loadingOff(false);
        showLoading = true;
        notifyDataSetChanged();
    }

    /*Xử lý trạng thái*/
    public void loadingOn(int currentPage) {

        if (currentPage == 0 && !showLoading) {
            isError = false;
            showLoading = true;
            notifyItemRangeChanged(0, 1);
        } else if (!showLoading) {
            showLoadingMore = true;
            if (isError) {
                isError = false;
                // notifyItemChanged(data.size() + 1);
                notifyItemRemoved(itemTrunksList.size());
                notifyItemRangeChanged(itemTrunksList.size(), itemTrunksList.size() + 1);
            } else
                notifyItemRangeChanged(itemTrunksList.size(), itemTrunksList.size() + 1);
        }

    }

    public void loadingOff(boolean isError) {
        showLoadingMore = false;
        showLoading = false;
        if (isError) errorOn();
    }

    private void errorOn() {
        isError = true;
        notifyItemRemoved(itemTrunksList.size());
        notifyItemRangeChanged(itemTrunksList.size(), itemTrunksList.size() + 1);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return new ItemViewHolder(createView(parent, R.layout.item_trunk));
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

    protected View createView(ViewGroup parent, int layoutResource) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder)
            ((ItemViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        if (showLoading || isError || itemTrunksList.size() == 0) return itemTrunksList.size() + 1;
        return showLoadingMore ? itemTrunksList.size() + 1 : itemTrunksList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public interface ItemListener {

        void onRetryClick();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public String code = "";
        public boolean needUpdate = false;
        public int position = -1;
        protected boolean isBindData = true;

        @BindView(R.id.trunkNumber)
        TextView trunkNumber;
        @BindView(R.id.trunkNumberPicture1)
        TextView numberPictureFront;
        @BindView(R.id.trunkNumberPicture2)
        TextView numberPictureBehind;
        @BindView(R.id.trunkTimeLine)
        TextView trunkTimeLine;
        @BindView(R.id.trunkTime)
        TextView trunkTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void bindData(int position) {
            this.position = position;
            needUpdate = false;
            isBindData = true;

            final HistoryUtil.ItemTrunk itemTrunk = itemTrunksList.get(position);

            trunkNumber.setText(position + 1);
            trunkTimeLine.setText(itemTrunk.getTime() + "");
            numberPictureFront.setText(itemTrunk.getFrontCam());
            numberPictureBehind.setText(itemTrunk.getBackCam());
            trunkTime.setText(itemTrunk.getTime() + "");
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
