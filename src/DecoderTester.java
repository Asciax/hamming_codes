import java.util.Arrays;

public class DecoderTester {
    public static void main(String[] args){
        Decoder test = new Decoder(
                "100110011100001100110010010111001100111100110011001111001100110111111" +
                        "1010010100011111100110111111100011110101010110011001111001100110100110010000111111111");
        System.out.println(Arrays.toString(test.getBitstream_in()));
        System.out.println(test.getBitstream_in().length);
        test.decode();
        System.out.println(test.getDecodedSequence());
        /*
        for (Integer[] x : test.getCodeQueue()){
            System.out.println(Arrays.toString(x));
        }
        if (Objects.equals(test.getCodeQueue().getLast()[0], test.getBitstream()[0])){
            System.out.println("Both are the same object");
        }
         */
    }
}
