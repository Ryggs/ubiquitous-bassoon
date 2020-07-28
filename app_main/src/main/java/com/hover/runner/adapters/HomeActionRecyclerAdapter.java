package com.hover.runner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hover.runner.ApplicationInstance;
import com.hover.runner.R;
import com.hover.runner.enums.StatusEnums;
import com.hover.runner.interfaces.CustomOnClickListener;
import com.hover.runner.models.ActionsModel;
import com.hover.runner.utils.UIHelper;

import java.util.List;

public  class HomeActionRecyclerAdapter extends RecyclerView.Adapter<HomeActionRecyclerAdapter.ActionListItemView> {

    private List<ActionsModel> actionsModel;
    private boolean showStatus;
    private CustomOnClickListener customOnClickListener;
    private View currentTextView;

    public HomeActionRecyclerAdapter(List<ActionsModel> actionsModel, boolean showStatus, CustomOnClickListener customOnClickListener) {
        this.actionsModel = actionsModel;
        this.showStatus = showStatus;
        this.customOnClickListener = customOnClickListener;
    }

    public View getTitleView() {
        return currentTextView;
    }


    @NonNull
    @Override
    public ActionListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.action_list_items, parent, false);
        return new ActionListItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionListItemView holder, int position) {
        ActionsModel model = actionsModel.get(position);
        UIHelper.setTextUnderline(holder.actionIdText, model.getActionId());
        if(model.getActionEnum() == StatusEnums.PENDING)
            holder.actionIdText.setTextColor(ApplicationInstance.getColorYellow());
        else if(model.getActionEnum() == StatusEnums.UNSUCCESSFUL)
            holder.actionIdText.setTextColor(ApplicationInstance.getColorRed());
        else if(model.getActionEnum() == StatusEnums.SUCCESS)
            holder.actionIdText.setTextColor(ApplicationInstance.getColorGreen());
        holder.actionTitleText.setText(model.getActionTitle());


        if (showStatus) {
            if (model.getActionEnum() != StatusEnums.NOT_YET_RUN)
                holder.iconImage.setImageResource(UIHelper.getActionIconDrawable(model.getActionEnum()));
        }
        holder.itemView.setOnClickListener(v ->  {
            currentTextView = v;
            customOnClickListener.customClickListener(
                    model.getActionId(),
                    model.getActionTitle(),
                    model.getActionEnum()
            );
        });

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
        if (actionsModel == null) return 0;
        return actionsModel.size();
    }
    static class ActionListItemView extends RecyclerView.ViewHolder {
        TextView actionIdText, actionTitleText;
        ImageView iconImage;
        ActionListItemView(@NonNull View itemView) {
            super(itemView);
            actionIdText = itemView.findViewById(R.id.actionIdText_Id);
            actionTitleText = itemView.findViewById(R.id.actionTitle_Id);
            iconImage = itemView.findViewById(R.id.actionIconStatus);
        }
    }
}