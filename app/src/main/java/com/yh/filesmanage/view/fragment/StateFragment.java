package com.yh.filesmanage.view.fragment;

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
import com.yh.filesmanage.diagnose.LayerEntity;
import com.yh.filesmanage.utils.HexUtil;
import com.yh.filesmanage.utils.LogUtils;
import com.yh.filesmanage.view.MainActivity;

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
    @BindView(R.id.ll_state_choose_layer)
    LinearLayout llStateChooseLayer;
    @BindView(R.id.btn_state_back)
    ImageButton btnStateBack;
    @BindView(R.id.btn_state_next)
    ImageButton btnStateNext;

    private List<LayerEntity> layers = new ArrayList<>();
    private LayerAdapter adapter;
    private QMUIPopup popup;

    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ExecutorService readEs;

    private int areaNo = 0;//区号
    private int layerNo = 0;//层数
    private int cabinetNo = 0;//柜号
    private int boxNo = 0;//盒号


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
    }

    @Override
    protected void initData() {
        MainActivity activity = (MainActivity) getActivity();
        mInputStream = activity.getmInputStream();
        mOutputStream = activity.getmOutputStream();
        readEs = activity.getReadEs();

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

    @OnClick({R.id.btn_state_check, R.id.btn_state_up, R.id.btn_state_open,
            R.id.btn_state_close, R.id.btn_state_stop, R.id.btn_state_forward,
            R.id.btn_state_reverse, R.id.btn_state_open_layer, R.id.ll_state_choose_layer,
            R.id.btn_state_back, R.id.btn_state_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_state_check:
                break;
            case R.id.btn_state_up:
                break;
            case R.id.btn_state_open:
                sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) HexUtil.getIntForHexInt(areaNo),//区号
                        (byte) 0x1A,
                        (byte) HexUtil.getIntForHexInt(cabinetNo),//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_close:
                sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) HexUtil.getIntForHexInt(areaNo),//区号
                        (byte) 0x0C,
                        (byte) HexUtil.getIntForHexInt(cabinetNo),//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_stop:
                sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) HexUtil.getIntForHexInt(areaNo),//区号
                        (byte) 0x06,
                        (byte) HexUtil.getIntForHexInt(cabinetNo),//柜号
                        (byte) 0x9E});
                break;
            case R.id.btn_state_forward:
                break;
            case R.id.btn_state_reverse:
                break;
            case R.id.btn_state_open_layer:
                //0xac 区号 0x07 打开的柜号 01 层号 盒号 00 01 档案名称 0x9e
                sendSeriportData(new byte[]{(byte) 0xAC,
                        (byte) HexUtil.getIntForHexInt(areaNo),//区号
                        (byte) 0x07,
                        (byte) HexUtil.getIntForHexInt(cabinetNo),//区号
                        (byte) 0x01,
                        (byte) HexUtil.getIntForHexInt(layerNo),//区号
                        (byte) HexUtil.getIntForHexInt(boxNo),//区号
                        (byte) 0x00,
                        (byte) 0x01,
                        (byte) 0x01,//档案名称
                        (byte) 0x9E});
                break;
            case R.id.ll_state_choose_layer:
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list.add(i + 1 + "");
                }
                LayerChooseAdapter adapter = new LayerChooseAdapter(getContext(), list);
                AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), list.get(position), Toast.LENGTH_SHORT).show();
                        layerNo = position + 1;
                        if (popup != null) {
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
            case R.id.btn_state_back:

                break;
            case R.id.btn_state_next:

                break;
        }
    }

    public void sendSeriportData(byte[] send) {
        readEs.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mOutputStream.write(send);
                    mOutputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getSeriportData(byte[] send) {
        readEs.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] send = new byte[]{(byte) 0xAC,
                            (byte) 0x01,
                            (byte) HexUtil.getIntForHexInt(areaNo),//区号
                            (byte) 0x00,
                            (byte) 0x9E};//查询报文
                    mOutputStream.write(send);
                    mOutputStream.flush();
                    Thread.sleep(150);
                    mainloop(mInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void mainloop(InputStream inputStream) throws IOException {
        if (inputStream.available() >= 2) {
            byte[] Re_buf = new byte[inputStream.available()];
            int size = mInputStream.read(Re_buf);
            LogUtils.e("接收到串口回调w == " + size);
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    LogUtils.e("十进制=" + Re_buf[i]);
                    final String res = HexUtil.byteToHexString(Re_buf[i]);
                }
            }
        }
    }
}
