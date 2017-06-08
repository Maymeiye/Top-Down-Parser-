import java.util.LinkedList;

class SList
{
	LinkedList<Statement> Slist;

	SList(LinkedList<Statement> sl)
	{
		Slist = sl;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " <s list>");
		for ( Statement s : Slist )
			s.printParseTree(indent1);
	}	
}
