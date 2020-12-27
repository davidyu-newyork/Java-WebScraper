/**
* The <code>WebCrawler</code> class , is our "main" class in which we have the menu and our main method.
* We ask the user to input which operation they want to do, then act accordingly
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework5_214;
import java.util.Scanner;

public class WebCrawler {

	/**
	 * our main method in which we print out the menu and ask for user inputs
	 * @param args
	 */
	public static void main(String[] args) {
		String userInput;
		Scanner input = new Scanner(System.in);
		WebTree tree = new WebTree();
		while(true) {
			System.out.println("[L] : Load HTML file\r\n" + 
					"[P] : Print tree\r\n" + 
					"[D] : Print dead links\r\n" + 
					"[S] : Search for a page with a title within the tree\r\n" + 
					"[R] : Reset tree structure\r\n" + 
					"[Q] : Quit");
			System.out.print("enter your selction: ");
			try {
				userInput = input.nextLine();
				userInput = userInput.toUpperCase().trim();
				switch (userInput) {
				case "L":
					System.out.print("enter the file name: ");
					String fileName = input.nextLine().trim();

						 tree = WebTree.crawlHTML(fileName.trim());

					

					
					break;
				case "P":
					tree.printWebTree();
					
					break;
				case "D":
					tree.printDeadLinks();
					break;
				case "S":
					System.out.println("enter the keyword to search for:");
					String keyWord = input.nextLine();
					tree.search(keyWord);
					break;
				case "R":
					System.out.println("tree reset sucessfully");
					tree.resetTreeStructure();
					
					break;
				case "Q":
					System.out.println("terminating program normally");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid input");
					break;
				}
				System.out.println();
			} catch (Exception e) {
				System.out.println();

			}
			
		}
		
	

	}

}
