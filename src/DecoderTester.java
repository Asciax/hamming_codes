import java.util.Arrays;
import java.util.Objects;

public class DecoderTester {
    public static void main(String[] args){
        Decoder test = new Decoder("0101101");
        System.out.println(Arrays.toString(test.getBitstream()));
        System.out.println(test.getBitstream().length);
        test.loadQueue();
        /*
        for (Integer[] x : test.getCodeQueue()){
            System.out.println(Arrays.toString(x));
        }
        if (Objects.equals(test.getCodeQueue().getLast()[0], test.getBitstream()[0])){
            System.out.println("Both are the same object");
        }
         */
        test.checkAll();
    }
}
