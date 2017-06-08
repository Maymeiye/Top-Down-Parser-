class Assign
{
	String id; 
	Expr e;      

	Assign(String s, Expr exp)
	{
		id = s;
		e = exp;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <assign>");
		IO.displayln(indent1 + indent1.length() + " " + id);
		IO.displayln(indent1 + indent1.length() + " =");
		e.printParseTree(indent1);
	}
		
}
