package com.yh.filesmanage.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.yh.filesmanage.R;
import com.yh.filesmanage.diagnose.LayerEntity;
import com.yh.filesmanage.utils.DensityUtils;
import com.yh.filesmanage.utils.LogUtils;

import java.util.List;

import androidx.annotation.Nullable;

public class LayerView extends View {

    private Context mContext;
    private int itemSize = 15;
    private int itemBgWidth = 4;
    private int itemHeight;
    private int itemWidth = 0;
    Paint paint;
    Paint bg;
    Paint positionBg;
    private boolean isDrawBg = false;

    private LayerEntity item;

    public LayerView(Context context) {
        super(context);
        initView(context);
    }

    public LayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        paint = new Paint();
        paint.setColor(mContext.getResources().getColor(R.color.red));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20.0f);
        paint.setAlpha(180);
        bg = new Paint();
        bg.setColor(mContext.getResources().getColor(R.color.blue_bg));
        bg.setStyle(Paint.Style.STROKE);
        bg.setStrokeWidth(5.0f);
        bg.setAlpha(180);
        positionBg = new Paint();
        positionBg.setColor(mContext.getResources().getColor(R.color.yellow));
        positionBg.setStyle(Paint.Style.STROKE);
        positionBg.setStrokeWidth(5.0f);
        positionBg.setAlpha(180);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.itemHeight = heightMeasureSpec;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<LayerEntity.Item> items = item.getItems();
        for(int i = 0; i < items.size(); i++) {
            switch (items.get(i).getState()) {
                case 0:
                    paint.setColor(mContext.getResources().getColor(R.color.gray_dark));
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

            canvas.drawRect(QMUIDisplayHelper.dp2px(mContext,itemBgWidth + i*itemSize), QMUIDisplayHelper.dp2px(mContext,itemBgWidth),
                    QMUIDisplayHelper.dp2px(mContext,itemSize - itemBgWidth + i*itemSize), QMUIDisplayHelper.dp2px(mContext,95), paint);

            canvas.drawRect(QMUIDisplayHelper.dp2px(mContext,2), QMUIDisplayHelper.dp2px(mContext,2),
                    QMUIDisplayHelper.dp2px(mContext,itemSize+ i*itemSize), QMUIDisplayHelper.dp2px(mContext,98), bg);
            if(isDrawBg) {
                canvas.drawRect(QMUIDisplayHelper.dp2px(mContext,0), QMUIDisplayHelper.dp2px(mContext,0),
                        QMUIDisplayHelper.dp2px(mContext,itemWidth), QMUIDisplayHelper.dp2px(mContext,100), positionBg);
            }
        }
    }

    public void setLayer(LayerEntity item) {
        this.item = item;
    }

    public void setPositionBg() {
        int size = item.getItems().size();
        itemWidth = itemSize + (size - 1) * itemSize;
        isDrawBg = true;
    }
}
