/**
* The <code>HTMLLinkNode</code> class , is our node class in which we have store certain data that our nodes store
* such as the link name, page title, and file name.
*    
*Recitation number: 08
* @author D.J.Y David Justin Yu
*    e-mail: david.yu@stonybrook.edu
*    Stony Brook ID:111922653
**/
package homework5_214;

import homework1_cse214.FullCollectionException;

public class HTMLLinkNode {
	private String pageTitle; //title of the page
	private String linkName; //name of the link
	private HTMLLinkNode[] links = new HTMLLinkNode[3]; //array in which we can hold up to 3 child nodes
	private String fileName; //name of the file
	private LinkType linkType; //type of link dead, active or circular
	private HTMLLinkNode parent; //parent of the node
	
	/**
	 * default contructor
	 */
	public HTMLLinkNode() {
		
	}
	/**
	 * constructor with parameters
	 * @param filename
	 * filename is the name of the file
	 */
	public HTMLLinkNode(String filename) {
		super();
		//this.pageTitle = pageTitle;
		//this.linkName = linkName;
		this.fileName = filename;
		this.parent =null;
		//this.linkType = linkType;
	}
	/**
	 * constructor with parameters when we have link name, and a parent
	 * @param filename
	 * @param linkName
	 * @param parent
	 * parent is the parent node
	 */
	public HTMLLinkNode(String filename, String linkName, HTMLLinkNode parent) {
		super();
		this.linkName = linkName;
		this.fileName = filename;
		this.parent = parent;
	}
	//start of getters and setters

	/**
	 * getter for pageTitle
	 * @return pageTitle
	 */
	public String getTitle() {
		return pageTitle;
	}
	
	/**
	 * getter for parent
	 * @return parent
	 */
	public HTMLLinkNode getParent() {
		return parent;
	}
	/**
	 * getter for links 
	 * @return links
	 */
	public HTMLLinkNode[] getLinks() {
		return links;
	}
	/**
	 * getter for pageTitle
	 * @return pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}
	/**
	 * setter for pageTitle
	 * @param pageTitle
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	/**
	 * getter for linkName
	 * @return linkName
	 */
	public String getLinkName() {
		return linkName;
	}
	/**
	 * setter for linkName
	 * @param linkName
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	/**
	 * getter for fileName
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * setter for fileName
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * getter for linkType 
	 * @return linkType
	 */
	public LinkType getLinkType() {
		return linkType;
	}
	/**
	 * setter for linkType
	 * @param linkType
	 */
	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}
	//end of getters and setters

	/**
	 * method in which we see if link is dead or not
	 * @return true or false
	 */
	public boolean isDeadLink() {
		if(linkType==LinkType.DEAD)
			return true;
		else
			return false;
			
	}
	/** method in which we see if link is circular or not
	 * @return true or false
	 */
	public boolean isCircularLink() {
		if(linkType==LinkType.CIRCULAR)
			return true;
		else
			return false;
		
	}
	
	/**
	 * method in which we add newLink to first open position 
	 * @precondition There is at least one open position in the links array.
	 * @precondition newLink is not null.
	 * @param newLink
	 * @throws FullNodeException
	 * @throws IllegalArgumentException
	 * @postcondition newLink has been added as a child of this node.
	 * @throws FullNodeException: Thrown if all positions in the links array are currently full.
	 * @throws IllegalArgumentException: Thrown if newLink is not a valid reference to an HTMLLinkNode object.
	 */
	public void addLink(HTMLLinkNode newLink) throws FullNodeException, IllegalArgumentException{
		//method was tested and seems to work as intended
		if (newLink==null) { //throws if newLink not valid reference
			throw new IllegalArgumentException();	
		}
		if (links[2]!=null) {//if array is full throws this
			throw new FullNodeException("links array is full");
			
		}
		for (int i = 0; i < links.length; i++) { //loops through and adds to first available spot
			if (links[i]==null) {
				links[i] = newLink;
				break;	
			}
			
		}
	}
	/**
	 * method in which we can get a node at a certain index of the array
	 * @precondition index is within the bounds of the links array.
	 * @param index
	 * @return HTMLLinkNode 
	 * @throws IllegalArgumentException
	 */
	public HTMLLinkNode getLinkAt(int index) throws IllegalArgumentException{
		//this methos was tested and seems to work as intended
		if (index<0 ||index>2) {//throws this if index is out of bounds of (0,2)
			throw new IllegalArgumentException();
			
		}
		return links[index];
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		//this method was tested and seems to be working
		//HTMLLinkNodes are only equal if 
		//filename (case sensative) and page titles(insensitive) are equal
		HTMLLinkNode x = (HTMLLinkNode) o;
		return (this.fileName.equals(x.getFileName()) &&  //compares file name
			this.pageTitle.toLowerCase().equals(x.getPageTitle().toLowerCase()));//compares titles (case insensitive)	
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (linkType==LinkType.DEAD) {
			return "|- " + fileName + " ("+linkName+")*** " + "[ " + pageTitle +" ]";
		}
		else if (linkType==LinkType.CIRCULAR) {
			return "|- " + fileName + "* ("+linkName+") " + "[ " + pageTitle +" ]";
		}
		else
			return "|- " + fileName + " ("+linkName+") " + "[ " + pageTitle +" ]";
			
	}



}
