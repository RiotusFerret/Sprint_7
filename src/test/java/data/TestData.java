package data;

import com.github.javafaker.Faker;

public class TestData {
    public final static String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    static Faker user = new Faker();
    public final static String LOGIN = user.name().lastName() + System.currentTimeMillis();
    public final static String PASSWORD = user.regexify("[0-9]{6}");
    public final static String FIRST_NAME = user.name().firstName();

    public final static String ORDER_FIRST_NAME = user.name().firstName();
    public final static String ORDER_LAST_NAME = user.name().lastName();
    public final static String ADDRESS = "Гора Олимп";
    public final static String METRO_STATION = "Фрунзенская";
    public final static String PHONE = "+7 800 555 35 35";
    public final static int RENT_TIME = 2;
    public final static String DELIVERY_DATE = "2026-06-06";
    public final static String COMMENT = "Hello there";
}
