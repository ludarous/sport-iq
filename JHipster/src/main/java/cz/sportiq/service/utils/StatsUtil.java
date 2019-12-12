package cz.sportiq.service.utils;

import cz.sportiq.domain.AthleteActivityResultSplit;
import cz.sportiq.domain.ResultValueable;
import cz.sportiq.domain.enumeration.ResultType;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatsUtil {
    public static Float median(List<Float> values) {
        if(values != null && values.size() > 0) {
            Collections.sort(values);
            int length = values.size();
            Float median;
            if (values.size() % 2 == 0)
                median = (values.get(length / 2) + values.get(length / 2 - 1)) / 2;
            else
                median = values.get(length / 2);
            return median;
        }
        return null;
    }

    public static Float average (List<Float> values) {
        if(values != null && values.size() > 0) {
            Float sum = 0F; //average will have decimal point


            for (int i = 0; i < values.size(); i++) {
                sum += values.get(i);
            }

            Float average = sum / values.size();
            return average;
        }
        return null;
    }

    public static Float min(List<Float> values) {
        if(values != null && values.size() > 0) {
            return Collections.min(values);
        }
        return null;
    }

    public static Float max(List<Float> values) {
        if(values != null && values.size() > 0) {
            return Collections.max(values);
        }
        return null;
    }

    public static Float sum(List<Float> values)
    {
        if(values != null && values.size() > 0) {
            Float sum = 0F;
            for (Float num : values) {
                sum += num;
            }
            return sum;
        }
        return 0F;
    }

    public static Integer rankAthleteActivityValue(List<ResultValueable> items, ResultValueable rankedItem, Function<ResultValueable, Float> compareFn,  ResultType resultType) {
        Comparator<ResultValueable> resultComparator = Comparator
            .comparing(compareFn, Comparator.nullsLast(Float::compareTo));
        if(ResultType.LESS_IS_BETTER.equals(resultType)) {
            Collections.sort(items, resultComparator);
        } else {
            Collections.sort(items, resultComparator.reversed());
        }
        Integer rank = items.indexOf(rankedItem);
        return rank + 1;
    }

    public static Integer medianInt(List<Integer> values) {
        if(values != null && values.size() > 0) {

            List<Integer> notNullValues = values.stream().filter(v -> v != null).collect(Collectors.toList());

            Collections.sort(notNullValues);
            int length = notNullValues.size();


            Integer median;
            if (notNullValues.size() % 2 == 0)
                median = (notNullValues.get(length / 2) + notNullValues.get(length / 2 - 1)) / 2;
            else
                median = notNullValues.get(length / 2);
            return median;
        }
        return null;
    }

    public static Float averageInt (List<Integer> values) {

        if(values != null && values.size() > 0) {
            Float sum = 0F; //average will have decimal point
            Float average = null;

            List<Integer> notNullValues = values.stream().filter(v -> v != null).collect(Collectors.toList());

            if (notNullValues != null) {
                for (int i = 0; i < notNullValues.size(); i++) {
                    if (notNullValues.get(i) != null) {
                        sum += notNullValues.get(i);
                    }
                }
                average = sum / notNullValues.size();
            }
            return average;
        }
        return null;
    }

    public static Integer minInt(List<Integer> values) {
        if(values != null && values.size() > 0) {

            return Collections.min(values.stream().filter(v -> v != null).collect(Collectors.toList()));
        }
        return null;
    }

    public static Integer maxInt(List<Integer> values) {
        if(values != null && values.size() > 0) {
            return Collections.max(values.stream().filter(v -> v != null).collect(Collectors.toList()));
        }
        return null;
    }

    public static Float getRankInPercents(Integer rank, Integer totalCount) {
        if(totalCount > 1) {
            return ((totalCount.floatValue() - rank.floatValue()) / (totalCount.floatValue() - 1F)) * 100F;
        } else {
            return 100F;
        }
    }
}
