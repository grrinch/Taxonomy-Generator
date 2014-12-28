package helper;

/**
 * pomocnik do wypisywania komunikatów na konsoli. Sory, ale ciągłe
 * System.out.prinln() doprowadzało mnie do szału :)
 *
 * @author radmin
 */
public class Sp {

    public static void s(String s) {
        System.out.println(s);
    }
    
    public static void s() {
        System.out.println();
    }

    public static void i(int i) {
        System.out.println(i);
    }
    
    public static void i(int[] ints) {
        int i = 0;
        for(int e: ints) {
            System.out.println(i + ": " +e);
            i++;
        }
    }
    
    public static void d(double i) {
        System.out.println(i);
    }
    
    public static void l(long i) {
        System.out.println(i);
    }
}
