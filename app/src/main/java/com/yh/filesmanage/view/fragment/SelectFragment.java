package com.yh.filesmanage.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.ChooseViewAdapter;
import com.yh.filesmanage.adapter.LayerChooseAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.widget.ChooseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectFragment extends BaseFragment {
    @BindView(R.id.ll_select_choose_way)
    ChooseView llSelectChooseWay;
    @BindView(R.id.et_select_key)
    EditText etSelectKey;
    @BindView(R.id.rl_select_choose_area)
    ChooseView rlSelectChooseArea;
    @BindView(R.id.rl_select_choose_col)
    ChooseView rlSelectChooseCol;
    @BindView(R.id.rl_select_choose_face)
    ChooseView rlSelectChooseFace;
    @BindView(R.id.rl_select_choose_way)
    ChooseView rlSelectChooseWay;
    @BindView(R.id.rl_select_choose_layer)
    ChooseView rlSelectChooseLayer;

    private QMUIPopup popup;
    private List<String> areas = new ArrayList<>();
    private List<String> cols = new ArrayList<>();
    private List<String> faces = new ArrayList<>();
    private List<String> ways = new ArrayList<>();
    private List<String> layers = new ArrayList<>();

    private String[] selects = new String[]{"档案题名","档案条码","架位条码"};
    private ChooseViewAdapter selectWayAdapter,areaAdapter,colAdapter,faceAdapter,wayAdapter,layerAdapter;
    private AdapterView.OnItemClickListener selectWayOnItemClickListener,areaOnItemClickListener,colOnItemClickListener,
                                        faceOnItemClickListener,wayOnItemClickListener,layerOnItemClickListener;

    private int selectWay = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select;
    }

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        for (String s : selects) {
            list.add(s);
        }
        selectWayAdapter = new ChooseViewAdapter(getContext(), list);
        selectWayOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectWay = position;
                llSelectChooseWay.setTextValue(selects[position]);
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
        areaAdapter = new ChooseViewAdapter(getContext(), areas);
        areaOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rlSelectChooseArea.setTextValue(areas.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
        colAdapter = new ChooseViewAdapter(getContext(), cols);
        colOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rlSelectChooseCol.setTextValue(cols.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
        faceAdapter = new ChooseViewAdapter(getContext(), faces);
        faceOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rlSelectChooseFace.setTextValue(faces.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
        wayAdapter = new ChooseViewAdapter(getContext(), ways);
        wayOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rlSelectChooseWay.setTextValue(ways.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
        layerAdapter = new ChooseViewAdapter(getContext(), layers);
        layerOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rlSelectChooseLayer.setTextValue(layers.get(position));
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            cols.clear();
            int cabinetSize = (int) SPUtils.getParam(getContext(), Constants.SP_SIZE_CABINET,1);
            for(int i = 0;i < cabinetSize; i++) {
                cols.add(i+1 + "");
            }
            colAdapter.notifyDataSetChanged();
            ways.clear();
            int classSize = (int) SPUtils.getParam(getContext(), Constants.SP_SIZE_CLASS,1);
            for(int i = 0;i < classSize; i++) {
                ways.add(i+1 + "");
            }
            wayAdapter.notifyDataSetChanged();
            layers.clear();
            int layerSize = (int) SPUtils.getParam(getContext(), Constants.SP_SIZE_LAYER,1);
            for(int i = 0;i < layerSize; i++) {
                layers.add(i+1 + "");
            }
            layerAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.ll_select_choose_way, R.id.rl_select_choose_area, R.id.rl_select_choose_col, R.id.rl_select_choose_face, R.id.rl_select_choose_way, R.id.rl_select_choose_layer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_choose_way:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        selectWayAdapter, selectWayOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(llSelectChooseWay);
                break;
            case R.id.rl_select_choose_area:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        areaAdapter, areaOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rlSelectChooseArea);
                break;
            case R.id.rl_select_choose_col:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        colAdapter, colOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rlSelectChooseCol);
                break;
            case R.id.rl_select_choose_face:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        faceAdapter, faceOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rlSelectChooseFace);
                break;
            case R.id.rl_select_choose_way:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        wayAdapter, wayOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rlSelectChooseWay);
                break;
            case R.id.rl_select_choose_layer:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        layerAdapter, layerOnItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rlSelectChooseLayer);
                break;
        }
    }
}
