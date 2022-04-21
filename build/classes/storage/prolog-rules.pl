quickSort([], []).
quickSort([Head|Tail], SortedList) :-
    partition(Head, Tail, GreaterValue, LesserValue),
    quickSort(GreaterValue, SortedGreaterSubList),
    quickSort(LesserValue, SortedLesserSubList),
    append(SortedGreaterSubList, [Head|SortedLesserSubList], SortedList).

partition(_, [], [], []).
partition((PivotKey, PivotValue), [(ComparedKey, ComparedValue)|Tail], [(ComparedKey, ComparedValue)|GreaterValue], LesserValue) :-
    ComparedValue > PivotValue,
    partition((PivotKey, PivotValue), Tail, GreaterValue, LesserValue).
partition((PivotKey, PivotValue), [(ComparedKey, ComparedValue)|Tail], GreaterValue, [(ComparedKey, ComparedValue)|LesserValue]) :-
    ComparedValue =< PivotValue,
    partition((PivotKey, PivotValue), Tail, GreaterValue, LesserValue).