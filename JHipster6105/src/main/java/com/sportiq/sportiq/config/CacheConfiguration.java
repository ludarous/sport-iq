package com.sportiq.sportiq.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
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
            createCache(cm, com.sportiq.sportiq.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.sportiq.sportiq.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.sportiq.sportiq.domain.User.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Authority.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.User.class.getName() + ".authorities");
            createCache(cm, com.sportiq.sportiq.domain.Agreement.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.LegalRepresentative.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.UserProperties.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.BodyCharacteristics.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Country.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Address.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.MembershipRole.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.MembershipRole.class.getName() + ".organisationMemberships");
            createCache(cm, com.sportiq.sportiq.domain.MembershipRole.class.getName() + ".groupMemberships");
            createCache(cm, com.sportiq.sportiq.domain.Organisation.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Organisation.class.getName() + ".memberships");
            createCache(cm, com.sportiq.sportiq.domain.Organisation.class.getName() + ".visibleActivities");
            createCache(cm, com.sportiq.sportiq.domain.OrganisationMembership.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.OrganisationMembership.class.getName() + ".roles");
            createCache(cm, com.sportiq.sportiq.domain.Group.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Group.class.getName() + ".memberships");
            createCache(cm, com.sportiq.sportiq.domain.Group.class.getName() + ".visibleActivities");
            createCache(cm, com.sportiq.sportiq.domain.GroupMembership.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.GroupMembership.class.getName() + ".roles");
            createCache(cm, com.sportiq.sportiq.domain.Event.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Event.class.getName() + ".activities");
            createCache(cm, com.sportiq.sportiq.domain.Event.class.getName() + ".entrants");
            createCache(cm, com.sportiq.sportiq.domain.Activity.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Activity.class.getName() + ".activityResults");
            createCache(cm, com.sportiq.sportiq.domain.Activity.class.getName() + ".visibleForOrganisations");
            createCache(cm, com.sportiq.sportiq.domain.Activity.class.getName() + ".visibleForGroups");
            createCache(cm, com.sportiq.sportiq.domain.Activity.class.getName() + ".events");
            createCache(cm, com.sportiq.sportiq.domain.ActivityResult.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.ActivityResult.class.getName() + ".resultSplits");
            createCache(cm, com.sportiq.sportiq.domain.ActivityResultSplit.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.UserEvent.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.UserEvent.class.getName() + ".athleteActivities");
            createCache(cm, com.sportiq.sportiq.domain.UserActivity.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.UserActivity.class.getName() + ".athleteActivityResults");
            createCache(cm, com.sportiq.sportiq.domain.UserActivityResult.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.UserActivityResult.class.getName() + ".athleteActivityResultSplits");
            createCache(cm, com.sportiq.sportiq.domain.UserActivityResultSplit.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.EventLocation.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Sport.class.getName());
            createCache(cm, com.sportiq.sportiq.domain.Unit.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
