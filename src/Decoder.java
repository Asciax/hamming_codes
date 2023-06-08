// Author: Robert C.
// June 2023


import java.util.Arrays;
import java.util.LinkedList;

public class Decoder {
    // Check matrix used to verify whether any transmission errors occured:
    /*  H = [ 1 1 0 1 1 0 0 ]
     *      | 1 0 1 1 0 1 0 |
     *      [ 0 1 1 1 0 0 1 ]
     */
    private final Integer[] parity_check_matrix = {108, 90, 57};

    // Raw bitstream of encoded message
    private Integer[] bitstream;

    // In case the encoded message is passed as a String.
    private String incomingStringCode;

    // Current 7-bit hamming code error position, 0 if no errors.
    private int errorPosition;

    // Queue to keep track of the 7-bit arrays to decode.
    private LinkedList<Integer[]> codeQueue;

    public void setBitstream(Integer[] bitstream)
        throws  NullPointerException, IllegalArgumentException{
        if (bitstream == null) {
            throw new NullPointerException("bitsream cannot be null");
        }
        if ((bitstream.length % 7) != 0){
            throw new IllegalArgumentException("Bitstream has to have a length multiple of 7." +
                    "Possible loss of information occured.");
        }
        for (Integer bit : bitstream){
            if (bit != 0 && bit != 1){
                throw new IllegalArgumentException("Bitstream has to contain only bits!");
            }
        }
        this.bitstream = bitstream;
    }

    public Integer[] getBitstream(){
        return Arrays.copyOf(this.bitstream, this.bitstream.length);
    }

    public void setIncomingStringCode(String incomingStringCode) throws NullPointerException {
        if (incomingStringCode == null){
            throw new NullPointerException("String cannot be null");
        }
        this.incomingStringCode = incomingStringCode;
    }

    public String getIncomingStringCode(){
        return new String(this.incomingStringCode);
    }

    public Decoder(Integer[] bitstream){
        this.setBitstream(bitstream);
        this.codeQueue = new LinkedList<>();
    }
    public Decoder(String incomingString){
        this.setIncomingStringCode(incomingString);
        this.bitstream = new Integer[this.incomingStringCode.length()];
        this.codeQueue = new LinkedList<>();
        this.processInput();
    }

    public void decode(){
        Integer[] errorBinaryPosition = new Integer[3];
        for (int i=0; i<3; i++){
            errorBinaryPosition[i] = Integer.bitCount(this.parity_check_matrix[i] & )
        }
    }

    private void loadQueue(){
        for (int i=0, j=0; i<(this.bitstream.length/7);){

        }
    }
    private void processInput(){
        if (this.incomingStringCode != null){
            String[] preliminaryArr = this.incomingStringCode.split("");
            for (int i=0; i<this.bitstream.length; i++){
                this.bitstream[i] = Integer.parseInt(preliminaryArr[i]);
            }
        }
    }

    public static void main(String[] args){
        Decoder test = new Decoder("10011");
        System.out.println(Arrays.toString(test.bitstream));
    }
}
