// Generated from D:/LAPR4_REPO/projects/eapli.base/base.core/src/main/java/eapli/base/surveysmanagement/answergrammar\AnswerGrammar.g4 by ANTLR 4.10.1
package eapli.base.surveysmanagement.answergrammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AnswerGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AnswerGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#answers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswers(AnswerGrammarParser.AnswersContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#answer_Struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer_Struct(AnswerGrammarParser.Answer_StructContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#question_ID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion_ID(AnswerGrammarParser.Question_IDContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer(AnswerGrammarParser.AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#multiple_Choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_Choice(AnswerGrammarParser.Multiple_ChoiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#multiple_Choice_With_Input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_Choice_With_Input(AnswerGrammarParser.Multiple_Choice_With_InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#sorting_Options}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSorting_Options(AnswerGrammarParser.Sorting_OptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#scaling_Options}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScaling_Options(AnswerGrammarParser.Scaling_OptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#single_Choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_Choice(AnswerGrammarParser.Single_ChoiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#single_Choice_With_Input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_Choice_With_Input(AnswerGrammarParser.Single_Choice_With_InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#simple_Answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_Answer(AnswerGrammarParser.Simple_AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#multiple_Options}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_Options(AnswerGrammarParser.Multiple_OptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#scaling_Option}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScaling_Option(AnswerGrammarParser.Scaling_OptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#scale_Answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScale_Answer(AnswerGrammarParser.Scale_AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#input_Answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput_Answer(AnswerGrammarParser.Input_AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link AnswerGrammarParser#option}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOption(AnswerGrammarParser.OptionContext ctx);
}