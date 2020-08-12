package com.mottainai.driver.ui.caselog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.caselog.CaseLog;
import com.mottainai.driver.utils.Constants;

import java.util.List;

public class CaseLogListAdapter extends RecyclerView.Adapter<CaseLogListAdapter.CaseLogViewHolder> {

    private Context mContext;
    private List<CaseLog> caseLogList;

    public CaseLogListAdapter(List<CaseLog> caseLogList) {
        this.caseLogList = caseLogList;
    }

    @NonNull
    @Override
    public CaseLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_case_log, parent, false);
        return new CaseLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseLogViewHolder holder, int position) {
        CaseLog caseLog = caseLogList.get(position);
        holder.category.setText(caseLog.getCategory());
        holder.comments.setText(caseLog.getComments());

        String statusInfo = caseLog.getStatusInfo();
        holder.statusInfo.setText(statusInfo.toUpperCase());
        holder.date.setText(caseLog.getCreatedAt());
        if(statusInfo.equalsIgnoreCase(Constants.RESOLVED)) {
            holder.viewLine.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryGreen));
            holder.statusInfo.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryGreen));
            holder.statusIndicator.setBackground(mContext.getDrawable(R.drawable.status_indicator_primary_green));
        } else {
            holder.viewLine.setBackgroundColor(mContext.getResources().getColor(R.color.colorStatusRedLight));
            holder.statusInfo.setTextColor(mContext.getResources().getColor(R.color.colorStatusRedLight));
            holder.statusIndicator.setBackground(mContext.getDrawable(R.drawable.status_indiactor_light_red));
        }
    }

    @Override
    public int getItemCount() {
        return caseLogList.size();
    }

    public static class CaseLogViewHolder extends RecyclerView.ViewHolder {

        public View viewLine;
        public TextView category;
        public TextView comments;
        public ImageView statusIndicator;
        public TextView statusInfo;
        public TextView date;

        public CaseLogViewHolder(@NonNull View itemView) {
            super(itemView);
            viewLine = itemView.findViewById(R.id.viewLine);
            category = itemView.findViewById(R.id.category);
            comments = itemView.findViewById(R.id.comments);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
            statusInfo = itemView.findViewById(R.id.statusInfo);
            date = itemView.findViewById(R.id.date);
        }

    }
}
