package cz.sportiq.domain.comparators;

import cz.sportiq.domain.ActivityResult;
import cz.sportiq.domain.AthleteActivityResult;
import cz.sportiq.domain.enumeration.ResultType;
import cz.sportiq.service.impl.AthleteActivityServiceImpl;
import cz.sportiq.service.utils.StatsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AthleteActivityResultSplitsCompareValueComparator implements Comparator<AthleteActivityResult> {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityServiceImpl.class);

    @Override
    public int compare(AthleteActivityResult o1, AthleteActivityResult o2) {
        if (!o1.getActivityResult().equals(o2.getActivityResult())) {
            log.error("Try to compare incompatible athlete results!");
            return 0;
        }

        if(o1.getAthleteActivityResultSplits() == null ||
            o1.getAthleteActivityResultSplits().isEmpty() ||
            o2.getAthleteActivityResultSplits() == null ||
            o2.getAthleteActivityResultSplits().isEmpty() ||
            o1.getAthleteActivityResultSplits().size() != o2.getAthleteActivityResultSplits().size()
        )   {
            log.error("Split results does not exists or their length does not fit!");
            return 0;
        }

        ActivityResult activityResult = o1.getActivityResult();

        List<Float> splitValues1 = o1.getAthleteActivityResultSplits().stream().map(rs -> rs.getCompareValue()).collect(Collectors.toList());
        List<Float> splitValues2 = o2.getAthleteActivityResultSplits().stream().map(rs -> rs.getCompareValue()).collect(Collectors.toList());

        Float splitSum1 = StatsUtil.sum(splitValues1);
        Float splitSum2 = StatsUtil.sum(splitValues2);


        int result = 0;
        if (splitSum1 > splitSum2) {
            result = 1;
        } else if (splitSum1 < splitSum2) {
            result = -1;
        }

        if (ResultType.MORE_IS_BETTER.equals(activityResult.getResultType())) {
            result *= -1;
        }

        return result;
    }

}
