package com.ghl.biz_track.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.ghl.biz_track.R;
import com.ghl.router_annotation.Route;

import static com.ghl.common.constants.track.TrackConstantsKt.ACTIVITY_TOOL_BAR;


/**
 * 测试ToolBar，RadioGroup和CompoundButton
 */

@Route(ACTIVITY_TOOL_BAR)
public class ToolBarActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        mToolbar.setTitle("大马哈");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView == null) {
            return true;
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ToolBarActivity.this, "搜索中。。。", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.action_search) {
            Toast.makeText(this, "点击了搜素", Toast.LENGTH_SHORT).show();
            return true;
        } else if (menuId == R.id.action_notifi) {
            Toast.makeText(this, "点击了提示", Toast.LENGTH_SHORT).show();
            return true;
        } else if (menuId == R.id.action_menu_item0) {
            Toast.makeText(this, "点击了item0", Toast.LENGTH_SHORT).show();
            return true;
        } else if (menuId == R.id.action_menu_item1) {
            Toast.makeText(this, "点击了item1", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
