package com.mottainai.driver.ui.pickuphistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.pickuphistory.dashboard.Wastestream;
import com.mottainai.driver.utils.ImageLoaderUtils;

import java.util.List;

public class PickupDetailsAdapter extends RecyclerView.Adapter<PickupDetailsAdapter.PickupDetailsViewHolder> {

    private List<Wastestream> wastestreamList;
    private Context context;

    public PickupDetailsAdapter(List<Wastestream> wastestreamList) {
        this.wastestreamList = wastestreamList;
    }

    @NonNull
    @Override
    public PickupDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_pickup_detail, parent, false);
        return new PickupDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupDetailsViewHolder holder, int position) {
        Wastestream wastestream = wastestreamList.get(position);
        ImageLoaderUtils.getInstance().loadImage(context, holder.wasteStreamImage, wastestream.getImage());
        holder.wasteStreamName.setText(wastestream.getWasteStreamName());
        if(position == 0) {
            holder.quantity.setText(wastestream.getQuantityinkg());
        } else  {
            holder.quantity.setText(wastestream.getQuantityinkg() + " Kgs");
        }
    }

    @Override
    public int getItemCount() {
        return wastestreamList.size();
    }

    class PickupDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView wasteStreamImage;
        TextView wasteStreamName;
        TextView quantity;

        public PickupDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            wasteStreamImage = itemView.findViewById(R.id.waste_stream_image);
            wasteStreamName = itemView.findViewById(R.id.waste_stream_name);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
