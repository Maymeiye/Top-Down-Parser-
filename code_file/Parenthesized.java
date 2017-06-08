class Parenthesized extends Primary
{
	Expr e;

	Parenthesized(Expr exp)
	{
		e = exp;
	}

	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		IO.displayln("");
		e.printParseTree(indent+" ");
	}	
}