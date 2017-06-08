class ForLoop extends Statement2
{
	Assign as;
	Expr e; 
	Assign ass;
	Statement s;
	
	ForLoop( Assign asi, Expr exp, Assign assig, Statement st)
	{
		as = asi;
		e = exp;
		ass = assig;
		s = st;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		IO.displayln(indent + indent.length() + " <for Loop>");
		IO.displayln(indent1 + indent1.length() + " for");
		as.printParseTree(indent1);	
		e.printParseTree(indent1);
		ass.printParseTree(indent1);
		s.printParseTree(indent1);		
	}	
}


