package in.aplicatia.homeocure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestCalander extends AppCompatActivity implements OnSelectDateListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_calander);




        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -5);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 3);


        Button openOneDayPickerDialog = (Button) findViewById(R.id.datepicker);
        openOneDayPickerDialog.setOnClickListener(v -> {
            DatePickerBuilder oneDayBuilder = new DatePickerBuilder(this, this)
                    .pickerType(CalendarView.ONE_DAY_PICKER)
                    .date(max)
                    .headerColor(R.color.colorPrimaryDark)
                    .headerLabelColor(R.color.currentMonthDayColor)
                    .selectionColor(R.color.daysLabelColor)
                    .todayLabelColor(R.color.colorAccent)
                    .dialogButtonsColor(android.R.color.holo_green_dark)
                    .disabledDaysLabelsColor(android.R.color.holo_purple)
                    .previousButtonSrc(R.drawable.ic_chevron_left_black_24dp)
                    .forwardButtonSrc(R.drawable.ic_chevron_right_black_24dp)
                    .minimumDate(min)
                    .maximumDate(max)
                    .disabledDays(getDisabledDays());

            DatePicker oneDayPicker = oneDayBuilder.build();
            oneDayPicker.show();
        });



    }


    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }




    @Override
    public void onSelect(List<Calendar> calendar) {

    }
}
