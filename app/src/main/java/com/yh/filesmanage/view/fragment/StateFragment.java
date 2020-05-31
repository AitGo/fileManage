package com.yh.filesmanage.view.fragment;

import android.view.View;

import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.LayerAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.diagnose.LayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class StateFragment extends BaseFragment {


    @BindView(R.id.ll_state_save_right)
    QMUILinearLayout llStateSaveRight;
    @BindView(R.id.ll_state_save_left)
    QMUILinearLayout llStateSaveLeft;
    @BindView(R.id.ll_state_error_right)
    QMUILinearLayout llStateErrorRight;
    @BindView(R.id.ll_state_error_left)
    QMUILinearLayout llStateErrorLeft;
    @BindView(R.id.ll_state_time_right)
    QMUILinearLayout llStateTimeRight;
    @BindView(R.id.ll_state_time_left)
    QMUILinearLayout llStateTimeLeft;
    @BindView(R.id.rv_state_layer)
    RecyclerView rvStateLayer;

    private List<LayerEntity> layers = new ArrayList<>();
    private LayerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_state;
    }

    @Override
    protected void initView(View inflate) {
        llStateSaveLeft.setRadiusAndShadow(40, 0, 10);
        llStateSaveRight.setRadiusAndShadow(40, IQMUILayout.HIDE_RADIUS_SIDE_LEFT, 0, 10);
        llStateErrorLeft.setRadiusAndShadow(40, 0, 10);
        llStateErrorRight.setRadiusAndShadow(40, IQMUILayout.HIDE_RADIUS_SIDE_LEFT, 0, 10);
        llStateTimeLeft.setRadiusAndShadow(40, 0, 10);
        llStateTimeRight.setRadiusAndShadow(40, IQMUILayout.HIDE_RADIUS_SIDE_LEFT, 0, 10);

        rvStateLayer.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LayerAdapter(layers,getContext());
        rvStateLayer.setAdapter(adapter);
        //添加Android自带的分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.bg_custom_layer));
        rvStateLayer.addItemDecoration(divider);

    }

    @Override
    protected void initData() {

        Random rand = new Random();
        for(int i = 0; i < 20; i++) {
            LayerEntity entity = new LayerEntity();
            entity.setIndex(i + 1);
            entity.setState(0);
            List<LayerEntity.Item> items = new ArrayList<>();
            for(int j = 0; j < 15; j++) {
                LayerEntity.Item item = new LayerEntity.Item();
                item.setIndex(j+1);
                int randNum = rand.nextInt(4);
                item.setState(randNum);
                items.add(item);
            }
            entity.setItems(items);
            layers.add(entity);
        }
    }

}
