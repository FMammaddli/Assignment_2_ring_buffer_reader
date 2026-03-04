package ringbuffer;

public final class Demo {
    public static void main(String[] args) {
        ringbuffer<String> buf = new ringbuffer<>(3);

        ringbuffer_reader<String> r1 = buf.createReader();
        ringbuffer_reader<String> r2 = buf.createReader();

        buf.write("A");
        buf.write("B");
        buf.write("C");

        System.out.println("r1: " + r1.read().orElse("<empty>"));
        System.out.println("r1: " + r1.read().orElse("<empty>"));
        
        // capacity=3, so this overwrites the oldest A
        buf.write("D");


        // r2 is slower, might miss something depending on timing
        System.out.println("r2: " + r2.read().orElse("<empty>"));
        System.out.println("r2 missed so far: " + r2.getMissedCount());

        System.out.println("r2: " + r2.read().orElse("<empty>"));
        System.out.println("r2: " + r2.read().orElse("<empty>"));
        System.out.println("r2: " + r2.read().orElse("<empty>"));


        // r1 continues reading independently, expected output:
        /*

        r1 continues reading:
        C
        D
        <empty>

        */
        System.out.println("\nr1 continues reading:");
        System.out.println("  " + r1.read().orElse("<empty>")); 
        System.out.println("  " + r1.read().orElse("<empty>")); 
        System.out.println("  " + r1.read().orElse("<empty>")); 
    }
}

