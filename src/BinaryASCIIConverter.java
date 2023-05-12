import java.io.UnsupportedEncodingException;
import java.util.Arrays;

final class BinaryASCIIConverter {
    // Utility class to convert ASCII -> binary and binary -> ASCII
    private static String charset = "US-ASCII";

    private BinaryASCIIConverter(){} // private constructor to avoid instantiation

    public static String convertToASCII(String input){
        return "";
    }

    public static int[] convertToBinary(String input){
        String ascii_version = input;
        int[] output = new int[input.length()*8];
        byte[] byte_arr;
        try {
            byte_arr = ascii_version.getBytes(charset);
        }
        catch(UnsupportedEncodingException e){
            byte_arr = ascii_version.getBytes();
        }
        int tempindex = 0;
        for (byte current: byte_arr){
            byte temp_byte = current;
            for (int i = 0; i < 8; i++){
                output[tempindex] = (temp_byte & 128 ) == 0 ? 0 : 1;
                tempindex++;
                temp_byte = (byte) (temp_byte << 1);
            }
        }
        return output;
    }

    public static void main(String[] args){
        //int[] test = BinaryASCIIConverter.convertToBinary("real");
        //System.out.println(Arrays.toString(test));
    }
}
