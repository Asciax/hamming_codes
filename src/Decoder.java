// Author: Robert C.
// June 2023


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Decoder {
    // Check matrix used to verify whether any transmission errors occured:
    /*  H = [ 1 0 1 0 1 0 1 ]
     *      | 0 1 1 0 0 1 1 |
     *      [ 0 0 0 1 1 1 1 ]
     */
    private final Integer[] parity_check_matrix = {85, 51, 15};

    // Raw bitstream of encoded message
    private Integer[] bitstream_in;

    // In case the encoded message is passed as a String.
    private String incomingStringCode;

    // Current 7-bit hamming code error position, 0 if no errors.
    private int errorPosition = 0;

    // Queue to keep track of the 7-bit arrays to decode.
    private LinkedList<Integer[]> codeQueue;

    // ArrayList to get final decoded sequence
    private ArrayList<Integer> bitstream_out;

    public void setBitstream_in(Integer[] bitstream_in)
        throws  NullPointerException, IllegalArgumentException{
        if (bitstream_in == null) {
            throw new NullPointerException("bitsream cannot be null");
        }
        else if ((bitstream_in.length % 7) != 0){
            throw new IllegalArgumentException("Bitstream has to have a length multiple of 7." +
                    "Possible loss of information occured.");
        }
        else {
            for (Integer bit : bitstream_in){
                if (bit != 0 && bit != 1){
                    throw new IllegalArgumentException("Bitstream has to contain only bits!");
                }
            }
            this.bitstream_in = bitstream_in;
        }
    }

    public Integer[] getBitstream_in(){
        return Arrays.copyOf(this.bitstream_in, this.bitstream_in.length);
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

    public Integer[] getBitstream_out() {
        Integer[] returnArr = new Integer[this.bitstream_out.size()];
        return bitstream_out.toArray(returnArr);
    }

    public String getDecodedSequence(){
        Integer[] bitstream = this.getBitstream_out();
        return BinaryASCIIConverter.convertToASCII(bitstream);
    }

    public Decoder(Integer[] bitstream_in){
        this.setBitstream_in(bitstream_in);
        this.codeQueue = new LinkedList<>();
        this.bitstream_out = new ArrayList<>();
    }
    public Decoder(String incomingString){
        this.setIncomingStringCode(incomingString);

        if (this.incomingStringCode.length() % 7 != 0){
            throw new IllegalArgumentException("Bitstream has to have a length multiple of 7." +
                    "Possible loss of information occured.");
        }

        this.bitstream_in = new Integer[this.incomingStringCode.length()];
        this.codeQueue = new LinkedList<>();
        this.bitstream_out = new ArrayList<>();
        this.processInput();
    }

    public void decode(){
        this.loadQueue();
        while (!this.codeQueue.isEmpty()){
            Integer[] currentArr = this.codeQueue.removeLast();
            checkCodeSegment(currentArr);
            if (this.errorPosition != 0){
                // HANDLE ERROR
                this.errorPosition = 0;
            }
            else {
                this.bitstream_out.add(currentArr[2]);
                this.bitstream_out.add(currentArr[4]);
                this.bitstream_out.add(currentArr[5]);
                this.bitstream_out.add(currentArr[6]);
            }

        }
    }

    void checkCodeSegment(Integer[] codeSegment) throws IllegalArgumentException{
        Integer[] errorBinaryPosition = new Integer[3];
        Integer currentCodeVal = Decoder.codeSegmentToInt(Arrays.copyOfRange(codeSegment, 0, 7));
        for (int i=0; i<3; i++){

            errorBinaryPosition[i] = Integer.bitCount(
                    this.parity_check_matrix[i] & currentCodeVal) % 2;
            }
            for (Integer errBit: errorBinaryPosition){
                if (errBit != 0){
                    this.errorPosition = errorBinaryPosition[2]*4+errorBinaryPosition[1]*2+errorBinaryPosition[0] - 1;
                    // METHOD TO WRITE : HANDLE ERROR
                    throw new IllegalArgumentException("Error in the code segment at position [" +
                            (errorBinaryPosition[2]*4+errorBinaryPosition[1]*2+errorBinaryPosition[0] + "/7]."));
                }
            }
    }

    private static Integer codeSegmentToInt(Integer[] codeSegment){
        return codeSegment[0]*64+codeSegment[1]*32+codeSegment[2]*16+codeSegment[3]*8+codeSegment[4]*4+codeSegment[5]*2+codeSegment[6];
    }


    void loadQueue(){
        for (int i = 0, j = 0; i<(this.bitstream_in.length/7); i++, j+=7){
            this.codeQueue.addFirst(
                    Arrays.copyOfRange(this.bitstream_in, j, j+7)
            );
        }
    }

    private void processInput(){
        if (this.incomingStringCode != null){
            String[] preliminaryArr = this.incomingStringCode.split("");
            for (int i = 0; i<this.bitstream_in.length; i++){
                this.bitstream_in[i] = Integer.parseInt(preliminaryArr[i]);
            }
        }
    }

}
