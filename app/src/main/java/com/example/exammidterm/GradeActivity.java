package com.example.exammidterm;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class GradeActivity extends AppCompatActivity {
    private int [] text = {R.id.house, R.id.grade2, R.id.grade3, R.id.grade4, R.id.grade5};
    private TextInputEditText [] grades = new TextInputEditText[text.length];
    private TextInputEditText output;
    private TextInputLayout outputLayout;
    private Button btnCal, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grade);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for (int n = 0; n < grades.length; n++) {
            grades[n] = findViewById(text[n]);
        }
        output = findViewById(R.id.output);
        outputLayout = findViewById(R.id.outputLayout);
        btnCal = findViewById(R.id.btnCal);
        btnBack = findViewById(R.id.btnBack);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calGrade();
            }
        });

        btnBack.setOnClickListener(view -> finish());
    }

    private void calGrade() {
        if (checkError(grades)) {
            String text = "";
            String [] values = new String[grades.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = grades[i].getText().toString().toUpperCase();
            }

            double [] GPA = checkGPA(values);
            double [] listGPA = checkListGPA(GPA);
            double totalGPA = checkTotalGPA(listGPA);
            double point = 3.0 * 5;
            double GPAX = checkGPAX(totalGPA, point);

            text += "เกรดทั้ง 5 วิชา : " + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4];
            text += "\nหน่วยกิตของทุกวิชา : 3.0";
            text += "\nGPA : " + GPA[0] + ", " + GPA[1] + ", " + GPA[2] + ", " + GPA[3] + ", " + GPA[4];
            text += "\nGPA แต่ละวิชา : " + listGPA[0] + ", " + listGPA[1] + ", " + listGPA[2] + ", " + listGPA[3] + ", " + listGPA[4];
            text += "\nผลรวม GPA ทั้งหมด : " + totalGPA;
            text += "\nผลรวมหน่วยกิตทั้งหมด : " + point;
            text += "\nGPAX : " + GPAX;
            output.setText(text);

            changeColorOutput(GPAX);
        }
    }

    private void changeColorOutput(double gpax) {
        if (gpax >= 3.5) {
            outputLayout.setBackgroundColor(Color.rgb(135, 206, 250));
            output.setBackgroundColor(Color.rgb(135, 206, 250));
        } else if (gpax >= 3.0) {
            outputLayout.setBackgroundColor(Color.rgb(64,224,208));
            output.setBackgroundColor(Color.rgb(64,224,208));
        } else if (gpax >= 2.5) {
            outputLayout.setBackgroundColor(Color.rgb(50,205,50));
            output.setBackgroundColor(Color.rgb(50,205,50));
        } else if (gpax >= 2.0) {
            outputLayout.setBackgroundColor(Color.rgb(173, 255, 47));
            output.setBackgroundColor(Color.rgb(173, 255, 47));
        } else if (gpax >= 1.5) {
            outputLayout.setBackgroundColor(Color.rgb(255, 255, 0));
            output.setBackgroundColor(Color.rgb(255, 255, 0));
        } else if (gpax >= 1.0) {
            outputLayout.setBackgroundColor(Color.rgb(255,165,0));
            output.setBackgroundColor(Color.rgb(255,165,0));
        } else {
            outputLayout.setBackgroundColor(Color.rgb(255, 0, 0));
            output.setBackgroundColor(Color.rgb(255, 0, 0));
        }
    }

    private double checkGPAX(double totalGPA, double point) {
        return totalGPA / point;
    }

    private double checkTotalGPA(double[] listGPA) {
        return listGPA[0] + listGPA[1] + listGPA[2] + listGPA[3] + listGPA[4];
    }

    private double[] checkListGPA(double[] gpa) {
        double [] listGPA = new double[gpa.length];
        for (int i = 0; i < listGPA.length; i++) {
            listGPA[i] = gpa[i] * 3.0;
        }
        return listGPA;
    }

    private double[] checkGPA(String[] values) {
        double [] GPA = new double[values.length];
        for (int i = 0; i < GPA.length; i++) {
            if (values[i].toUpperCase().equals("A")) GPA[i] = 4.0;
            else if (values[i].toUpperCase().equals("B+")) GPA[i] = 3.5;
            else if (values[i].toUpperCase().equals("B")) GPA[i] = 3.0;
            else if (values[i].toUpperCase().equals("C+")) GPA[i] = 2.5;
            else if (values[i].toUpperCase().equals("C")) GPA[i] = 2.0;
            else if (values[i].toUpperCase().equals("D+")) GPA[i] = 1.5;
            else if (values[i].toUpperCase().equals("D")) GPA[i] = 1.0;
            else if (values[i].toUpperCase().equals("F")) GPA[i] = 0.0;
        }
        return GPA;
    }

    private boolean checkError(TextInputEditText[] grade) {
        String [] checkText = new String[grade.length];
        for (int i = 0; i < checkText.length; i++) {
            checkText[i] = grade[i].getText().toString();
        }

        Boolean CheckText = false;
        for (int i = 0; i < checkText.length; i++) {
            if (TextUtils.isEmpty(checkText[i])) CheckText = true;
            else {
                switch (checkText[i].toUpperCase()) {
                    case "A":
                    case "B+":
                    case "B":
                    case "C+":
                    case "C":
                    case "D+":
                    case "D":
                    case "F":
                        break;
                    default:
                        CheckText = true;
                        break;
                }
            }

            if (CheckText) {
                grades[i].setError("กรุณากรอกข้อมูล(A,B+,B,C+,C,D+,D,F)");
                return false;
            }
        }

        return true;
    }
}