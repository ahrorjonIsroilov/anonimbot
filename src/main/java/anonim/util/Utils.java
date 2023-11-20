package anonim.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utils {
    public static Boolean isNumeric(String number) {
        try {
            Long.parseLong(number);
            Integer.parseInt(number);
            Double.parseDouble(number);
            Float.parseFloat(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validPhone(String phone) {
        try {
            Long.parseLong(phone);
            return validUzbPhone(phone);
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateIdentifier() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(24);
    }

    private static boolean validUzbPhone(String phone) {
        if (phone.startsWith("+")) phone = phone.substring(1);
        return phone.length() == 12 && phone.startsWith("998");
    }
}
