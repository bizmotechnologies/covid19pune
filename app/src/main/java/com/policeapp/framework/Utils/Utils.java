package com.policeapp.framework.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.policeapp.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by Bizmo Technologies .
 */
public class Utils {


    /**
     * @param editText
     * @return
     */
    public static boolean isEmptyOrNull(EditText editText) {
        boolean returnValue = false;
        if (editText != null) {
            if (editText.getText() == null || editText.getText().length() == 0 && editText.getText().toString().equalsIgnoreCase("")) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public static boolean isValidEmailAddress(String email) {

        if(!TextUtils.isEmpty(email) && email.contains("@"))
        {
            return true;
        }
        return false;
    }




    public static void showSnackBarNotificationError(String message, View rootView) {

        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(Color.parseColor("#F44336"));
        TextView tv = (TextView) layout.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.setText(message);
        snackbar.setDuration(4000);
        snackbar.show();
    }

    public static void showSnackBarNotificationGreen(String message, View rootView) {

        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(Color.parseColor("#FF4FAC2D"));
        TextView tv = (TextView) layout.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.setText(message);
        snackbar.setDuration(4000);
        snackbar.show();
    }

    public static void showSnackBarNotificationOrange(String message, View rootView) {

        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(Color.parseColor("#F49B0A"));
        TextView tv = (TextView) layout.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.setText(message);
        snackbar.setDuration(4000);
        snackbar.show();
    }


    /**
     * This method calculates the distance between the two locations in miles
     * @param lat1 lat of first location
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }



    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method will return the date in standard format for the application
     * @param date date that has to be converted
     * @return standard from of the date in MMM dd
     */
    public static String getNormalisedDate(String date)
    {
        SimpleDateFormat form = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        Date newDate = null;
        try
        {
            newDate = form.parse(date);
            SimpleDateFormat postFormater = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
            return postFormater.format(newDate);
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return "";
    }

    public static String currencyFormat(Float value){
        return new DecimalFormat("₹ ##,##,##,##0.00").format(value);
    }
    public static String getDay(String date)
    {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try
        {
            newDate = form.parse(date);
            SimpleDateFormat postFormater = new SimpleDateFormat("dd", Locale.ENGLISH);
            return postFormater.format(newDate);
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return "";
    }

    public static String getFullDay(String date)
    {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try
        {
            newDate = form.parse(date);
            SimpleDateFormat postFormater = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
            return postFormater.format(newDate);
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return "";
    }

    public static boolean isThisDateValid(String dateToValidate){
        String dateFormat = "yyyy-MM-dd";
        if(dateToValidate == null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getMonth(String date)
    {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try
        {
            newDate = form.parse(date);
            SimpleDateFormat postFormater = new SimpleDateFormat("MMM", Locale.ENGLISH);
            return postFormater.format(newDate);
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return "";
    }


    public static double round(double value) {
        int places =2;
        if (places < 0) throw new IllegalArgumentException();
        if(value<0) value = 0;
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double getRoundedDouble(double doubleValue)
    {
        double convertedDouble =0.0;
        try{
            convertedDouble = round(doubleValue);
        }catch (Exception e)
        {

        }
        return convertedDouble;
    }

    /**
     * This method will naviate the user to the destination url into the
     * new browser instance
     * @param destination_url url to load in the destination browser
     */
    public static void browserNavigateTo(String destination_url, Context context) {
//        if(destination_url!= null && destination_url.length()>0) {
//            Intent intent = new Intent(context, InAppBrowser.class);
//            intent.putExtra(Constants.PAYLOAD, destination_url);
//            context.startActivity(intent);
//        }
    }






    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String getHtmlFromAssets(Context ctx, String fileName) {
        StringBuilder buf = new StringBuilder();
        InputStream json = null;
        try {
            json = ctx.getAssets().open(fileName+".html");

            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
            return buf.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String loadJSONFromAsset(Context ctx) {
        String json = null;
        try {
            InputStream is = ctx.getAssets().open(ctx.getString(R.string.lower_menu));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getExp(String year, String month){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(Integer.parseInt(year), Integer.parseInt(month),1);

        int yearsInBetween = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int monthsDiff = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
        long ageInMonths = yearsInBetween*12 + monthsDiff;
        long age = yearsInBetween;

        if((++monthsDiff)<0)
        {
            yearsInBetween--;
            monthsDiff = 12+monthsDiff;
        }

        String ageS = "Exp - "+yearsInBetween+" yrs, "+ monthsDiff+" mnth";

        return ageS;
    }

    public static String getTimeDetails(String start, String end)
    {

        Calendar Startcal = new GregorianCalendar();
        Startcal.set(Calendar.HOUR_OF_DAY, 0);
        Startcal.set(Calendar.MINUTE, 0);
        Startcal.set(Calendar.SECOND, 0);
        Startcal.set(Calendar.MILLISECOND, 0);

        // Next we add the number of milliseconds since midnight
        Startcal.add(Calendar.SECOND, Integer.parseInt(start));



        Calendar endCal = new GregorianCalendar();
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        // Next we add the number of milliseconds since midnight
        endCal.add(Calendar.SECOND, Integer.parseInt(end));


        DateFormat startdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String strtDate = startdf.format(Startcal.getTime());

        DateFormat enddf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String eDate = enddf.format(endCal.getTime());


        return strtDate +" - "+ eDate;
    }

    public static String convertToTime(String inMillisFromMidNight)
    {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Next we add the number of milliseconds since midnight
        calendar.add(Calendar.SECOND, (int) (Long.parseLong(inMillisFromMidNight)));

        DateFormat startdf = new SimpleDateFormat("hh:mm a", Locale.US);
        String strtDate = startdf.format(calendar.getTime());



        return strtDate ;
    }

    public static String convertToRemainingTime(String inMillisFromMidNight)
    {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Next we add the number of milliseconds since midnight
        calendar.add(Calendar.MILLISECOND, (int) (Long.parseLong(inMillisFromMidNight)));

        String strtDate = calendar.get(Calendar.HOUR_OF_DAY)+" Hrs "+calendar.get(Calendar.MINUTE)+" Mins More";

        return strtDate ;
    }

    public static String convertToTineFromUTC(String millis)
    {
        Calendar Startcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        Startcal.setTimeInMillis(Long.parseLong(millis));

        DateFormat startdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String strtDate = startdf.format(Startcal.getTime());

        return strtDate;
    }

    public static String convertToDateFromUTC(String millis)
    {
        Calendar Startcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        Startcal.setTimeInMillis(Long.parseLong(millis));
        Startcal.setTimeZone(TimeZone.getDefault());

        DateFormat startdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String strtDate = startdf.format(Startcal.getTime());

        return strtDate;
    }
    public static String convertToDateFromUTCFormated(String millis)
    {
        Calendar Startcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        Startcal.setTimeInMillis(Long.parseLong(millis));
        Startcal.setTimeZone(TimeZone.getDefault());

        DateFormat startdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String strtDate = startdf.format(Startcal.getTime());

        return strtDate;
    }

    public static String convertToShorDateFromUTC(String millis)
    {
        Calendar Startcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        Startcal.setTimeInMillis(Long.parseLong(millis));
        Startcal.setTimeZone(TimeZone.getDefault());

        DateFormat startdf = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
        String strtDate = startdf.format(Startcal.getTime());

        return strtDate;
    }

//    public static String convertToAgeFromUTC(String millis)
//    {
//        Calendar Startcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
//        Startcal.setTimeInMillis(Long.parseLong(millis));
//        Startcal.setTimeZone(TimeZone.getDefault());
//
//        return getAge(Startcal.get(Calendar.YEAR),Startcal.get(Calendar.MONTH),Startcal.get(Calendar.DAY_OF_MONTH));
//    }

    public static String getAgefromUtc(String millis)
    {
        DateTime today = new DateTime(DateTimeZone.getDefault());

        Period period = new Period(Long.parseLong(millis),today.getMillis(), PeriodType.yearMonthDay());

        Duration duration = new Duration(Long.parseLong(millis),today.getMillis());

        if(period.getYears()==0 && period.getMonths()!=0) {
            return  period.getMonths() + (period.getMonths()>1?" Months ":" Month ") + period.getDays() + (period.getDays()>1?" Days":" Day");
        }else if (period.getYears()==0 && period.getMonths()==0)
        {
            return  period.getDays() + (period.getDays()>1?" Days":" Day");
        }else
        {
            if(period.getMonths()>0)
                return  period.getYears() + (period.getYears()>1?" Years ":" Year ")+ period.getMonths()+(period.getMonths()>1?" Months":" Month");
            else
                return  period.getYears() + (period.getYears()>1?" Years ":" Year ");

        }
    }

    public static String getAgeYears(String millis)
    {
        DateTime today = new DateTime(DateTimeZone.getDefault());
        Period period = new Period(Long.parseLong(millis),today.getMillis(), PeriodType.yearMonthDay());
        return period.getYears()+"";
    }

    private static String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = "";
        if(ageInt !=0){
            if(age>1)
                ageS = ageInt.toString() +" Years";
            else
                ageS = ageInt.toString() +" Year";
        }
        else {
            int months = (today.get(Calendar.MONTH) - dob.get(Calendar.MONTH));
            if(months>1) {
                ageS = "" + months + " Months";
            }else if(month<0)
            {
                ageS = "0 Month";
            }
            else {
                ageS = "" + months + " Month";
            }
        }
        return ageS;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void setImageDynamically(Context ctx, String name, ImageView image)
    {
        Resources res = ctx.getResources();
        int resID;
        if(res != null) {
            resID = res.getIdentifier(name, "drawable", ctx.getPackageName());
            if (resID == 0)
                resID = res.getIdentifier("no_image", "drawable", ctx.getPackageName());
            Drawable drawable = res.getDrawable(resID);
            image.setImageDrawable(drawable);
        }
    }
    public static void setImageDynamically(Context ctx, String name, ImageView image, String defaultName)
    {
        if(ctx!= null) {
            Resources res = ctx.getResources();
            int resID;
            if (res != null) {
                resID = res.getIdentifier(name, "drawable", ctx.getPackageName());
                if (resID == 0)
                    resID = res.getIdentifier(defaultName, "drawable", ctx.getPackageName());
                Drawable drawable = res.getDrawable(resID);
                image.setImageDrawable(drawable);
            }
        }
    }

    public static float getVersionName(Context ctx)
    {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return  Float.valueOf(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1.0f;
    }

    public static void setOPDStatus(Context ctx, String status, ImageView image)
    {
        switch (status)
        {
            case "DID_NOT_VISIT":
            case"AUTO_PAUSE_WHEN_OPD_NOT_STARTED":
            case "YET_TO_OPEN":
            case "NOT_STARTED":
                setImageDynamically(ctx,"status_yet_to_open",image,"status_active");
                break;
            case "AVAILABLE":
            case "ACTIVE":
            case "PATIENT_CHECK_IN":
            case "CLINIC_CHECK_IN":
            case "SKIPPED":
            case "AUTO_PAUSED":
            case "START_OPD":
            case "STARTED":
            case "UNDO_CHECK_IN":
            case "GO":
            case "RESTART_ONLINE_BOOKING_FOR_SLOT":
                setImageDynamically(ctx,"status_active",image,"status_active");
                break;
            case "ON_LEAVE":
                setImageDynamically(ctx,"status_on_leave",image,"status_active");
                break;
            case "PAUSED":
            case "PAUSE_OPD":
            case "FILLING_FAST":
                setImageDynamically(ctx,"status_fast",image,"status_active");
                break;
            case "CLOSED":
            case "CANCELLED":
            case"STOPPED":
            case "STOP_ONLINE_BOOKING_FOR_SLOT":
                setImageDynamically(ctx,"status_full",image,"status_active");
                break;
            case "COMPLETED":
                setImageDynamically(ctx,"done_green",image,"status_active");
                break;
            case "CANCELLED_BY_DOCTOR":
            case "CANCELLED_BY_PATIENT":
                setImageDynamically(ctx,"cancelled_icon_red",image,"status_active");
                break;
//            default:
//                setImageDynamically(ctx,"status_active",image,"status_active");
//                    break;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getFormattedDate(String date){
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat server = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
        try {
            return server.format(localFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String changeDateFormat(String currentFormat,String newFormat,String date){
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(currentFormat, Locale.US);
        SimpleDateFormat nreDateFormat = new SimpleDateFormat(newFormat, Locale.US);
        try {
            return nreDateFormat.format(currentDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static Date getDateFormat(String currentFormat, String newFormat, String date){
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(currentFormat, Locale.US);
        SimpleDateFormat nreDateFormat = new SimpleDateFormat(newFormat, Locale.US);
        try {
            return nreDateFormat.parse(currentDateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
