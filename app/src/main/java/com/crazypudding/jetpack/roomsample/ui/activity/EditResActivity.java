package com.crazypudding.jetpack.roomsample.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crazypudding.jetpack.roomsample.DataRepository;
import com.crazypudding.jetpack.roomsample.R;
import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;
import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;

import java.util.Date;

public class EditResActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEtName;
    private EditText mEtPrice;
    private EditText mEtDate;
    private EditText mEtPlace;
    private DataRepository mDataRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_res);

        mDataRepo = DataRepository.getInstance(this.getApplicationContext());

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setTitle(R.string.title_add_res);
        mToolbar.inflateMenu(R.menu.menu_editor);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_done:
                        // 保存操作
                        saveRecord();
                        return true;
                    case R.id.action_del:
                        // 删除记录
                        deleteRecord();
                        return true;
                }
                return false;
            }
        });

        mEtName = findViewById(R.id.et_product_name);
        mEtPrice = findViewById(R.id.et_product_price);
        mEtPlace = findViewById(R.id.et_place);
        mEtDate = findViewById(R.id.et_purchase_date);
        mEtDate.setKeyListener(null);
        mEtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(EditResActivity.this, "hello", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveRecord() {
        String pName = mEtName.getText().toString().trim();
        String pPrice = mEtPrice.getText().toString().trim();
        String date = mEtDate.getText().toString().trim();
        String pPlace = mEtPlace.getText().toString().trim();
        Date pDate = new Date();

        if (TextUtils.isEmpty(pName) && TextUtils.isEmpty(pPrice) && TextUtils.isEmpty(date) && TextUtils.isEmpty(pPlace)) {
            return;
        }

        if (TextUtils.isEmpty(pName)) {
            mEtName.setError(getString(R.string.error_field_null));
            return;
        }

        long placeId = SystemClock.elapsedRealtime();
        PlaceEntity place = new PlaceEntity(placeId, pPlace);
        ProductEntity product = new ProductEntity(pName, Double.valueOf(pPrice), pDate,  placeId);

        mDataRepo.saveProductAndPlace(place, product);

    }

    private void deleteRecord() {

    }
}
