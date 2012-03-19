package br.UFSC.GRIMA.j3d;

public class InvalidBooleanOperationException extends Exception
{
	/** Constructs a default InvalidBooleanOperationException object */
	public InvalidBooleanOperationException()
	{
		super();
	}
	
	/**
	 * Constructs a new exception with the specified detail message 
	 * 
	 * @param message detail message
	 */
	public InvalidBooleanOperationException(String message)
	{
		super(message);
	}
}