package com.hover.runner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hover.runner.ApplicationInstance;
import com.hover.runner.R;
import com.hover.runner.enums.StatusEnums;
import com.hover.runner.interfaces.CustomOnClickListener;
import com.hover.runner.models.TransactionModels;
import com.hover.runner.utils.UIHelper;

import java.util.List;

public  class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionListItemView> {

    private List<TransactionModels> transactionModelsList;
    private CustomOnClickListener customOnClickListener;

    public TransactionRecyclerAdapter(List<TransactionModels> transactionModelsList, CustomOnClickListener customOnClickListener) {
        this.transactionModelsList = transactionModelsList;
        this.customOnClickListener = customOnClickListener;
    }

    @NonNull
    @Override
    public TransactionListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_items, parent, false);
        return new TransactionListItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListItemView holder, int position) {
        TransactionModels transactionModels = transactionModelsList.get(position);
        holder.date.setText(transactionModels.getDate());
        holder.content.setText(transactionModels.getCaption());

        if(transactionModels.getStatusEnums() != StatusEnums.NOT_YET_RUN) {
            holder.date.setCompoundDrawablesWithIntrinsicBounds(0,0, UIHelper.getActionIconDrawable(transactionModels.getStatusEnums()), 0);
            holder.date.setCompoundDrawablePadding(8);
            holder.date.setTextColor(transactionModels.getStatusEnums() == StatusEnums.PENDING ?
                    ApplicationInstance.getColorYellow() : transactionModels.getStatusEnums() == StatusEnums.UNSUCCESSFUL ? ApplicationInstance.getColorRed() : ApplicationInstance.getColorGreen());
        }

        holder.itemView.setOnClickListener(v -> customOnClickListener.customClickListener(
                transactionModels.getTransaction_id(),
                transactionModels.getDate(),
                transactionModels.getStatusEnums()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (transactionModelsList == null) return 0;
        return Math.min(transactionModelsList.size(), 200);
    }

    static class TransactionListItemView extends  RecyclerView.ViewHolder {
        TextView date, content;
        TransactionListItemView(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.transaction_date_id);
            content = itemView.findViewById(R.id.transaction_content_id);
        }
    }


}