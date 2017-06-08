class Cond extends Statement2
{
	Expr e; 
	Statement sta;
	Statement sta1 = null;
	
	Cond( Expr exp, Statement s, Statement s1)
	{
		e = exp;
		sta = s;
		sta1 = s1;
	}
	
	Cond( Expr exp, Statement s)
	{
		e = exp;
		sta = s;
		
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
			
		IO.displayln(indent + indent.length() + " <cond>");
		IO.displayln(indent1 + indent1.length() + " if");
		
		e.printParseTree(indent1);	
		sta.printParseTree(indent1);
		if (sta1 != null) 
		{
			IO.displayln(indent1 + indent1.length() +" else");
			sta1.printParseTree(indent1);
			
		}
	}	
}
