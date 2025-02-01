package com.example.exammidterm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGrade, btnVat, btnSlice, btnAbout;
    private ImageView btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrade = findViewById(R.id.btnGrade);
        btnVat = findViewById(R.id.btnVat);
        btnSlice = findViewById(R.id.btnSlice);
        btnAbout = findViewById(R.id.btnAbout);
        btnExit = findViewById(R.id.btnExit);

        btnGrade.setOnClickListener(this);
        btnVat.setOnClickListener(this);
        btnSlice.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGrade) {
            startActivity(new Intent(this, GradeActivity.class));
        } else if (view.getId() == R.id.btnVat) {
            startActivity(new Intent(this, VatActivity.class));
        } else if (view.getId() == R.id.btnSlice) {
            startActivity(new Intent(this, SliceActivity.class));
        } else if (view.getId() == R.id.btnAbout) {
            LayoutInflater inflater = getLayoutInflater();
            View viewToast = inflater.inflate(R.layout.custom_layout, (ViewGroup) findViewById(R.id.linearLayout));
            Toast toast = Toast.makeText(this, "ViewToast", Toast.LENGTH_SHORT);
            toast.setView(viewToast);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (view.getId() == R.id.btnExit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ออก");
            builder.setMessage("คุณต้องการออกจากโปรแกรมหรือไม่?");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create();
            builder.show();
        }
    }
}