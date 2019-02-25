package com.crazypudding.jetpack.roomsample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.crazypudding.jetpack.roomsample.DataRepository;
import com.crazypudding.jetpack.roomsample.GetDatasCallback;
import com.crazypudding.jetpack.roomsample.R;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlace;
import com.crazypudding.jetpack.roomsample.ui.adapter.RecordAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRvRes;
    private TextView mTvLoadHint;
    private RecordAdapter mAdapter;
    private DataRepository mDataRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.inflateMenu(R.menu.menu_main);
        mRvRes = findViewById(R.id.rv_res);
        mTvLoadHint = findViewById(R.id.tv_load_hint);
        mTvLoadHint.setText(R.string.string_loading_hint);

        mAdapter = new RecordAdapter(this);
        mRvRes.setAdapter(mAdapter);

        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRes();
            }
        });

        mDataRepo = DataRepository.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        mDataRepo.getProductsWithPlace(new GetDatasCallback<List<ProductWithPlace>>() {
            @Override
            public void onDataLoaded(List<ProductWithPlace> arg) {
                mAdapter.setData(arg);
                mTvLoadHint.setVisibility(View.GONE);
            }

            @Override
            public void onDataNotAvailable() {
                mAdapter.clearData();
                mTvLoadHint.setText(R.string.string_no_data);
                mTvLoadHint.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addRes() {
        Intent intent = new Intent(this, EditResActivity.class);
        startActivity(intent);
    }
}
