package com.example.exammidterm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class VatActivity extends AppCompatActivity {
    private TextInputEditText price, house, car, output;
    private RadioButton famOne, famTwo, famTree;
    private Button btnCal, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        price = findViewById(R.id.price);
        house = findViewById(R.id.house);
        car = findViewById(R.id.car);
        famOne = findViewById(R.id.famOne);
        famTwo = findViewById(R.id.famTwo);
        famTree = findViewById(R.id.famThree);
        output = findViewById(R.id.output);
        btnCal = findViewById(R.id.btnCal);
        btnBack = findViewById(R.id.btnBack);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calVat();
            }
        });

        btnBack.setOnClickListener(view -> finish());
    }

    private void calVat() {
        if (checkError(price)) {
            String text = "";
            final double deductionPerson = 60000;
            final double deductionFam = 30000;
            final double deductionHouse = 100000;
            final double deductionCar = 50000;

            double Income = Double.parseDouble(price.getText().toString());
            double ValueFam = famOne.isChecked() ? 1.0 : famTwo.isChecked() ? 2.0 : famTree.isChecked() ? 3.0 : 1.0;
            double ValueHouse = TextUtils.isEmpty(house.getText().toString()) ? 0.0 : Double.parseDouble(house.getText().toString());
            double ValueCar = TextUtils.isEmpty(car.getText().toString()) ? 0.0 : Double.parseDouble(car.getText().toString());

            double Family = checkDeductionFamily(ValueFam, deductionFam);
            double HouseLoan = checkDeductionHouseLoan(ValueHouse, deductionHouse);
            double CarLoan = checkDeductionCarLoan(ValueCar, deductionCar);
            double Total = checkDeductionTotal(Income, deductionPerson, Family, HouseLoan, CarLoan);
            double Tax = checkDeductionVat(Total);

            DecimalFormat df = new DecimalFormat("#,###.##");
            text += "รายได้ทั้งหมด : " + df.format(Income);
            text += "\nจำนวนสมาชิกในครอบครัว : " + df.format(ValueFam);
            text += "\nรายจ่ายค่าบ้าน : " + df.format(ValueHouse);
            text += "\nรายจ่ายค่ารถ : " + df.format(ValueCar);
            text += "\nค่าลดหย่อนส่วนตัว : " + df.format(deductionPerson);
            text += "\nค่าลดหย่อนสมาชิกในครอบครัว : " + df.format(Family);
            text += "\nค่าลดหย่อนดอกเบี้ยบ้าน : " + df.format(HouseLoan);
            text += "\nค่าลดหย่อนค่าผ่อนรถ : " + df.format(CarLoan);
            text += "\nรายได้สุทธิหลังหักลดหย่อน : " + df.format(Total);
            text += "\nภาษีที่ต้องจ่าย : " + df.format(Tax);

            output.setText(text);
        }
    }

    private double checkDeductionVat(double total) {
        double [] fixValue = {0, 150000, 300000, 500000, 750000, 1000000, 2000000, 5000000};
        double [] perVat = {0, 5, 10, 15, 20, 25, 30, 35};
        double tax = 0.0;
        for (int i = 0; i < perVat.length - 1; i++) {
            if (total > fixValue[i]) {
                if (!(total <= fixValue[i+1])) {
                    tax += ((fixValue[i+1] - fixValue[i]) * perVat[i]) / 100;
                } else {
                    tax += ((total - fixValue[i]) * perVat[i]) / 100;
                }
            }
        }
        if (total > fixValue[fixValue.length - 1]) {
            tax += ((total - fixValue[fixValue.length - 1]) * perVat[perVat.length - 1]) / 100;
        }
        return tax;
    }

    private double checkDeductionTotal(double income, double deductionPerson, double family, double houseLoan, double carLoan) {
        double total = income - (deductionPerson + family + houseLoan + carLoan);
        return Math.max(total, 0);
    }

    private double checkDeductionCarLoan(double valueCar, double deductionCar) {
        return Math.min(valueCar/10, deductionCar);
    }

    private double checkDeductionHouseLoan(double valueHouse, double deductionHouse) {
        return Math.min(valueHouse, deductionHouse);
    }

    private double checkDeductionFamily(double valueFam, double deductionFam) {
        double deFam = 0.0;
        if (valueFam == 1.0) deFam = 1 * deductionFam;
        if (valueFam == 2.0) deFam = 2 * deductionFam;
        if (valueFam == 3.0) deFam = 3 * deductionFam;
        return deFam;
    }

    private boolean checkError(TextInputEditText Price) {
        String checkPrice = Price.getText().toString();
        if (TextUtils.isEmpty(checkPrice)) {
            price.setError("กรุณาใส่ตัวเลข");
            return false;
        }
        return true;
    }
}