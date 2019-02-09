package com.alexiusdev.depeat.ui.adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
    private OnQuantityChangedListener onQuantityChangedListener;
    private OnQuantitySettedListener onQuantitySettedListener;

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
        productViewHolder.productSinglePrice.setText(context.getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f", product.getPrice())));
        productViewHolder.productQty.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnQuantityChangedListener {
        void onChange(float price);
    }

    public OnQuantityChangedListener getOnQuantityChangedListener() {
        return onQuantityChangedListener;
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener onQuantityChangedListener) {
        this.onQuantityChangedListener = onQuantityChangedListener;
    }

    public interface OnQuantitySettedListener {
        void setPrice(float price);
    }

    public OnQuantitySettedListener getOnQuantitySettedListener() {
        return onQuantitySettedListener;
    }

    public void setOnQuantitySettedListener(OnQuantitySettedListener onQuantitySettedListener) {
        this.onQuantitySettedListener = onQuantitySettedListener;
    }




    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextView.OnEditorActionListener {
        private TextView productName, productTotalPrice, productSinglePrice;
        private ImageView addBtn, removeBtn;
        private EditText productQty;
        private Product product;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.food_tv);
            productTotalPrice = itemView.findViewById(R.id.total_price_item);
            productSinglePrice = itemView.findViewById(R.id.single_price);
            productQty = itemView.findViewById(R.id.quantity_et);
            addBtn = itemView.findViewById(R.id.plus_iv);
            removeBtn = itemView.findViewById(R.id.minus_iv);

            addBtn.setOnClickListener(this);
            removeBtn.setOnClickListener(this);
            productQty.setImeOptions(EditorInfo.IME_ACTION_DONE);
            productQty.setOnEditorActionListener(this);
        }

        @Override
        public void onClick(View view) {
            product = products.get(getAdapterPosition());

            if (view.getId() == R.id.plus_iv) {
                product.increaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuantityChangedListener.onChange(product.getPrice());
            } else if (view.getId() == R.id.minus_iv) {
                if(product.getQuantity() == 0)return;
                product.decreaseQuantity();
                notifyItemChanged(getAdapterPosition());
                onQuantityChangedListener.onChange(product.getPrice() * -1);
            }
        }

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                product = products.get(getAdapterPosition());
                int newQty = Integer.parseInt(textView.getText().toString());

                if (textView.getId() == productQty.getId()) {
                    if(newQty > product.getQuantity()) {
                        product.setQuantity(newQty);
                        notifyItemChanged(getAdapterPosition());
                        onQuantitySettedListener.setPrice(product.getPrice() * product.getQuantity());
                    }else if (newQty < product.getQuantity()) {
                        product.setQuantity(newQty);
                        notifyItemChanged(getAdapterPosition());
                        onQuantitySettedListener.setPrice(product.getPrice() * -product.getQuantity());
                    }else if(newQty == 0){
                        product.setQuantity(newQty);
                        notifyItemChanged(getAdapterPosition());
                        onQuantitySettedListener.setPrice(product.getPrice() * -product.getQuantity());
                    }
                    else
                        return false;
                    return true;
                }
            }
            return false;
        }
    }
}