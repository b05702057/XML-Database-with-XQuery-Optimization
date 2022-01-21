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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, EQ=20, IS=21, ID=22, FILENAME=23, STRING=24, ESCAPE=25, 
		WHITESPACE=26;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "EQ", "IS", "ID", "FILENAME", "STRING", "ESCAPE", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'/'", "'//'", "'doc(\"'", "'\")'", "'document(\"'", "'*'", "'.'", 
			"'..'", "'text()'", "'@'", "'('", "')'", "'['", "']'", "','", "'='", 
			"'and'", "'or'", "'not'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "EQ", "IS", "ID", "FILENAME", 
			"STRING", "ESCAPE", "WHITESPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\34\u00ac\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3"+
		"\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\5\25{\n\25\3\26\3\26\3\26\3\26"+
		"\5\26\u0081\n\26\3\27\3\27\7\27\u0085\n\27\f\27\16\27\u0088\13\27\3\30"+
		"\6\30\u008b\n\30\r\30\16\30\u008c\3\31\3\31\3\31\7\31\u0092\n\31\f\31"+
		"\16\31\u0095\13\31\3\31\3\31\3\31\3\31\7\31\u009b\n\31\f\31\16\31\u009e"+
		"\13\31\3\31\5\31\u00a1\n\31\3\32\3\32\3\32\3\33\6\33\u00a7\n\33\r\33\16"+
		"\33\u00a8\3\33\3\33\2\2\34\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\3\2\t\5\2C\\aac|\6\2\62;C\\aac|\7\2\60\60\62;C\\aac|\4\2"+
		"$$^^\4\2))^^\5\2$$))^^\5\2\13\f\17\17\"\"\2\u00b5\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\3\67\3\2\2\2\59\3\2\2\2\7<\3\2\2\2\tB\3\2\2\2\13E"+
		"\3\2\2\2\rP\3\2\2\2\17R\3\2\2\2\21T\3\2\2\2\23W\3\2\2\2\25^\3\2\2\2\27"+
		"`\3\2\2\2\31b\3\2\2\2\33d\3\2\2\2\35f\3\2\2\2\37h\3\2\2\2!j\3\2\2\2#l"+
		"\3\2\2\2%p\3\2\2\2\'s\3\2\2\2)z\3\2\2\2+\u0080\3\2\2\2-\u0082\3\2\2\2"+
		"/\u008a\3\2\2\2\61\u00a0\3\2\2\2\63\u00a2\3\2\2\2\65\u00a6\3\2\2\2\67"+
		"8\7\61\2\28\4\3\2\2\29:\7\61\2\2:;\7\61\2\2;\6\3\2\2\2<=\7f\2\2=>\7q\2"+
		"\2>?\7e\2\2?@\7*\2\2@A\7$\2\2A\b\3\2\2\2BC\7$\2\2CD\7+\2\2D\n\3\2\2\2"+
		"EF\7f\2\2FG\7q\2\2GH\7e\2\2HI\7w\2\2IJ\7o\2\2JK\7g\2\2KL\7p\2\2LM\7v\2"+
		"\2MN\7*\2\2NO\7$\2\2O\f\3\2\2\2PQ\7,\2\2Q\16\3\2\2\2RS\7\60\2\2S\20\3"+
		"\2\2\2TU\7\60\2\2UV\7\60\2\2V\22\3\2\2\2WX\7v\2\2XY\7g\2\2YZ\7z\2\2Z["+
		"\7v\2\2[\\\7*\2\2\\]\7+\2\2]\24\3\2\2\2^_\7B\2\2_\26\3\2\2\2`a\7*\2\2"+
		"a\30\3\2\2\2bc\7+\2\2c\32\3\2\2\2de\7]\2\2e\34\3\2\2\2fg\7_\2\2g\36\3"+
		"\2\2\2hi\7.\2\2i \3\2\2\2jk\7?\2\2k\"\3\2\2\2lm\7c\2\2mn\7p\2\2no\7f\2"+
		"\2o$\3\2\2\2pq\7q\2\2qr\7t\2\2r&\3\2\2\2st\7p\2\2tu\7q\2\2uv\7v\2\2v("+
		"\3\2\2\2w{\7?\2\2xy\7g\2\2y{\7s\2\2zw\3\2\2\2zx\3\2\2\2{*\3\2\2\2|}\7"+
		"?\2\2}\u0081\7?\2\2~\177\7k\2\2\177\u0081\7u\2\2\u0080|\3\2\2\2\u0080"+
		"~\3\2\2\2\u0081,\3\2\2\2\u0082\u0086\t\2\2\2\u0083\u0085\t\3\2\2\u0084"+
		"\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2"+
		"\2\2\u0087.\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008b\t\4\2\2\u008a\u0089"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\60\3\2\2\2\u008e\u0093\7$\2\2\u008f\u0092\5\63\32\2\u0090\u0092\n\5\2"+
		"\2\u0091\u008f\3\2\2\2\u0091\u0090\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091"+
		"\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096"+
		"\u00a1\7$\2\2\u0097\u009c\7)\2\2\u0098\u009b\5\63\32\2\u0099\u009b\n\6"+
		"\2\2\u009a\u0098\3\2\2\2\u009a\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c"+
		"\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2"+
		"\2\2\u009f\u00a1\7)\2\2\u00a0\u008e\3\2\2\2\u00a0\u0097\3\2\2\2\u00a1"+
		"\62\3\2\2\2\u00a2\u00a3\7^\2\2\u00a3\u00a4\t\7\2\2\u00a4\64\3\2\2\2\u00a5"+
		"\u00a7\t\b\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a6\3\2"+
		"\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\b\33\2\2\u00ab"+
		"\66\3\2\2\2\r\2z\u0080\u0086\u008c\u0091\u0093\u009a\u009c\u00a0\u00a8"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}