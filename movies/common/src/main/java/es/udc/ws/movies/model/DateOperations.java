package es.udc.ws.movies.model;

import java.util.Calendar;

public final class DateOperations {

    private DateOperations() {
    }

    public static Calendar getDate(
            String dayAsString, String monthAsString, String yearAsString) {

        if(dayAsString == null && monthAsString == null && yearAsString == null) {
            return null;
        }
        
        int day = dayAsString != null ? Integer.parseInt(dayAsString) : 
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH);;
        int month = monthAsString != null ? Integer.parseInt(monthAsString) : 
                Calendar.getInstance().get(Calendar.MONTH);
        int year = yearAsString != null ? Integer.parseInt(yearAsString) :
                Calendar.getInstance().get(Calendar.YEAR);;
        Calendar date = Calendar.getInstance();

        date.set(Calendar.DAY_OF_MONTH, day);
        date.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        date.set(Calendar.YEAR, year);

        /*
         * Recompute Calendar's fields.
         */
        date.getTime();

        /*
         * Return Calendar object.
         */
        return date;

    }

    public static boolean datesAreEqual(Calendar dateOne,
            Calendar dateTwo) {

        return dateTwo == null || 
                (dateOne.get(Calendar.DAY_OF_MONTH) == 
                    dateTwo.get(Calendar.DAY_OF_MONTH))
                && (dateOne.get(Calendar.MONTH) == 
                    dateTwo.get(Calendar.MONTH))
                && (dateOne.get(Calendar.YEAR) == 
                    dateTwo.get(Calendar.YEAR));

    }

}
