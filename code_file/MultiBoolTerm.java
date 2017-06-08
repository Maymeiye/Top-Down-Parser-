class MultiBoolTerm extends BoolTermItem
{
	MultiBoolTerm(BoolTerm t)
	{
		boolTerm = t;
	}

	void printParseTree(String indent)
	{
		boolTerm.printParseTree(indent);
	}	
}