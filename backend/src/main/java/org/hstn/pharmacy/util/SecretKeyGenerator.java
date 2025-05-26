package org.hstn.pharmacy.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static String generateSecretKey(int keyLengthBytes) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[keyLengthBytes];
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static void main(String[] args) {
        // Генеруємо ключ довжиною 32 байти (256 біт), що є рекомендованим розміром для HMAC-SHA256
        int keyLength = 32;
        String secretKey = generateSecretKey(keyLength);
        System.out.println("Згенерований секретний ключ (Base64):");
        System.out.println(secretKey);
    }
}
