package main.java;

public class Date {
    public final int day;
    public final int monthInt;
    public final String monthText;
    public final int year;


    /**
     * Constructor method that creates a new date object from 3 input integers
     * @param day
     * @param month
     * @param year
     */
    public Date(int day, int month, int year) throws IllegalArgumentException {
        this.day = day;
        this.year = year;
        Month myMonth = new Month(month);
        this.monthInt = myMonth.monthInt;
        this.monthText = myMonth.monthText;

        if(!checkArguments())
            throw new IllegalArgumentException("Invalid Argument(s)!");


    }

    /**
     * Constructor method that creates a new date object from 2 input integers, day and year, and the month as a string
     * @param day
     * @param month
     * @param year
     */
    public Date(int day, String month, int year) throws IllegalArgumentException {
        this.day = day;
        this.year = year;
        Month myMonth = new Month(month);
        this.monthInt = myMonth.monthInt;
        this.monthText = myMonth.monthText;

        if(!checkArguments())
            throw new IllegalArgumentException("Invalid Argument(s)!");
    }

    /**
     * Constructor method that creates a date object from a string, in the format dd/mm/yyyy
     * @param date
     */
    public Date(String date) throws IllegalArgumentException {
        StringBuffer myBuffer = new StringBuffer(date);
        StringBuffer day = new StringBuffer("");
        StringBuffer month = new StringBuffer("");
        StringBuffer year = new StringBuffer("");

        int count = 0;
        while(myBuffer.charAt(count) != '/') {
            day.append(myBuffer.charAt(count));
            count++;
        }
        count++;
        while(myBuffer.charAt(count) != '/') {
            month.append(myBuffer.charAt(count));
            count++;
        }
        count++;
        while((count < myBuffer.length()) && (myBuffer.charAt(count) != ' ')) {
            year.append(myBuffer.charAt(count));
            count++;
        }

        this.day = Integer.parseInt(day.toString());
        this.year = Integer.parseInt(year.toString());

        Month myMonth = new Month(Integer.parseInt(month.toString()));
        this.monthInt = myMonth.monthInt;
        this.monthText = myMonth.monthText;

        if(!checkArguments())
            throw new IllegalArgumentException("Invalid Argument(s)!");
    }

    /**
     * Method to check that all the arguments are valid
     * @return true if the arguments are valid, false is otherwise
     */
    boolean checkArguments() {
        boolean success = true;
        switch(this.monthInt) {
            case 1: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 3: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 5: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 7: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 8: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 10: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 12: if((this.day < 1) || (this.day > 31) )
                success = false;
                break;
            case 4: if((this.day < 1) || (this.day > 30) )
                success = false;
                break;
            case 6: if((this.day < 1) || (this.day > 30) )
                success = false;
                break;
            case 9: if((this.day < 1) || (this.day > 30) )
                success = false;
                break;
            case 11: if((this.day < 1) || (this.day > 30) )
                success = false;
                break;
            case 2: if((this.monthInt % 4) == 0) {
                if ((this.day < 1) || (this.day > 29))
                    success = false;
                }
                else if((this.monthInt % 4) == 0) {
                    if((this.day < 1) || (this.day > 28))
                        success = false;
                }
                break;
        }
        return success;
    }

    /**
     * Method compares this date to another date and returns true if this date is older than the input date
     * @param otherDate
     * @return boolean indicating whether this date is older than the input date
     */
    public boolean isOlderThan(Date otherDate) {
        boolean isOlderThan = false;
        if(this.year < otherDate.year) {
            isOlderThan = true;
            return isOlderThan;
        }
        else if(this.year == otherDate.year) {
            if(this.monthInt < otherDate.monthInt) {
                isOlderThan = true;
                return isOlderThan;
            }
            else if(this.monthInt == otherDate.monthInt) {
                if(this.day < otherDate.day) {
                    isOlderThan = true;
                    return isOlderThan;
                }
            }
        }
        return isOlderThan;
    }

    /**
     * Method compares this date to another date and returns true if this date is younger than the input date
     * @param otherDate
     * @return boolean indicating whether this date is younger than the input date
     */
    public boolean isYoungerThan(Date otherDate) {
        boolean isYoungerThan = false;
        if(this.year > otherDate.year) {
            isYoungerThan = true;
            return isYoungerThan;
        }
        else if(this.year == otherDate.year) {
            if(this.monthInt > otherDate.monthInt) {
                isYoungerThan = true;
                return isYoungerThan;
            }
            else if(this.monthInt == otherDate.monthInt) {
                if(this.day > otherDate.day) {
                    isYoungerThan = true;
                    return isYoungerThan;
                }
            }
        }
        return isYoungerThan;
    }

    /**
     * Method compares two dates and returns true if they are equal
     * @param other
     * @return
     */
    public boolean equals(Date other) {
        boolean equals = true;
        if(this.isOlderThan(other) || this.isYoungerThan(other))
            equals = false;
        return equals;
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

        /**
         * requires the String input to be a valid month
         * @param month
         */
        public Month(String month) throws IllegalArgumentException {
            this.monthText = month;
            switch(month) {
                case "January" : this.monthInt =  1;
                    break;
                case "February" : this.monthInt =  2;
                    break;
                case "March" : this.monthInt =  3;
                    break;
                case "April" : this.monthInt =  4;
                    break;
                case "May" : this.monthInt =  5;
                    break;
                case "June" : this.monthInt =  6;
                    break;
                case "July" : this.monthInt =  7;
                    break;
                case "August" : this.monthInt =  8;
                    break;
                case "September" : this.monthInt =  9;
                    break;
                case "October" : this.monthInt =  10;
                    break;
                case "November" : this.monthInt =  11;
                    break;
                case "December" : this.monthInt =  12;
                    break;
                default: throw new IllegalArgumentException("Invalid month entered!");
            }
        }
    }

}

