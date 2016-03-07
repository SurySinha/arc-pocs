Step 1
```
package com.vm.cache.service;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;


public class CacheServiceFactoryConverter {
    public static SessionFactory getSessionFactory(EntityManagerFactory hemf) {
           return ((HibernateEntityManagerFactory)hemf).getSessionFactory();
    }
}

```


Step 2
```
    <bean id="cacheService"
          class="com.vm.cache.service.CacheServiceHibernateCacheImpl">
        <property name="sessionFactory">
            <bean class="com.vm.cache.service.CacheServiceFactoryConverter"
                  factory-method="getSessionFactory">
                <constructor-arg index="0"  ref="entityManagerFactory" />
            </bean>
        </property>
    </bean>
```