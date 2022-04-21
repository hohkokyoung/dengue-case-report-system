/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

public class PrologConnection {
    private String filePath;
    
    //A constructor to assign the file path retrieved.
    public PrologConnection(String filePath) {
        this.filePath = filePath;
    }
    
    //A function to check if the connection to the prolog file succeeded or not.
    public void consult() throws Exception {
        Query connectionQuery = new Query("consult('" + this.filePath + "')");
        if (!connectionQuery.hasSolution()) 
            throw new Exception("The connection to the prolog file has failed.");
        connectionQuery.close();
    }
    
    //A function that can be called to sort the passed java data map.
    public Map<String, Integer> sort(LinkedHashMap<String, Integer> totalDengueCasesPerArea) {
        Term term = new Query(formulateQuery(totalDengueCasesPerArea))
                        .oneSolution()
                        .get("SortedDengueCasesPerArea");
        return convert(term, new LinkedHashMap<>());
    }
        
    //A function to formulate the prolog query based on the java data map passed.
    public String formulateQuery(LinkedHashMap<String, Integer> totalDengueCasesPerArea) {
        List<String> totalDengueCasesPerAreaList = new ArrayList<>();
        totalDengueCasesPerArea
                .entrySet()
                .stream()
                .forEach(dengueCasesPerArea -> totalDengueCasesPerAreaList
                    .add("('" 
                            + dengueCasesPerArea.getKey() 
                            + "'," 
                            + dengueCasesPerArea.getValue() 
                            + ")"
                    )
                );
        return "quickSort(" 
                + totalDengueCasesPerAreaList.toString() 
                + ", SortedDengueCasesPerArea).";
    }
    
    //A function to convert the retrieved data from prolog to java data map.
    public Map<String, Integer> convert(Term term, Map<String, Integer> unformatedDengueCasesPerArea) {
        Map<String, Integer> formattedDengueCasesPerAreaMap = 
                new LinkedHashMap<>(unformatedDengueCasesPerArea);
        formattedDengueCasesPerAreaMap
            .put(term.arg(1).arg(1).toString().replace("'", ""), 
                term.arg(1).arg(2).intValue());
        
        return ((term.arg(2).arity() != 0) 
                ? convert(term.arg(2), formattedDengueCasesPerAreaMap) 
                : formattedDengueCasesPerAreaMap);
    }
    
}
