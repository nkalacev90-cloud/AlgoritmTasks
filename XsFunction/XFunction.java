package AlgoritmTask.XsFunction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class XFunction {


    public static void XFunctionText(String text) {
        int number = 255;
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        // Двоичное представление числа (8 бит)
        String acc = String.format("%8s",
                Integer.toBinaryString(number)).replace(' ', '0');

        System.out.println("number = " + number
                + "  bin=" + acc);
        System.out.println();

        String accLate ="";

        String bits = "";

        for (int i = 0; i < bytes.length; i++) {
            String byteBinary = String.format("%8s",
                    Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');

            accLate = acc;
            acc = bitwiseMultiply(acc, byteBinary);

            int dec = Integer.parseInt(acc, 2);
            String hex = String.format("%02X", dec);

            System.out.println("шаг " + (i + 1)
                    + ":  " + byteBinary
                    + " * " + accLate
                    + "  ->  " + acc);
        }


        int finalDec = Integer.parseInt(acc, 2);
        String finalHex = String.format("%02X", finalDec);

        System.out.println();
        System.out.println("КОНЕЧНЫЙ РЕЗУЛЬТАТ");
        System.out.println("  bin = " + acc);
        System.out.println("  dec = " + finalDec);
        System.out.println("  hex = 0x" + finalHex);
    }

    static String bitwiseMultiply(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            int bitA = a.charAt(i) - '0';
            int bitB = b.charAt(i) - '0';
            sb.append(bitA & bitB);
        }
        return sb.toString();
    }


    public static String sortTextOne(String text1, String text2){
        Map<String, Integer> text = new HashMap<>();

        String[] textArray1 = text1.split(" ");
        String[] textArray2 = text2.split(" ");

        ArrayList<String> result = new ArrayList<>();
        for (int i =0; i < textArray2.length; i++){
            text.put(textArray2[i], i);
        }
        for (int i =0; i < textArray1.length; i++){
            if(text.get(textArray1[i]) == null){
                result.add(textArray1[i]);
            }
        }
        return result.toString();
    }

    public static String searchSumfTerms(int[] numbers, int target){
        Map<Integer, Integer> numbers1 = new HashMap<>();
        for (int i =0; i < numbers.length; i++){
            if (numbers1.get(numbers[i]) == null){
                numbers1.put(target-numbers[i], i);
            }
            else {
                return ("[" + numbers1.get(numbers[i]) + ", " + i +  "]");
            }
        }
        return "Такого нет" ;
    }
}
