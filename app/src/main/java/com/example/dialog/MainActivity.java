package com.example.dialog;
// ייבוא המחלקות הדרושות
import android.app.DatePickerDialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // הגדרת המשתנים של המסך
    EditText etFirstName, etLastName, etCity;
    Button btnDate, btnGender;

    // משתנה לשמירת תאריך הלידה
    String birthDate = "";

    // משתנה לשמירת המין שנבחר
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // קישור בין המשתנים לבין הרכיבים ב-XML
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etCity = findViewById(R.id.etCity);

        btnDate = findViewById(R.id.btnDate);
        btnGender = findViewById(R.id.btnGender);

        // לחיצה על כפתור תאריך לידה
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        // לחיצה על כפתור בחירת מין
        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGenderDialog();
            }
        });
    }

    // פונקציה לפתיחת חלונית בחירת תאריך
    private void openDatePicker() {

        // קבלת התאריך הנוכחי
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // יצירת חלונית לבחירת תאריך
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,

                // פעולה שמתבצעת לאחר בחירת תאריך
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int selectedYear,
                                          int selectedMonth,
                                          int selectedDay) {

                        // שמירת התאריך שנבחר
                        birthDate = selectedDay + "/" +
                                (selectedMonth + 1) + "/" +
                                selectedYear;

                        // הצגת התאריך על הכפתור
                        btnDate.setText(birthDate);
                    }
                },

                year, month, day
        );

        // הצגת חלונית התאריך
        datePickerDialog.show();
    }

    // פונקציה לפתיחת Custom Dialog לבחירת מין
    private void openGenderDialog() {

        // יצירת חלונית דיאלוג
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);

        // טעינת העיצוב שיצרנו בקובץ XML
        LayoutInflater inflater = getLayoutInflater();

        View dialogView =
                inflater.inflate(R.layout.gender_dialog, null);

        // קישור רכיבי ה-RadioButton וה-RadioGroup
        RadioGroup radioGroup =
                dialogView.findViewById(R.id.radioGroupGender);

        RadioButton rbMale =
                dialogView.findViewById(R.id.rbMale);

        RadioButton rbFemale =
                dialogView.findViewById(R.id.rbFemale);

        // הצגת העיצוב בתוך הדיאלוג
        builder.setView(dialogView);

        // הוספת כפתור אישור
        builder.setPositiveButton("OK", (dialog, which) -> {

            // בדיקה איזו אפשרות נבחרה
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.rbMale) {

                gender = "Male";
                btnGender.setText("Male");

            } else if (selectedId == R.id.rbFemale) {

                gender = "Female";
                btnGender.setText("Female");

            } else {

                // הודעה אם לא נבחרה אפשרות
                Toast.makeText(MainActivity.this,
                        "Please choose a gender",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // הוספת כפתור ביטול
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        // יצירת הדיאלוג והצגתו
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
