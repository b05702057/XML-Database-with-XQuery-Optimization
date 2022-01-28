// Generated from Xpath.g4 by ANTLR 4.7.2

package edu.ucsd.cse232b.Antlr4Xpath;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XpathLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, DOC=17, 
		EQ=18, IS=19, ID=20, STRING=21, ESCAPE=22, WHITESPACE=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "DOC", 
			"EQ", "IS", "ID", "STRING", "ESCAPE", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'/'", "'//'", "'('", "')'", "'*'", "'.'", "'..'", "'text()'", 
			"'@'", "'['", "']'", "','", "'='", "'and'", "'or'", "'not'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "DOC", "EQ", "IS", "ID", "STRING", "ESCAPE", 
			"WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public XpathLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Xpath.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u0091\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\5\23e\n\23\3\24\3\24\3\24\3\24\5\24k\n\24\3\25\3\25\7\25"+
		"o\n\25\f\25\16\25r\13\25\3\26\3\26\3\26\7\26w\n\26\f\26\16\26z\13\26\3"+
		"\26\3\26\3\26\3\26\7\26\u0080\n\26\f\26\16\26\u0083\13\26\3\26\5\26\u0086"+
		"\n\26\3\27\3\27\3\27\3\30\6\30\u008c\n\30\r\30\16\30\u008d\3\30\3\30\2"+
		"\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\3\2\13\4\2FFff\4\2QQqq\4\2"+
		"EEee\5\2C\\aac|\6\2\62;C\\aac|\4\2$$^^\4\2))^^\5\2$$))^^\5\2\13\f\17\17"+
		"\"\"\2\u0099\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\3\61\3\2\2\2\5\63\3\2\2\2\7\66\3\2\2\2\t8\3\2\2\2\13:\3"+
		"\2\2\2\r<\3\2\2\2\17>\3\2\2\2\21A\3\2\2\2\23H\3\2\2\2\25J\3\2\2\2\27L"+
		"\3\2\2\2\31N\3\2\2\2\33P\3\2\2\2\35R\3\2\2\2\37V\3\2\2\2!Y\3\2\2\2#]\3"+
		"\2\2\2%d\3\2\2\2\'j\3\2\2\2)l\3\2\2\2+\u0085\3\2\2\2-\u0087\3\2\2\2/\u008b"+
		"\3\2\2\2\61\62\7\61\2\2\62\4\3\2\2\2\63\64\7\61\2\2\64\65\7\61\2\2\65"+
		"\6\3\2\2\2\66\67\7*\2\2\67\b\3\2\2\289\7+\2\29\n\3\2\2\2:;\7,\2\2;\f\3"+
		"\2\2\2<=\7\60\2\2=\16\3\2\2\2>?\7\60\2\2?@\7\60\2\2@\20\3\2\2\2AB\7v\2"+
		"\2BC\7g\2\2CD\7z\2\2DE\7v\2\2EF\7*\2\2FG\7+\2\2G\22\3\2\2\2HI\7B\2\2I"+
		"\24\3\2\2\2JK\7]\2\2K\26\3\2\2\2LM\7_\2\2M\30\3\2\2\2NO\7.\2\2O\32\3\2"+
		"\2\2PQ\7?\2\2Q\34\3\2\2\2RS\7c\2\2ST\7p\2\2TU\7f\2\2U\36\3\2\2\2VW\7q"+
		"\2\2WX\7t\2\2X \3\2\2\2YZ\7p\2\2Z[\7q\2\2[\\\7v\2\2\\\"\3\2\2\2]^\t\2"+
		"\2\2^_\t\3\2\2_`\t\4\2\2`$\3\2\2\2ae\7?\2\2bc\7g\2\2ce\7s\2\2da\3\2\2"+
		"\2db\3\2\2\2e&\3\2\2\2fg\7?\2\2gk\7?\2\2hi\7k\2\2ik\7u\2\2jf\3\2\2\2j"+
		"h\3\2\2\2k(\3\2\2\2lp\t\5\2\2mo\t\6\2\2nm\3\2\2\2or\3\2\2\2pn\3\2\2\2"+
		"pq\3\2\2\2q*\3\2\2\2rp\3\2\2\2sx\7$\2\2tw\5-\27\2uw\n\7\2\2vt\3\2\2\2"+
		"vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2{\u0086\7"+
		"$\2\2|\u0081\7)\2\2}\u0080\5-\27\2~\u0080\n\b\2\2\177}\3\2\2\2\177~\3"+
		"\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082"+
		"\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0086\7)\2\2\u0085s\3\2\2\2\u0085"+
		"|\3\2\2\2\u0086,\3\2\2\2\u0087\u0088\7^\2\2\u0088\u0089\t\t\2\2\u0089"+
		".\3\2\2\2\u008a\u008c\t\n\2\2\u008b\u008a\3\2\2\2\u008c\u008d\3\2\2\2"+
		"\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090"+
		"\b\30\2\2\u0090\60\3\2\2\2\f\2djpvx\177\u0081\u0085\u008d\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}