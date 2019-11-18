package cz.sportiq.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(cz.sportiq.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(cz.sportiq.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Event.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Event.class.getName() + ".athleteEvents", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Event.class.getName() + ".tests", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Workout.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Workout.class.getName() + ".activities", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Workout.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Workout.class.getName() + ".sports", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Activity.class.getName() + ".activityResults", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Activity.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.ActivityResult.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.ActivityResult.class.getName() + ".resultSplits", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.ActivityResultSplit.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.WorkoutCategory.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.ActivityCategory.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteEvent.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteEvent.class.getName() + ".athleteWorkouts", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteWorkout.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteWorkout.class.getName() + ".athleteActivities", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteActivity.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteActivity.class.getName() + ".athleteActivityResults", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteActivityResult.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteActivityResult.class.getName() + ".athleteActivityResultSplits", jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.AthleteActivityResultSplit.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Athlete.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Sport.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.Unit.class.getName(), jcacheConfiguration);
            cm.createCache(cz.sportiq.domain.ActivityCategory.class.getName() + ".childActivityCategories", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
