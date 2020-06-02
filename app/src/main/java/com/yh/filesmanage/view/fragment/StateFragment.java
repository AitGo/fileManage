package com.yh.filesmanage.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.LayerAdapter;
import com.yh.filesmanage.adapter.LayerChooseAdapter;
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
import butterknife.OnClick;

public class StateFragment extends BaseFragment {

    @BindView(R.id.rv_state_layer)
    RecyclerView rvStateLayer;
    @BindView(R.id.tv_state_place_num)
    TextView tvStatePlaceNum;
    @BindView(R.id.tv_state_use_num)
    TextView tvStateUseNum;
    @BindView(R.id.tv_state_useless_num)
    TextView tvStateUselessNum;
    @BindView(R.id.tv_state_error)
    TextView tvStateError;
    @BindView(R.id.btn_state_check)
    QMUIButton btnStateCheck;
    @BindView(R.id.btn_state_up)
    QMUIButton btnStateUp;
    @BindView(R.id.btn_state_open)
    QMUIButton btnStateOpen;
    @BindView(R.id.btn_state_close)
    QMUIButton btnStateClose;
    @BindView(R.id.btn_state_stop)
    QMUIButton btnStateStop;
    @BindView(R.id.btn_state_forward)
    QMUIButton btnStateForward;
    @BindView(R.id.btn_state_reverse)
    QMUIButton btnStateReverse;
    @BindView(R.id.btn_state_open_layer)
    QMUIButton btnStateOpenLayer;
    @BindView(R.id.ll_state_choose_layer)
    LinearLayout llStateChooseLayer;

    private List<LayerEntity> layers = new ArrayList<>();
    private LayerAdapter adapter;
    private QMUIPopup popup;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_state;
    }

    @Override
    protected void initView(View inflate) {
        rvStateLayer.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LayerAdapter(layers, getContext());
        rvStateLayer.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                adapter.setPositionBg(layers.get(position));
                adapter.notifyDataSetChanged();
            }
        });
        //添加Android自带的分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_custom_layer));
        rvStateLayer.addItemDecoration(divider);
//        LayerView layerView = new LayerView(getContext());

    }

    @Override
    protected void initData() {

        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            LayerEntity entity = new LayerEntity();
            entity.setIndex(i + 1);
            entity.setState(0);
            List<LayerEntity.Item> items = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                LayerEntity.Item item = new LayerEntity.Item();
                item.setIndex(j + 1);
                int randNum = rand.nextInt(4);
                item.setState(randNum);
                items.add(item);
            }
            entity.setItems(items);
            layers.add(entity);
        }
    }

    @OnClick({R.id.btn_state_check, R.id.btn_state_up, R.id.btn_state_open, R.id.btn_state_close, R.id.btn_state_stop, R.id.btn_state_forward, R.id.btn_state_reverse, R.id.btn_state_open_layer, R.id.ll_state_choose_layer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_state_check:
                break;
            case R.id.btn_state_up:
                break;
            case R.id.btn_state_open:
                break;
            case R.id.btn_state_close:
                break;
            case R.id.btn_state_stop:
                break;
            case R.id.btn_state_forward:
                break;
            case R.id.btn_state_reverse:
                break;
            case R.id.btn_state_open_layer:
                break;
            case R.id.ll_state_choose_layer:
                List<String> list = new ArrayList<>();
                for(int i = 0; i < 20; i++) {
                    list.add(i+1 + "");
                }
                LayerChooseAdapter adapter = new LayerChooseAdapter(getContext(),list);
                AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), list.get(position), Toast.LENGTH_SHORT).show();
                        if(popup != null) {
                            popup.dismiss();
                        }
                    }
                };
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        adapter, onItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(llStateChooseLayer);
                break;
        }
    }
}
