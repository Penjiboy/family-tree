package penjiboy.familytree.Database;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String integerListToString(List<Integer> ids) {
        StringBuilder result = new StringBuilder();
        for(Integer id : ids) {
            result.append(id + ",");
        }

        return result.toString();
    }

    @TypeConverter
    public static List<Integer> stringToIntegerList(String intList) {
        String[] splitStrings = intList.split(",");
        List<Integer> result = new ArrayList<>();

        for(String idString : splitStrings) {
            result.add(Integer.parseInt(idString));
        }

        return result;
    }
}
