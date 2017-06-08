class Case
{
	Label id = null; 
	SList sl = null;  

	Case( Label s, SList sli)
	{
		id = s;
		sl = sli;
	}
	
	Case( SList sli)
	{
		sl = sli;
	  	
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
			
		if (id == null) IO.displayln(indent1 + indent1.length() + " default");
		else id.printParseTree(indent1);
	
		sl.printParseTree(indent2);
	}
}
	
