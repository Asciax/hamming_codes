// Author: Robert C.
// May 2023

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Encoder {
    // Incoming message as a String;
    private String raw_msg;

    // Raw bitstream of non-encoded message.
    private Integer[] bitstream_in;

    // LinkedList used as a queue in encode()/
    private LinkedList<Integer[]> nibbleQueue;

    // Final encoded bitstream presented as Integer ArrayList.
    private ArrayList<Integer> bitstream_out;

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

    public Integer[] getBitstream_in(){
        return this.bitstream_in.clone();
    }
    public void setBitstream_in(Integer[] bitstream)
            throws IndexOutOfBoundsException, IllegalArgumentException{
        if (bitstream == null){
            throw new IllegalArgumentException("Bitstream can't be empty");
        }
        else if ((bitstream.length % 4) != 0){
            throw new IndexOutOfBoundsException("Bitstream must be a multiple of 4");
        }
        else {
            for (Integer val : bitstream){
                if (val != 0 && val != 1){
                    throw new IllegalArgumentException("Bitstream should only contain binary");
                }
            }
            this.bitstream_in = bitstream;
        }
    }
    public Integer[] getBitstreamOut(){
        Integer[] temp = new Integer[this.bitstream_out.size()];
        return this.bitstream_out.toArray(temp);
    }

    public String getCodeString(){
        return new String(this.final_code);
    }
    public Encoder(String raw_message){
        this.raw_msg = raw_message;
        this.nibbleQueue = new LinkedList<Integer[]>();
        this.bitstream_out = new ArrayList<Integer>();
        this.__convertRawToBitstream__();
    }

    public Encoder(Integer[] bitstream_in){
        this.setBitstream_in(bitstream_in);
        this.raw_msg = null;
        this.nibbleQueue = new LinkedList<Integer[]>();
        this.bitstream_out = new ArrayList<Integer>();
    }
    private void __convertRawToBitstream__(){
        this.bitstream_in = BinaryASCIIConverter.convertToBinary(this.raw_msg);
    }


    /* encode():
     *  Main function of the class, encodes bitstream_in using Hamming[7,4] code.
     */
    public void encode(){

        // Nibble queue to be used by a more general Hamming code algorithm
        for (int i = 0, j = 0; i < (this.bitstream_in.length / 4); i++, j+=4){
            this.nibbleQueue.addFirst( Arrays.copyOfRange(this.bitstream_in, j, j+4));
        }

        while (!this.nibbleQueue.isEmpty()){
            Integer[] current_nibble = this.nibbleQueue.removeLast();

            int nibble_value = nibbleToInt(current_nibble);
            for (int i = 0 ; i < 7; i++){
                // The matrix mult. result mod 2 is the same as the population count of "1" bits when bitwise ANDing
                // each row from the generator with the nibble column.
                this.bitstream_out.add((Integer.bitCount(this.seven_four_generator_matrixT[i] & nibble_value)) % 2);
            }

        }
        StringBuilder strBuilder = new StringBuilder(this.bitstream_out.size());
        for (Integer integer : this.bitstream_out) {
            strBuilder.append(integer);
        }
        this.final_code = strBuilder.toString();
    }
    /* nibbleToInt(Integer[] nibble):
     *  Converts a 4-bit binary sequence to its decimal int.
     */
    private int nibbleToInt(Integer[] nibble) throws IllegalArgumentException{
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

}
