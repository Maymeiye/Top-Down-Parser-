/**
 
This class is a lexical analyzer for the 24 token categories <int> through <semicolon> defined by:

<letter> --> a | b | ... | z | A | B | ... | Z
<digit> --> 0 | 1 | ... | 9
<int> --> {<digit>}+
<id> --> <letter> { <letter> | <digit> }
<float> --> {<digit>}+ "." {<digit>} | "." {<digit>}+
<floatE> --> <float> (E|e) [+|-] {<digit>}+
<add> --> +
<sub> --> -
<mul> --> *
<div> --> /
<or> --> "||"
<and> --> "&&"
<inv> --> !
<lt> --> "<"
<le> --> "<="
<gt> --> ">"
<ge> --> ">="
<eq> --> "=="
<neq> --> "!="
<assign> --> = 
<LParen> --> (
<RParen> --> )
<LBrace> --> {
<RBrace> --> }
<colon> --> :
<semicolon> --> ;

This class implements a DFA that will accept the above tokens.
The DFA has 24 final and 6 non-final states represented by enum-type literals.

There are also special states for the keywords:
   "if", "else", "switch", "case", "default", "while", "do", "for", "print", "false", "true".
The keywords are initially extracted as identifiers.
The keywordCheck() function checks if the extracted token is a keyword,
and if so, moves the DFA to the corresponding special state.

The states are represented by the Enum type called "State".
The function "driver" is the driver to operate the DFA. 
The array "nextState" returns the next state given the current state and the input character.

To modify this lexical analyzer to recognize a different token set,
the array "nextState", the functions "isFinal", "setNextState", "setKeywordMap", and 
the enum type "State" need to be modified.
The function "driver" and the other utility functions remain the same.

**/

import java.util.*;

public abstract class LexAnalyzer extends IO
{
	public enum State 
	{ 
	// final states    ordinal number  token accepted 

		Add,             // 0          +
		Sub,             // 1          -
		Mul,             // 2          *
		Div,	             // 3          /
		Or,              // 4          ||
		And,             // 5          &&
		Inv,             // 6          !
		Lt,              // 7          <
		Le,              // 8          <=
		Gt,              // 9          >
		Ge,              // 10         >=
		Eq,              // 11         ==
		Neq,             // 12         !=
		Assign,          // 13         = 
		Id,              // 14         identifiers
		Int,             // 15         integers
		Float,           // 16         floats without exponentiation part
		FloatE,          // 17         floats with exponentiation part
		LParen,          // 18         (
		RParen,          // 19         )
		LBrace,          // 20         {
		RBrace,          // 21         }
		Colon,           // 22         :
		Semicolon,       // 23         ;

	// non-final states                string recognized    

		Start,           // 24      the empty string
		Bar,             // 25         |
		Ampersand,       // 26         &
		Period,          // 27        "."
		E,               // 28      float parts ending with E or e
		EPlusMinus,      // 29      float parts ending with + or - in exponentiation part

	// keyword states

		Keyword_if,
		Keyword_else,
		Keyword_switch,
		Keyword_case,
		Keyword_default,
		Keyword_while,
		Keyword_do,
		Keyword_for,
		Keyword_print,
		Keyword_false,
		Keyword_true,

		UNDEF;

		boolean isFinal()
		{
			return ( this.compareTo(State.Semicolon) <= 0 );  
		}

		boolean isRelationalOp()
		{
			return this.compareTo(Lt) >= 0 && this.compareTo(Neq) <= 0;
		}
	}

	// By enumerating the final states first and then the non-final states,
	// test for a final state can be done by testing if the state's ordinal number
	// is less than or equal to that of Semicolon.
	
	public static String t; // holds an extracted token
	public static State state; // the current state of the FA

	private static State[][] nextState = new State[30][128];
 
          // This array implements the state transition function
          // State x (ASCII char set) --> State.
          // The state argument is converted to its ordinal number used as
          // the first array index from 0 through 29.

	private static HashMap<String, State> keywordMap = new HashMap<String, State>();

	private static void setKeywordMap()
	{
		keywordMap.put("if",      State.Keyword_if);
		keywordMap.put("else",    State.Keyword_else);
		keywordMap.put("switch",  State.Keyword_switch);
		keywordMap.put("case",    State.Keyword_case);
		keywordMap.put("default", State.Keyword_default);
		keywordMap.put("while",   State.Keyword_while);
		keywordMap.put("do",      State.Keyword_do);
		keywordMap.put("for",     State.Keyword_for);
		keywordMap.put("print",   State.Keyword_print);
		keywordMap.put("false",   State.Keyword_false);
		keywordMap.put("true",    State.Keyword_true);
	}
	
	private static int driver()

	// This is the driver of the FA. 
	// If a valid token is found, assigns it to "t" and returns 1.
	// If an invalid token is found, assigns it to "t" and returns 0.
	// If end-of-stream is reached without finding any non-whitespace character, returns -1.

