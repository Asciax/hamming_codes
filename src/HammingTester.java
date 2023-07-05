import java.util.Arrays;

class HammingTester {
    public void EncoderFinalTest(String messageToEncode){
        Encoder enc = new Encoder(messageToEncode);
        System.out.println("Provided message: " + messageToEncode);
        enc.encode();
        System.out.println("----------" + "\n" + "Nibbles:");
        for (int i = 0; i < (enc.getBitstream_in().length); i+=4){
            System.out.print(
                    Arrays.toString(Arrays.copyOfRange(enc.getBitstream_in(), i, i+4))
            );

            System.out.print(" , ");
        }
        System.out.println("\n" + "----------" + "\nFinal coded bitstream: ");
        System.out.println("\n" + Arrays.toString(enc.getBitstreamOut()));
        System.out.println(enc.getCodeString());

    }
    public void EncoderFinalTest(Integer[] incomingRawBitstream){
        Encoder enc = new Encoder(incomingRawBitstream);
        System.out.println("Provided message: " + BinaryASCIIConverter.convertToASCII(incomingRawBitstream));
        enc.encode();
        System.out.println("----------" + "\n" + "Nibbles:");
        for (int i = 0; i < (enc.getBitstream_in().length); i+=4){
            System.out.print(
                    Arrays.toString(Arrays.copyOfRange(enc.getBitstream_in(), i, i+4))
            );

            System.out.print(" , ");
        }
        System.out.println("\n" + "----------" + "\nFinal coded bitstream: ");
        System.out.println("\n" + Arrays.toString(enc.getBitstreamOut()));
        System.out.println(enc.getCodeString());
    }

    public void DecoderFinalTest(Integer[] incomingBitstream){
        Decoder dec = new Decoder(incomingBitstream);
        System.out.println(Arrays.toString(dec.getBitstream_in()));
        System.out.println(dec.getBitstream_in().length);
        dec.decode();
        System.out.println(dec.getDecodedSequence());
    }

    public void DecoderFinalTester(String incomingCodedBitstream){
        Decoder dec = new Decoder(incomingCodedBitstream);
        System.out.println(Arrays.toString(dec.getBitstream_in()));
        System.out.println(dec.getBitstream_in().length);
        dec.decode();
        System.out.println(dec.getDecodedSequence());
    }
    public static void main(String[] args){

    }
}
