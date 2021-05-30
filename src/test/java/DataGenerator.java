import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.now().plusDays(shift).format(formatter);
    }

    public static String generateValidCity() {

        List<String> validCitY = new ArrayList<String>(Arrays.asList("Волгоград", "Уфа", "Элиста", "Казань", "Симферополь", "Владивосток"));
        Random randomIndex = new Random();
        return validCitY.get(randomIndex.nextInt(5));
    }

    public static String generateInvalidCity() {

        List<String> invalidCitY = new ArrayList<String>(Arrays.asList("Paris", "Boston", "Narva", "Berlin", "Granada", "Tokyo"));
        Random randomIndex = new Random();
        return invalidCitY.get(randomIndex.nextInt(5));
    }

    public static String generateValidName(String locale) {

        return new Faker(new Locale(locale)).name().fullName();
    }

    public static String generateInvalidName(String locale) {

        return new Faker(new Locale(locale)).name().fullName();
    }

    public static String generateValidPhone(String locale) {

        return new Faker(new Locale(locale)).phoneNumber().phoneNumber();
    }

    public static String generateInvalidPhone() {

        return new Faker(new Locale("en")).phoneNumber().phoneNumber().substring(1, 5);
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateValidUser(String locale) {
           DataGenerator.UserInfo user = new DataGenerator.UserInfo(generateValidCity(), generateValidName(locale), generateValidPhone(locale));
           return user;
        }

        public static UserInfo generateInvalidUser(String locale) {
            DataGenerator.UserInfo user = new DataGenerator.UserInfo(generateInvalidCity(), generateInvalidName(locale), generateInvalidPhone());
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}