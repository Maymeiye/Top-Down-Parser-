class Switch extends Statement2
{
	Expr e; 
	CaseList csl;
	
	Switch( Expr exp, CaseList cs)
	{
		e = exp;
		csl = cs;	
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";		
		IO.displayln(indent + indent.length() + " <switch>");
		IO.displayln(indent1 + indent1.length() + " switch");
		e.printParseTree(indent1);
		csl.printParseTree(indent1);
	}	
}
