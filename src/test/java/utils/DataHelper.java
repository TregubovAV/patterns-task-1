package utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static String generateValidDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static UserInfo generateUser() {
        return new UserInfo(
                faker.options().option("Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань", "Нижний Новгород", "Челябинск", "Самара", "Омск", "Ростов-на-Дону"),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber()
        );
    }

    public static class UserInfo {
        private final String city;
        private final String fullName;
        private final String phone;

        public UserInfo(String city, String fullName, String phone) {
            this.city = city;
            this.fullName = fullName;
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public String getFullName() {
            return fullName;
        }

        public String getPhone() {
            return phone;
        }
    }
}