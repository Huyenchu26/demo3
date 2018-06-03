package com.example.admin.demo3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.GetRFID;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 29/3/2018.
 */

public class VehicleAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING_MORE = 1;
    private static final int VIEW_TYPE_LOADING = 2;
    private static final int VIEW_TYPE_LIST_ERROR = 3;
    private static final int VIEW_TYPE_LIST_EMPTY = 4;
    private static final int VIEW_TYPE_LIST_LOAD_MORE_ERROR = 5;
    protected List<Vehicle> data = new ArrayList<>();
    protected ItemListener itemListener;
    private boolean showLoadingMore = false;
    private boolean showLoading = true;
    private boolean isError = false;

    public void addData(List<Vehicle> vehicles) {
        int startPosition = data.size();
        int endPosition = startPosition;
        if (vehicles != null && !vehicles.isEmpty()) {
            this.data.addAll(vehicles);
            endPosition = data.size();
        }
        loadingOff(false);
        if (endPosition != startPosition) notifyItemRangeChanged(startPosition, endPosition);
        else notifyDataSetChanged();
    }

    public void addVehicle(List<Vehicle> vehicles) {
        int startPosition = data.size();
        int endPosition = startPosition;
        if (vehicles != null && !vehicles.isEmpty()) {
            this.data.addAll(vehicles);
            endPosition = data.size();
        }
        loadingOff(false);
        if (endPosition != startPosition) notifyItemRangeChanged(startPosition, endPosition);
        else notifyDataSetChanged();
    }

    public void addData(Vehicle vehicles) {
        if (vehicles != null) {
            this.data.add(vehicles);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
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
                notifyItemRemoved(data.size());
                notifyItemRangeChanged(data.size(), data.size() + 1);
            } else
                notifyItemRangeChanged(data.size(), data.size() + 1);
        }

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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return new ItemViewHolder(createView(parent, R.layout.item_vehicle_child));
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
        if (holder instanceof VehicleAdapter.ItemViewHolder)
            ((ItemViewHolder) holder).bindData(position);
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

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public interface ItemListener {
        void onRetryClick();

        void onImageLocationClick(String longitude, String latitude);

        void onOpenDialogRfid(List<String> rfid);

        void onItemListener(Vehicle.Data vehicle);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public String code = "";
        public boolean needUpdate = false;
        public int position = -1;
        protected boolean isBindData = true;

        @BindView(R.id.linearItemContainer)
        View linearItemContainer;

        @BindView(R.id.txtImei)
        TextView txtImei;
        @BindView(R.id.txtDateTime)
        TextView txtDatetime;
        @BindView(R.id.imgLocation)
        ImageView imgLocation;
        @BindView(R.id.positionStatus)
        TextView positionStatus;
        @BindView(R.id.txtFirmWare)
        TextView txtFirmWare;
        @BindView(R.id.txtCPUtime)
        TextView txtCPUtime;
        @BindView(R.id.imgRunning)
        ImageView imgRunning;

        @BindView(R.id.imgTrunk)
        ImageView imgTrunk;
        @BindView(R.id.imgGPS)
        ImageView imgGPS;
        @BindView(R.id.imgSOS)
        ImageView imgSOS;
        @BindView(R.id.imgStatus)
        ImageView imgStatus;
        @BindView(R.id.imgEngine)
        ImageView imgEngine;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void bindData(int position) {
            this.position = position;
            needUpdate = false;
            isBindData = true;

            final Vehicle vehicle = data.get(position);
            final Vehicle.Data vehicleData = vehicle.data;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onDelayedClick(View v) {
                    itemListener.onItemListener(vehicleData);
                }
            });
            txtImei.setText(vehicleData.getImei() + "");
            txtDatetime.setText(vehicleData.getDateTime());
            positionStatus.setText(" Trạng thái định vị: " + vehicleData.getPosStatus());
            imgLocation.setOnClickListener(new OnClickListener() {
                @Override
                public void onDelayedClick(View v) {
                    if (itemListener != null)
                        itemListener.onImageLocationClick(vehicleData.getLongitude(), vehicleData.getLatitude());
                }
            });

            if (vehicleData.getStatus().equals("1"))
                imgRunning.setImageResource(R.mipmap.icon_running);
            else
                imgRunning.setImageResource(R.mipmap.icon_stop);
            imgRunning.setOnClickListener(new OnClickListener() {
                @Override
                public void onDelayedClick(View v) {
                    if (itemListener != null) itemListener.onOpenDialogRfid(GetRFID.getRFID(vehicleData.getRfidList()));
                }
            });
            txtFirmWare.setText(" Firmware: " + vehicleData.getFirmware());
            txtCPUtime.setText(" CPU time: " + vehicleData.getCpuTime());

            setTrafficLight(vehicleData);
        }

        private void setTrafficLight(Vehicle.Data vehicle) {
            if (vehicle.getSos().equals("1"))
                imgSOS.setImageResource(R.drawable.bg_traffic_light);
            else imgSOS.setImageResource(R.drawable.bg_traffic_dark);
            if (vehicle.getGps().equals("1"))
                imgGPS.setImageResource(R.drawable.bg_traffic_light);
            else imgGPS.setImageResource(R.drawable.bg_traffic_dark);
            if (vehicle.getEngine().equals("1"))
                imgEngine.setImageResource(R.drawable.bg_traffic_light);
            else imgEngine.setImageResource(R.drawable.bg_traffic_dark);
            if (vehicle.getTrunk().equals("1"))
                imgTrunk.setImageResource(R.drawable.bg_traffic_light);
            else imgTrunk.setImageResource(R.drawable.bg_traffic_dark);
            if (vehicle.getStatus().equals("1"))
                imgStatus.setImageResource(R.drawable.bg_traffic_light);
            else imgStatus.setImageResource(R.drawable.bg_traffic_dark);
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
