package com.mottainai.driver.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.utils.EnumConstants;

import java.util.Objects;

public abstract class CustomDialogFragment extends DialogFragment {

    protected View dialogFragmentLayout;
    protected DialogFragmentListener dialogFragmentListener;
    protected EnumConstants.TransactionType requestedScreen = EnumConstants.TransactionType.NON;
    protected EnumConstants.ParentComponentType parentComponentType = EnumConstants.ParentComponentType.NON;

    protected RecyclerView recyclerView;

    protected AlertDialog onCreateInit(int layoutResourceID){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(dialogFragmentLayout = inflater.inflate(layoutResourceID, null));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initDialogListener();
    }

    protected <T extends View> T bindFragmentWidgets(int resourceID){
        return dialogFragmentLayout.findViewById(resourceID);
    }

    private void initDialogListener(){
        switch (parentComponentType){
            case ACTIVITY: //For Activity : getActivity()
                dialogFragmentListener = (DialogFragmentListener) getActivity();
                break;
            case FRAGMENT: //For Fragment : getTargetFragment()
                dialogFragmentListener = (DialogFragmentListener) getTargetFragment();
                break;
        }
    }

    protected void initRecyclerView(Boolean isNestedScrolling){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(isNestedScrolling);
    }
}
