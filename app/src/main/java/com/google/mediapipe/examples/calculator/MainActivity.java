package com.google.mediapipe.examples.calculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String errorMessage = "Error";
    private static final String defaultInputValue = "";

    private EditText editInput1, editInput2, editResult;
    private TextView textOperationSign;
    private Button buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonClear;

    private String currentOperation = "+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupClickListeners();
        setupTextChangeListeners();
        clearAllFields();
        setOperation("+");
    }

    private void initializeViews() {
        editInput1 = findViewById(R.id.editInput1);
        editInput2 = findViewById(R.id.editInput2);
        editResult = findViewById(R.id.editResult);
        textOperationSign = findViewById(R.id.textOperationSign);

        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonClear = findViewById(R.id.buttonClear);
    }

    private void setupClickListeners() {
        buttonPlus.setOnClickListener(v -> setOperation("+"));

        buttonMinus.setOnClickListener(v -> setOperation("-"));

        buttonMultiply.setOnClickListener(v -> setOperation("*"));

        buttonDivide.setOnClickListener(v -> setOperation("/"));

        buttonClear.setOnClickListener(v -> clearAllFields());
    }

    private void setupTextChangeListeners() {
        editInput1.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateResult();
            }
        });

        editInput2.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateResult();
            }
        });
    }

    private void setOperation(String operation) {
        currentOperation = operation;
        textOperationSign.setText(operation);
        calculateResult();
    }

    private void calculateResult() {
        String input1Str = editInput1.getText().toString();
        String input2Str = editInput2.getText().toString();

        if (TextUtils.isEmpty(input1Str) || TextUtils.isEmpty(input2Str)) {
            editResult.setText("");
            return;
        }

        try {
            double num1 = Double.parseDouble(input1Str);
            double num2 = Double.parseDouble(input2Str);
            double result = 0;

            switch (currentOperation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        editResult.setText(errorMessage);
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            String resultStr;
            if (result == (long) result) {
                resultStr = String.valueOf((long) result);
            } else {
                resultStr = String.valueOf(result);
            }

            editResult.setText(resultStr);

        } catch (Exception e) {
            editResult.setText(errorMessage);
        }
    }

    private void clearAllFields() {
        editInput1.setText(defaultInputValue);
        editInput2.setText(defaultInputValue);
        editResult.setText(defaultInputValue);
    }

    private abstract static class SimpleTextWatcher implements android.text.TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(android.text.Editable s) {}
    }
}