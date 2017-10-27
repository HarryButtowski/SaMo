package ru.key_next.savemoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.key_next.savemoney.DBHelper.ExpenseCategory.Category;

public class ExpenseCategoryAdapter extends BaseAdapter {
    private ArrayList<DBHelper.ExpenseCategory.Category> categories;
    private LayoutInflater lInflater;

    ExpenseCategoryAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.expense_category_item, parent, false);
        }

        Category category = getCategory(position);

        int i = category.nesting_level;
        String s = "";

        while (i-- > 0) {
            s += "-";
        }

        ((TextView) view.findViewById(R.id.indent)).setText(s);

//        if (category.nesting_level == 0) {
//            ((TextView) view.findViewById(R.id.indent)).setText("");
//        } else {
//            ((TextView) view.findViewById(R.id.indent)).setText(String.format("%" + category.nesting_level + "s", ""));
//        }

        ((TextView) view.findViewById(R.id.name)).setText(category.name);

        return view;
    }

    private Category getCategory(int position) {
        return (DBHelper.ExpenseCategory.Category) getItem(position);
    }
}
