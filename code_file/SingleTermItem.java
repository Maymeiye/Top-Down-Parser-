class SingleTermItem extends TermItem
{
	SingleTermItem(Term t)
	{
		term = t;
	}
	void printParseTree(String indent)
	{
		term.printParseTree(indent);
	}	
}