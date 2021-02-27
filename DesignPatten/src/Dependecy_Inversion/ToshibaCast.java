package Dependecy_Inversion;

//Sony Cast can but in the car 
public final class ToshibaCast implements ICast{
    
    public ToshibaCast(){
        this.printType();
    }
	
    @Override
    public void printType() {
            System.out.println("You Change the built in cast by : Toshiba" );
    }


}
