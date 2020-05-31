package com.yh.filesmanage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yh.filesmanage.R;
import com.yh.filesmanage.diagnose.LayerEntity;
import com.yh.filesmanage.utils.DensityUtils;

import java.util.List;

import androidx.annotation.Nullable;

public class LayerAdapter extends BaseQuickAdapter<LayerEntity,BaseViewHolder> {

    private Context mContext;
    private int itemSize = 40;
    private int itemBgWidth = 5;
    Paint paint;
    Paint bg;
    Bitmap bitmap;

    public LayerAdapter(@Nullable List<LayerEntity> data,Context mContext) {
        super(R.layout.item_adapter_state_layer, data);
        this.mContext = mContext;
        paint = new Paint();
        paint.setColor(mContext.getResources().getColor(R.color.red));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20.0f);
        paint.setAlpha(180);
        bg = new Paint();
        bg.setColor(mContext.getResources().getColor(R.color.blue_bg));
        bg.setStyle(Paint.Style.STROKE);
        bg.setStrokeWidth(10.0f);
        bg.setAlpha(180);
//        bitmap = Bitmap.createBitmap(DensityUtils.dip2px(mContext,2000),DensityUtils.dip2px(mContext,100), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void convert(BaseViewHolder helper, LayerEntity item) {
        bitmap = Bitmap.createBitmap(DensityUtils.dip2px(mContext,2000),DensityUtils.dip2px(mContext,100), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        List<LayerEntity.Item> items = item.getItems();
        for(int i = 0; i < items.size(); i++) {
            switch (items.get(i).getState()) {
                case 0:
                    paint.setColor(mContext.getResources().getColor(R.color.gray));
                    break;
                case 1:
                    paint.setColor(mContext.getResources().getColor(R.color.green));
                    break;
                case 2:
                    paint.setColor(mContext.getResources().getColor(R.color.red));
                    break;
                case 3:
                    paint.setColor(mContext.getResources().getColor(R.color.yellow));
                    break;
            }

            canvas.drawRect(DensityUtils.dip2px(mContext,itemBgWidth + i*itemSize), DensityUtils.dip2px(mContext,5),
                    DensityUtils.dip2px(mContext,itemSize - itemBgWidth + i*itemSize), DensityUtils.dip2px(mContext,95), paint);

            canvas.drawRect(DensityUtils.dip2px(mContext,0), DensityUtils.dip2px(mContext,0),
                    DensityUtils.dip2px(mContext,itemSize+ i*itemSize), DensityUtils.dip2px(mContext,100), bg);
        }

        Glide.with(mContext).load(bitmap)
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.iv_item));
        helper.setText(R.id.tv_index1,item.getIndex() + "");
    }
}
