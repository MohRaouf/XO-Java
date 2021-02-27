package Dependecy_Inversion;

//Sony Cast can but in the car 
public final class SonyCast implements ICast{
    
    public SonyCast(){
        this.printType();
    }
	
    @Override
    public void printType() {
            System.out.println("You Change the built in cast by : Sony" );
    }


}
