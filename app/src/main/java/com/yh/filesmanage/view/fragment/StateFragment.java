package com.yh.filesmanage.view.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.LayerAdapter;
import com.yh.filesmanage.adapter.LayerChooseAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.diagnose.LayerEntity;
import com.yh.filesmanage.socket.FastSocketClient;
import com.yh.filesmanage.socket.interfaces.OnSocketClientCallBackList;
import com.yh.filesmanage.utils.CRC16;
import com.yh.filesmanage.utils.HexUtil;
import com.yh.filesmanage.utils.LogUtils;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.utils.StringUtils;
import com.yh.filesmanage.utils.ToastUtils;
import com.yh.filesmanage.view.MainActivity;
import com.yh.filesmanage.widget.ChooseView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

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
    @BindView(R.id.state_choose_layer)
    ChooseView StateChooseLayer;
    @BindView(R.id.btn_state_back)
    ImageButton btnStateBack;
    @BindView(R.id.btn_state_next)
    ImageButton btnStateNext;
    @BindView(R.id.tv_state_layer_no)
    TextView tvStateLayerNo;
    @BindView(R.id.tv_state_cabinet_no)
    TextView tvStateCabinetNo;

    private List<LayerEntity> layers = new ArrayList<>();
    private LayerAdapter adapter;
    private QMUIPopup popup;

    private int areaNo = 1;//区号
    private int layerNo = 1;//层数
    private int cabinetNo = 1;//柜号
    private int boxNo = 1;//盒号

    private LayerChooseAdapter chooseAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private MainActivity activity;
    private Context mContext;

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
                int index = layers.get(position).getIndex();
                tvStateLayerNo.setText(StringUtils.getNumber(index));
                adapter.notifyDataSetChanged();
            }
        });
        //添加Android自带的分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_custom_layer));
        rvStateLayer.addItemDecoration(divider);

        //初始化数据
        adapter.setPositionBg(layers.get(0));
        tvStateLayerNo.setText(StringUtils.getNumber(layers.get(0).getIndex()));

    }

    @Override
    protected void initData() {
        activity = (MainActivity) getActivity();
        mContext = getContext();
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

        int layerSize = (int) SPUtils.getParam(getContext(),Constants.SP_SIZE_LAYER,10);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            list.add(i + 1 + "");
        }
        chooseAdapter = new LayerChooseAdapter(getContext(), list);
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                layerNo = position + 1;
                StateChooseLayer.setTextValue("第" + list.get(position) + "层");
                SPUtils.setParam(mContext,Constants.SP_NO_LAYER,layerNo);
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
            areaNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_AREA,1);
            cabinetNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET,1);
            layerNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_LAYER,1);
            StateChooseLayer.setTextValue("第" + StringUtils.getNumber(layerNo) + "层");
//            boxNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_BOX,1);
        }
    }

    @OnClick({R.id.btn_state_check, R.id.btn_state_up, R.id.btn_state_open,
            R.id.btn_state_close, R.id.btn_state_stop, R.id.btn_state_forward,
            R.id.btn_state_reverse, R.id.btn_state_open_layer, R.id.state_choose_layer,
            R.id.btn_state_back, R.id.btn_state_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_state_check:
                // 0xac 区号 0x18 盘点柜号 盘点层号 0x9e
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x18,
                        (byte) cabinetNo,//柜号
                        (byte) layerNo,//层号
                        (byte) 0x9E});
                //RFID开始检卡
                byte[] startRead = {(byte) 0x1B,
                        (byte) 0x00, (byte) 0x06,
                        (byte) 0x00, (byte) 0x01,//硬件地址
                        (byte) 0x00, (byte) 0x03,
                        (byte) 0x01,
                        (byte) 0x01};
                activity.sendSocketData(HexUtil.getSocketBytes(startRead));
                break;
            case R.id.btn_state_up:
                break;
            case R.id.btn_state_open:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x1A,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_close:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x0C,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_stop:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x06,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_forward:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x08,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_reverse:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x09,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_open_layer:
                //0xac 区号 0x07 打开的柜号 01 层号 盒号 00 01 档案名称 0x9e
                //工控机打开时盒号为0，档案名称不填
                    activity.sendSeriportData(new byte[]{(byte) 0xAC,
                            (byte) areaNo,//区号
                            (byte) 0x07,
                            (byte) cabinetNo,//柜号
                            (byte) 0x01,
                            (byte) layerNo,//层号
                            (byte) 0x00,//盒号
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x00,//档案名称
                            (byte) 0x9E});
//                    //控制RFID对应层灯闪烁
//                    byte[] bytes = {(byte) 0x1B,
//                            (byte) 0x00,
//                            (byte) 0x0B,
//                            (byte) 0x00,
//                            (byte) 0x01,
//                            (byte) 0x00,
//                            (byte) 0x00,
//                            (byte) 0x07,
//                            (byte) 0x01,
//                            (byte) 0x0a,//闪烁次数
//                            (byte) 0x05,//闪烁周期
//                            (byte) 0x00,
//                            (byte) layerNo,//层号
//                            (byte) 0x00, (byte) 0x00//位置(盒号) 2字节
//                    };
//                    byte[] socketBytes = HexUtil.getSocketBytes(bytes);
//                    activity.sendSocketData(socketBytes);

                break;
            case R.id.state_choose_layer:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        chooseAdapter, onItemClickListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(StateChooseLayer);
                break;
            case R.id.btn_state_back:
                int cabinetMin = (int) SPUtils.getParam(getContext(),Constants.SP_NO_CABINET_MIN,1);
                if(cabinetNo != cabinetMin) {
                    cabinetNo--;
                    SPUtils.setParam(mContext,Constants.SP_NO_CABINET,cabinetNo);
                    tvStateCabinetNo.setText(StringUtils.getNumber(cabinetNo));
                }else {
                    ToastUtils.showShort("没有更小的柜号");
                }
                break;
            case R.id.btn_state_next:
                int cabinetMax = (int) SPUtils.getParam(getContext(),Constants.SP_NO_CABINET_MAX,5);
                if(cabinetNo != cabinetMax) {
                    cabinetNo++;
                    SPUtils.setParam(mContext,Constants.SP_NO_CABINET,cabinetNo);
                    tvStateCabinetNo.setText(StringUtils.getNumber(cabinetNo));
                }else {
                    ToastUtils.showShort("没有更大的柜号");
                }
                break;
        }
    }


}
