package com.example.admin.demo3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.HistoryUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrunkAdapter extends RecyclerView.Adapter{

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
        return new ItemViewHolder(createView(parent, R.layout.item_trunk));
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

        void onItemListener(Vehicle vehicle);
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
}
