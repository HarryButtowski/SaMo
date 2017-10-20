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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class AddExpenseDialogFragment extends DialogFragment implements View.OnClickListener, OnCallbackUpdateExpenseDictionary {
    private OnCallbackUpdateExpense callbackUpdateExpense;
    private Dialog dialog;

    public final static String FRAGMENT_TAG = "add_expense_dialog_fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String strtext = getArguments().getString("edttext");

        View view = inflater.inflate(R.layout.dialog_fragment_add_expense, container, false);

        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(this);

        ImageButton buttonAdd = view.findViewById(R.id.add_expense);
        buttonAdd.setOnClickListener(this);


        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        dialog = new Dialog(getContext(), android.R.style.Theme_Holo_Light);
//        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
//        dialog.setTitle("rerferf");
//        dialog.setContentView(R.layout.dialog_fragment_add_expense);
//
//        return dialog;
//////////////////////////////////////////
        dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Добавить расход");
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbackUpdateExpense = (OnCallbackUpdateExpense) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCallbackUpdateExpense");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                System.out.println("OnClick");
                callbackUpdateExpense.updateExpense();

//                if (dialog != null) {
//                    dialog.dismiss();
//                } else {
                dismiss();
//                }
                break;

            case R.id.button_cancel:
                dismiss();

                break;

            case R.id.add_expense:
                showDialogAddExpense();

                break;
        }
    }

    public void showDialogAddExpense() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AddExpenseDictionaryDialogFragment newFragment = new AddExpenseDictionaryDialogFragment();

        newFragment.show(fragmentManager, AddExpenseDictionaryDialogFragment.FRAGMENT_TAG);
    }

    @Override
    public void updateExpenseDictionary() {

    }
}
