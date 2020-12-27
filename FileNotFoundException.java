/**
* The <code>FileNotFoundException</code> exception , is our exception for when the file is not found
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework5_214;

public class FileNotFoundException extends Exception {
	/**
	 * default constructor 
	 */
	public FileNotFoundException() {
		
	}
	/**
	 * constructor in which we want to send a message with the exception
	 * @param message
	 */
	public FileNotFoundException(String message) {
		super(message);
	}
}
