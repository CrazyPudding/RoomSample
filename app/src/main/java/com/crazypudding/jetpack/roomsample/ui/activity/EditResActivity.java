package com.crazypudding.jetpack.roomsample.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crazypudding.jetpack.roomsample.ActionCallback;
import com.crazypudding.jetpack.roomsample.DataRepository;
import com.crazypudding.jetpack.roomsample.GetDatasCallback;
import com.crazypudding.jetpack.roomsample.R;
import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;
import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;
import com.crazypudding.jetpack.roomsample.modle.Place;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlaceEntity;
import com.crazypudding.jetpack.roomsample.ui.DatePickerFragment;
import com.crazypudding.jetpack.roomsample.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class EditResActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEtName;
    private EditText mEtPrice;
    private EditText mEtDate;
    private EditText mEtPlace;
    private DataRepository mDataRepo;
    private Date mPurchaseDate;
    private String mPlaceDesc;
    private int mProductId;
    private ProductEntity mProduct;
    private boolean mDateFocused;
    public static final String TAG_PRODUCT_ID = "com.crazypudding.jetpack.roomsample.ui.productid";
    private boolean mRecordHasChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mRecordHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_res);

        mProductId = getIntent().getIntExtra(TAG_PRODUCT_ID, -1);

        mDataRepo = DataRepository.getInstance(this.getApplicationContext());

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
            }
        });
        mToolbar.inflateMenu(R.menu.menu_editor);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_done:
                        // 保存操作
                        saveRecord();
                        finish();
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
        mEtName.setOnTouchListener(mTouchListener);
        mEtPrice.setOnTouchListener(mTouchListener);
        mEtPlace.setOnTouchListener(mTouchListener);
        mEtDate.setOnTouchListener(mTouchListener);
        mEtDate.setKeyListener(null);
        mEtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mDateFocused = true;
                    showDatePicker();
                }
            }
        });
        mEtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDateFocused) {
                    showDatePicker();
                }
            }
        });

        if (mProductId == -1) {
            mToolbar.getMenu().findItem(R.id.action_del).setVisible(false);
            mToolbar.setTitle(R.string.title_editor_res);
        } else {
            mToolbar.setTitle(R.string.title_add_res);
            populateData();
        }
    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
        datePickerFragment.setListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                mPurchaseDate = c.getTime();
                mEtDate.setText(DateUtil.toString(mPurchaseDate));
            }
        });
    }

    private void saveRecord() {
        String pName = mEtName.getText().toString().trim();
        String pPrice = mEtPrice.getText().toString().trim();
        String pPlace = mEtPlace.getText().toString().trim();
        Date pDate = new Date();

        if (TextUtils.isEmpty(pName) && TextUtils.isEmpty(pPrice) && mPurchaseDate == null && TextUtils.isEmpty(pPlace)) {
            return;
        }

        if (TextUtils.isEmpty(pName)) {
            mEtName.setError(getString(R.string.error_field_null));
            return;
        }

        if (TextUtils.isEmpty(pPrice)) {
            pPrice = "-1";
        }

        if (mPurchaseDate != null) {
            pDate = mPurchaseDate;
        }

        long placeId = SystemClock.elapsedRealtime();
        if (TextUtils.isEmpty(pPlace)) {
            placeId = -1;
        }

        if (mProductId == -1) {
            // 保存
            PlaceEntity place = new PlaceEntity(placeId, pPlace);
            ProductEntity product = new ProductEntity(pName, Double.valueOf(pPrice), pDate, placeId);

            mDataRepo.saveProductAndPlace(place, product, new ActionCallback<Long>() {
                @Override
                public void onActionDone(Long arg) {
                    if (arg == 0) {
                        Toast.makeText(EditResActivity.this, getString(R.string.toast_insert_fail), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditResActivity.this, getString(R.string.toast_insert_success), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // 更新
            if (mRecordHasChanged) {
                if (!mPlaceDesc.equals(pPlace)) {
                    PlaceEntity placeEntity = new PlaceEntity(placeId, mPlaceDesc);
                    placeId = mProduct.getPlaceId();
                    mDataRepo.updatePlace(placeEntity, null);
                }
                ProductEntity productEntity = new ProductEntity(pName, Double.valueOf(pPrice), pDate, placeId);
                mDataRepo.updateProduct(productEntity, new ActionCallback<Integer>() {
                    @Override
                    public void onActionDone(Integer arg) {
                        if (arg == 0) {
                            Toast.makeText(EditResActivity.this, getString(R.string.toast_update_fail), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditResActivity.this, getString(R.string.toast_update_success), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!mRecordHasChanged) {
            super.onBackPressed();
        } else {
            showChangeDialog(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }

    private void showChangeDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否放弃编辑？")
                .setPositiveButton("放弃", discardButtonClickListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                })
                .create()
                .show();
    }

    private void deleteRecord() {
        mDataRepo.deleteProduct(mProduct, new ActionCallback<Integer>() {
            @Override
            public void onActionDone(Integer arg) {
                if (arg == 0) {
                    Toast.makeText(EditResActivity.this, getString(R.string.toast_delete_fail), Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                    Toast.makeText(EditResActivity.this, getString(R.string.toast_delete_success), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populateData() {
        mDataRepo.getProductWithPlaceById(mProductId, new GetDatasCallback<ProductWithPlaceEntity>() {
            @Override
            public void onDataLoaded(ProductWithPlaceEntity datas) {
                mProduct = datas.getProduct();
                if (mProduct != null) {
                    if (!TextUtils.isEmpty(mProduct.getName())) {
                        mEtName.setText(mProduct.getName());
                    }
                    if (mProduct.getPrice() != -1) {
                        mEtPrice.setText(String.valueOf(mProduct.getPrice()));
                    }
                    if (mProduct.getPurchaseDate() != null) {
                        mEtDate.setText(DateUtil.toString(mProduct.getPurchaseDate()));
                    }
                }
                Place place = datas.getPlace();
                if (place != null) {
                    if (!TextUtils.isEmpty(place.getDescription())) {
                        mPlaceDesc = place.getDescription();
                        mEtPlace.setText(mPlaceDesc);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(EditResActivity.this, R.string.toast_data_error, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
