package com.yh.filesmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yh.filesmanage.R;
import com.yh.filesmanage.diagnose.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * @创建者 ly
 * @创建时间 2020/6/2
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class CWAdapter extends BaseAdapter {

    private Context mContext;
    private List<FileInfo> list;

    public CWAdapter(Context mContext, List<FileInfo> list) {
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
        cwHolder holder = null;
        if(convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.item_adapter_state_cw,null);
            holder = new cwHolder();
            holder.mIndex = convertView.findViewById(R.id.tv_index);
            holder.mCode = convertView.findViewById(R.id.tv_code);
            holder.mTitle = convertView.findViewById(R.id.tv_title);
            holder.mAddressNow = convertView.findViewById(R.id.tv_address_now);
            holder.mAddressRight = convertView.findViewById(R.id.tv_address_right);
            convertView.setTag(holder);
        }else {
            holder = (cwHolder) convertView.getTag();
        }
        holder.mCode.setText(list.get(position).getBarcode());
        holder.mTitle.setText(list.get(position).getMaintitle());
        holder.mAddressNow.setText(list.get(position).getRev1());
        holder.mAddressRight.setText(list.get(position).getShelf_no());
        return convertView;
    }

    class cwHolder {
        public TextView mIndex;
        public TextView mCode;
        public TextView mTitle;
        public TextView mAddressNow;
        public TextView mAddressRight;
    }
}
