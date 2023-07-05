package rs.ac.ni.pmf.rwa.tvseries.exception;

public class IdsNotMatchException extends RuntimeException{

    public IdsNotMatchException(){
        super("Ids must match");
    }
}
