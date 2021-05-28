import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        // TODO: добавить логику для объявления переменной date и задания её значения, для генерации строки с датой
        // Вы можете использовать класс LocalDate и его методы для получения и форматирования даты
        //берем текущую, прибавляем shift, переводим в строку в нужном формате

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.now().plusDays(shift).format(formatter);
    }

    public static String generateCity(String locale) {

        List<String> validCitY = new ArrayList<String>(Arrays.asList("Волгоград", "Уфа", "Элиста", "Казань", "Симферополь", "Владивосток"));
        Random randomIndex = new Random();
        return validCitY.get(randomIndex.nextInt(5));
    }

    public static String generateName(String locale) {

        return new Faker(new Locale(locale)).name().fullName();
    }

    public static String generatePhone(String locale) {

        return new Faker(new Locale(locale)).phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
           DataGenerator.UserInfo user = new DataGenerator.UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
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