package com.sourceit.task2.ui;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sourceit.task2.R;
import com.sourceit.task2.model.Data;
import com.sourceit.task2.utils.L;
import com.sourceit.task2.utils.Toasts;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT = 10;
    public static final int MAX = 50;

    private TextView countText;
    private Button countButton;
    private Button addFields;
    private LinearLayout ll_root;
    private EditText numberFields;
    private ScrollView scrollView;

    private String KEY = "data";

    private EditText tempText;
    private int countChildWithText;
    private int countFields;
    private Map<Integer, String> childsWithText = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        if (savedInstanceState != null) {
            L.d("recovery...");
            Data dataRetained = (Data) savedInstanceState.get(KEY);
            countChildWithText = dataRetained.countWithText;
            childsWithText = dataRetained.childsWithText;
            countFields = dataRetained.countFields;

            addFields(countFields);

            countText.setText(String.valueOf(countChildWithText));

            for (Map.Entry<Integer, String> tempChild : childsWithText.entrySet()) {
                EditText tempField = (EditText) ll_root.getChildAt(tempChild.getKey());
                tempField.setText(tempChild.getValue());
            }
        } else addFields(DEFAULT);

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countChildWithText = 0;
                for (int i = 0; i < ll_root.getChildCount(); i++) {
                    tempText = (EditText) ll_root.getChildAt(i);
                    if (tempText.getText().toString().length() > 0) {

                        countChildWithText++;
                        childsWithText.put(i, tempText.getText().toString());
                        L.d("countText++" + countChildWithText);
                    }
                }
                countText.setText(String.valueOf(countChildWithText));
            }
        });

        addFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addFields(Integer.parseInt(numberFields.getText().toString()));
                } catch (NumberFormatException e) {
                    Toasts.show("Enter number fields");
                }
            }
        });

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @TargetApi(11)
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                int rootHeight = ll_root.getHeight() - scrollView.getBottom();
                L.d("rootHeight: " + rootHeight);

                int currentPosition = scrollView.getScrollY();
                L.d("cur.pos." + currentPosition);

                if (currentPosition == rootHeight) {

                    addFields(DEFAULT);


                }
            }
        });
    }

    private void init() {
        scrollView = (ScrollView) findViewById(R.id.scrollViewElements);
        ll_root = (LinearLayout) findViewById(R.id.root);
        countButton = (Button) findViewById(R.id.buttonCount);
        addFields = (Button) findViewById(R.id.buttonAdd);
        numberFields = (EditText) findViewById(R.id.editTextNum);
        countText = (TextView) findViewById(R.id.textViewCount);
    }

    private void addFields(int number) {
        if (ll_root.getChildCount() != MAX) {
            for (int i = 0; i < number; i++) {
                ll_root.addView(new EditText(this));
            }
            Toasts.show("add " + number + " fields");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Data data = new Data();
        data.countFields = ll_root.getChildCount();
        data.countWithText = countChildWithText;
        data.childsWithText = childsWithText;
        outState.putSerializable(KEY, data);
        L.d("create serializable..");
    }
}
