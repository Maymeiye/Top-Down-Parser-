class Print extends Statement2
{
	
	Expr e;       

	Print( Expr exp)
	{
		e = exp;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";		
		IO.displayln(indent + indent.length() + " <print>");
		e.printParseTree(indent1);
	}	
}
