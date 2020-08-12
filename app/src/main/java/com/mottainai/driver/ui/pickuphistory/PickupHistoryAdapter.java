package com.mottainai.driver.ui.pickuphistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistory;

import java.util.List;

public class PickupHistoryAdapter extends RecyclerView.Adapter<PickupHistoryAdapter.PickupHistoryViewHolder> {

    private List<PickupHistory> pickupHistories;

    public PickupHistoryAdapter(List<PickupHistory> pickupHistories) {
        this.pickupHistories = pickupHistories;
    }

    @NonNull
    @Override
    public PickupHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pickup_history, parent, false);
        return new PickupHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupHistoryViewHolder holder, int position) {
        PickupHistory pickupHistory = pickupHistories.get(position);
        holder.customerName.setText(pickupHistory.getCustomerName());
        holder.address.setText(pickupHistory.getAddress());
        holder.time.setText(pickupHistory.getPickupDate());
    }

    @Override
    public int getItemCount() {
        return pickupHistories.size();
    }

    class PickupHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView customerName;
        TextView address;
        TextView wasteStreamName;
        TextView time;
        TextView container;
        TextView weight;

        public PickupHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customer_name);
            address = itemView.findViewById(R.id.address);
            wasteStreamName = itemView.findViewById(R.id.waste_stream_name);
            time = itemView.findViewById(R.id.time);
            container = itemView.findViewById(R.id.container);
            weight = itemView.findViewById(R.id.weight);
        }
    }
}
