package com.crazypudding.jetpack.roomsample.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazypudding.jetpack.roomsample.R;
import com.crazypudding.jetpack.roomsample.modle.Place;
import com.crazypudding.jetpack.roomsample.modle.Product;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlace;
import com.crazypudding.jetpack.roomsample.ui.activity.EditResActivity;
import com.crazypudding.jetpack.roomsample.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.crazypudding.jetpack.roomsample.ui.activity.EditResActivity.TAG_PRODUCT_ID;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<ProductWithPlace> data;
    private Context mContext;

    public RecordAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_res_desc, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProductWithPlace pwp = getData().get(i);
        final Product product = pwp.getProduct();
        String productName = product.getProductName();
        double productPrice = product.getProductPrice();
        Date purchaseDate = product.getPurchaseDate();
        Place place = pwp.getPlace();
        String placeDesc = place.getDescription();

        if (!TextUtils.isEmpty(productName)) {
            viewHolder.tvProductName.setText(productName);
        }

        if (productPrice != -1) {
            viewHolder.tvProductPrice.setText(mContext.getString(R.string.string_price, productPrice));
        }

        if (purchaseDate != null) {
            viewHolder.tvPurchaseDate.setText(DateUtil.toString(purchaseDate));
        }

        if (!TextUtils.isEmpty(placeDesc)) {
            viewHolder.tvPlaceDesc.setText(placeDesc);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = -1;
                if (product.getId() > 0) {
                    productId = product.getId();
                }
                Intent intent = new Intent(mContext, EditResActivity.class);
                intent.putExtra(TAG_PRODUCT_ID, productId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public List<ProductWithPlace> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<ProductWithPlace> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView tvPurchaseDate;
        private TextView tvPlaceDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_price);
            tvPurchaseDate = itemView.findViewById(R.id.tv_purchase_date);
            tvPlaceDesc = itemView.findViewById(R.id.tv_product_place);
        }
    }
}
