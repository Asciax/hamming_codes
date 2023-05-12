public class Encoder {
    private String raw_msg;
    private String final_result;

    public Encoder(String raw_message){
        this.raw_msg = raw_message;
    }

    public static void main(String[] args) {
        System.out.println("Test");
        String example = "AaBb";
        System.out.println(example.getBytes());
    }
}
