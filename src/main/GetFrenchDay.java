package main;

import java.time.DayOfWeek;

public class GetFrenchDay {
    public static String getFrenchDay(DayOfWeek day) {
        switch (day) {
            case MONDAY:
                return "Lundi";
            case TUESDAY:
                return "Mardi";
            case WEDNESDAY:
                return "Mercredi";
            case THURSDAY:
                return "Jeudi";
            case FRIDAY:
                return "Vendredi";
            case SATURDAY:
                return "Samedi";
            case SUNDAY:
                return "Dimanche";
            default:
                return "";
        }
    }

    public static DayOfWeek convertToDayOfWeek(String dayString) {
        switch (dayString.toLowerCase()) {
            case "lundi":
                return DayOfWeek.MONDAY;
            case "mardi":
                return DayOfWeek.TUESDAY;
            case "mercredi":
                return DayOfWeek.WEDNESDAY;
            case "jeudi":
                return DayOfWeek.THURSDAY;
            case "vendredi":
                return DayOfWeek.FRIDAY;
            case "samedi":
                return DayOfWeek.SATURDAY;
            case "dimanche":
                return DayOfWeek.SUNDAY;
            default:
                return null;
        }
    }

}
