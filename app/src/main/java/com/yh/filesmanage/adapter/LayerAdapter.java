package com.yh.filesmanage.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yh.filesmanage.R;
import com.yh.filesmanage.diagnose.LayerEntity;
import com.yh.filesmanage.widget.LayerView;

import java.util.List;

import androidx.annotation.Nullable;

public class LayerAdapter extends BaseQuickAdapter<LayerEntity,BaseViewHolder> {

    private Context mContext;

    public LayerAdapter(@Nullable List<LayerEntity> data,Context mContext) {
        super(R.layout.item_adapter_state_layer, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, LayerEntity item) {
        helper.setText(R.id.tv_index,item.getIndex() + "");
        LayerView view = helper.getView(R.id.iv_item);
        view.setLayer(item);
    }

}
