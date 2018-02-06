package com.inflater;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout container;
    TextView tvAddViews, tvClearAll, tvEmptyAddViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        container = (LinearLayout) findViewById(R.id.container);
        tvAddViews = (TextView) findViewById(R.id.tv_AddViews);
        tvClearAll = (TextView) findViewById(R.id.tv_clearAllViews);
        tvEmptyAddViews = (TextView) findViewById(R.id.tv_empty_addViews);
        tvAddViews.setOnClickListener(this);
        tvClearAll.setOnClickListener(this);
        setClearButton();
    }

    private void showDialog() {
        final TextInputLayout textInputLayout;
        TextView tv_Cancel, tv_Ok;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_view);
        textInputLayout = dialog.findViewById(R.id.et_numberWrapper);
        EditText editText = (EditText) dialog.findViewById(R.id.et_input);
        showSoftKeyboard(editText);
        tv_Cancel = dialog.findViewById(R.id.tv_dialog_cancel);
        tv_Ok = dialog.findViewById(R.id.tv_dialog_ok);
        tv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textInputLayout.getEditText().getText().toString();
                if (input != null && !input.isEmpty()) {
                    int num = Integer.parseInt(textInputLayout.getEditText().getText().toString());

                    if (num <= 0 || num > 20) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError("Enter a number between 1-20 at a time.");
                    } else {
                        textInputLayout.setError(null);
                        textInputLayout.setErrorEnabled(false);
                        addViews(num);
                        dialog.dismiss();
                    }
                } else if (input.isEmpty()) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Enter a number between 1-20 at a time.");
                }
            }
        });
        dialog.show();
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    private void addViews(int numberOfViews) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        int currentChildCount = container.getChildCount();
        for (int i = currentChildCount; i < currentChildCount + numberOfViews; i++) {
            View view = inflater.inflate(R.layout.item_view, container, false);
//            TextView tv = view.findViewById(R.id.tv_item_text);
//            tv.setText("Text View " + (i + 1));
            TextView tvDelete = view.findViewById(R.id.tv_delete);
            tvDelete.setOnClickListener(this);
            container.addView(view);
        }
        currentChildCount = container.getChildCount();
        for (int i = 0; i < currentChildCount; i++) {
            LinearLayout linearLayout = (LinearLayout) container.getChildAt(i);
            if (linearLayout.getChildAt(0).getId() == R.id.tv_item_text) {
                TextView view = (TextView) linearLayout.getChildAt(0);
//                view.setText("Text View " + (i + 1));
                view.setText(getString(R.string.demo_view) + (i + 1));
                if (i % 2 == 0) {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorBlueLight));

                } else {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorLime));

                }

            }
            setClearButton();
        }
    }

    private void setClearButton() {
        if (container.getChildCount() <= 0) {
            tvClearAll.setVisibility(View.GONE);
            tvEmptyAddViews.setVisibility(View.VISIBLE);
        } else {
            tvClearAll.setVisibility(View.VISIBLE);
            tvEmptyAddViews.setVisibility(View.GONE);
        }
    }

    private void deleteThisView(View view) {
        container.removeView((View) view.getParent());
        setClearButton();

    }

    private void clearAllViews() {
        if (container.getChildCount() > 0) {
            Toast.makeText(this, "All views cleared", Toast.LENGTH_SHORT).show();
            container.removeAllViews();
            setClearButton();
        }
//        else {
//            Toast.makeText(this, "No views to clear", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_AddViews:
                showDialog();
                break;
            case R.id.tv_clearAllViews:
                clearAllViews();
                break;
            case R.id.tv_delete:
                deleteThisView(v);
                break;
            default:
                break;
        }
    }
}
