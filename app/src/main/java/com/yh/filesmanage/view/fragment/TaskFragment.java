package com.yh.filesmanage.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yh.filesmanage.R;
import com.yh.filesmanage.adapter.ChooseViewAdapter;
import com.yh.filesmanage.base.BaseFragment;
import com.yh.filesmanage.widget.ChooseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskFragment extends BaseFragment {
    @BindView(R.id.btn_task_menu)
    Button btnTaskMenu;
    @BindView(R.id.btn_task_all_finish)
    Button btnTaskAllFinish;
    @BindView(R.id.btn_task_add_task)
    Button btnTaskAddTask;
    @BindView(R.id.btn_task_delete_task)
    Button btnTaskDeleteTask;
    @BindView(R.id.btn_task_finish_task)
    Button btnTaskFinishTask;

    private QMUIPopup popup;
    private String[] menus = new String[]{"最新队列","显示队列","开架","定位"};
    private ChooseViewAdapter menuAdapter;
    private AdapterView.OnItemClickListener menuOnItemClientListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        for (String s : menus) {
            list.add(s);
        }
        menuAdapter = new ChooseViewAdapter(getContext(), list);
        menuOnItemClientListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
    }

    @OnClick({R.id.btn_task_menu, R.id.btn_task_all_finish, R.id.btn_task_add_task, R.id.btn_task_delete_task, R.id.btn_task_finish_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_task_menu:
                popup = QMUIPopups.listPopup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 100),
                        QMUIDisplayHelper.dp2px(getContext(), 250),
                        menuAdapter, menuOnItemClientListener)
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                        .offsetX(QMUIDisplayHelper.dp2px(getContext(), 0))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_AUTO)
                        .show(btnTaskMenu);
                break;
            case R.id.btn_task_all_finish:
                break;
            case R.id.btn_task_add_task:
                break;
            case R.id.btn_task_delete_task:
                break;
            case R.id.btn_task_finish_task:
                break;
        }
    }
}
