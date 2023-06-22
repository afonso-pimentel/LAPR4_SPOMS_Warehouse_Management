// Generated from D:/LAPR4_REPO/projects/eapli.base/base.core/src/main/java/eapli/base/surveysmanagement/answergrammar\AnswerGrammar.g4 by ANTLR 4.10.1
package eapli.base.surveysmanagement.answergrammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AnswerGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, POSITIVE_NUMERIC=5, CARDINAL=6, INPUT_SEP=7, 
		SCALING_SEP=8, SCALING_OP=9, DOT=10, DOT_COMMA=11, COMMA=12, SEPARATOR=13, 
		NEWLINE=14, WS=15, APOSTROPHE=16, EQUAL=17, SPECIAL=18, HIFEN=19, SCALE_VALUE=20, 
		FREE_TEXT=21, ZERO=22, NUMERIC=23, WORD=24, NAME=25, UPPERCASE_WORD=26;
	public static final int
		RULE_answers = 0, RULE_answer_Struct = 1, RULE_question_ID = 2, RULE_answer = 3, 
		RULE_multiple_Choice = 4, RULE_multiple_Choice_With_Input = 5, RULE_sorting_Options = 6, 
		RULE_scaling_Options = 7, RULE_single_Choice = 8, RULE_single_Choice_With_Input = 9, 
		RULE_simple_Answer = 10, RULE_multiple_Options = 11, RULE_scaling_Option = 12, 
		RULE_scale_Answer = 13, RULE_input_Answer = 14, RULE_option = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"answers", "answer_Struct", "question_ID", "answer", "multiple_Choice", 
			"multiple_Choice_With_Input", "sorting_Options", "scaling_Options", "single_Choice", 
			"single_Choice_With_Input", "simple_Answer", "multiple_Options", "scaling_Option", 
			"scale_Answer", "input_Answer", "option"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'('", "')'", null, "'#'", "'%in%'", "'%s%'", "'%so%'", 
			"'.'", "';'", "','", "'::'", "'\\n'", null, "'''", "'='", null, "'-'", 
			null, null, "'0'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "POSITIVE_NUMERIC", "CARDINAL", "INPUT_SEP", 
			"SCALING_SEP", "SCALING_OP", "DOT", "DOT_COMMA", "COMMA", "SEPARATOR", 
			"NEWLINE", "WS", "APOSTROPHE", "EQUAL", "SPECIAL", "HIFEN", "SCALE_VALUE", 
			"FREE_TEXT", "ZERO", "NUMERIC", "WORD", "NAME", "UPPERCASE_WORD"
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

	@Override
	public String getGrammarFileName() { return "AnswerGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AnswerGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class AnswersContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(AnswerGrammarParser.EOF, 0); }
		public List<Answer_StructContext> answer_Struct() {
			return getRuleContexts(Answer_StructContext.class);
		}
		public Answer_StructContext answer_Struct(int i) {
			return getRuleContext(Answer_StructContext.class,i);
		}
		public AnswersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterAnswers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitAnswers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitAnswers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswersContext answers() throws RecognitionException {
		AnswersContext _localctx = new AnswersContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_answers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(32);
				answer_Struct();
				}
				}
				setState(35); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==POSITIVE_NUMERIC );
			setState(37);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Answer_StructContext extends ParserRuleContext {
		public Question_IDContext question_ID() {
			return getRuleContext(Question_IDContext.class,0);
		}
		public TerminalNode SEPARATOR() { return getToken(AnswerGrammarParser.SEPARATOR, 0); }
		public AnswerContext answer() {
			return getRuleContext(AnswerContext.class,0);
		}
		public Answer_StructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer_Struct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterAnswer_Struct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitAnswer_Struct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitAnswer_Struct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Answer_StructContext answer_Struct() throws RecognitionException {
		Answer_StructContext _localctx = new Answer_StructContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_answer_Struct);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(40);
			question_ID();
			setState(41);
			match(SEPARATOR);
			setState(42);
			answer();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Question_IDContext extends ParserRuleContext {
		public TerminalNode POSITIVE_NUMERIC() { return getToken(AnswerGrammarParser.POSITIVE_NUMERIC, 0); }
		public Question_IDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question_ID; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterQuestion_ID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitQuestion_ID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitQuestion_ID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question_IDContext question_ID() throws RecognitionException {
		Question_IDContext _localctx = new Question_IDContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_question_ID);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(POSITIVE_NUMERIC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnswerContext extends ParserRuleContext {
		public Single_ChoiceContext single_Choice() {
			return getRuleContext(Single_ChoiceContext.class,0);
		}
		public Single_Choice_With_InputContext single_Choice_With_Input() {
			return getRuleContext(Single_Choice_With_InputContext.class,0);
		}
		public Multiple_ChoiceContext multiple_Choice() {
			return getRuleContext(Multiple_ChoiceContext.class,0);
		}
		public Multiple_Choice_With_InputContext multiple_Choice_With_Input() {
			return getRuleContext(Multiple_Choice_With_InputContext.class,0);
		}
		public Sorting_OptionsContext sorting_Options() {
			return getRuleContext(Sorting_OptionsContext.class,0);
		}
		public Scaling_OptionsContext scaling_Options() {
			return getRuleContext(Scaling_OptionsContext.class,0);
		}
		public Simple_AnswerContext simple_Answer() {
			return getRuleContext(Simple_AnswerContext.class,0);
		}
		public AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterAnswer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitAnswer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitAnswer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswerContext answer() throws RecognitionException {
		AnswerContext _localctx = new AnswerContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(46);
				single_Choice();
				}
				break;
			case 2:
				{
				setState(47);
				single_Choice_With_Input();
				}
				break;
			case 3:
				{
				setState(48);
				multiple_Choice();
				}
				break;
			case 4:
				{
				setState(49);
				multiple_Choice_With_Input();
				}
				break;
			case 5:
				{
				setState(50);
				sorting_Options();
				}
				break;
			case 6:
				{
				setState(51);
				scaling_Options();
				}
				break;
			case 7:
				{
				setState(52);
				simple_Answer();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiple_ChoiceContext extends ParserRuleContext {
		public Multiple_OptionsContext multiple_Options() {
			return getRuleContext(Multiple_OptionsContext.class,0);
		}
		public Multiple_ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_Choice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterMultiple_Choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitMultiple_Choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitMultiple_Choice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_ChoiceContext multiple_Choice() throws RecognitionException {
		Multiple_ChoiceContext _localctx = new Multiple_ChoiceContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_multiple_Choice);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(T__0);
			setState(56);
			multiple_Options();
			setState(57);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiple_Choice_With_InputContext extends ParserRuleContext {
		public Multiple_ChoiceContext multiple_Choice() {
			return getRuleContext(Multiple_ChoiceContext.class,0);
		}
		public Input_AnswerContext input_Answer() {
			return getRuleContext(Input_AnswerContext.class,0);
		}
		public Multiple_Choice_With_InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_Choice_With_Input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterMultiple_Choice_With_Input(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitMultiple_Choice_With_Input(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitMultiple_Choice_With_Input(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_Choice_With_InputContext multiple_Choice_With_Input() throws RecognitionException {
		Multiple_Choice_With_InputContext _localctx = new Multiple_Choice_With_InputContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_multiple_Choice_With_Input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(59);
			multiple_Choice();
			}
			{
			setState(60);
			input_Answer();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sorting_OptionsContext extends ParserRuleContext {
		public List<TerminalNode> CARDINAL() { return getTokens(AnswerGrammarParser.CARDINAL); }
		public TerminalNode CARDINAL(int i) {
			return getToken(AnswerGrammarParser.CARDINAL, i);
		}
		public Multiple_OptionsContext multiple_Options() {
			return getRuleContext(Multiple_OptionsContext.class,0);
		}
		public Sorting_OptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sorting_Options; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterSorting_Options(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitSorting_Options(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitSorting_Options(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sorting_OptionsContext sorting_Options() throws RecognitionException {
		Sorting_OptionsContext _localctx = new Sorting_OptionsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_sorting_Options);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(CARDINAL);
			{
			setState(63);
			multiple_Options();
			}
			setState(64);
			match(CARDINAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Scaling_OptionsContext extends ParserRuleContext {
		public List<TerminalNode> SCALING_SEP() { return getTokens(AnswerGrammarParser.SCALING_SEP); }
		public TerminalNode SCALING_SEP(int i) {
			return getToken(AnswerGrammarParser.SCALING_SEP, i);
		}
		public List<Scaling_OptionContext> scaling_Option() {
			return getRuleContexts(Scaling_OptionContext.class);
		}
		public Scaling_OptionContext scaling_Option(int i) {
			return getRuleContext(Scaling_OptionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AnswerGrammarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AnswerGrammarParser.COMMA, i);
		}
		public Scaling_OptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scaling_Options; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterScaling_Options(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitScaling_Options(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitScaling_Options(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Scaling_OptionsContext scaling_Options() throws RecognitionException {
		Scaling_OptionsContext _localctx = new Scaling_OptionsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_scaling_Options);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(SCALING_SEP);
			setState(70); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					{
					setState(67);
					scaling_Option();
					}
					setState(68);
					match(COMMA);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(72); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			{
			setState(74);
			scaling_Option();
			}
			setState(75);
			match(SCALING_SEP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Single_ChoiceContext extends ParserRuleContext {
		public OptionContext option() {
			return getRuleContext(OptionContext.class,0);
		}
		public Single_ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_Choice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterSingle_Choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitSingle_Choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitSingle_Choice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Single_ChoiceContext single_Choice() throws RecognitionException {
		Single_ChoiceContext _localctx = new Single_ChoiceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_single_Choice);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			option();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Single_Choice_With_InputContext extends ParserRuleContext {
		public Single_ChoiceContext single_Choice() {
			return getRuleContext(Single_ChoiceContext.class,0);
		}
		public Input_AnswerContext input_Answer() {
			return getRuleContext(Input_AnswerContext.class,0);
		}
		public Single_Choice_With_InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_Choice_With_Input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterSingle_Choice_With_Input(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitSingle_Choice_With_Input(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitSingle_Choice_With_Input(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Single_Choice_With_InputContext single_Choice_With_Input() throws RecognitionException {
		Single_Choice_With_InputContext _localctx = new Single_Choice_With_InputContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_single_Choice_With_Input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(79);
			single_Choice();
			}
			{
			setState(80);
			input_Answer();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Simple_AnswerContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(AnswerGrammarParser.FREE_TEXT, 0); }
		public Simple_AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_Answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterSimple_Answer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitSimple_Answer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitSimple_Answer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Simple_AnswerContext simple_Answer() throws RecognitionException {
		Simple_AnswerContext _localctx = new Simple_AnswerContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_simple_Answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(FREE_TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiple_OptionsContext extends ParserRuleContext {
		public List<OptionContext> option() {
			return getRuleContexts(OptionContext.class);
		}
		public OptionContext option(int i) {
			return getRuleContext(OptionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AnswerGrammarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AnswerGrammarParser.COMMA, i);
		}
		public Multiple_OptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_Options; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterMultiple_Options(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitMultiple_Options(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitMultiple_Options(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_OptionsContext multiple_Options() throws RecognitionException {
		Multiple_OptionsContext _localctx = new Multiple_OptionsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_multiple_Options);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(87); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					{
					setState(84);
					option();
					}
					setState(85);
					match(COMMA);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(89); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			{
			setState(91);
			option();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Scaling_OptionContext extends ParserRuleContext {
		public OptionContext option() {
			return getRuleContext(OptionContext.class,0);
		}
		public List<TerminalNode> SCALING_OP() { return getTokens(AnswerGrammarParser.SCALING_OP); }
		public TerminalNode SCALING_OP(int i) {
			return getToken(AnswerGrammarParser.SCALING_OP, i);
		}
		public Scale_AnswerContext scale_Answer() {
			return getRuleContext(Scale_AnswerContext.class,0);
		}
		public Scaling_OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scaling_Option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterScaling_Option(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitScaling_Option(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitScaling_Option(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Scaling_OptionContext scaling_Option() throws RecognitionException {
		Scaling_OptionContext _localctx = new Scaling_OptionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_scaling_Option);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			option();
			setState(94);
			match(SCALING_OP);
			setState(95);
			scale_Answer();
			setState(96);
			match(SCALING_OP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Scale_AnswerContext extends ParserRuleContext {
		public TerminalNode SCALE_VALUE() { return getToken(AnswerGrammarParser.SCALE_VALUE, 0); }
		public Scale_AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scale_Answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterScale_Answer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitScale_Answer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitScale_Answer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Scale_AnswerContext scale_Answer() throws RecognitionException {
		Scale_AnswerContext _localctx = new Scale_AnswerContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_scale_Answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(SCALE_VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Input_AnswerContext extends ParserRuleContext {
		public List<TerminalNode> INPUT_SEP() { return getTokens(AnswerGrammarParser.INPUT_SEP); }
		public TerminalNode INPUT_SEP(int i) {
			return getToken(AnswerGrammarParser.INPUT_SEP, i);
		}
		public Simple_AnswerContext simple_Answer() {
			return getRuleContext(Simple_AnswerContext.class,0);
		}
		public Input_AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input_Answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterInput_Answer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitInput_Answer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitInput_Answer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Input_AnswerContext input_Answer() throws RecognitionException {
		Input_AnswerContext _localctx = new Input_AnswerContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_input_Answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(INPUT_SEP);
			setState(101);
			simple_Answer();
			setState(102);
			match(INPUT_SEP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionContext extends ParserRuleContext {
		public TerminalNode POSITIVE_NUMERIC() { return getToken(AnswerGrammarParser.POSITIVE_NUMERIC, 0); }
		public Simple_AnswerContext simple_Answer() {
			return getRuleContext(Simple_AnswerContext.class,0);
		}
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AnswerGrammarListener ) ((AnswerGrammarListener)listener).exitOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AnswerGrammarVisitor ) return ((AnswerGrammarVisitor<? extends T>)visitor).visitOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_option);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(104);
			match(POSITIVE_NUMERIC);
			setState(105);
			match(T__2);
			setState(106);
			simple_Answer();
			setState(107);
			match(T__3);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001an\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0001\u0000\u0004\u0000\"\b\u0000\u000b\u0000\f\u0000#\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u00036\b\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0004\u0007G\b\u0007\u000b\u0007\f\u0007H\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0004\u000bX\b\u000b\u000b"+
		"\u000b\f\u000bY\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0000\u0000\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e\u0000\u0000f\u0000!\u0001\u0000\u0000\u0000"+
		"\u0002(\u0001\u0000\u0000\u0000\u0004,\u0001\u0000\u0000\u0000\u00065"+
		"\u0001\u0000\u0000\u0000\b7\u0001\u0000\u0000\u0000\n;\u0001\u0000\u0000"+
		"\u0000\f>\u0001\u0000\u0000\u0000\u000eB\u0001\u0000\u0000\u0000\u0010"+
		"M\u0001\u0000\u0000\u0000\u0012O\u0001\u0000\u0000\u0000\u0014R\u0001"+
		"\u0000\u0000\u0000\u0016W\u0001\u0000\u0000\u0000\u0018]\u0001\u0000\u0000"+
		"\u0000\u001ab\u0001\u0000\u0000\u0000\u001cd\u0001\u0000\u0000\u0000\u001e"+
		"h\u0001\u0000\u0000\u0000 \"\u0003\u0002\u0001\u0000! \u0001\u0000\u0000"+
		"\u0000\"#\u0001\u0000\u0000\u0000#!\u0001\u0000\u0000\u0000#$\u0001\u0000"+
		"\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0005\u0000\u0000\u0001&\'\u0006"+
		"\u0000\uffff\uffff\u0000\'\u0001\u0001\u0000\u0000\u0000()\u0003\u0004"+
		"\u0002\u0000)*\u0005\r\u0000\u0000*+\u0003\u0006\u0003\u0000+\u0003\u0001"+
		"\u0000\u0000\u0000,-\u0005\u0005\u0000\u0000-\u0005\u0001\u0000\u0000"+
		"\u0000.6\u0003\u0010\b\u0000/6\u0003\u0012\t\u000006\u0003\b\u0004\u0000"+
		"16\u0003\n\u0005\u000026\u0003\f\u0006\u000036\u0003\u000e\u0007\u0000"+
		"46\u0003\u0014\n\u00005.\u0001\u0000\u0000\u00005/\u0001\u0000\u0000\u0000"+
		"50\u0001\u0000\u0000\u000051\u0001\u0000\u0000\u000052\u0001\u0000\u0000"+
		"\u000053\u0001\u0000\u0000\u000054\u0001\u0000\u0000\u00006\u0007\u0001"+
		"\u0000\u0000\u000078\u0005\u0001\u0000\u000089\u0003\u0016\u000b\u0000"+
		"9:\u0005\u0002\u0000\u0000:\t\u0001\u0000\u0000\u0000;<\u0003\b\u0004"+
		"\u0000<=\u0003\u001c\u000e\u0000=\u000b\u0001\u0000\u0000\u0000>?\u0005"+
		"\u0006\u0000\u0000?@\u0003\u0016\u000b\u0000@A\u0005\u0006\u0000\u0000"+
		"A\r\u0001\u0000\u0000\u0000BF\u0005\b\u0000\u0000CD\u0003\u0018\f\u0000"+
		"DE\u0005\f\u0000\u0000EG\u0001\u0000\u0000\u0000FC\u0001\u0000\u0000\u0000"+
		"GH\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000"+
		"\u0000IJ\u0001\u0000\u0000\u0000JK\u0003\u0018\f\u0000KL\u0005\b\u0000"+
		"\u0000L\u000f\u0001\u0000\u0000\u0000MN\u0003\u001e\u000f\u0000N\u0011"+
		"\u0001\u0000\u0000\u0000OP\u0003\u0010\b\u0000PQ\u0003\u001c\u000e\u0000"+
		"Q\u0013\u0001\u0000\u0000\u0000RS\u0005\u0015\u0000\u0000S\u0015\u0001"+
		"\u0000\u0000\u0000TU\u0003\u001e\u000f\u0000UV\u0005\f\u0000\u0000VX\u0001"+
		"\u0000\u0000\u0000WT\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000"+
		"YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000"+
		"\u0000[\\\u0003\u001e\u000f\u0000\\\u0017\u0001\u0000\u0000\u0000]^\u0003"+
		"\u001e\u000f\u0000^_\u0005\t\u0000\u0000_`\u0003\u001a\r\u0000`a\u0005"+
		"\t\u0000\u0000a\u0019\u0001\u0000\u0000\u0000bc\u0005\u0014\u0000\u0000"+
		"c\u001b\u0001\u0000\u0000\u0000de\u0005\u0007\u0000\u0000ef\u0003\u0014"+
		"\n\u0000fg\u0005\u0007\u0000\u0000g\u001d\u0001\u0000\u0000\u0000hi\u0005"+
		"\u0005\u0000\u0000ij\u0005\u0003\u0000\u0000jk\u0003\u0014\n\u0000kl\u0005"+
		"\u0004\u0000\u0000l\u001f\u0001\u0000\u0000\u0000\u0004#5HY";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}