package dk.easj.anbo.datepickerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "dating";
    private final Calendar meetingStart = Calendar.getInstance();
    private Button dateButton;
    private Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateButton = findViewById(R.id.mainDateButton);
        timeButton = findViewById(R.id.mainTimeButton);
    }

    public void datePickButtonClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DATE);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                meetingStart.set(Calendar.YEAR, year);
                meetingStart.set(Calendar.MONTH, month);
                meetingStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateFormat dateFormatter = DateFormat.getDateInstance();
                String dateString = dateFormatter.format(meetingStart.getTimeInMillis());
                dateButton.setText(dateString);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                this, dateSetListener, currentYear, currentMonth, currentDayOfMonth);
        dialog.show();
    }

    public void timePickButtonClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int currentHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                meetingStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
                meetingStart.set(Calendar.MINUTE, minute);
                DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
                String timeString = timeFormatter.format(meetingStart.getTimeInMillis());
                timeButton.setText(timeString);
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(this, timeSetListener, currentHourOfDay,
                currentMinute, true);
        dialog.show();
    }

    public void showTimes(View view) {
        TextView mainUnixTimeTextView = findViewById(R.id.mainUnixTimeTextView);
        int timeInSeconds = convertCalendarToTimeInSeconds(meetingStart);
        Log.d(LOG_TAG, "Time in seconds: " + timeInSeconds);
        mainUnixTimeTextView.setText("Unix time in seconds: " + timeInSeconds);

        TextView mainTimeTextView = findViewById(R.id.mainTimeTextView);
        Calendar cal = convertTimeInSecondsToCalendar(timeInSeconds);
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        Date date = cal.getTime();
        String dateTimeString = dateFormatter.format(date);
        mainTimeTextView.setText(dateTimeString);
    }

    public static Calendar convertTimeInSecondsToCalendar(int timeInSeconds) {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = timeInSeconds * 1000L; // L means long
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    public static Date convertTimeInSecondsToDate(int timeInSeconds) {
        long timeInMillis = timeInSeconds * 1000L; // L means long
        Date date = new Date(timeInMillis);
        return date;
    }

    public static int convertCalendarToTimeInSeconds(Calendar calendar) {
        long timeInMillis = calendar.getTimeInMillis();
        int timeInSeconds = (int) (timeInMillis / 1000);
        return timeInSeconds;
    }
}

