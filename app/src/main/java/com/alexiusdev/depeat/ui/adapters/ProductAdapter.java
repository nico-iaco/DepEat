package com.alexiusdev.depeat.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.datamodels.Product;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Product> products;
    private OnQuanityChangedListener onQuanityChangedListener;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        inflater = LayoutInflater.from(context);
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_cart, viewGroup,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int index) {
        Product product = products.get(index);
        ProductViewHolder productViewHolder = (ProductViewHolder) viewHolder;

        productViewHolder.productName.setText(product.getName());
        productViewHolder.productTotalPrice.setText(context.getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",product.getPrice() * product.getQuantity())));
        productViewHolder.productSinglePrice.setText(context.getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",product.getPrice())));
        productViewHolder.productQty.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnQuanityChangedListener {
        void onChange(float price);
    }

    public OnQuanityChangedListener getOnQuanityChangedListener() {
        return onQuanityChangedListener;
    }

    public void setOnQuanityChangedListener(OnQuanityChangedListener onQuanityChangedListener) {
        this.onQuanityChangedListener = onQuanityChangedListener;
    }





    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView productName, productTotalPrice, productQty, productSinglePrice;
        public ImageView addBtn, removeBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.food_tv);
            productTotalPrice = itemView.findViewById(R.id.total_price_item);
            productSinglePrice = itemView.findViewById(R.id.single_price);
            productQty = itemView.findViewById(R.id.quantity_tv);
            addBtn = itemView.findViewById(R.id.plus_iv);
            removeBtn = itemView.findViewById(R.id.minus_iv);

            addBtn.setOnClickListener(this);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product product = products.get(getAdapterPosition());

            if (view.getId() == R.id.plus_iv) {
                product.increaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuanityChangedListener.onChange(product.getPrice());
            } else if (view.getId() == R.id.minus_iv) {
                if(product.getQuantity() == 0)return;
                product.decreaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuanityChangedListener.onChange(product.getPrice() * -1);
            }
        }
    }
}