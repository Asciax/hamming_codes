// Author: Robert C.
// May 2023

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Encoder {
    // Incoming message as a String;
    private String raw_msg;

    // Raw bitstream of non-encoded message.
    private Integer[] bitstream;

    // LinkedList used as a queue in encode()/
    private LinkedList<Integer[]> nibble_queue;

    // Final encoded result presented as Integer ArrayList.
    private ArrayList<Integer> raw_final;

    // Hamming encoding in string version.
    private String final_code;

    /* seven_four_generator_matrixT : Transposed matrix of the generator matrix of the Hamming(7,4) code, where
     * each int represents a binary row inside the matrix. The true form of the generator matrix is:
     *  G^T = [ 1 1 0 1 ]
     *        | 1 0 1 1 |
     *        | 1 0 0 0 |
     *        | 0 1 1 1 |
     *        | 0 1 0 0 |
     *        | 0 0 1 0 |
     *        [ 0 0 0 1 ]
     */
    private final Integer[] seven_four_generator_matrixT = {13, 11, 8, 7, 4, 2, 1};
    public Encoder(String raw_message){
        this.raw_msg = raw_message;
        this.nibble_queue = new LinkedList<Integer[]>();
        this.raw_final = new ArrayList<Integer>();
    }

    public void encode(){
        this.bitstream = BinaryASCIIConverter.convertToBinary(this.raw_msg);

        // Nibble queue to be used by a more general Hamming code algorithm
        for (int i = 0, j = 0; i < (this.bitstream.length / 4); i++, j+=4){
            this.nibble_queue.addFirst( Arrays.copyOfRange(this.bitstream, j, j+4));
        }

        while (!this.nibble_queue.isEmpty()){
            Integer[] current_nibble = this.nibble_queue.removeLast();
            //ArrayList<Integer> hamming_nibble = new ArrayList<Integer>();
            int nibble_value = nibbleToInt(current_nibble);
            for (int i = 0 ; i < 7; i++){
                // The matrix mult. result mod 2 is the same as the population count of "1" bits when bitwise ANDing
                // each row from the generator with the nibble column. Solution from wikipedia.
                this.raw_final.add((Integer.bitCount(this.seven_four_generator_matrixT[i] & nibble_value)) % 2);
            }

        }
        StringBuilder strBuilder = new StringBuilder(this.raw_final.size());
        for (Integer integer : this.raw_final) {
            strBuilder.append(integer);
        }
        this.final_code = strBuilder.toString();
    }

    private int nibbleToInt(Integer[] nibble){
        if (nibble.length != 4){
            throw new IllegalArgumentException("A nibble has 4 bits, please input an array of length 4");
        }
        for (Integer j : nibble) {
            if(j.intValue() != 0 && j.intValue() != 1){
                throw new IllegalArgumentException("All values should be binary");
            }
        }

        return nibble[0]*8 + nibble[1]*4 + nibble[2]*2 + nibble[3];
    }

    public Integer[] getCodeArray(){
        Integer[] temp = new Integer[this.raw_final.size()];
        return this.raw_final.toArray(temp);
    }

    public String getCodeString(){
        return new String(this.final_code);
    }

    public static void main(String[] args) {
        String test = "R";
        Encoder encoderV1 = new Encoder(test);
        encoderV1.encode();
        System.out.println(Arrays.toString(encoderV1.bitstream));
        for (int i  = 0; i < (encoderV1.bitstream.length); i+=4){
            System.out.print(
                    Arrays.toString(Arrays.copyOfRange(encoderV1.bitstream, i, i+4))
            );

            System.out.print(" , ");
        }
        System.out.println("\n" + Arrays.toString(encoderV1.getCodeArray()));
        System.out.println(encoderV1.getCodeString());

    }
}
