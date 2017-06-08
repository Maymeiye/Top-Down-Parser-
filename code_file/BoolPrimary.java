class BoolPrimary
{
	E e_left;
	E e_right;
	LexAnalyzer.State state;

	BoolPrimary(E el)
	{
		e_left = el;
		e_right = null;
		state = null;
	}
	
	BoolPrimary(E el, E er, LexAnalyzer.State st)
	{
		e_left = el;
		e_right = er;
		state = st;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <boolPrimary>");
		e_left.printParseTree(indent1);
		if (state != null) 
		{
			IO.displayln(indent1 + indent1.length() + " <rel op>");
			/*
			switch ( state )
			{
				case Lt:			
					IO.displayln(indent1 + indent1.length() + " <");
					break;		
				case Le:
					IO.displayln(indent1 + indent1.length() + " <=");
					break;
				case Gt:
					IO.displayln(indent1 + indent1.length() + " >");
					break;
				case Ge:
					IO.displayln(indent1 + indent1.length() + " >=");
					break;
				case Eq:
					IO.displayln(indent1 + indent1.length() + " ==");
					break;
				case Neq:
					IO.displayln(indent1 + indent1.length() + " !=");
					break;
			default:
				break;			
			}		
			*/
		}
		if (e_right != null) e_right.printParseTree(indent1);
	}

}
