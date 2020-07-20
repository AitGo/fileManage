package com.yh.filesmanage.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.CWAdapter;
import com.yh.filesmanage.adapter.LayerAdapter;
import com.yh.filesmanage.adapter.LayerChooseAdapter;
import com.yh.filesmanage.adapter.QSAdapter;
import com.yh.filesmanage.base.BaseEvent;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.diagnose.FileInfo;
import com.yh.filesmanage.diagnose.LayerEntity;
import com.yh.filesmanage.diagnose.Response;
import com.yh.filesmanage.diagnose.ResponseList;
import com.yh.filesmanage.utils.DBUtils;
import com.yh.filesmanage.utils.GsonUtils;
import com.yh.filesmanage.utils.HexUtil;
import com.yh.filesmanage.utils.LogUtils;
import com.yh.filesmanage.utils.SPUtils;
import com.yh.filesmanage.utils.StringUtils;
import com.yh.filesmanage.utils.ToastUtils;
import com.yh.filesmanage.view.MainActivity;
import com.yh.filesmanage.widget.ChooseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
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
    @BindView(R.id.tv_state_run)
    TextView tvStateRun;
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
    @BindView(R.id.ll_kw)
    LinearLayout llKw;
    @BindView(R.id.ll_zw)
    LinearLayout llZw;
    @BindView(R.id.ll_qs)
    LinearLayout llQs;
    @BindView(R.id.ll_cw)
    LinearLayout llCw;
    @BindView(R.id.ll_wzl)
    LinearLayout llWzl;
    @BindView(R.id.ll_dpd)
    LinearLayout llDpd;
    @BindView(R.id.tv_UseNum)
    TextView tvUseNum;
    @BindView(R.id.tv_UselessNum)
    TextView tvUselessNum;

    private List<LayerEntity> layers = new ArrayList<>();
    private LayerAdapter adapter;
    private QMUIPopup popup, cwpop, qspop;

    private int areaNo = 1;//区号
    private int layerNo = 1;//层数
    private int cabinetNo = 1;//柜号

    private LayerChooseAdapter chooseAdapter;
    private QSAdapter qsAdapter;
    private CWAdapter cwAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private MainActivity activity;
    private Context mContext;
    private int useSize = 0;

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
                String index = layers.get(position).getIndex();
                tvStateLayerNo.setText(index);
                adapter.notifyDataSetChanged();
            }
        });
        //添加Android自带的分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_custom_layer));
        rvStateLayer.addItemDecoration(divider);

