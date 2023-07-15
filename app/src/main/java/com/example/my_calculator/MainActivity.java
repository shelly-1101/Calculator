package com.example.my_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.faendir.rhino_android.RhinoAndroidHelper;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result_view, solution_view;
    AppCompatButton clear_button, ac_button, equal_button, button_dot;
    AppCompatButton button_add, button_mul, button_div, button_sub, button_open, button_close;
    AppCompatButton button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        result_view = findViewById(R.id.result_view);
        solution_view = findViewById(R.id.solution_view);

        assignID(this.button_0, R.id.zero_btn);
        assignID(this.button_1, R.id.one_btn);
        assignID(this.button_2, R.id.two_btn);
        assignID(this.button_3, R.id.three_btn);
        assignID(this.button_4, R.id.four_btn);
        assignID(this.button_5, R.id.five_btn);
        assignID(this.button_6, R.id.six_btn);
        assignID(this.button_7, R.id.seven_btn);
        assignID(this.button_8, R.id.eight_btn);
        assignID(this.button_9, R.id.nine_btn);


        assignID(this.clear_button, R.id.clear_btn);
        assignID(this.ac_button, R.id.ac_btn);
        assignID(this.button_open, R.id.open_bracket);
        assignID(this.button_close, R.id.close_bracket);
        assignID(this.button_mul, R.id.mul_btn);
        assignID(this.button_div, R.id.div_btn);
        assignID(this.button_add, R.id.add_btn);
        assignID(this.button_sub, R.id.sub_btn);
        assignID(this.button_dot, R.id.dot_btn);
        assignID(this.equal_button, R.id.equal_btn);
    }

    public void assignID(AppCompatButton button, int id) {


        button = findViewById(id);
        button.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        AppCompatButton button = (AppCompatButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution_view.getText().toString();

        if (buttonText.equals("AC")) {
            solution_view.setText("");
            result_view.setText("0");
            return;
        }
        if(button.getId()==R.id.clear_btn)
        {
            if(dataToCalculate.length()>=1)
            {
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
            }
        }
        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);

            if (!finalResult.equals("Error") && !dataToCalculate.equals("")) {
                result_view.setText(finalResult);
            } else {
                result_view.setText("error");
                Toast.makeText(this, "Write proper expression", Toast.LENGTH_SHORT).show();
            }
            return;

        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        solution_view.setText(dataToCalculate);

    }

    String getResult(String data) {
        try {

            Context context = new RhinoAndroidHelper().enterContext();
            ((org.mozilla.javascript.Context) context).setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;


        } catch (Exception e) {

            return "Error";


        }

    }


}
