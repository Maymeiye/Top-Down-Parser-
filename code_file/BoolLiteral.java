class BoolLiteral extends Primary
{
	boolean id;

	BoolLiteral(boolean ident)
	{
		id = ident;
	}

	void printParseTree(String indent)
	{
		super.printParseTree(indent);

		IO.displayln("\n " + indent + (indent.length()+1) + " <boolLiteral> " + (id?"\"true\"":"\"false\""));
	}	
}