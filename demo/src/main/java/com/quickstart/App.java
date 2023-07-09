package com.quickstart;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String jsonStr = "{\"batters\": [{\"type\": \"Regular\", \"id\": \"1001\"}, {\"type\": \"Chocolate\", \"id\": \"1002\" }, {\"type\": \"BlueBerry\",  \"id\": \"1003\"}], \"name\": \"Cake\", \"cakeId\": \"0001\"}";

        JSONObjectIterator obj = new JSONObjectIterator();
        obj.handleJSONObject(new JSONObject(jsonStr));
        System.out.println(obj.getKeyValuePairs());

        // Class<?> clazz = QueueName.class;
        // Field[] fields = clazz.getFields();
        // for (Field field : fields) {
        //     try {
        //         String queueName = field.get(field.getName()).toString();
        //         System.out.println(queueName);
        //     } catch (IllegalAccessException e) {
        //     }
        // }

        UserDto dto = new UserDto();
        // Class<?> clazzUser = UserDto.class;
        // Field[] fieldsUser = clazzUser.getDeclaredFields();
        // for (Field field : fieldsUser) {
        //     try {
        //         String fieldName = field.getName();
        //         System.out.println(fieldName);

        //         field.setAccessible(true);
        //         if (Arrays.asList("password").contains(fieldName)) {
        //             field.set(dto, "*****");
        //         }
        //         //Object v = field.get(dto);
        //         System.out.println(" => " + field.get(dto).getClass().getDeclaredClasses());
        //         //System.out.println(" => " + v );
        //     } catch (Exception e) {
        //         System.out.println("error => " + e);
        //     }
        // }

        String s = "{\"name\":\"testing\",\"password\":\"123456\",\"age\":\"20\",\"password\":\"123456\"}";
        // Pattern pattern = Pattern.compile("[dx][A-Z][a-zA-Z]+");
        // Matcher matcher = pattern.matcher(s);
        // while (matcher.find()) {
        //     String orig = s.substring(matcher.start(), matcher.end());
        //     String rep = s.substring(matcher.start() + 1, matcher.end());
        //     s = s.replaceAll(orig, rep);
        // }

        ShellProcessor.exec();
        //SecurityKeyPairGenerator.generate();
        String encrypted = Crypter.encryptRSA("testing");
        System.out.println("encrypted => " + encrypted);
        System.out.println("decrypted => " + Crypter.decryptRSA(encrypted));

        //String f = s.replaceAll("(\"password\"\\:(.*)\\,)", "\"password\":\"*****\",");
        String f = s.replace("(\"password\"\\:(.*)\\,$)", "-------:-------")
        .replace("(\"password\"\\:(.*)\\}$)", "========:-------")
        ;

        UserDto dto2 = Util.clone(dto);

        Ref1.setSecureFields(Arrays.asList("city"));
        System.out.println("value => " + Ref1.getValue(dto2, "address.city"));
        System.out.println("dto => " + dto2.address.city);
        System.out.println("dto => " + dto.address.city);

        String[] parts = s.split("\"password\"");

        int i = 0;
        for (String part: parts) {
            if (i == 0) {
                i++;
                continue;
            }
            String firstChar = part.substring(0, 1);

            System.out.println("part => " + part);

            System.out.println("firstChar 1 => " + firstChar);

            if (firstChar.equals(":")) {
                int ss = part.indexOf(",");
                if (ss > -1) {
                    String r = part.substring(0, ss);
                    System.out.println(part.substring(9));
                    part = (r.contains("\"") ? ":\"*****\"" : ":*****") + part.substring(9);
                    System.out.println(part);
                   
                }
                
            }
            i++;
        }
        System.out.println("parts => " + parts);
    }
}
