package main.java;

public class date {
    private final int day;
    private final int monthInt;
    private final String monthString;
    private final int year;


    /**
     * Constructor method that creates a new date object from 3 input integers
     * @param day
     * @param month
     * @param year
     */
    public date(int day, int month, int year) {
        this.day = day;
        this.monthInt = month;
        this.year = year;
        Month myMonth;
        myMonth.monthDigit = month;
    }

    /**
     * Constructor method that creates a new date object from 2 input integers, day and year, and the month as a string
     * @param day
     * @param month
     * @param year
     */
    public date(int day, String month, int year) {

    }

    /**
     * Constructor method that creates a date object from a string, in the format dd/mm/yyyy
     * @param date
     */
    public date(String date) {

    }

    
    private class Month {
        int monthInt;
        String monthText;

    /**
     * requires the integer to be between 1 and 12
     * @param month
     */
    public Month(int month) throws IllegalArgumentException {
        if((month < 1) || (month > 12) )
            throw new IllegalArgumentException("Invalid Month Input!");
        this.monthInt = month;
        switch(month){
            case 1: this.monthText = "January";
                break;
            case 2: this.monthText = "February";
                break;
            case 3: this.monthText = "March";
                break;
            case 4: this.monthText = "April";
                break;
            case 5: this.monthText = "May";
                break;
            case 6: this.monthText = "June";
                break;
            case 7: this.monthText = "July";
                break;
            case 8: this.monthText = "August";
                break;
            case 9: this.monthText = "September";
                break;
            case 10: this.monthText = "October";
                break;
            case 11: this.monthText = "November";
                break;
            case 12: this.monthText = "December";
                break;
            default: this.monthText = "INVALID MONTH!";
                break;
        }
    }
}

}

