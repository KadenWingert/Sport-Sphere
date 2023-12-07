package com.example.sportssphere;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class EventOverlayView extends View {
    private final Set<String> eventDates = new HashSet<>();
    private final Paint paint = new Paint();

    public EventOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
    }

    public void setEventDates(Set<String> dates) {
        eventDates.clear();
        eventDates.addAll(dates);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cellWidth = getWidth() / 7f;
        float cellHeight = getHeight() / 6f; // Adjust according to your calendar's row count

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        for (String dateString : eventDates) {
            try {
                Date date = format.parse(dateString);
                calendar.setTime(date);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

                float x = (dayOfWeek - 1) * cellWidth;
                float y = (weekOfMonth - 1) * cellHeight;
                canvas.drawCircle(x + cellWidth / 2, y + cellHeight / 2, 10, paint);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}