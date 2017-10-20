package ru.key_next.savemoney;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AddExpenseDictionaryDialogFragment extends DialogFragment implements View.OnClickListener, OnCallbackUpdateExpenseCategory {
    private OnCallbackUpdateExpenseDictionary callbackUpdateExpenseDictionary;
    private Dialog dialog;

    public final static String FRAGMENT_TAG = "add_expense_category_dialog_fragment";
    public final static String CATEGORIES_BUNDLE_KEY = "add_expense_category_dialog_fragment";

    private ArrayList<String> categories = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_expense_dictionary, container, false);

        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(this);

        ImageButton buttonAddCategory = view.findViewById(R.id.add_expense_category);
        buttonAddCategory.setOnClickListener(this);


        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Добавить статью расхода");

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbackUpdateExpenseDictionary = (OnCallbackUpdateExpenseDictionary) getActivity().getSupportFragmentManager().findFragmentByTag(AddExpenseDialogFragment.FRAGMENT_TAG);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCallbackUpdateExpenseDictionary");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                System.out.println("OnClick");
                callbackUpdateExpenseDictionary.updateExpenseDictionary();

//                if (dialog != null) {
//                    dialog.dismiss();
//                } else {
                dismiss();
//                }
                break;

            case R.id.button_cancel:
                dismiss();

                break;

            case R.id.add_expense_category:
                showDialogAddExpenseCategory();

                break;
        }
    }

    public void showDialogAddExpenseCategory() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AddExpenseCategoryDialogFragment newFragment = new AddExpenseCategoryDialogFragment();

        Bundle bundle = new Bundle();

        categories.add("ww");
        categories.add("ee");

        bundle.putStringArray(CATEGORIES_BUNDLE_KEY, categories.toArray(new String[0]));
        newFragment.setArguments(bundle);

        newFragment.show(fragmentManager, AddExpenseCategoryDialogFragment.FRAGMENT_TAG);
    }

    @Override
    public void updateExpenseCategory() {
        categories.add("bb");
    }
}
