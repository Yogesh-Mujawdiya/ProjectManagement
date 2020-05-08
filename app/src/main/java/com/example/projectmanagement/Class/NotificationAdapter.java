package com.example.projectmanagement.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanagement.R;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Notification> productList;

    public NotificationAdapter(Context mCtx, List<Notification> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_view_notification, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Notification product = productList.get(position);
        holder.textViewDescription.setText(product.getDescription());
        holder.textViewDate.setText(String.valueOf(product.getDate()));
        holder.textViewGuide.setText(String.valueOf(product.getGuide_Name()));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDescription, textViewDate, textViewGuide;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewDescription = itemView.findViewById(R.id.tvNotification);
            textViewDate = itemView.findViewById(R.id.tvTime);
            textViewGuide = itemView.findViewById(R.id.tvGuide);
        }
    }
}