package com.mottainai.driver.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mottainai.driver.R;
import com.mottainai.driver.utils.SelectImageUtils;

import static android.app.Activity.RESULT_OK;
import static com.mottainai.driver.utils.SelectImageUtils.SELECT_CAMERA_IMAGE_REQUEST;

public class BeforeAfterPickupFragment extends Fragment implements View.OnClickListener {

    private ImageView trashImage;
    private Button before, after;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beforeafter_pickup, container, false);
        init(view);
        SelectImageUtils.selectImageFromCamera(this, SelectImageUtils.SELECT_CAMERA_IMAGE_REQUEST);
        return view;
    }

    private void init(View rootView) {
        trashImage = rootView.findViewById(R.id.trash_image);
        before = rootView.findViewById(R.id.before);
        after = rootView.findViewById(R.id.after);

        before.setOnClickListener(this);
        after.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_CAMERA_IMAGE_REQUEST:
                    if (data != null && data.getExtras() != null) {
                        Log.d("gaurav", "test3");
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                        trashImage.setImageBitmap(imageBitmap);
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == before) {
            getActivity().onBackPressed();
        } else if(view == after) {
            getActivity().onBackPressed();
        }
    }
}
