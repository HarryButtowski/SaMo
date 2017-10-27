package ru.key_next.savemoney;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import ru.key_next.savemoney.DBHelper.ExpenseCategory.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class AddExpenseCategoryDialogFragment extends DialogFragment implements View.OnClickListener {
    private OnCallbackUpdateExpenseCategory callbackUpdateExpenseCategory;
    private Dialog dialog;
    private SparseArray<Category> spinnerMap = new SparseArray<Category>();

    public final static String FRAGMENT_TAG = "add_expense_dictionary_dialog_fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        spinner.setAdapter(getCategoryAdapter());

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
                Spinner spinner = (Spinner)  getView().findViewById(R.id.expense_parent_category_spinner);
                String categoryName = ((TextView) getView().findViewById(R.id.category_name)).getText().toString();
                Category category = spinnerMap.get(spinner.getSelectedItemPosition());

                addCategory(category._id, categoryName);

                dismiss();

                break;

            case R.id.button_cancel:
                dismiss();

                break;
        }
    }

    private void addCategory(String id, String categoryName) {
        SQLiteDatabase db = new DBHelper(getActivity()).getWritableDatabase();
        DBHelper.ExpenseCategory.set(db, id, categoryName);
        db.close();
    }

    public ExpenseCategoryAdapter getCategoryAdapter() {
        Bundle bundle = getArguments();
        ArrayList<Category> categories = new ArrayList<Category>();
        Category category = new Category("0", "0", "Без категории", 0);

        categories.add(category);
        categories.addAll((ArrayList) bundle.getParcelableArrayList(AddExpenseDictionaryDialogFragment.CATEGORIES_BUNDLE_KEY));

        for (int i = 0; i < categories.size(); i++) {
            spinnerMap.put(i, categories.get(i));
        }

        return new ExpenseCategoryAdapter(getContext(), categories);
    }
}
