/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import classes.DengueCase;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

 public class CloneList {
     
     //A function specialised in cloning a list of DengueCase object,
     //so as to prevent side effect.
    static Function <List<DengueCase>, List<DengueCase>> cloneList = list -> {
        List<DengueCase> clonedList = new ArrayList<>();
        list.stream().forEach(element -> clonedList.add((DengueCase) element.clone()));
        return clonedList;
    };
}
