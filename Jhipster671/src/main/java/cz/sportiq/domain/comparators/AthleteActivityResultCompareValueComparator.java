package cz.sportiq.domain.comparators;

import cz.sportiq.domain.ActivityResult;
import cz.sportiq.domain.AthleteActivityResult;
import cz.sportiq.domain.enumeration.ResultType;
import cz.sportiq.service.impl.AthleteActivityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class AthleteActivityResultCompareValueComparator implements Comparator<AthleteActivityResult> {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityServiceImpl.class);

    @Override
    public int compare(AthleteActivityResult o1, AthleteActivityResult o2) {
        if (!o1.getActivityResult().equals(o2.getActivityResult())) {
            log.error("Try to compare incompatible athlete results!");
            return 0;
        }

        ActivityResult activityResult = o1.getActivityResult();
        int result = 0;
        if (o1.getCompareValue() > o2.getCompareValue()) {
            result = 1;
        } else if (o1.getCompareValue() < o2.getCompareValue()) {
            result = -1;
        }

        if (ResultType.MORE_IS_BETTER.equals(activityResult.getResultType())) {
            result *= -1;
        }

        return result;
    }

}
