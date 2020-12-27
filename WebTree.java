/**
* The <code>WebTree</code> class , is our class in which we have a tree comprised of our nodes.
* Each node contains a link, which has up to 3 other links as childs.
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework5_214;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class WebTree {
	private static int totalLinks; //total links found
	private static int deadLinks; //total dead links found
	private static int activeLinks;//total active links found
	private static int circularLinks; //total circular links found

	
	private HTMLLinkNode root; //root of tree
	/**
	 * default contructor 
	 */
	public WebTree() {
		
	}
	/**
	 * contrusctor in which we instianiate the root with a new node
	 * @param filename
	 */
	public WebTree(String filename) {
		this.root=new HTMLLinkNode(filename);
	}
	/**
	 * constructor in wihch we already have a node to set root to
	 * @param node
	 */
	public WebTree(HTMLLinkNode node) {
		this.root= node;
	}
	/**
	 * getter for root
	 * @return root
	 */
	public HTMLLinkNode getRoot() {
		return root;
	}
	/**
	 * setter for root
	 * @param root
	 */
	public void setRoot(HTMLLinkNode root) {
		this.root = root;
	}
	
	/**
	 * method in which we parse a file and look for certain data items such as title and links
	 * @precondition The file specified is loaded and parsed for any title tag 
	 * and any link tags. If there is no file as specified by filename, then the 
	 * HTMLLinkNode object should be marked as a DEAD link. The HTMLLinkNode is inserted into the 
	 * WebTree object at its proper position. Once the full structure of the WebTree object has been 
	 * constructured and all links have been followed, print out the information 
	 * about the tree regarding the numbers and types of links encountered.
	 * @param filename
	 * @return WebTree with main root
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	public static WebTree crawlHTML(String filename) throws IllegalArgumentException, FileNotFoundException{
		totalLinks = 0;
		deadLinks=0;
		activeLinks = 0;
		circularLinks = 0;
		if (filename.equals(null) || filename.equals(" ")) {
			throw new IllegalArgumentException("no filename");
		}
		File tempFile = new File(filename);
		if (!tempFile.exists()) { //checks if file exists
			System.out.println("file " +filename +" does not exist, crawl unsuccessful");
			return new WebTree();
		}
		WebTree tree = new WebTree(filename);
		
		String[] links = new String[3];
		String[] linkNames = new String[3];

		

		try {
			String title = "";
			FileInputStream fis = new FileInputStream(filename); 
			InputStreamReader inStream = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(inStream);
			String line;
			int counter = 0;

			while ((line = reader.readLine())!=null) {
				line = line.trim();
				//line.length()>6 && line.substring(1, 6).equals("title")
				if (line.contains("<title>")) {// if line is title
					//title = line.substring(line.indexOf(">") + 1, line.indexOf("</"));
					title = line.substring(line.indexOf("<title>") + 7, line.indexOf("</"));

					tree.getRoot().setPageTitle(title);
					

				}
				if (line.contains("<a href=")) {
					String regex = "href=";
					String[] data = line.split(regex);

					
					for (int i = 0; i < data.length; i++) { //adds links and linksnames to a list
						if(data[i].contains(".html") && links[counter]==null) {
							links[counter] = data[i].substring(data[i].indexOf("\"")+1, data[i].indexOf("html")+4);
							linkNames[counter] = data[i].substring(data[i].indexOf(">")+1, data[i].indexOf("<"));
							counter++;
						}
						else if(data[i].contains(".htm") && links[counter]==null) { //for specific case where its .htm ?????? why tho
							links[counter] = data[i].substring(data[i].indexOf("\"")+1, data[i].indexOf("htm")+3);
							System.out.println(links[counter]);
							linkNames[counter] = data[i].substring(data[i].indexOf(">")+1, data[i].indexOf("<"));
							counter++;
						}
						
						
					}
					
				}
				
			}
			for (int i = 0; i < 3; i++) {
				if (links[i]!=null) {
					HTMLLinkNode temp = new HTMLLinkNode(links[i],linkNames[i],tree.getRoot());
					tree.getRoot().addLink(temp);
					WebTree tempTree = new WebTree(temp);
					tree.getRoot().setLinkType(LinkType.ACTIVE);
					crawlHTML(links[i],tempTree);
					
					
				}
				
			}
			System.out.println();
			System.out.println(filename + " was sucesssfuly crawledd");
			System.out.println(activeLinks + " active links found");
			System.out.println(circularLinks + " circular links found");
			System.out.println(deadLinks + " dead links found");

			System.out.println(activeLinks+deadLinks+circularLinks + " total links found");

			return tree;

		}
		catch (Exception e) {
			System.out.println("YO");
			System.out.println(e);
		}
		return tree;
		

	}
	/**
	 * method in which is recursively called to parse through each file
	 * @param filename
	 * @param newTree
	 * @return tree
	 * @throws IllegalArgumentException
	 */
	public static WebTree crawlHTML(String filename, WebTree newTree) throws IllegalArgumentException{ //recursive method
		if (filename.equals(null) || filename.equals(" ")) {
			throw new IllegalArgumentException("no filename");
		}
		File tempFile = new File(filename);
		WebTree tree = newTree;
		String[] links = new String[3];
		String[] linkNames = new String[3];

		if (!tempFile.exists()) { //checks if file exists
			tree.getRoot().setLinkType(LinkType.DEAD);
			deadLinks++;
			return tree;
		}

		try {
			String title = "";
			FileInputStream fis = new FileInputStream(filename); 
			InputStreamReader inStream = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(inStream);
			String line;
			int counter = 0;

			while ((line = reader.readLine())!=null) { //parsing through the file
				line = line.trim();
				if (line.length()>6 && line.substring(1, 6).equals("title")|| line.contains("<title>")) {// if line is title
					//title = line.substring(line.indexOf(">") + 1, line.indexOf("</"));
					title = line.substring(line.indexOf("<title>") + 7, line.indexOf("</"));

					tree.getRoot().setPageTitle(title);
					if (tree.existsAsAncestor(tree.getRoot())) { //if this is a duplicate, we set to circular and dont recursively call
						tree.getRoot().setLinkType(LinkType.CIRCULAR);
						circularLinks++;
						return tree;
					}
					

				}
				//if (line.contains("<a href=")) { //if line contains a link

				if (line.contains("href=")) { //if line contains a link
					String regex = "href=";
					String[] data = line.split(regex);

					for (int i = 0; i < data.length; i++) { //adds links and linksnames to a list
						if(data[i].contains(".html") && links[counter]==null) { //if line contains .html
							links[counter] = data[i].substring(data[i].indexOf("\"")+1, data[i].indexOf("html")+4);
							linkNames[counter] = data[i].substring(data[i].indexOf(">")+1, data[i].indexOf("<"));
							counter++;
						}
						else if(data[i].contains(".htm") && links[counter]==null) { //for specific case where its .htm ?????? why tho
							links[counter] = data[i].substring(data[i].indexOf("\"")+1, data[i].indexOf("htm")+3);
							linkNames[counter] = data[i].substring(data[i].indexOf(">")+1, data[i].indexOf("<"));
							counter++;
						}
						
					}
					
				}
				
			}
			for (int i = 0; i < 3; i++) { //creates nodes and adds them to root
				if (links[i]!=null) {
					HTMLLinkNode temp = new HTMLLinkNode(links[i],linkNames[i],tree.getRoot()); //link, link name, parent
					tree.getRoot().addLink(temp);
					WebTree tempTree = new WebTree(temp);
					tree.getRoot().setLinkType(LinkType.ACTIVE);
					crawlHTML(links[i],tempTree);
					
					
				}
				
			}
			activeLinks++;

			
			return tree;

		}
		
		catch (Exception e) {
			System.out.println(e);
		}
		return tree;
		
	}
	/**
	 * method to check if a node already exists as an ancestor
	 * @param node
	 * @return true or false, 
	 */
	public boolean existsAsAncestor(HTMLLinkNode node) { // if pagetitle and filename is same
		HTMLLinkNode parent = node.getParent();
		while(parent!=null) {
		
			if (node.getFileName().equals(parent.getFileName()) && //if pageTitle and file name is same we will return true
					node.getPageTitle().equals(parent.getPageTitle())) {
				return true;
				
			}
			parent = parent.getParent();
			
		}
		return false;
	}
	/**
	 * method that prints out the entire tree, starting call in which calls the recursive version
	 * @postcondition The hierarchy of the entire web site is output to the console. 
	 * If the tree is empty of if root is null, 
	 * print a message saying so and return to the calling method.
	 */
	public void printWebTree() {
		if(root == null)
			System.out.println("TREE IS NULL/EMPTY");
		else {
			System.out.println(root);
			if (root.getLinks()[0]!=null) {
				printWebTree(root.getLinks()[0],1);
				
			}
			if (root.getLinks()[1]!=null) {
				printWebTree(root.getLinks()[1],1);

				
			}
			if (root.getLinks()[2]!=null) {
				printWebTree(root.getLinks()[2],1);
		
			}
			
		}

	}
	/**
	 * method in which is recursive to traverse through the tree
	 * @param node
	 * @param counter
	 */
	public void printWebTree(HTMLLinkNode node, int counter) {
		for (int i = 0; i <counter; i++) {
			System.out.print("   ");
			
		}
		System.out.println(node);
		if (node.getLinks()[0]!=null) {
			printWebTree(node.getLinks()[0],counter+1);
			
		}
		if (node.getLinks()[1]!=null) {
			printWebTree(node.getLinks()[1],counter+1);

			
		}
		if (node.getLinks()[2]!=null) {
			printWebTree(node.getLinks()[2],counter+1);

			
		}	
		
		
	}
	
	
	/**
	 * method to reset the tree
	 * @postcondition root is set to null
	 */
	public void resetTreeStructure() {
		root = null;
	}
	/**
	 * method which we call to start the recursive method to print out dead links
	 * @postcondition Any pages which contain DEAD links should have their full paths required to reached them output to the console, 
	 * along with the dead link names and target file information. 
	 * If the tree is empty or root is null, print a message saying so and return to the calling method.

	 */
	public void printDeadLinks() { //prints all dead links, this method is one to be called
		if(root == null) {
			System.out.println("tree is null/empty");
			
		}
		else {
			System.out.println();
			System.out.println("dead links found at");
			if (root.getLinks()[0]!=null) {
				printDeadLinks(root.getLinks()[0]);
				
			}
			if (root.getLinks()[1]!=null) {
				printDeadLinks(root.getLinks()[1]);

				
			}
			if (root.getLinks()[2]!=null) {
				printDeadLinks(root.getLinks()[2]);
		
			}
			
		}
		
		
	}
	/**
	 * recursive method in which traverses the tree and prints out the dead links
	 * @param node
	 */
	public void printDeadLinks(HTMLLinkNode node) {//recursive method for print dead links
		if (node.isDeadLink()) {//if node is dead
			HTMLLinkNode parent = node.getParent();
			HTMLLinkNode[] tempNodes = new HTMLLinkNode[40];
			int counter = 0;
			while(parent.getParent()!= null) {
				tempNodes[counter] = parent;
				counter++;
				parent = parent.getParent();

			}
			System.out.print(parent.getFileName()+"->");
			while(counter!=0) {
				if (tempNodes[counter+1]==null) {
					System.out.print(tempNodes[counter-1].getFileName()+" ");
				}
				else
				System.out.print(tempNodes[counter-1].getFileName()+"->");
				counter--;
			}
			
			System.out.print("contains dead link " + node.getLinkName() + " with target " + node.getFileName());
			System.out.println();
			
		}
		//recursively go through tree
		if (node.getLinks()[0]!=null) {
			printDeadLinks(node.getLinks()[0]);
			
		}
		if (node.getLinks()[1]!=null) {
			printDeadLinks(node.getLinks()[1]);

			
		}
		if (node.getLinks()[2]!=null) {
			printDeadLinks(node.getLinks()[2]);
	
		}
		
	
	}
	/**
	 * method which is base call to search for a particular title within tree
	 * @param keyword
	 * 
	 * @throws IllegalArgumentException if keyword is invalid
	 * @postcondition Any valid path for reaching a HTMLLinkNode object which contains a 
	 * title attribute which contains keyword as a substring is output to the console.

	 */
	public void search(String keyword) throws IllegalArgumentException { //searches for node with specified title
		if(keyword.equals(null) || keyword.equals(" " ) || keyword.equals("")|| root==null) {
			System.out.println("keyword is empty/not valid input or the tree is empty");
			throw new IllegalArgumentException();
		}
		System.out.println("'" + keyword + "' can be found at following locations");

		if (root.getLinks()[0]!=null) {
			search(keyword,root.getLinks()[0]);
			
		}
		if (root.getLinks()[1]!=null) {
			search(keyword,root.getLinks()[1]);

			
		}
		if (root.getLinks()[2]!=null) {
			search(keyword,root.getLinks()[2]);
	
		}
		
		
	}
	/**
	 * recursive method in which traverses the tree searching for the particular instance of a title
	 * @param keyword
	 * @param node
	 */
	public void search(String keyword,HTMLLinkNode node) { //using the link name as comparisons for the keyword
		if (node.getLinkType()!= LinkType.DEAD && node.getLinkType()!= LinkType.CIRCULAR &&  //if links is dead or circular we dont do this
				node.getPageTitle() != null && node.getPageTitle().toLowerCase().contains(keyword.toLowerCase())) { //if title contains the keyword
	
			HTMLLinkNode parent = node.getParent();
			HTMLLinkNode[] tempNodes = new HTMLLinkNode[40];
			int counter = 0;
			while(parent.getParent()!= null) {
				tempNodes[counter] = parent;
				counter++;
				parent = parent.getParent();

			}
			System.out.print(parent.getFileName()+"->");
			
			
			while(counter!=0) {
				if (tempNodes[counter+1]==null) {
					System.out.print(tempNodes[counter-1].getFileName()+"->");
				}
				else
				System.out.print(tempNodes[counter-1].getFileName()+"->");
				counter--;

				
			}
			System.out.println(node.getFileName());
		}
		
		if (node.getLinks()[0]!=null) {
			search(keyword,node.getLinks()[0]);
			
		}
		if (node.getLinks()[1]!=null) {
			search(keyword,node.getLinks()[1]);

			
		}
		if (node.getLinks()[2]!=null) {
			search(keyword,node.getLinks()[2]);
	
		}
		
		
	}








		
	
}
