class SubTermItem extends TermItem
{
	SubTermItem(Term t)
	{
		term = t;
	}

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " -");
		term.printParseTree(indent);
	}	
}