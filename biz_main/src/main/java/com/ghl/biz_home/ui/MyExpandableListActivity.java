package com.ghl.biz_home.ui;


import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.ghl.router_annotation.Route;

import static com.ghl.common.constants.main.HomeConstantsKt.ACTIVITY_EXPANDABLE_LIST;

@Route(ACTIVITY_EXPANDABLE_LIST)
public class MyExpandableListActivity extends ExpandableListActivity {
    private ExpandableListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("ExpandableList");
        mAdapter = new MyExpandableListAdapter(this);
        setListAdapter(mAdapter);
        registerForContextMenu(this.getExpandableListView());
    }


    //为列表的每一项创建上下文菜单（即长按后 呼出的菜单）
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("ContexMenu");
        menu.add(0, 0, 0, "ContextMenu");
    }

    //单击上下文菜单后的逻辑
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            Toast.makeText(this, title + "-Group Index" + groupPos + "Child Index:" + childPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}