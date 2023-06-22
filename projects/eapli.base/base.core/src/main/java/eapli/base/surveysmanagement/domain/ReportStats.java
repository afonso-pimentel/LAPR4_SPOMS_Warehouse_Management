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

package eapli.base.surveysmanagement.domain;

import java.util.*;

/**
 * Model for the report stats of a given survey
 */
public class ReportStats {

    private Map<Integer, List<Integer>> singleChoiceMap;

    private Map<Integer, List<Integer>> multipleChoiceMap;

    private Map<Integer, List<String>> multipleChoiceComboMap;

    private Map<Integer, List<String>> sortingChoiceComboMap;

    /**
     * Initializes a new instance of ReportStats
     */
    public ReportStats() {
        this.singleChoiceMap = new TreeMap<>();
        this.multipleChoiceMap = new TreeMap<>();
        this.multipleChoiceComboMap = new TreeMap<>();
        this.sortingChoiceComboMap = new TreeMap<>();
    }

    /**
     * Map for single choice questions
     * @return Map
     */
    public Map<Integer, List<Integer>> singleChoiceMap() {
        return singleChoiceMap;
    }

    /**
     * Map for multiple choice questions
     * @return
     */
    public Map<Integer, List<Integer>> multipleChoiceMap() { return multipleChoiceMap; }

    /**
     * Map for multiple choice questions
     * @return
     */
    public Map<Integer, List<String>> multipleChoiceComboMap() { return multipleChoiceComboMap; }

    /**
     * Map for sorting choice questions
     * @return
     */
    public Map<Integer, List<String>> sortingChoiceComboMap() {
        return sortingChoiceComboMap;
    }

    /**
     * Adds an entry to the single choice map
     * @param questionID Question ID
     * @param choice Choice
     */
    public void addEntryToSingleChoiceMap(Integer questionID, Integer choice){
        List<Integer> choiceTotals = Arrays.asList(0,0,0,0,0,0);
        Integer currentChoiceTotal = 0;
        if(singleChoiceMap.containsKey(questionID)) {
            choiceTotals = singleChoiceMap.get(questionID);
        }
        currentChoiceTotal = choiceTotals.get(choice);
        choiceTotals.set(choice,currentChoiceTotal+1);
        singleChoiceMap.put(questionID,choiceTotals);
    }

    /**
     * Adds an entry to the multiple choice map
     * @param questionID Question ID
     * @param choices Choices
     */
    public void addEntryToMultipleChoiceMap(Integer questionID, List<Integer> choices){
        List<Integer> choiceTotals = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0);
        Integer currentChoiceTotal = 0;
        if(multipleChoiceMap.containsKey(questionID)){
            choiceTotals = multipleChoiceMap.get(questionID);
        }
        String combo = "";
        for (Integer choice:choices) {
            currentChoiceTotal = choiceTotals.get(choice);
            choiceTotals.set(choice, currentChoiceTotal+1);
    //        choiceTotals.add(choice,1);
            combo += choice;
        }
        multipleChoiceMap.put(questionID,choiceTotals);
        List<String> comboList = new ArrayList<>();
        if(multipleChoiceComboMap.containsKey(questionID)){
            comboList = multipleChoiceComboMap.get(questionID);
        }
        comboList.add(combo);
        multipleChoiceComboMap.put(questionID,comboList);
    }

    /**
     * Adds an entry to the sorting choice map
     * @param questionID Question ID
     * @param sortedChoices Sorted choicees
     */
    public void addEntryToSortingChoiceMap(Integer questionID,String sortedChoices) {
        List<String> comboList = new ArrayList<>();
        if (sortingChoiceComboMap.containsKey(questionID)) {
            comboList = sortingChoiceComboMap.get(questionID);
        }
        comboList.add(sortedChoices);
        sortingChoiceComboMap.put(questionID, comboList);
    }
}
