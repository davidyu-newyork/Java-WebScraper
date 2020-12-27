/**
* The <code>FullNodeException</code> exception , is our exception for when the node is full 
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework5_214;

public class FullNodeException extends Exception {
	/**
	 * default constructor
	 */
	public FullNodeException() {}
	 /**
	  * constructor in which we want to send a message with the exception
	 * @param message
	 */
	public FullNodeException(String message)
     {
        super(message);
     }

}
