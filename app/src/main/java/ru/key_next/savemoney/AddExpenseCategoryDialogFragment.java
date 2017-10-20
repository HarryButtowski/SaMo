package ru.key_next.savemoney;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddExpenseCategoryDialogFragment extends DialogFragment implements View.OnClickListener {
    private OnCallbackUpdateExpenseCategory callbackUpdateExpenseCategory;
    private Dialog dialog;

    public final static String FRAGMENT_TAG = "add_expense_dictionary_dialog_fragment";

    private String[] categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        categories = bundle.getStringArray(AddExpenseDictionaryDialogFragment.CATEGORIES_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_expense_category, container, false);

        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(this);

        Button buttonCancel = view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);

        Spinner spinner = view.findViewById(R.id.expense_parent_category_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Добавить категорию");

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbackUpdateExpenseCategory = (OnCallbackUpdateExpenseCategory) getActivity().getSupportFragmentManager().findFragmentByTag(AddExpenseDictionaryDialogFragment.FRAGMENT_TAG);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCallbackUpdateExpenseCategory");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                System.out.println("OnClick");
                callbackUpdateExpenseCategory.updateExpenseCategory();
                dismiss();

                break;

            case R.id.button_cancel:
                dismiss();

                break;
        }
    }
}
