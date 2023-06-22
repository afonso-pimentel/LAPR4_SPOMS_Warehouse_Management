/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eapli.base.surveysmanagement.application;

import eapli.base.surveysmanagement.answergrammar.AnswerGrammarLexer;
import eapli.base.surveysmanagement.answergrammar.AnswerGrammarParser;
import eapli.base.surveysmanagement.domain.ReportStats;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for ReportService
 */
public class ReportServiceImpl implements ReportService {

    /**
     * @inheritDoc
     */
    @Override
    public ReportStats analyzeAnswerFile(byte[] answerFile, ReportStats reportEntry) throws IOException {
        InputStream answerFileStream = new ByteArrayInputStream(answerFile);
        CharStream in = CharStreams.fromStream(answerFileStream);

        AnswerGrammarLexer lexer = new AnswerGrammarLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        AnswerGrammarParser parser = new AnswerGrammarParser(tokens);
        AnswerGrammarParser.AnswersContext answersContext = parser.answers();

        SingleChoiceVisitor singleChoiceVisitor = new SingleChoiceVisitor();
        MultipleChoiceVisitor multipleChoiceVisitor = new MultipleChoiceVisitor();
        SortingChoiceVisitor sortingChoiceVisitor = new SortingChoiceVisitor();

        int i = 0;

        for (AnswerGrammarParser.Answer_StructContext ctx : answersContext.answer_Struct()){
            Integer questionID = Integer.parseInt(ctx.question_ID().getText());
            AnswerGrammarParser.AnswerContext actx = ctx.answer();

            try{
                Integer choice = singleChoiceVisitor.visitSingle_Choice(actx.single_Choice()); // returns the answered option
                reportEntry.addEntryToSingleChoiceMap(questionID,choice);
            }catch (Exception e1) {}

            try{
                Integer choice = singleChoiceVisitor.visitSingle_Choice_With_Input(actx.single_Choice_With_Input()); // returns the answered option
                reportEntry.addEntryToSingleChoiceMap(questionID,choice);
            }catch (Exception e2) {}

            try{
                List<Integer> optionList = multipleChoiceVisitor.visitMultiple_Choice(actx.multiple_Choice()); // returns the list of answered options
                reportEntry.addEntryToMultipleChoiceMap(questionID,optionList);
            }catch (Exception e3) {}

            try{
                List<Integer> optionList = multipleChoiceVisitor.visitMultiple_Choice_With_Input(actx.multiple_Choice_With_Input()); // returns the list of answered options
                reportEntry.addEntryToMultipleChoiceMap(questionID,optionList);
            }catch (Exception e4) {}

            try{
                String sortCombo = sortingChoiceVisitor.visitSorting_Options(actx.sorting_Options()); // returns a string with the sort combination
                reportEntry.addEntryToSortingChoiceMap(questionID,sortCombo);
            }catch (Exception e5) {}

            i++;
        }
        return reportEntry;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String reportGenerator(String surveyID, ReportStats entry) {
        StringBuilder reportStringBuilder = new StringBuilder();

        reportStringBuilder.append("Report for Survey:");
        reportStringBuilder.append(surveyID);
        reportStringBuilder.append("\n\nQuestions:\n\n");

        //Compute the single choice answers stats
        if (!entry.singleChoiceMap().isEmpty()) {
            reportStringBuilder.append("Single-Choice Questions.\n");
            Map<Integer, List<Integer>> singleChoiceMap = entry.singleChoiceMap();
            reportStringBuilder.append(choicesPercentage(singleChoiceMap));
        }

        //Compute the multiple choice answers stats
        if (!entry.multipleChoiceMap().isEmpty() && !entry.multipleChoiceComboMap().isEmpty()) {
            reportStringBuilder.append("Multiple-Choice Questions.\n");
            reportStringBuilder.append("Multiple-Choice per choice percentage.\n");

            Map<Integer, List<Integer>> multipleChoiceMap = entry.multipleChoiceMap();
            Map<Integer, List<String>> multipleChoiceComboMap = entry.multipleChoiceComboMap();

            reportStringBuilder.append(choicesPercentage(multipleChoiceMap));
            reportStringBuilder.append("Multiple-Choice combo percentage.\n");
            reportStringBuilder.append(comboPercentage(multipleChoiceComboMap));
        }

        //Compute the sorting choice answers stats
        if (!entry.sortingChoiceComboMap().isEmpty()) {
            reportStringBuilder.append("Sorting-Choice Questions.\n");

            Map<Integer, List<String>> sortingChoiceComboMap = entry.sortingChoiceComboMap();

            reportStringBuilder.append(comboPercentage(sortingChoiceComboMap));
        }

        return reportStringBuilder.toString();
    }

    /**
     * This is a useful method to help build the main report string.
     * This one focuses only on answers of single choice and multiple choice.
     * For the multiple choice it is useful to know the percentage per
     * option that was selected.
     *
     * @param choicesMap
     * @return
     */
    private String choicesPercentage(Map<Integer, List<Integer>> choicesMap){
        DecimalFormat df = new DecimalFormat("#");
        StringBuilder reportStringBuilder = new StringBuilder();

        for (Map.Entry<Integer, List<Integer>> mapEntry : choicesMap.entrySet()) {
            Integer questionID = mapEntry.getKey();
            reportStringBuilder.append(questionID+":\n");

            List<Integer> choiceTotals = mapEntry.getValue();
            Double totalsSum = Double.valueOf(choiceTotals.stream().reduce(Integer::sum).get());

            for (int i = 1; i < choiceTotals.size(); i++) {
                Double currentChoiceTotal = Double.valueOf(choiceTotals.get(i));
                Double percentage = (currentChoiceTotal/totalsSum)*100.0;

                if(percentage>0){
                    reportStringBuilder.append(i+"-"+df.format(percentage)+"%\n");
                }
            }
        }
        return reportStringBuilder.toString();
    }

    /**
     * This method is useful to generate a string with the percentages
     * per combination of options.
     * For the multiple choices it is useful due to the fact that it holds
     * the combination and the percentage that combination was selected overall.
     *
     * @param comboMap
     * @return
     */
    private String comboPercentage(Map<Integer, List<String>> comboMap){
        DecimalFormat df = new DecimalFormat("#");
        StringBuilder reportStringBuilder = new StringBuilder();

        for (Map.Entry<Integer,List<String>> mapEntry: comboMap.entrySet()) {
            Integer questionID = mapEntry.getKey();
            reportStringBuilder.append(questionID+":\n");

            List<String> comboList = mapEntry.getValue();
            Map<String,Integer> frequencyMap = new HashMap<>();
            Double frequencySum = Double.valueOf(0);

            for(String s : comboList){
                Integer count = frequencyMap.get(s);
                if(count == null){
                    count = 0;
                }
                frequencyMap.put(s, count+1);
                frequencySum++;
            }

            for(Map.Entry<String,Integer> entryFrequencyMap : frequencyMap.entrySet()){
                String combo = entryFrequencyMap.getKey();
                Double frequency = Double.valueOf(entryFrequencyMap.getValue());
                Double percentage = (frequency/frequencySum)*100.0;

                if(percentage>0){
                    reportStringBuilder.append(combo+"-"+df.format(percentage)+"%\n");
                }
            }
        }
        return reportStringBuilder.toString();
    }
}
