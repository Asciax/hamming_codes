// Author : Asciax
// May 2023

import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.charset.Charset;

final class BinaryASCIIConverter {
    // Utility class to convert ASCII -> binary and binary -> ASCII
    private static Charset charsetObj = Charset.forName("Cp437");
    private static String charsetName = "Cp437";

    private BinaryASCIIConverter(){} // private constructor to avoid instantiation

    public static String getCharsetName(){
        return charsetName;
    }
    /* ---setCharset(String charsetName)---
     * String charsetName -> Accepted charset names in the Charset class from java.nio.charset.Charset.
     *                    -> If charsetName is invalid, throws Exception.
     */
    public static void setCharset(String charsetName)
            throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException
    {
            charsetObj = Charset.forName(charsetName);
            BinaryASCIIConverter.charsetName = charsetName;
    }

    /*---convertToASCII(int[] input)---
     * int[] input -> Raw stream/array of bits
     * Returns : String containing (charsetName) characters
     * O(n) runtime because of the binary check.
     */
    public static String convertToASCII(Integer[] input) throws IllegalArgumentException{
        if (input.length % 8 != 0){
            throw new IllegalArgumentException("Number of bits has to be a multiple of 8");
        }
        for (Integer i : input){
            if (i != 1 && i != 0){
                throw new IllegalArgumentException("Stream has to contain only binary");
            }
        }
        int nb_bytes = input.length/8;
        int index=0;
        char[] char_arr = new char[nb_bytes];

        for (int bitindex = 0; index < nb_bytes; index++, bitindex+=8 ){
            Integer val = 128*input[bitindex] + 64*input[bitindex+1] + 32*input[bitindex+2] + 16*input[bitindex+3]
                    + 8*input[bitindex+4] + 4*input[bitindex+5] + 2*input[bitindex+6] + input[bitindex+7];
            char_arr[index] = (char) val.intValue();
        }

        return String.valueOf(char_arr);
    }


    // Converts ASCII String to binary, outputs binary array
    public static Integer[] convertToBinary(String input) throws IllegalArgumentException{
        if (input == null || input.length() == 0){
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }

        Integer[] output = new Integer[input.length()*8];
        byte[] byte_arr;

        // Verifying that the charset is supported, if not defaulting to ASCII.
        try {
            byte_arr = input.getBytes(charsetObj);

        }
        catch(Exception e){
            byte_arr = input.getBytes();
        }


        int tempindex = 0;
        for (byte current: byte_arr){
            Integer temp_byte = (int) current;
            for (int i = 0; i < 8; i++){
                output[tempindex] = (temp_byte & 128 ) == 0 ? 0 : 1;
                tempindex++;
                temp_byte = (temp_byte << 1);
            }
        }
        return output;
    }
    /*
    public static void main(String[] args){
        String rawstring = "REAL";
        int[] test = BinaryASCIIConverter.convertToBinary(rawstring);
        System.out.println(Arrays.toString(test));
        String otherside = BinaryASCIIConverter.convertToASCII(test);
        System.out.println(otherside);
    }
    */
}
