class WhileLoop extends Statement2
{
	Expr e; 
	Statement st;
	
	WhileLoop( Expr exp, Statement s)
	{
		e = exp;
		st = s;	
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <while Loop>");
		IO.displayln(indent1 + indent1.length() + " while");
		e.printParseTree(indent1);
		st.printParseTree(indent1);
	}	
}


