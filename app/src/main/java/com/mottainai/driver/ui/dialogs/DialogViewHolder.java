package com.mottainai.driver.ui.dialogs;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;

public final class DialogViewHolder {
    private DialogViewHolder() {
    }

    public static int SIMPLE_TEXT_VIEW_LAYOUT = R.layout.dialog_text_row;
    public static String SELECTED_ROW_DATA = "selectedRowData";
    public static String SELECTED_ROW_POSITION = "selectedRowPosition";
    public static String INPUT_TYPE = "inputType";
    public static String INPUT_DATA = "inputData";
    public static String TYPE_COUNTRY = "country";
    public static String TYPE_STATE_CITY = "state_city";
    public static String TYPE_VEHICLE_TYPES = "vehicle_types";
    public static String TYPE_CASE_CATEGORY = "case_category";
    public static String TYPE_MONTH = "month";

    public static class SimpleTextView extends RecyclerView.ViewHolder {
        public TextView title;

        public SimpleTextView(View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
        }
    }
}
