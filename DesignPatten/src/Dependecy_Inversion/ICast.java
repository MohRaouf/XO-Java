package Dependecy_Inversion;

//Cast interface every type of cast can implement it 
public interface ICast {
    
    default void printType() {
        System.out.println("*****The Built In Cast ***** ");
    }

}
