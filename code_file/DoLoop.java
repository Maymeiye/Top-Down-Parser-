class DoLoop extends Statement2
{
	Expr e; 
	Statement st;
	
	DoLoop(Statement s, Expr exp)
	{
		st = s;
		e = exp;	
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <do Loop>");
		IO.displayln(indent1 + indent1.length() + " do");
		st.printParseTree(indent1);
		IO.displayln(indent1 + indent1.length() + " while");
		e.printParseTree(indent1);
			
	}	
}


