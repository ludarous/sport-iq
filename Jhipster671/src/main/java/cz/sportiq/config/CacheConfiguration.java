package cz.sportiq.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, cz.sportiq.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, cz.sportiq.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, cz.sportiq.domain.User.class.getName());
            createCache(cm, cz.sportiq.domain.Authority.class.getName());
            createCache(cm, cz.sportiq.domain.User.class.getName() + ".authorities");
            createCache(cm, cz.sportiq.domain.Event.class.getName());
            createCache(cm, cz.sportiq.domain.Event.class.getName() + ".athleteEvents");
            createCache(cm, cz.sportiq.domain.Event.class.getName() + ".tests");
            createCache(cm, cz.sportiq.domain.Event.class.getName() + ".athletes");
            createCache(cm, cz.sportiq.domain.Workout.class.getName());
            createCache(cm, cz.sportiq.domain.Workout.class.getName() + ".activities");
            createCache(cm, cz.sportiq.domain.Workout.class.getName() + ".categories");
            createCache(cm, cz.sportiq.domain.Workout.class.getName() + ".sports");
            createCache(cm, cz.sportiq.domain.Workout.class.getName() + ".events");
            createCache(cm, cz.sportiq.domain.Activity.class.getName());
            createCache(cm, cz.sportiq.domain.Activity.class.getName() + ".activityResults");
            createCache(cm, cz.sportiq.domain.Activity.class.getName() + ".categories");
            createCache(cm, cz.sportiq.domain.Activity.class.getName() + ".workouts");
            createCache(cm, cz.sportiq.domain.ActivityResult.class.getName());
            createCache(cm, cz.sportiq.domain.ActivityResult.class.getName() + ".resultSplits");
            createCache(cm, cz.sportiq.domain.ActivityResultSplit.class.getName());
            createCache(cm, cz.sportiq.domain.WorkoutCategory.class.getName());
            createCache(cm, cz.sportiq.domain.WorkoutCategory.class.getName() + ".workouts");
            createCache(cm, cz.sportiq.domain.ActivityCategory.class.getName());
            createCache(cm, cz.sportiq.domain.ActivityCategory.class.getName() + ".childActivityCategories");
            createCache(cm, cz.sportiq.domain.ActivityCategory.class.getName() + ".activities");
            createCache(cm, cz.sportiq.domain.AthleteEvent.class.getName());
            createCache(cm, cz.sportiq.domain.AthleteEvent.class.getName() + ".athleteWorkouts");
            createCache(cm, cz.sportiq.domain.AthleteWorkout.class.getName());
            createCache(cm, cz.sportiq.domain.AthleteWorkout.class.getName() + ".athleteActivities");
            createCache(cm, cz.sportiq.domain.AthleteActivity.class.getName());
            createCache(cm, cz.sportiq.domain.AthleteActivity.class.getName() + ".athleteActivityResults");
            createCache(cm, cz.sportiq.domain.AthleteActivityResult.class.getName());
            createCache(cm, cz.sportiq.domain.AthleteActivityResult.class.getName() + ".athleteActivityResultSplits");
            createCache(cm, cz.sportiq.domain.AthleteActivityResultSplit.class.getName());
            createCache(cm, cz.sportiq.domain.Athlete.class.getName());
            createCache(cm, cz.sportiq.domain.Athlete.class.getName() + ".events");
            createCache(cm, cz.sportiq.domain.Address.class.getName());
            createCache(cm, cz.sportiq.domain.EventLocation.class.getName());
            createCache(cm, cz.sportiq.domain.Sport.class.getName());
            createCache(cm, cz.sportiq.domain.Sport.class.getName() + ".workouts");
            createCache(cm, cz.sportiq.domain.Unit.class.getName());
            createCache(cm, cz.sportiq.domain.Athlete.class.getName() + ".sports");
            createCache(cm, cz.sportiq.domain.Sport.class.getName() + ".athletes");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
