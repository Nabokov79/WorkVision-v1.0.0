package ru.nabokovsg.documentNK.service.common;

import org.springframework.stereotype.Component;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;

import java.time.LocalDate;
@Component
public class ConstantMonthServiceImpl implements ConstantMonthService{

    private final static String JANUARY = "января";
    private final static String FEBRUARY = "февраля";
    private final static String MARCH = "марта";
    private final static String APRIL = "апреля";
    private final static String MAY = "мая";
    private final static String JUNE = "июня";
    private final static String JULY = "июля";
    private final static String AUGUST = "августа";
    private final static String SEPTEMBER = "сентября";
    private final static String OCTOBER = "октября";
    private final static String NOVEMBER = "ноября";
    private final static String DECEMBER = "декабря";

    @Override

    public String getName(LocalDate date) {
        switch (date.getMonth().getValue()) {
            case 1 -> {return JANUARY;}
            case 2 -> {return FEBRUARY;}
            case 3 -> {return MARCH;}
            case 4 -> {return APRIL;}
            case 5 -> {return MAY;}
            case 6 -> {return JUNE;}
            case 7 -> {return JULY;}
            case 8 -> {return AUGUST;}
            case 9 -> {return SEPTEMBER;}
            case 10 -> {return OCTOBER;}
            case 11 -> {return NOVEMBER;}
            case 12 -> {return DECEMBER;}
            default -> throw new BadRequestException(
                                            String.format("Unknown month number=%s", date.getMonth().getValue()));
        }
    }


    @Override
    public String getNumber(LocalDate date) {
        switch (date.getMonth()) {
            case JANUARY -> {return "01";}
            case FEBRUARY -> {return "02";}
            case MARCH -> {return "03";}
            case APRIL -> {return "04";}
            case MAY -> {return "05";}
            case JUNE -> {return "06";}
            case JULY -> {return "07";}
            case AUGUST -> {return "08";}
            case SEPTEMBER -> {return "09";}
            case OCTOBER -> {return "10";}
            case NOVEMBER -> {return "11";}
            case DECEMBER -> {return "12";}
            default -> throw new BadRequestException(String.format("Unknown month month=%s", date.getMonth()));
        }
    }
}