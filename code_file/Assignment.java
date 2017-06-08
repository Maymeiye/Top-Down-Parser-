
class Assignment extends Statement2
{
	String id; 
	Expr e;      

	Assignment(String s, Expr exp)
	{
		id = s;
		e = exp;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <assignment>");
		IO.displayln(indent1 + indent1.length() + " " + id);
		IO.displayln(indent1 + indent1.length() + " =");
		e.printParseTree(indent1);
	}
		
}
