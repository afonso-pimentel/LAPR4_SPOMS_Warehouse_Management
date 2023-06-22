// Generated from D:/LAPR4_REPO/projects/eapli.base/base.core/src/main/java/eapli/base/surveysmanagement/grammar\Survey.g4 by ANTLR 4.10.1
package eapli.base.surveysmanagement.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SurveyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INPUT_LABEL=1, OBLIGATORINESS=2, CONDITION=3, SCALE_VALUE_HEADER=4, SCALE_VALUE=5, 
		SUCCESS_MESSAGE=6, TIME_UNIT=7, INPUT_ANSWER=8, POSITIVE_NUMERIC=9, ZERO=10, 
		NUMERIC=11, WORD=12, NAME=13, UPPERCASE_WORD=14, DOT=15, DOT_COMMA=16, 
		COMMA=17, NEWLINE=18, WS=19, APOSTROPHE=20, SPECIAL=21, HIFEN=22, SURVEY_CODE=23, 
		TITLE=24, GREETING_MESSAGE=25, OBJECTIVE=26, DURATION_MESSAGE=27, THANK_YOU_MESSAGE=28, 
		QUESTION_TEXT=29, OPTION=30, FREE_TEXT=31;
	public static final int
		RULE_survey = 0, RULE_welcome_Message = 1, RULE_final_Message = 2, RULE_section = 3, 
		RULE_section_ID = 4, RULE_section_Title = 5, RULE_category = 6, RULE_question = 7, 
		RULE_question_ID = 8, RULE_instruction = 9, RULE_why = 10, RULE_extra_Info = 11, 
		RULE_answer = 12, RULE_single_Choice = 13, RULE_single_Choice_With_Input = 14, 
		RULE_multiple_Choice = 15, RULE_multiple_Choice_With_Input = 16, RULE_sorting_Options = 17, 
		RULE_scaling_Options = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"survey", "welcome_Message", "final_Message", "section", "section_ID", 
			"section_Title", "category", "question", "question_ID", "instruction", 
			"why", "extra_Info", "answer", "single_Choice", "single_Choice_With_Input", 
			"multiple_Choice", "multiple_Choice_With_Input", "sorting_Options", "scaling_Options"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Answer:'", null, null, "'Strongly Agree | Agree | Neutral | Disagree | Strongly Disagree'", 
			null, "'You have successfully completed the survey.'", null, null, null, 
			"'0'", null, null, null, null, "'.'", "';'", "','", "'\\n'", null, "'''", 
			null, "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INPUT_LABEL", "OBLIGATORINESS", "CONDITION", "SCALE_VALUE_HEADER", 
			"SCALE_VALUE", "SUCCESS_MESSAGE", "TIME_UNIT", "INPUT_ANSWER", "POSITIVE_NUMERIC", 
			"ZERO", "NUMERIC", "WORD", "NAME", "UPPERCASE_WORD", "DOT", "DOT_COMMA", 
			"COMMA", "NEWLINE", "WS", "APOSTROPHE", "SPECIAL", "HIFEN", "SURVEY_CODE", 
			"TITLE", "GREETING_MESSAGE", "OBJECTIVE", "DURATION_MESSAGE", "THANK_YOU_MESSAGE", 
			"QUESTION_TEXT", "OPTION", "FREE_TEXT"
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
	public String getGrammarFileName() { return "Survey.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SurveyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class SurveyContext extends ParserRuleContext {
		public TerminalNode SURVEY_CODE() { return getToken(SurveyParser.SURVEY_CODE, 0); }
		public TerminalNode TITLE() { return getToken(SurveyParser.TITLE, 0); }
		public Final_MessageContext final_Message() {
			return getRuleContext(Final_MessageContext.class,0);
		}
		public Welcome_MessageContext welcome_Message() {
			return getRuleContext(Welcome_MessageContext.class,0);
		}
		public List<SectionContext> section() {
			return getRuleContexts(SectionContext.class);
		}
		public SectionContext section(int i) {
			return getRuleContext(SectionContext.class,i);
		}
		public SurveyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_survey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSurvey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSurvey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSurvey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SurveyContext survey() throws RecognitionException {
		SurveyContext _localctx = new SurveyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_survey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(38);
			match(SURVEY_CODE);
			}
			{
			setState(39);
			match(TITLE);
			}
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GREETING_MESSAGE) {
				{
				setState(40);
				welcome_Message();
				}
			}

			setState(44); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(43);
				section();
				}
				}
				setState(46); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==POSITIVE_NUMERIC );
			{
			setState(48);
			final_Message();
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

	public static class Welcome_MessageContext extends ParserRuleContext {
		public TerminalNode GREETING_MESSAGE() { return getToken(SurveyParser.GREETING_MESSAGE, 0); }
		public TerminalNode OBJECTIVE() { return getToken(SurveyParser.OBJECTIVE, 0); }
		public TerminalNode DURATION_MESSAGE() { return getToken(SurveyParser.DURATION_MESSAGE, 0); }
		public TerminalNode THANK_YOU_MESSAGE() { return getToken(SurveyParser.THANK_YOU_MESSAGE, 0); }
		public Welcome_MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_welcome_Message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterWelcome_Message(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitWelcome_Message(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitWelcome_Message(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Welcome_MessageContext welcome_Message() throws RecognitionException {
		Welcome_MessageContext _localctx = new Welcome_MessageContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_welcome_Message);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(GREETING_MESSAGE);
			setState(51);
			match(OBJECTIVE);
			setState(52);
			match(DURATION_MESSAGE);
			setState(53);
			match(THANK_YOU_MESSAGE);
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

	public static class Final_MessageContext extends ParserRuleContext {
		public TerminalNode SUCCESS_MESSAGE() { return getToken(SurveyParser.SUCCESS_MESSAGE, 0); }
		public TerminalNode THANK_YOU_MESSAGE() { return getToken(SurveyParser.THANK_YOU_MESSAGE, 0); }
		public TerminalNode EOF() { return getToken(SurveyParser.EOF, 0); }
		public Final_MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_final_Message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterFinal_Message(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitFinal_Message(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitFinal_Message(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Final_MessageContext final_Message() throws RecognitionException {
		Final_MessageContext _localctx = new Final_MessageContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_final_Message);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(SUCCESS_MESSAGE);
			setState(56);
			match(THANK_YOU_MESSAGE);
			setState(57);
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

	public static class SectionContext extends ParserRuleContext {
		public Section_IDContext section_ID() {
			return getRuleContext(Section_IDContext.class,0);
		}
		public Section_TitleContext section_Title() {
			return getRuleContext(Section_TitleContext.class,0);
		}
		public TerminalNode OBLIGATORINESS() { return getToken(SurveyParser.OBLIGATORINESS, 0); }
		public TerminalNode OBJECTIVE() { return getToken(SurveyParser.OBJECTIVE, 0); }
		public TerminalNode CONDITION() { return getToken(SurveyParser.CONDITION, 0); }
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_section);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(59);
			section_ID();
			}
			{
			setState(60);
			section_Title();
			}
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OBJECTIVE) {
				{
				setState(61);
				match(OBJECTIVE);
				}
			}

			{
			setState(64);
			match(OBLIGATORINESS);
			}
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONDITION) {
				{
				setState(65);
				match(CONDITION);
				}
			}

			{
			setState(69); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(68);
					question();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(71); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class Section_IDContext extends ParserRuleContext {
		public TerminalNode POSITIVE_NUMERIC() { return getToken(SurveyParser.POSITIVE_NUMERIC, 0); }
		public Section_IDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section_ID; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSection_ID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSection_ID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSection_ID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Section_IDContext section_ID() throws RecognitionException {
		Section_IDContext _localctx = new Section_IDContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_section_ID);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
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

	public static class Section_TitleContext extends ParserRuleContext {
		public CategoryContext category() {
			return getRuleContext(CategoryContext.class,0);
		}
		public Section_TitleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section_Title; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSection_Title(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSection_Title(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSection_Title(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Section_TitleContext section_Title() throws RecognitionException {
		Section_TitleContext _localctx = new Section_TitleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_section_Title);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			category();
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

	public static class CategoryContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(SurveyParser.NAME, 0); }
		public TerminalNode FREE_TEXT() { return getToken(SurveyParser.FREE_TEXT, 0); }
		public CategoryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterCategory(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitCategory(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitCategory(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CategoryContext category() throws RecognitionException {
		CategoryContext _localctx = new CategoryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_category);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			_la = _input.LA(1);
			if ( !(_la==NAME || _la==FREE_TEXT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class QuestionContext extends ParserRuleContext {
		public Question_IDContext question_ID() {
			return getRuleContext(Question_IDContext.class,0);
		}
		public TerminalNode QUESTION_TEXT() { return getToken(SurveyParser.QUESTION_TEXT, 0); }
		public TerminalNode OBLIGATORINESS() { return getToken(SurveyParser.OBLIGATORINESS, 0); }
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public TerminalNode SCALE_VALUE_HEADER() { return getToken(SurveyParser.SCALE_VALUE_HEADER, 0); }
		public List<TerminalNode> OPTION() { return getTokens(SurveyParser.OPTION); }
		public TerminalNode OPTION(int i) {
			return getToken(SurveyParser.OPTION, i);
		}
		public WhyContext why() {
			return getRuleContext(WhyContext.class,0);
		}
		public TerminalNode CONDITION() { return getToken(SurveyParser.CONDITION, 0); }
		public Extra_InfoContext extra_Info() {
			return getRuleContext(Extra_InfoContext.class,0);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_question);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(79);
			question_ID();
			}
			{
			setState(80);
			match(QUESTION_TEXT);
			}
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FREE_TEXT) {
				{
				setState(81);
				instruction();
				}
			}

			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SCALE_VALUE_HEADER) {
				{
				setState(84);
				match(SCALE_VALUE_HEADER);
				}
			}

			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OPTION) {
				{
				{
				setState(87);
				match(OPTION);
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QUESTION_TEXT) {
				{
				setState(93);
				why();
				}
			}

			{
			setState(96);
			match(OBLIGATORINESS);
			}
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONDITION) {
				{
				setState(97);
				match(CONDITION);
				}
			}

			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FREE_TEXT) {
				{
				setState(100);
				extra_Info();
				}
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
		public TerminalNode POSITIVE_NUMERIC() { return getToken(SurveyParser.POSITIVE_NUMERIC, 0); }
		public Question_IDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question_ID; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterQuestion_ID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitQuestion_ID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitQuestion_ID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question_IDContext question_ID() throws RecognitionException {
		Question_IDContext _localctx = new Question_IDContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_question_ID);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
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

	public static class InstructionContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(SurveyParser.FREE_TEXT, 0); }
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
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

	public static class WhyContext extends ParserRuleContext {
		public TerminalNode QUESTION_TEXT() { return getToken(SurveyParser.QUESTION_TEXT, 0); }
		public WhyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_why; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterWhy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitWhy(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitWhy(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhyContext why() throws RecognitionException {
		WhyContext _localctx = new WhyContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_why);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(QUESTION_TEXT);
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

	public static class Extra_InfoContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(SurveyParser.FREE_TEXT, 0); }
		public Extra_InfoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extra_Info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterExtra_Info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitExtra_Info(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitExtra_Info(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Extra_InfoContext extra_Info() throws RecognitionException {
		Extra_InfoContext _localctx = new Extra_InfoContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_extra_Info);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
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

	public static class AnswerContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(SurveyParser.FREE_TEXT, 0); }
		public TerminalNode ZERO() { return getToken(SurveyParser.ZERO, 0); }
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
		public AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterAnswer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitAnswer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitAnswer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswerContext answer() throws RecognitionException {
		AnswerContext _localctx = new AnswerContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_answer);
		try {
			setState(119);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(111);
				match(FREE_TEXT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(112);
				match(ZERO);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(113);
				single_Choice();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(114);
				single_Choice_With_Input();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(115);
				multiple_Choice();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(116);
				multiple_Choice_With_Input();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(117);
				sorting_Options();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(118);
				scaling_Options();
				}
				break;
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
		public TerminalNode OPTION() { return getToken(SurveyParser.OPTION, 0); }
		public Single_ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_Choice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSingle_Choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSingle_Choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSingle_Choice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Single_ChoiceContext single_Choice() throws RecognitionException {
		Single_ChoiceContext _localctx = new Single_ChoiceContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_single_Choice);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(OPTION);
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
		public TerminalNode INPUT_ANSWER() { return getToken(SurveyParser.INPUT_ANSWER, 0); }
		public Single_Choice_With_InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_Choice_With_Input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSingle_Choice_With_Input(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSingle_Choice_With_Input(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSingle_Choice_With_Input(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Single_Choice_With_InputContext single_Choice_With_Input() throws RecognitionException {
		Single_Choice_With_InputContext _localctx = new Single_Choice_With_InputContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_single_Choice_With_Input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(123);
			single_Choice();
			setState(124);
			match(INPUT_ANSWER);
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
		public List<TerminalNode> OPTION() { return getTokens(SurveyParser.OPTION); }
		public TerminalNode OPTION(int i) {
			return getToken(SurveyParser.OPTION, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(SurveyParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SurveyParser.COMMA, i);
		}
		public Multiple_ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_Choice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterMultiple_Choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitMultiple_Choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitMultiple_Choice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_ChoiceContext multiple_Choice() throws RecognitionException {
		Multiple_ChoiceContext _localctx = new Multiple_ChoiceContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_multiple_Choice);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(128); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(126);
					match(OPTION);
					setState(127);
					match(COMMA);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(130); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			{
			setState(132);
			match(OPTION);
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

	public static class Multiple_Choice_With_InputContext extends ParserRuleContext {
		public Multiple_ChoiceContext multiple_Choice() {
			return getRuleContext(Multiple_ChoiceContext.class,0);
		}
		public TerminalNode INPUT_ANSWER() { return getToken(SurveyParser.INPUT_ANSWER, 0); }
		public Multiple_Choice_With_InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_Choice_With_Input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterMultiple_Choice_With_Input(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitMultiple_Choice_With_Input(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitMultiple_Choice_With_Input(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_Choice_With_InputContext multiple_Choice_With_Input() throws RecognitionException {
		Multiple_Choice_With_InputContext _localctx = new Multiple_Choice_With_InputContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_multiple_Choice_With_Input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(134);
			multiple_Choice();
			setState(135);
			match(INPUT_ANSWER);
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
		public Multiple_ChoiceContext multiple_Choice() {
			return getRuleContext(Multiple_ChoiceContext.class,0);
		}
		public Sorting_OptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sorting_Options; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterSorting_Options(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitSorting_Options(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitSorting_Options(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sorting_OptionsContext sorting_Options() throws RecognitionException {
		Sorting_OptionsContext _localctx = new Sorting_OptionsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_sorting_Options);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			multiple_Choice();
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
		public TerminalNode SCALE_VALUE() { return getToken(SurveyParser.SCALE_VALUE, 0); }
		public TerminalNode WS() { return getToken(SurveyParser.WS, 0); }
		public TerminalNode OPTION() { return getToken(SurveyParser.OPTION, 0); }
		public List<Scaling_OptionsContext> scaling_Options() {
			return getRuleContexts(Scaling_OptionsContext.class);
		}
		public Scaling_OptionsContext scaling_Options(int i) {
			return getRuleContext(Scaling_OptionsContext.class,i);
		}
		public Scaling_OptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scaling_Options; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).enterScaling_Options(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SurveyListener ) ((SurveyListener)listener).exitScaling_Options(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SurveyVisitor ) return ((SurveyVisitor<? extends T>)visitor).visitScaling_Options(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Scaling_OptionsContext scaling_Options() throws RecognitionException {
		Scaling_OptionsContext _localctx = new Scaling_OptionsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_scaling_Options);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(139);
			match(SCALE_VALUE);
			setState(140);
			match(WS);
			setState(141);
			match(OPTION);
			}
			setState(144); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(143);
					scaling_Options();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(146); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
		"\u0004\u0001\u001f\u0095\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000*\b\u0000\u0001"+
		"\u0000\u0004\u0000-\b\u0000\u000b\u0000\f\u0000.\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003?\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003C\b\u0003\u0001"+
		"\u0003\u0004\u0003F\b\u0003\u000b\u0003\f\u0003G\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007S\b\u0007\u0001\u0007\u0003\u0007V\b\u0007\u0001"+
		"\u0007\u0005\u0007Y\b\u0007\n\u0007\f\u0007\\\t\u0007\u0001\u0007\u0003"+
		"\u0007_\b\u0007\u0001\u0007\u0001\u0007\u0003\u0007c\b\u0007\u0001\u0007"+
		"\u0003\u0007f\b\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0003\fx\b\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0004\u000f\u0081\b\u000f\u000b\u000f"+
		"\f\u000f\u0082\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0004\u0012\u0091\b\u0012\u000b\u0012\f\u0012\u0092\u0001"+
		"\u0012\u0000\u0000\u0013\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$\u0000\u0001\u0002\u0000\r\r\u001f"+
		"\u001f\u0095\u0000&\u0001\u0000\u0000\u0000\u00022\u0001\u0000\u0000\u0000"+
		"\u00047\u0001\u0000\u0000\u0000\u0006;\u0001\u0000\u0000\u0000\bI\u0001"+
		"\u0000\u0000\u0000\nK\u0001\u0000\u0000\u0000\fM\u0001\u0000\u0000\u0000"+
		"\u000eO\u0001\u0000\u0000\u0000\u0010g\u0001\u0000\u0000\u0000\u0012i"+
		"\u0001\u0000\u0000\u0000\u0014k\u0001\u0000\u0000\u0000\u0016m\u0001\u0000"+
		"\u0000\u0000\u0018w\u0001\u0000\u0000\u0000\u001ay\u0001\u0000\u0000\u0000"+
		"\u001c{\u0001\u0000\u0000\u0000\u001e\u0080\u0001\u0000\u0000\u0000 \u0086"+
		"\u0001\u0000\u0000\u0000\"\u0089\u0001\u0000\u0000\u0000$\u008b\u0001"+
		"\u0000\u0000\u0000&\'\u0005\u0017\u0000\u0000\')\u0005\u0018\u0000\u0000"+
		"(*\u0003\u0002\u0001\u0000)(\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000"+
		"\u0000*,\u0001\u0000\u0000\u0000+-\u0003\u0006\u0003\u0000,+\u0001\u0000"+
		"\u0000\u0000-.\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001"+
		"\u0000\u0000\u0000/0\u0001\u0000\u0000\u000001\u0003\u0004\u0002\u0000"+
		"1\u0001\u0001\u0000\u0000\u000023\u0005\u0019\u0000\u000034\u0005\u001a"+
		"\u0000\u000045\u0005\u001b\u0000\u000056\u0005\u001c\u0000\u00006\u0003"+
		"\u0001\u0000\u0000\u000078\u0005\u0006\u0000\u000089\u0005\u001c\u0000"+
		"\u00009:\u0005\u0000\u0000\u0001:\u0005\u0001\u0000\u0000\u0000;<\u0003"+
		"\b\u0004\u0000<>\u0003\n\u0005\u0000=?\u0005\u001a\u0000\u0000>=\u0001"+
		"\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000"+
		"@B\u0005\u0002\u0000\u0000AC\u0005\u0003\u0000\u0000BA\u0001\u0000\u0000"+
		"\u0000BC\u0001\u0000\u0000\u0000CE\u0001\u0000\u0000\u0000DF\u0003\u000e"+
		"\u0007\u0000ED\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000\u0000GE\u0001"+
		"\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000H\u0007\u0001\u0000\u0000"+
		"\u0000IJ\u0005\t\u0000\u0000J\t\u0001\u0000\u0000\u0000KL\u0003\f\u0006"+
		"\u0000L\u000b\u0001\u0000\u0000\u0000MN\u0007\u0000\u0000\u0000N\r\u0001"+
		"\u0000\u0000\u0000OP\u0003\u0010\b\u0000PR\u0005\u001d\u0000\u0000QS\u0003"+
		"\u0012\t\u0000RQ\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000SU\u0001"+
		"\u0000\u0000\u0000TV\u0005\u0004\u0000\u0000UT\u0001\u0000\u0000\u0000"+
		"UV\u0001\u0000\u0000\u0000VZ\u0001\u0000\u0000\u0000WY\u0005\u001e\u0000"+
		"\u0000XW\u0001\u0000\u0000\u0000Y\\\u0001\u0000\u0000\u0000ZX\u0001\u0000"+
		"\u0000\u0000Z[\u0001\u0000\u0000\u0000[^\u0001\u0000\u0000\u0000\\Z\u0001"+
		"\u0000\u0000\u0000]_\u0003\u0014\n\u0000^]\u0001\u0000\u0000\u0000^_\u0001"+
		"\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`b\u0005\u0002\u0000\u0000"+
		"ac\u0005\u0003\u0000\u0000ba\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000"+
		"\u0000ce\u0001\u0000\u0000\u0000df\u0003\u0016\u000b\u0000ed\u0001\u0000"+
		"\u0000\u0000ef\u0001\u0000\u0000\u0000f\u000f\u0001\u0000\u0000\u0000"+
		"gh\u0005\t\u0000\u0000h\u0011\u0001\u0000\u0000\u0000ij\u0005\u001f\u0000"+
		"\u0000j\u0013\u0001\u0000\u0000\u0000kl\u0005\u001d\u0000\u0000l\u0015"+
		"\u0001\u0000\u0000\u0000mn\u0005\u001f\u0000\u0000n\u0017\u0001\u0000"+
		"\u0000\u0000ox\u0005\u001f\u0000\u0000px\u0005\n\u0000\u0000qx\u0003\u001a"+
		"\r\u0000rx\u0003\u001c\u000e\u0000sx\u0003\u001e\u000f\u0000tx\u0003 "+
		"\u0010\u0000ux\u0003\"\u0011\u0000vx\u0003$\u0012\u0000wo\u0001\u0000"+
		"\u0000\u0000wp\u0001\u0000\u0000\u0000wq\u0001\u0000\u0000\u0000wr\u0001"+
		"\u0000\u0000\u0000ws\u0001\u0000\u0000\u0000wt\u0001\u0000\u0000\u0000"+
		"wu\u0001\u0000\u0000\u0000wv\u0001\u0000\u0000\u0000x\u0019\u0001\u0000"+
		"\u0000\u0000yz\u0005\u001e\u0000\u0000z\u001b\u0001\u0000\u0000\u0000"+
		"{|\u0003\u001a\r\u0000|}\u0005\b\u0000\u0000}\u001d\u0001\u0000\u0000"+
		"\u0000~\u007f\u0005\u001e\u0000\u0000\u007f\u0081\u0005\u0011\u0000\u0000"+
		"\u0080~\u0001\u0000\u0000\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082"+
		"\u0080\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0001\u0000\u0000\u0000\u0084\u0085\u0005\u001e\u0000\u0000\u0085"+
		"\u001f\u0001\u0000\u0000\u0000\u0086\u0087\u0003\u001e\u000f\u0000\u0087"+
		"\u0088\u0005\b\u0000\u0000\u0088!\u0001\u0000\u0000\u0000\u0089\u008a"+
		"\u0003\u001e\u000f\u0000\u008a#\u0001\u0000\u0000\u0000\u008b\u008c\u0005"+
		"\u0005\u0000\u0000\u008c\u008d\u0005\u0013\u0000\u0000\u008d\u008e\u0005"+
		"\u001e\u0000\u0000\u008e\u0090\u0001\u0000\u0000\u0000\u008f\u0091\u0003"+
		"$\u0012\u0000\u0090\u008f\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000"+
		"\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000"+
		"\u0000\u0000\u0093%\u0001\u0000\u0000\u0000\u000e).>BGRUZ^bew\u0082\u0092";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}