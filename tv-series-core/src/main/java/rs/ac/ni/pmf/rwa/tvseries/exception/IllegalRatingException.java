package rs.ac.ni.pmf.rwa.tvseries.exception;

public class IllegalRatingException extends  RuntimeException{
    public IllegalRatingException(){
        super("Rating must be between 0 and 10");
    }
}
