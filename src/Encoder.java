// Author: Asciax
// May 2023

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Encoder {
    private String raw_msg;
    private String final_result;
    private Integer[] bitstream;

    private LinkedList<Integer[]> nibble_queue;
    private ArrayList<Integer> raw_final;

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
                this.raw_final.add((Integer.bitCount(this.seven_four_generator_matrixT[i] & nibble_value)) % 2);
            }

        }
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
        System.out.println(Arrays.toString(encoderV1.getCodeArray()));

    }
}
