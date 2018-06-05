package com.example.admin.demo3.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.base.BaseAdapter;
import com.example.admin.demo3.util.HistoryUtil;

import butterknife.BindView;

public class TrunkAdapter extends BaseAdapter<TrunkAdapter.ItemViewHolder, TrunkAdapter.ItemListener, HistoryUtil.ItemTrunk> {

    @Override
    protected ItemViewHolder getCustomItemViewHolder(ViewGroup parent) {
        return new ItemViewHolder(createView(parent, R.layout.item_trunk));
    }

    public interface ItemListener extends BaseAdapter.BaseItemListener {
    }

    class ItemViewHolder extends BaseAdapter.BaseItemViewHolder {

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
        }

        @Override
        protected void setupView() {

        }

        protected void bindData(int position) {
            this.position = position;
            needUpdate = false;
            isBindData = true;

            final HistoryUtil.ItemTrunk itemTrunk = data.get(position);

            trunkNumber.setText(position + 1);
            trunkTimeLine.setText(itemTrunk.getTime() + "");
            numberPictureFront.setText(itemTrunk.getFrontCam());
            numberPictureBehind.setText(itemTrunk.getBackCam());
            trunkTime.setText(itemTrunk.getTime() + "");
        }
    }
}