	{
		State nextSt; // the next state of the FA

		t = "";
		state = State.Start;

		if ( Character.isWhitespace((char) a) )
			a = getChar(); // get the next non-whitespace character
		if ( a == -1 ) // end-of-stream is reached
			return -1;

		while ( a != -1 ) // do the body if "a" is not end-of-stream
		{
			c = (char) a;
			nextSt = nextState[state.ordinal()][a];
			if ( nextSt == State.UNDEF ) // The FA will halt.
			{
				if ( state.isFinal() )
					return 1; // valid token extracted
				else // "c" is an unexpected character
				{
					t = t+c;
					a = getNextChar();
					return 0; // invalid token found
				}
			}
			else // The FA will go on.
			{
				state = nextSt;
				t = t+c;
				a = getNextChar();
			}
		}

		// end-of-stream is reached while a token is being extracted

		if ( state.isFinal() )
			return 1; // valid token extracted
		else
			return 0; // invalid token found
	} // end driver

	private static void setNextState()
	{
		for (int s = 0; s <= 29; s++ )
			for (int c = 0; c <= 127; c++ )
				nextState[s][c] = State.UNDEF;

		for (char c = 'A'; c <= 'Z'; c++)
		{
			nextState[State.Start.ordinal()][c] = State.Id;
			nextState[State.Id   .ordinal()][c] = State.Id;
		}

		for (char c = 'a'; c <= 'z'; c++)
		{
			nextState[State.Start.ordinal()][c] = State.Id;
			nextState[State.Id   .ordinal()][c] = State.Id;
		}

		for (char d = '0'; d <= '9'; d++)
		{
			nextState[State.Start     .ordinal()][d] = State.Int;
			nextState[State.Id        .ordinal()][d] = State.Id;
			nextState[State.Int       .ordinal()][d] = State.Int;
			nextState[State.Period    .ordinal()][d] = State.Float;
			nextState[State.Float     .ordinal()][d] = State.Float;
			nextState[State.E         .ordinal()][d] = State.FloatE;
			nextState[State.EPlusMinus.ordinal()][d] = State.FloatE;
			nextState[State.FloatE    .ordinal()][d] = State.FloatE;
		}

		nextState[State.Start.ordinal()]['+'] = State.Add;
		nextState[State.Start.ordinal()]['-'] = State.Sub;
		nextState[State.Start.ordinal()]['*'] = State.Mul;
		nextState[State.Start.ordinal()]['/'] = State.Div;
		nextState[State.Start.ordinal()]['!'] = State.Inv;
		nextState[State.Start.ordinal()]['<'] = State.Lt;
		nextState[State.Start.ordinal()]['>'] = State.Gt;
		nextState[State.Start.ordinal()]['='] = State.Assign;
		nextState[State.Start.ordinal()]['('] = State.LParen;
		nextState[State.Start.ordinal()][')'] = State.RParen;
		nextState[State.Start.ordinal()]['{'] = State.LBrace;
		nextState[State.Start.ordinal()]['}'] = State.RBrace;
		nextState[State.Start.ordinal()][':'] = State.Colon;
		nextState[State.Start.ordinal()][';'] = State.Semicolon;
		nextState[State.Start.ordinal()]['|'] = State.Bar;
		nextState[State.Start.ordinal()]['&'] = State.Ampersand;
		nextState[State.Start.ordinal()]['.'] = State.Period;
			
		nextState[State.Bar      .ordinal()]['|'] = State.Or;
		nextState[State.Ampersand.ordinal()]['&'] = State.And;
		nextState[State.Assign   .ordinal()]['='] = State.Eq;
		nextState[State.Inv      .ordinal()]['='] = State.Neq;
		nextState[State.Lt       .ordinal()]['='] = State.Le;
		nextState[State.Gt       .ordinal()]['='] = State.Ge;
			
		nextState[State.Int.ordinal()]['.'] = State.Float;
			
		nextState[State.Float.ordinal()]['E'] = state.E;
		nextState[State.Float.ordinal()]['e'] = state.E;
			
		nextState[State.E.ordinal()]['+'] = State.EPlusMinus;
		nextState[State.E.ordinal()]['-'] = State.EPlusMinus;

	} // end setNextState

	private static void keywordCheck()
	{
		State keywordState = keywordMap.get(t);
		if ( keywordState != null ) // "t" has a keyword
			state = keywordState;
	}

	public static void getToken()

	// Extract the next token using the driver of the FA.
	// If an invalid token is found, issue an error message.

	{
		int i = driver();
		if ( state == State.Id )
			keywordCheck();
		else if ( i == 0 )
			displayln( t+" : Lexical Error, invalid token");
	}

	public static void setLex()

	// Sets the nextState array and keywordMap.

	{
		setNextState();
		setKeywordMap();
	}

	public static void main(String argv[])

	// argv[0]: input file containing source code using tokens defined above
	// argv[1]: output file displaying a list of the tokens 

	{
		setIO( argv[0], argv[1] );
		setLex();

		int i;

		while ( a != -1 ) // while "a" is not end-of-stream
		{
			i = driver(); // extract the next token
			if ( i == 1 )
			{
				if ( state == State.Id )
					keywordCheck();
				displayln( t+"   : "+state.toString() );
			}
			else if ( i == 0 )
				displayln( t+" : Lexical Error, invalid token");
		}

		closeIO();
	}
}