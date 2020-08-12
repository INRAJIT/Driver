package com.mottainai.driver.ui.caselog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.caselog.CaseCategory;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.dialogs.DialogFragmentListener;
import com.mottainai.driver.ui.dialogs.SimpleRecyclerDialogFragment;
import com.mottainai.driver.utils.EnumConstants;
import com.mottainai.driver.utils.PrefManager;

import java.util.ArrayList;

import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_TYPE;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_CASE_CATEGORY;

public class CaseLogFragment extends Fragment implements View.OnClickListener, DialogFragmentListener {

    private TextView caseCategory;
    private AppCompatEditText editComment;
    private CaseLogViewModel caseLogViewModel;
    private CustomProgressBar progressBar;

    private String categoryId;
    private ArrayList<CaseCategory> caseCategoryList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        caseLogViewModel = new ViewModelProvider(this).get(CaseLogViewModel.class);

        caseLogViewModel.getCaseCategoryResponse().observe(this, caseCategoryListResponse -> {
            progressBar.dialog.dismiss();
            if (caseCategoryListResponse.getStatus()) {
                caseCategoryList = (ArrayList<CaseCategory>) caseCategoryListResponse.getSuccess();
            }
        });

        caseLogViewModel.createCaseLogResponse().observe(this, createCaseLogResponse -> {
            progressBar.dialog.dismiss();
            if (createCaseLogResponse.getStatus()) {
                resetDataAndView();
                Intent intent = new Intent(getActivity(), CaseLogActivity.class);
                intent.putExtra("page", "success");
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_log, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        final LinearLayout selectCaseCategory = rootView.findViewById(R.id.select_case_category);
        caseCategory = rootView.findViewById(R.id.case_category);
        editComment = rootView.findViewById(R.id.comment);
        final Button submit = rootView.findViewById(R.id.submit);
        final TextView caseLogList = rootView.findViewById(R.id.case_log_list);
        selectCaseCategory.setOnClickListener(this);
        submit.setOnClickListener(this);
        caseLogList.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        getCaseCategory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_case_category:
                showCategoryListInDialog(caseCategoryList, this, EnumConstants.TransactionType.SELECT_CASE_CATEGORY);
                break;
            case R.id.submit:
                String comment = editComment.getText().toString();
                if (categoryId == null) {
                    Toast.makeText(getContext(), getString(R.string.invalid_case_category), Toast.LENGTH_SHORT).show();
                } else if (comment.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.invalid_comment), Toast.LENGTH_SHORT).show();
                } else {
                    createCase(editComment.getText().toString());
                }
                break;
            case R.id.case_log_list:
                resetDataAndView();
                Intent intent = new Intent(getActivity(), CaseLogActivity.class);
                intent.putExtra("page", "list");
                startActivity(intent);
                break;
        }
    }

    private void getCaseCategory() {
        progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
        caseLogViewModel.getCaseCategory();
    }

    protected void showCategoryListInDialog(ArrayList<CaseCategory> optionList, Fragment sourceFragment,
                                            EnumConstants.TransactionType transactionScreen) {
        Bundle bundle = new Bundle();
        bundle.putString(INPUT_TYPE, TYPE_CASE_CATEGORY);
        bundle.putParcelableArrayList(INPUT_DATA, optionList);
        FragmentManager fm = getParentFragmentManager();
        SimpleRecyclerDialogFragment optionAlertFragment =
                SimpleRecyclerDialogFragment.newInstance(transactionScreen,
                        EnumConstants.ParentComponentType.FRAGMENT,
                        bundle);
        optionAlertFragment.setTargetFragment(sourceFragment, 200);
        optionAlertFragment.show(fm, "fragment_edit_name");
    }

    private void createCase(String comment) {
        progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
        String driverId = new PrefManager(getContext()).getSharedPrefValue(PrefManager.ID);
        caseLogViewModel.createCaseLog(categoryId, comment, driverId);
    }

    private void resetDataAndView() {
        editComment.setText("");
        caseCategory.setText(R.string.select_case_category);
        categoryId = null;
    }

    @Override
    public void onDialogFragmentListItemClick(Bundle bundle, EnumConstants.TransactionType requestedScreen) {
        CaseCategory selectedData = bundle.getParcelable(SELECTED_ROW_DATA);
        caseCategory.setText(selectedData.getCategory());
        categoryId = selectedData.getId();
    }
}