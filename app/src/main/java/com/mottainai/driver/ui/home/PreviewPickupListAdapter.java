package com.mottainai.driver.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.home.Bag;
import com.mottainai.driver.data.model.home.ContainerRequest;
import com.mottainai.driver.data.model.home.WasteStream;
import com.mottainai.driver.data.model.home.WasteStreamUpload;

import java.util.List;

public class PreviewPickupListAdapter extends RecyclerView.Adapter<PreviewPickupListAdapter.PreviewPickupListViewHolder> {

    private List<WasteStreamUpload> wasteStreamUploadList;


    public PreviewPickupListAdapter(List<WasteStreamUpload> wasteStreamUploadList) {
        this.wasteStreamUploadList = wasteStreamUploadList;
    }


    @NonNull
    @Override
    public PreviewPickupListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_preview_pickup, parent, false);
        return new PreviewPickupListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewPickupListViewHolder holder, int position) {
        WasteStreamUpload wasteStreamUpload = wasteStreamUploadList.get(position);
        holder.wasteStreamName.setText(wasteStreamUpload.getWasteStreamName());
        //added...
       // holder.wasteStreamName.setText(wasteStreamUpload.getPickupId());

        List<Bag> bagList = wasteStreamUpload.getBags();
        List<ContainerRequest> containerRequestList = wasteStreamUpload.getContainer();
        String details = "";

        // waste-stream
        if(bagList !=null) {
            holder.quantity.setText("Picked - " + bagList.size() + " Bags");
            for (int i = 0; i < bagList.size(); i++) {
                Bag bag = bagList.get(i);
                details = details +bag.getBagsTitle() + " - " + bag.getBagsWeightKg() + "." + bag.getBagsWeightGm() + " Kg" +
                        " - " + bag.getBarCode() + "\n";
            }
        }

        // trash
        if(containerRequestList !=null) {
            holder.quantity.setText("Picked - " + containerRequestList.size() + " Trash Bin Types");
            for (int j = 0; j < containerRequestList.size(); j++) {
                ContainerRequest containerRequest = containerRequestList.get(j);
                details = details + containerRequest.getContainerName() + " - " + containerRequest.getContainerQuantity() +"\n";
            }
        }

        holder.details.setText(details);
    }

    @Override
    public int getItemCount() {
        return wasteStreamUploadList.size();
    }

    public static class PreviewPickupListViewHolder extends RecyclerView.ViewHolder {

        private TextView id,wasteStreamName, quantity, details;

        public PreviewPickupListViewHolder(@NonNull View itemView) {
            super(itemView);
            // id added...
         //  id = itemView.findViewById(R.id.pickup_id);

            wasteStreamName = itemView.findViewById(R.id.waste_stream_name);
            quantity = itemView.findViewById(R.id.quantity);
            details = itemView.findViewById(R.id.details);
        }
    }
}
