// Author: Robert C.
// June 2023


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Decoder {
    // Check matrix used to verify whether any transmission errors occured:
    /*  H = [ 1 0 1 0 1 0 1 ]
     *      | 0 1 1 0 0 1 1 |
     *      [ 0 0 0 1 1 1 1 ]
     */
    private final Integer[] parity_check_matrix = {85, 51, 15};

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
        else if ((bitstream.length % 7) != 0){
            throw new IllegalArgumentException("Bitstream has to have a length multiple of 7." +
                    "Possible loss of information occured.");
        }
        else {
            for (Integer bit : bitstream){
                if (bit != 0 && bit != 1){
                    throw new IllegalArgumentException("Bitstream has to contain only bits!");
                }
            }
            this.bitstream = bitstream;
        }
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

    public LinkedList<Integer[]> getCodeQueue() {
        LinkedList<Integer[]> cloneQueue = new LinkedList<>();
        for (Integer[] currentArr : this.codeQueue){
            cloneQueue.addLast(currentArr);
        }
        return cloneQueue;
    }

    public Decoder(Integer[] bitstream){
        this.setBitstream(bitstream);
        this.codeQueue = new LinkedList<>();
    }
    public Decoder(String incomingString){
        this.setIncomingStringCode(incomingString);

        if (this.incomingStringCode.length() % 7 != 0){
            throw new IllegalArgumentException("Bitstream has to have a length multiple of 7." +
                    "Possible loss of information occured.");
        }

        this.bitstream = new Integer[this.incomingStringCode.length()];
        this.codeQueue = new LinkedList<>();
        this.processInput();
    }

    public void decode(){

    }

    void checkAll() throws IllegalArgumentException{
        Integer[] errorBinaryPosition = new Integer[3];
        for (int k=0; k<this.bitstream.length; k+=7){
            Integer currentCodeVal = Decoder.codeSegmentToInt(Arrays.copyOfRange(this.bitstream, k, k+7));
            for (int i=0; i<3; i++){

                errorBinaryPosition[i] = Integer.bitCount(
                        this.parity_check_matrix[i] & currentCodeVal) % 2;
            }
            for (Integer errBit: errorBinaryPosition){
                if (errBit != 0){
                    // METHOD TO WRITE : HANDLE ERROR
                    throw new IllegalArgumentException("Error in the code segment [" + k + "," + (k+7) + "] at position [" +
                            (errorBinaryPosition[2]*4+errorBinaryPosition[1]*2+errorBinaryPosition[0] + 1 + "/7]."));
                    // 1 added to the index to correct for index starting at 0
                    // in computer science and at 1 in hamming notation.
                }
            }
        }
    }

    private static Integer codeSegmentToInt(Integer[] codeSegment){
        return codeSegment[0]*64+codeSegment[1]*32+codeSegment[2]*16+codeSegment[3]*8+codeSegment[4]*4+codeSegment[5]*2+codeSegment[6];
    }


    void loadQueue(){
        for (int i=0, j=0; i<(this.bitstream.length/7); i++, j+=7){
            this.codeQueue.addFirst(
                    Arrays.copyOfRange(this.bitstream, j, j+7)
            );
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



    }
}
