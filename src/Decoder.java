// Author: Robert C.
// June 2023


public class Decoder {
    // Check matrix used to verify whether any transmission errors occured:
    /*  H = [ 1 1 0 1 1 0 0 ]
     *      | 1 0 1 1 0 1 0 |
     *      [ 0 1 1 1 0 0 1 ]
     */
    private Integer[] parity_check_matrix = {108, 90, 57};

    // Raw bitstream of encoded message
    private Integer[] bitstream;

    // In case the encoded message is passed as a String.
    private String incomingStringCode;
}
