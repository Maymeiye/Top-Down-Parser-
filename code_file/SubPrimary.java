class SubPrimary extends Primary
{
	Primary e;

	SubPrimary(Primary exp)
	{
		e = exp;
	}

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <primary>");
		IO.displayln(indent + indent.length() + " -");
		e.printParseTree(indent+" ");
	}	
}
