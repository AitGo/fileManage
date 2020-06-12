package com.yh.filesmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yh.filesmanage.R;

import java.util.List;

/**
 * @创建者 ly
 * @创建时间 2020/6/2
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class ChooseViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;

    public ChooseViewAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayerChooseViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.item_adapter_state_layer_choose,null);
            holder = new LayerChooseViewHolder();
            holder.mLayer = convertView.findViewById(R.id.tv_item_choose_layer);
            convertView.setTag(holder);
        }else {
            holder = (LayerChooseViewHolder) convertView.getTag();
        }
        holder.mLayer.setText(list.get(position));
        return convertView;
    }

    class LayerChooseViewHolder {
        public TextView mLayer;
    }
}
