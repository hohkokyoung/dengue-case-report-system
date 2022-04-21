/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import classes.DengueCase;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Calculation {
    
    //A currying function to summate the dengue cases for each year of all the districts.
    Function<Integer, Function<List<DengueCase>, Integer>> dengueCasesSummation = 
            year -> 
            dengueCasesData -> 
            dengueCasesData
                .stream()
                .mapToInt(dengueCase -> 
                    dengueCase
                        .getDengueCasePerYear()
                        .get(year))
                    .sum();
   
    //A function to calculate the total dengue cases of all the districts per year.
    public List<DengueCase> getTotalCasesPerYear(List<DengueCase> dengueCasesData) {
        //Avoid side effect.
        List<DengueCase> totalDengueCasesByYear = CloneList.cloneList.apply(dengueCasesData);
        Map<Integer, Integer> dengueCasesByYear = new TreeMap<>();
        List<Integer> years = Arrays.asList(2014, 2015, 2016, 2017, 2018, 2019);
        years.stream().forEach(year -> dengueCasesByYear
                .put(year, dengueCasesSummation.apply(year).apply(dengueCasesData)));
        totalDengueCasesByYear.add(new DengueCase("Pahang", (TreeMap) dengueCasesByYear));
        return totalDengueCasesByYear;
    }
    
    //A recursive function to calculate the dengue case per area.
    BiFunction<Map<Integer, Integer>, Integer, Integer> calculateCasePerAreaRecursively 
                = (dengueCasePerYear, year) -> 
                    year <= (int) dengueCasePerYear
                            .keySet()
                            .toArray()
                            [dengueCasePerYear.size() - 1] 
                        ? this.calculateCasePerAreaRecursively
                            .apply(dengueCasePerYear, year + 1) 
                            + dengueCasePerYear.get(year) 
                        : 0;
    
    //A function to calculate the total dengue cases per district throughout the years.
    public LinkedHashMap<String, Integer> getTotalCasesPerArea(List<DengueCase> dengueCasesData) {
        LinkedHashMap<String, Integer> totalDengueCasesPerArea = new LinkedHashMap<>();
        dengueCasesData
                .stream()
                .forEach((dengueCaseData) -> totalDengueCasesPerArea
                .put(dengueCaseData.getDistrictName(), calculateCasePerAreaRecursively
                    .apply(dengueCaseData.getDengueCasePerYear(), 
                        (int) dengueCaseData.getDengueCasePerYear().keySet().toArray()[0])));
        return totalDengueCasesPerArea;
    }
    
    //A function to identify the district with lowest overall dengue cases.
    public Optional<Entry<String, Integer>> getLowestCasesArea
        (LinkedHashMap<String, Integer> totalDengueCasesPerArea) {
        return totalDengueCasesPerArea
                .entrySet()
                .stream()
                .min((firstElement, secondElement) -> 
                    firstElement.getValue() < secondElement.getValue() 
                        ? -1
                        : 1
                );
    }
    
    //A function to identify the district with highest overall dengue cases.
    public Map<String, Integer> getHighestCasesArea
        (LinkedHashMap<String, Integer> totalDengueCasesPerArea) {
        return totalDengueCasesPerArea
                .entrySet()
                .stream()
                .max((firstElement, secondElement) -> 
                        firstElement.getValue() > secondElement.getValue() 
                            ? 1 
                            : -1
                )
                .stream()
                .collect(Collectors.toMap(
                        element -> element.getKey(), 
                        element -> element.getValue()
                ));
    }

}
