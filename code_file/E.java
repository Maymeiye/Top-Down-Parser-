import java.util.*;

class E
{
	LinkedList<TermItem> termItemList;

	E(LinkedList<TermItem> tItemList)
	{
		termItemList = tItemList;
	}

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <E>");
		for ( TermItem t : termItemList )
			t.printParseTree(indent+" ");
	}	
}