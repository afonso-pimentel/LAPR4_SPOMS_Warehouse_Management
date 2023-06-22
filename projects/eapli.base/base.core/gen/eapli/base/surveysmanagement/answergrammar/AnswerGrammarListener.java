// Generated from D:/LAPR4_REPO/projects/eapli.base/base.core/src/main/java/eapli/base/surveysmanagement/answergrammar\AnswerGrammar.g4 by ANTLR 4.10.1
package eapli.base.surveysmanagement.answergrammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AnswerGrammarParser}.
 */
public interface AnswerGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#answers}.
	 * @param ctx the parse tree
	 */
	void enterAnswers(AnswerGrammarParser.AnswersContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#answers}.
	 * @param ctx the parse tree
	 */
	void exitAnswers(AnswerGrammarParser.AnswersContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#answer_Struct}.
	 * @param ctx the parse tree
	 */
	void enterAnswer_Struct(AnswerGrammarParser.Answer_StructContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#answer_Struct}.
	 * @param ctx the parse tree
	 */
	void exitAnswer_Struct(AnswerGrammarParser.Answer_StructContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#question_ID}.
	 * @param ctx the parse tree
	 */
	void enterQuestion_ID(AnswerGrammarParser.Question_IDContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#question_ID}.
	 * @param ctx the parse tree
	 */
	void exitQuestion_ID(AnswerGrammarParser.Question_IDContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(AnswerGrammarParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(AnswerGrammarParser.AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#multiple_Choice}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_Choice(AnswerGrammarParser.Multiple_ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#multiple_Choice}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_Choice(AnswerGrammarParser.Multiple_ChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#multiple_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_Choice_With_Input(AnswerGrammarParser.Multiple_Choice_With_InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#multiple_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_Choice_With_Input(AnswerGrammarParser.Multiple_Choice_With_InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#sorting_Options}.
	 * @param ctx the parse tree
	 */
	void enterSorting_Options(AnswerGrammarParser.Sorting_OptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#sorting_Options}.
	 * @param ctx the parse tree
	 */
	void exitSorting_Options(AnswerGrammarParser.Sorting_OptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#scaling_Options}.
	 * @param ctx the parse tree
	 */
	void enterScaling_Options(AnswerGrammarParser.Scaling_OptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#scaling_Options}.
	 * @param ctx the parse tree
	 */
	void exitScaling_Options(AnswerGrammarParser.Scaling_OptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#single_Choice}.
	 * @param ctx the parse tree
	 */
	void enterSingle_Choice(AnswerGrammarParser.Single_ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#single_Choice}.
	 * @param ctx the parse tree
	 */
	void exitSingle_Choice(AnswerGrammarParser.Single_ChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#single_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void enterSingle_Choice_With_Input(AnswerGrammarParser.Single_Choice_With_InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#single_Choice_With_Input}.
	 * @param ctx the parse tree
	 */
	void exitSingle_Choice_With_Input(AnswerGrammarParser.Single_Choice_With_InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#simple_Answer}.
	 * @param ctx the parse tree
	 */
	void enterSimple_Answer(AnswerGrammarParser.Simple_AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#simple_Answer}.
	 * @param ctx the parse tree
	 */
	void exitSimple_Answer(AnswerGrammarParser.Simple_AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#multiple_Options}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_Options(AnswerGrammarParser.Multiple_OptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#multiple_Options}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_Options(AnswerGrammarParser.Multiple_OptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#scaling_Option}.
	 * @param ctx the parse tree
	 */
	void enterScaling_Option(AnswerGrammarParser.Scaling_OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#scaling_Option}.
	 * @param ctx the parse tree
	 */
	void exitScaling_Option(AnswerGrammarParser.Scaling_OptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#scale_Answer}.
	 * @param ctx the parse tree
	 */
	void enterScale_Answer(AnswerGrammarParser.Scale_AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#scale_Answer}.
	 * @param ctx the parse tree
	 */
	void exitScale_Answer(AnswerGrammarParser.Scale_AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#input_Answer}.
	 * @param ctx the parse tree
	 */
	void enterInput_Answer(AnswerGrammarParser.Input_AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#input_Answer}.
	 * @param ctx the parse tree
	 */
	void exitInput_Answer(AnswerGrammarParser.Input_AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link AnswerGrammarParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(AnswerGrammarParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnswerGrammarParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(AnswerGrammarParser.OptionContext ctx);
}