//        initLayerData();
        layers.addAll(DBUtils.selectLayerNoList());
        if (layers.size() > 0) {
            //初始化数据
            adapter.setPositionBg(layers.get(0));
            tvStateLayerNo.setText(layers.get(0).getIndex());
        }

        byte[] startRead = {
                (byte) 0x00, (byte) 0x06,
                (byte) 0x00, (byte) 0x01,//硬件地址
                (byte) 0x00, (byte) 0x05,//读取单层命令
                (byte) 0x01,
                (byte) cabinetNo//ID，与柜号一致
        };
        activity.sendSocketData(HexUtil.getSocketBytes(startRead), Constants.VALUE_CHECK);
    }

    @Override
    protected void initData() {
        activity = (MainActivity) getActivity();
        mContext = getContext();

        int layerSize = (int) SPUtils.getParam(getContext(), Constants.SP_SIZE_LAYER, 10);
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
                SPUtils.setParam(mContext, Constants.SP_NO_LAYER, layerNo);
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(BaseEvent.CommonEvent event) {
        if (event == BaseEvent.CommonEvent.UPDATE_STATE) {
            String s = (String) event.getObject();
            String state = StringUtils.selectState(s);
            tvStateRun.setText(state);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            areaNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_AREA, 1);
            cabinetNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET, 1);
            layerNo = (int) SPUtils.getParam(getContext(), Constants.SP_NO_LAYER, 1);
            StateChooseLayer.setTextValue("第" + StringUtils.getNumber(layerNo) + "层");
            tvStateCabinetNo.setText(StringUtils.getNumber(cabinetNo));
        }
    }

    @OnClick({R.id.btn_state_check, R.id.btn_state_up, R.id.btn_state_open,
            R.id.btn_state_close, R.id.btn_state_stop, R.id.btn_state_forward,
            R.id.btn_state_reverse, R.id.btn_state_open_layer, R.id.state_choose_layer,
            R.id.btn_state_back, R.id.btn_state_next, R.id.ll_kw,
            R.id.ll_zw, R.id.ll_qs, R.id.ll_cw, R.id.ll_wzl, R.id.ll_dpd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_state_check:
                // 0xac 区号 0x18 盘点柜号 盘点层号 0x9e
//                activity.sendSeriportData(new byte[]{(byte) 0xAC,
//                        (byte) areaNo,//区号
//                        (byte) 0x20,
//                        (byte) cabinetNo,//柜号
//                        (byte) layerNo,//层号
//                        (byte) 0x9E});

//                byte[] startRead = {
//                        (byte) 0x00, (byte) 0x06,
//                        (byte) 0x00, (byte) 0x01,//硬件地址
//                        (byte) 0x00, (byte) 0x05,//读取单层命令
//                        (byte) 0x01,
//                        (byte) cabinetNo//ID，与柜号一致
//                };
//                activity.sendSocketData(HexUtil.getSocketBytes(startRead), Constants.VALUE_CHECK);
                activity.setSocektType(1);
                List<FileInfo> fileInfos1 = DBUtils.selectFileInfoByNotState(Constants.VALUE_STATE_KW);
                for (FileInfo fileInfo : fileInfos1) {
                    fileInfo.setStatus(Constants.VALUE_STATE_DPD);
                    DBUtils.insertOrReplaceFileInfo(fileInfo);
                }
//                List<FileInfo> fileInfos2 = DBUtils.selectFileInfoByState(Constants.VALUE_STATE_ZW);
//                for(FileInfo info : fileInfos2) {
//                    if(StringUtils.checkString(info.getRev1())) {
//                        info.setStatus(Constants.VALUE_STATE_CW);
//                        DBUtils.insertOrReplaceFileInfo(info);
//                    }
//                }
                layers.clear();
                layers.addAll(DBUtils.selectLayerNoList());
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_state_up:
                //上架
                //RFID开始检卡
//                byte[] startUp = {
//                        (byte) 0x00, (byte) 0x06,
//                        (byte) 0x00, (byte) 0x01,//硬件地址
//                        (byte) 0x00, (byte) 0x03,
//                        (byte) 0x01,
//                        (byte) 0x01};
//                byte[] startUp = {
//                        (byte) 0x00, (byte) 0x06,
//                        (byte) 0x00, (byte) 0x01,//硬件地址
//                        (byte) 0x00, (byte) 0x05,//读取单层命令
//                        (byte) 0x01,
//                        (byte) cabinetNo//ID，与柜号一致
//                };
//                activity.sendSocketData(HexUtil.getSocketBytes(startUp), Constants.VALUE_UP);
                activity.setSocektType(2);
                //修改错误信息
                List<FileInfo> fileInfos = DBUtils.selectFileInfoByState(Constants.VALUE_STATE_CW);
                for (FileInfo info : fileInfos) {
//                    String rev1 = info.getRev1();
//                    info.setRev1("");
                    String shelfNo = StringUtils.getShelfNo(info, info.getBoxNo());
                    info.setShelf_no(shelfNo);
                    info.setStatus(Constants.VALUE_STATE_ZW);
                    DBUtils.insertOrReplaceFileInfo(info);
                }
                layers.clear();
                layers.addAll(DBUtils.selectLayerNoList());
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_state_open:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x32,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_close:
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x33,
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
            case R.id.btn_state_forward://正转
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x30,
                        (byte) cabinetNo,//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_reverse://反转
                activity.sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) areaNo,//区号
                        (byte) 0x31,
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
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(StateChooseLayer);
                break;
            case R.id.btn_state_back:
                int cabinetMin = (int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET_MIN, 1);
                if (cabinetNo != cabinetMin) {
                    cabinetNo--;
                    SPUtils.setParam(mContext, Constants.SP_NO_CABINET, cabinetNo);
                    tvStateCabinetNo.setText(StringUtils.getNumber(cabinetNo));
                } else {
                    ToastUtils.showShort("没有更小的柜号");
                }
                break;
            case R.id.btn_state_next:
                int cabinetMax = (int) SPUtils.getParam(getContext(), Constants.SP_NO_CABINET_MAX, 5);
                if (cabinetNo != cabinetMax) {
                    cabinetNo++;
                    SPUtils.setParam(mContext, Constants.SP_NO_CABINET, cabinetNo);
                    tvStateCabinetNo.setText(StringUtils.getNumber(cabinetNo));
                } else {
                    ToastUtils.showShort("没有更大的柜号");
                }
                break;
            case R.id.ll_kw://空位

                break;
            case R.id.ll_zw://在位

                break;
            case R.id.ll_qs://缺失
                //        String qsState = "";
                List<FileInfo> qsLists = DBUtils.selectFileInfoByState(Constants.VALUE_STATE_QS);
                if (qsLists.size() == 0) {
                    ToastUtils.showShort("暂无缺失数据");
                    return;
                }
                FileInfo head = new FileInfo();
                head.setBarcode("条码");
                head.setMaintitle("题名");
                head.setShelf_no("当前架位");
                head.setRev1("正确架位");
                qsLists.add(0, head);
                qsAdapter = new QSAdapter(getContext(), qsLists);

                qspop = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 400),
                        QMUIDisplayHelper.dp2px(getContext(), 300),
                        qsAdapter, null)
                        .preferredDirection(QMUIPopup.DIRECTION_CENTER_IN_SCREEN)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rvStateLayer);
                break;
            case R.id.ll_cw://错位
                List<FileInfo> cwLists = DBUtils.selectFileInfoByState(Constants.VALUE_STATE_CW);
                if (cwLists.size() == 0) {
                    ToastUtils.showShort("暂无错位数据");
                    return;
                }
                FileInfo head1 = new FileInfo();
                head1.setBarcode("条码");
                head1.setMaintitle("题名");
                head1.setShelf_no("当前架位");
                head1.setRev1("正确架位");
                cwLists.add(0, head1);
                cwAdapter = new CWAdapter(getContext(), cwLists);

                cwpop = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 550),
                        QMUIDisplayHelper.dp2px(getContext(), 300),
                        cwAdapter, null)
                        .preferredDirection(QMUIPopup.DIRECTION_CENTER_IN_SCREEN)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(rvStateLayer);
                break;
            case R.id.ll_wzl://未著录

                break;
            case R.id.ll_dpd://待盘点

                break;
        }
    }

    private void initLayerData() {
        OkGo.<String>post(Constants.ipAddress + "/get_archive4shelf")
                .tag(this)
                .params("shelf_no", cabinetNo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.e(response.body());
                        Response<Object> objectResponse = GsonUtils.fromJsonArray(response.body(), ResponseList.class);
                        if (!objectResponse.isSuccess()) {
                            ToastUtils.showShort("柜号获取档案信息失败：" + objectResponse.getMessage());
                            return;
                        }
                        ResponseList<Map<String, Object>> fileInfoResponseList = (ResponseList<Map<String, Object>>) objectResponse.getData();
                        List<FileInfo> fileInfos = GsonUtils.map2List(fileInfoResponseList);
                        //解析架位条码shelf_no
                        analyShelfNo(fileInfos);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        LogUtils.e(response.getException().getMessage());
                        ToastUtils.showShort("柜号获取档案信息错误：" + response.getException().getMessage());
                    }

                });
    }

    /**
     * 解析架位条码shelf_no
     *
     * @param list
     */
    private void analyShelfNo(List<FileInfo> list) {
        for (FileInfo info : list) {
            /**
             * 密集架架位地址
             * 01       02  03   2                   03    04   01
             * 库房号   区  列   面（1左2右）         节    层    本
             */
            String shelf_no = info.getShelf_no();
            if (shelf_no.length() < 13) {
                ToastUtils.showShort("价位条码解析错误：长度=" + shelf_no.length());
                return;
            }
            String houseNo = shelf_no.substring(0, 2);
            String areaNo = shelf_no.substring(2, 4);
            String cabinetNo = shelf_no.substring(4, 6);
            String faceNo = shelf_no.substring(6, 7);
            String classNo = shelf_no.substring(7, 9);
            String layerNo = shelf_no.substring(9, 11);
            String boxNo = shelf_no.substring(11, 13);

            info.setHouseSNo(houseNo);
            info.setAreaNO(areaNo);
            info.setCabinetNo(cabinetNo);
            info.setFaceNo(faceNo);
            info.setClassNo(classNo);
            info.setLayerNo(layerNo);
            info.setBoxNo(boxNo);
            DBUtils.insertOrReplaceFileInfo(info);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void updateLayerList() {
        layers.clear();
        layers.addAll(DBUtils.selectLayerNoList());
        adapter.notifyDataSetChanged();
        //设置状态数据
        List<FileInfo> fileInfos = DBUtils.selectFileInfoByState(Constants.VALUE_STATE_KW);
        int boxSize = (int) SPUtils.getParam(getContext(), Constants.SP_SIZE_BOX, 1);
        int layerSize = (int) SPUtils.getParam(getContext(), Constants.SP_SIZE_LAYER, 1);
        int count = layerSize * boxSize;
        tvStatePlaceNum.setText(count + "");
        tvStateUseNum.setText(count - fileInfos.size() + "");
        tvStateUselessNum.setText(fileInfos.size() + "");
        tvUseNum.setText(count - fileInfos.size() + "");
        tvUselessNum.setText(fileInfos.size() + "");
    }
}
