### About this article ###
This article discusses dependency injection in a tutorial format. It covers some of the newer features of Spring DI like annotations, improved XML config and more.

### About the author ###
Rick Hightower
([Rick's bio](http://docs.google.com/View?docid=dw4ms58_7ghf4wbc4)) serves as chief technology officer for [ArcMind Inc., a consulting and training firm specializing in Spring, JPA/Hibernate, and JSF](http://www.arc-mind.com/). Rick enjoys [writing](http://docs.google.com/Doc?id=dw4ms58_9cbddr2xj) and [programming](http://code.google.com/p/krank/).


### Article status ###
This has been reviewed and copy edited. I have not applied the copy editing to this yet. The copy edited version was featured on Java Lobby as a featured article and you can find it here: [Spring 2.5 Dependency Injection - An Introductory Tutorial](http://java.dzone.com/articles/dependency-injection-an-introd). There is a part two for this article [Spring 2.5 AOP - An Introduction Tutorial](http://code.google.com/p/arc-pocs/wiki/AOPIntroTutorial).


# Dependency injection #

Dependency injection (DI) is the ability to inject dependencies. DI can help make your code architecturally pure. It aids in using a design by interface approach as well as test driven development by providing a consistent way to inject dependencies. For example a data access object (DAO) may need a database connection. Thus the DAO depends on the database connection. Instead of looking up the database connection with JNDI, you could inject it.

One way to think about a DI container like Spring is to think of JNDI turned inside out. Instead of the objects looking up other objects that it needs to get its job done (dependencies), with DI the container injects those dependent objects. This is the so-called Hollywood principle, you don't call us (lookup objects), we will call you (inject objects).

If you have worked with CRC cards, you can think of a dependency as a collaborator, i.e., an object that another objects needs to do perform its role.

Let's say you have a automated teller machine. And this automated banking machine needs the ability to talk to a bank, and it uses what it calls a transport object to do this. In this example, a transport object handles the low level communication to the bank.

This example could be represented by the two interfaces as follows:

#### AutomatedTellerMachine interface ####
```
package com.arcmind.springquickstart;

import java.math.BigDecimal;

public interface AutomatedTellerMachine {
	void deposit(BigDecimal bd);
	void withdraw(BigDecimal bd);
}

```

#### ATMTransport interface ####
```
package com.arcmind.springquickstart;

public interface ATMTransport {
	void communicateWithBank(byte [] datapacket);
}
```

Now an **AutomatedTellerMachine** needs a transport to do it role in life, namely withdraw money and deposit money. The **AutomatedTellerMachine** may depend on many objects to do it role in life. It collaborates with its dependencies to do its job in life.

An implementation of the **AutomatedTellerMachine** may look like this:

#### AutomatedTellerMachine implementation ####
```
package com.arcmind.springquickstart;

import java.math.BigDecimal;

public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
	
	private ATMTransport transport;
	
	public void deposit(BigDecimal bd) {
          ...
		transport.communicateWithBank(...);
	}

	public void withdraw(BigDecimal bd) {
          ...
		transport.communicateWithBank(...);
	}

	public void setTransport(ATMTransport transport) {
		this.transport = transport;
	}
	
}

```

The **AutomatedTellerMachineImpl** does not know or care how the **transport** does its job; just that it needs it to withdraw, and deposit money from the bank. This level of indirection allows us to replace the **transport** with different implementations for example:

#### Three example transports: SoapAtmTransport, StandardAtmTransport and SimulationAtmTransport ####
```
package com.arcmind.springquickstart;

public class SoapAtmTransport implements ATMTransport {

	public void communicateWithBank(byte[] datapacket) {
           ...
	}

}

package com.arcmind.springquickstart;

public class StandardAtmTransport implements ATMTransport {

	public void communicateWithBank(byte[] datapacket) {
          ...
	}

}

package com.arcmind.springquickstart;

public class SimulationAtmTransport implements ATMTransport {

	public void communicateWithBank(byte[] datapacket) {
		...
	}

}

```

Notice the possible implementations of the **ATMTransport** interface. The **AutomatedTellerMachineImpl** does not know or care which **transport** it uses. Also, notice for testing and developing, instead of talking to a real bank, we can use the **SimulationAtmTransport**.

You do not need Spring to do DI. The concept of DI transcends Spring. For example, DI can be done without Spring as follows:

#### Dependency injection without Spring ####
```
package com.arcmind.springquickstart;

import java.math.BigDecimal;

public class AtmMain {
	
	public void main (String[] args) {
		AutomatedTellerMachine atm = new AutomatedTellerMachineImpl();
		ATMTransport transport = new SoapAtmTransport();
		/* Inject the transport. */           
		((AutomatedTellerMachineImpl)atm).setTransport(transport);
		
		atm.withdraw(new BigDecimal("10.00"));
		
		atm.deposit(new BigDecimal("100.00"));
	}

}

```

Then to inject a different **transport**, it is a mere matter of calling a different setter method as follows:

#### Injecting a different dependency ####
```
		ATMTransport transport = new SimulationAtmTransport();
		((AutomatedTellerMachineImpl)atm).setTransport(transport);

```

To use Spring to inject a dependency you could do the following:

#### Using Spring to manage dependencies ####
```
package com.arcmind.springquickstart;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AtmMain {
	
	public static void main (String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:./spring/applicationContext.xml");
		AutomatedTellerMachine atm = (AutomatedTellerMachine) appContext.getBean("atm");
		
		atm.withdraw(new BigDecimal("10.00"));
		
		atm.deposit(new BigDecimal("100.00"));
	}

}

```

#### /spring/applicationContext.xml file ####

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">

	<bean id="atmTransport" class="com.arcmind.springquickstart.SoapAtmTransport" />


	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<property name="transport" ref="atmTransport" />
	</bean>

</beans>

```

The figure 1 illustrates how Spring injects the dependency using property setter method injection.

#### Figure 1 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide01.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide01.jpg)

In the application context, you declare two beans with the **bean** tag namely, **atmTransport** and **atm**. Then you use the **property** tag to inject the **atmTransport** bean into the transport property. This effectively calls the setter method of the **AutomatedTellerMachineImpl** transport property (setTransport(...)).

The application context is the central interface to the Spring DI container, it provides (taken from API docs):
  * "Bean factory methods for accessing application components"
  * "The ability to load file resources in a generic fashion"
  * "The ability to resolve messages, supporting internationalization"
  * and more.

The focues of this article will be the bean factory methods and dependency injection.

## Using constructor instead of setter ##
Another option when using Spring is to use a the constructor arguments instead of setter methods to inject dependencies. This keeps things more pure from a object oriented design standpoint as an object has to be created with all of its collaborators (a.k.a. dependencies) it needs to fulfill its role in life.

Using constructors injection is much like using setter methods as follows:

#### Application context for constructor injection ####
```
	
	<bean id="standardTransport" class="com.arcmind.springquickstart.StandardAtmTransport"/>
	
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<constructor-arg ref="standardTransport" />
	</bean>

```

Notice the use of the **constructor-arg** tag. This implies that we have a constructor that takes **transport** as a single argument.

#### Adding a constructor to **AutomatedTellerMachineImpl** ####
```
public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
	
	private ATMTransport transport;
	
	public AutomatedTellerMachineImpl (ATMTransport transport) {
		this.transport = transport;
	}

```

The above should keep the object purists in your group happy. Although, it should be noted that the setter injection style makes Test driven development a bit easier. In practice, I see the setter method approach used more often.

Figure 2 illustrates how the constructor injection occurs.

#### Figure 2 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide02.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide02.jpg)

If you have many constructor in the same class with variable number of arguments, Spring will try to pick the best fit. However you can give Spring some hints as follows:

#### Application context for constructor injection with a hint for Spring ####
```
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<constructor-arg index="0" ref="standardTransport" />
	</bean>

```

Under some circumstances you can even specify which type with the type attribute you want Spring to resolve the constructor argument just in case there are more than one possible constructor matches. I find most times I don't have to specify index or type. Your mileage may vary.

## Spring and Annotation driven DI ##

[Seam](http://www.jboss.com/products/seam), and [Guice](http://code.google.com/p/google-guice/) pioneered the use of DI using annotation instead of XML. Spring also added this support and in typical Spring fashion, it does this in a flexible non-invasive manner.

Let's start off with a simple example. Let's say that you misconfigured the **AutomatedTellerMachineImpl** and forgot to inject a dependency as follows:

#### Opps forgot to inject the transport ####
```
	<bean id="atmTransport" class="com.arcmind.springquickstart.SoapAtmTransport" />


	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
	</bean>
```

You might get an error like this:

#### Typical error from misconfiguring a bean ####
```
Exception in thread "main" java.lang.NullPointerException
	at com.arcmind.springquickstart.AutomatedTellerMachineImpl.withdraw(AutomatedTellerMachineImpl.java:25)
	at com.arcmind.springquickstart.AtmMain.main(AtmMain.java:14)
```

In a deployed app, this error could be quite cryptic. If you used the @Required annotation, you could ask Spring to scan the beans and look for missing dependencies as follows:

#### AutomatedTellerMachineImpl using @Required on the setter method of the transport property ####
```
import org.springframework.beans.factory.annotation.Required;

public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
	
	private ATMTransport transport;

	@Required
	public void setTransport(ATMTransport transport) {
		this.transport = transport;
	}
```

Now when we ran this and forgot to configure a transport, we would get this:
```
Caused by: org.springframework.beans.factory.BeanInitializationException: Property 'transport' is required for bean 'atm'
```

This is more clear and makes it easier to debug and develop. To enable this required dependency checking feature, you must use **context:component-scan** or the **context:annotation-config** tags. We will talk more about **context:component-scan** later. Here is the last example use **context:annotation-config**:

#### Application context file using annotation-config tag ####
```
<?xml version="1.0" encoding="UTF-8"?>
<beans 
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">

	<context:annotation-config/>
	
	<bean id="atmTransport" class="com.arcmind.springquickstart.SoapAtmTransport" />


	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<property name="transport" ref="atmTransport"/>
	</bean>

</beans>
```

#### Using @Autowire to define a default transport ####

It may be the case that we want to define a default **transport** for an **AutomatedTellerMachine**. By doing this, we are basically saying, we know the **AutomatedTellerMachine** needs a **transport** and by default it uses this one. We could do this with the **@Autowire** and **@Qualifier** annotations as follows:

#### Using @Autowire and @Qualifier annotations to do DI ####
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
	
	@Autowired (required=true)
	@Qualifier ("standardTransport")
	private ATMTransport transport;
```

When using Spring annotations for DI, you do not need to have setter methods (or a special constructor) for DI any longer. Spring can inject directly into the private fields. You have the option of annotating the setter methods instead. The applicationContext for this example looks like this:

#### Many transports configured in applicationContext, no injection specified in XML ####
```
<?xml version="1.0" encoding="UTF-8"?>
<beans 
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">

	<context:annotation-config/>
	
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport" />
	<bean id="standardTransport" class="com.arcmind.springquickstart.StandardAtmTransport" />
	<bean id="simulationTransport" class="com.arcmind.springquickstart.SimulationAtmTransport" />


	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl"/>


</beans>
```

Notice that no transport for injection is specified in this file. The annotations specify which transport gets injected by default.

Figure 3 illustrates injection using this technique.

#### Figure 3 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide03.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide03.jpg)


You could override which bean gets set by using standard spring injection. In this way, you have a default (**standardTransport**) that can be overridden. Here is an example of overriding with another transport (assumes you have a setter method for transport).

#### Overriding the annotation in the application context file ####
```
	
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport" />
	<bean id="standardTransport" class="com.arcmind.springquickstart.StandardAtmTransport" />
	<bean id="simulationTransport" class="com.arcmind.springquickstart.SimulationAtmTransport" />


	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<property name="transport" ref="simulationTransport"/>
	</bean>
```

Thus the XML DI injection takes precedence over the annotation. Therefore, the annotation is the "reasonable default", but the application context file has the final word.

## Avoiding hard-wiring beans directly to other beans with @Qualifier and qualifier tag ##

For an extra level of indirection you can add a qualifier to a bean in the configuration file and then specify which type of **transport** is needed in the **AutomatedTellerMachineImpl** as follows:

#### Using @Qualifier for an extra level of indirection ####
```
public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
	
	@Autowired (required=true)
	@Qualifier ("default")
	private ATMTransport transport;

```

#### Using qualifier tag in applicationContext.xml ####
```
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport" />
	<bean id="standardTransport" class="com.arcmind.springquickstart.StandardAtmTransport">
		<qualifier value="default"/> 
		<!-- NOTE ADDED THIS QUALIFIER that marks this as default -->
	</bean>
	<bean id="simulationTransport" class="com.arcmind.springquickstart.SimulationAtmTransport" />


	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl"/>

```

With this extra level of indirection, we are not hard-wiring beans directly to other beans and if we decide that we should use a new default transport object, we don't have to rewire every dependent bean.

Figure 4 illustrates injection using this technique.

#### Figure 4 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide04.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide04.jpg)


## Avoiding XML hell with component-scan tag and @Service, @Component, @Repository annotations ##

Imagine an application with hundreds of managed objects. Imagine the size of the XML configuration file(s). You can manage objects with Spring without putting them in the applicationContext files by marking them with @Service, @Component, @Repository, and telling Spring where to find them. Spring will then scan the classpath looking for these beans and then manage their dependencies automatically.

To perform this feat, you must configure a **context:component-scan** tag passing the packages you would like Spring to scan as follows:

#### Using component-scan tag in applicationContext.xml ####
```
<?xml version="1.0" encoding="UTF-8"?>
<beans 
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">


	<context:component-scan base-package="com.arcmind.springquickstart"/>
	
</beans>
```

Then you mark your beans with the @Service, @Component, @Repository as follows:

#### AutomatedTellerMachine class using @Service ####
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service ("atm")
public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
	
	@Autowired (required=true)
	@Qualifier ("default")
	private ATMTransport transport;

```

#### Three transports using @Component ####
```
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("standardTransport")
@Qualifier("default")
public class StandardAtmTransport implements ATMTransport {

	public void communicateWithBank(byte[] datapacket) {
		...
	}

}

@Component("soapTransport")
public class SoapAtmTransport implements ATMTransport {

	public void communicateWithBank(byte[] datapacket) {
		...
	}

}

@Component("simulationTransport")
public class SimulationAtmTransport implements ATMTransport {

	public void communicateWithBank(byte[] datapacket) {
		...
	}

}
```

Notice that there is a **@Qualifier** annotation used in the **StandardAtmTransport** to denote it as the default **transport** for this application. For new projects, it makes sense to use annotations for objects that don't change their dependencies often. Avoiding XML and using annotation is the new trend in DI; some may say best practice as well.

Figure 5 illustrates injection using this technique.

#### Figure 5 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide05.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide05.jpg)


## Configuring objects ##
In addition to injecting dependencies, Spring allows you to configure objects with primitive and basic types. Let's say that the **SoapAtmTransport** sometimes has to work in areas where the connection is not so great, so you decide to add a **retries** property to the **SoapAtmTransport** as follows:

#### SoapAtmTransport with retries ####
```
public class SoapAtmTransport implements ATMTransport {
	
	private int retries=3;

	public SoapAtmTransport() {
	}

	public SoapAtmTransport(int retries) {
		this.retries = retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public void communicateWithBank(byte[] datapacket) {
		System.out.printf("SOAP Transport retries %d: %s \n", retries, new String(datapacket));
	}

}
```

Notice that we can pass the retries to the constructor or call the setter method with the number or retries as follows:

#### Injecting the number of retires with the setter method ####
```
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport">
		<property name="retries" value="5"/>
	</bean>

```

#### Injecting the number of retires with a constructor arg ####
```
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport">
		<constructor-arg value="6"/>
	</bean>
	
```

Figure 6 illustrates configuring retries using setter method injection.

#### Figure 6 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide06.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide06.jpg)

Figure 7 illustrates configuring retries using constructor arguments.

#### Figure 7 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide07.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide07.jpg)



Since this type of configuration is so common, Spring has a shortcut to simplify property value injection as follows:

#### Using p namespace in an applicationContext.xml file ####
```
<?xml version="1.0" encoding="UTF-8"?>
<beans 
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p" 
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">

	
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport" p:retries="7"/>
	
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>

</beans>
```

Notice the use of p:retries="7" which is much less verbose than the previous example which used the **property** tag to set the value. If you are using the Spring IDE plugin for Eclipse, you will get code completion for the **p:property-name-syntax**.

Figure 8 illustrates configuring retries using the shortcut notation added in Spring 2.x.

#### Figure 8 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide08.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide08.jpg)

Spring allows you to configure all primitive types (int, byte, long, etc.), as well as wrapper objects (Integer, Byte, Long, etc.), and many basic types (String, URL, Class, File, etc.).

## Using property place holder configurer ##

Let's say for each installation of an Automated teller machine, the installer may need to configure the number of retries. You probably don't want the installer messing with your XML file for your application context that is too much like code and too many things could go wrong. Instead perhaps they could just edit a properties file. The properties file could have properties for each of the things that could vary for a given installation of an **AutomatedTellerMachineImpl**.

#### atm.properties Properties file ####
```
transport.retries=8
```

## applicationContext.xml using property-placeholder tag ##
```
<?xml version="1.0" encoding="UTF-8"?>
<beans 
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p" 
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">
	
	<context:property-placeholder location="classpath:atm.properties"   />
	
	
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport" p:retries="${transport.retries}"/>
	
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>

</beans>
```

Notice the property-placeholder loads the atm.properties file off of the classpath. Then we use the **transport.retries** defined in the atm.properties file as follows **p:retries="${transport.retries}"**.

Figure 9 illustrates using the property placeholder configurer

#### Figure 9 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide09.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide09.jpg)

You could load properties file from the file system using **file:** instead of **classpath:** in the location as follows:

#### Loading the properties file from the file system with the property-placeholder ####
```
	<context:property-placeholder location="file:./src/main/resources/atm.properties"   />
	
	
	<bean id="soapTransport" class="com.arcmind.springquickstart.SoapAtmTransport" p:retries="${transport.retries}"/>
	
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>
```

## Scopes and lifecycle ##

Spring supports the concepts of scopes. If you are familiar with JSP and Servlets, you may recall that they have request, session and application scope. Objects put into request scope stay around for the duration of one request. Objects put into session scope stay around the entire user session (unless destroyed manually). While objects put into application scope stay around as long as the web application is running.

Spring scope support is very similar. Spring supports the following scopes: prototype, singleton, request, session and more. Plus you can configure you own scope handlers. Outside of a web application, Spring mainly support two scopes out of the box: prototype and singleton. A singleton scoped object is the default. It means that the object will stay around as long as the application context does (typically very similar to application scope in a web application). A prototype scope means that every time that you ask for an object, Spring will create a new one. For example:

#### Two atm's configured with different scopes ####
```
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl" scope="singleton">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>

	<bean id="atmP" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl" scope="prototype">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>
```

If we looked up the **atm** twice, we would get the same object because it is in singleton scope; however, every time you looked up **atmP**, you would get a different object because it is in prototype scope. This can be demonstrated by the following example:

#### Example demonstrating prototype vs. singleton ####
```
		AutomatedTellerMachine atm1 = (AutomatedTellerMachine) appContext.getBean("atm");
		AutomatedTellerMachine atm2 = (AutomatedTellerMachine) appContext.getBean("atm");
		assert atm1 == atm2; //First assert
		
		AutomatedTellerMachine atmP1 = (AutomatedTellerMachine) appContext.getBean("atmP");
		AutomatedTellerMachine atmP2 = (AutomatedTellerMachine) appContext.getBean("atmP");
		assert atmP1 != atmP2; //Second assert
```

## Life cycle methods ##

Often times it is the case that you need an object to initialize itself after all of the dependencies have been set. Spring allows you to specify an init method as follows:

#### Specifying an init method with Spring (applicationContext.xml) ####
```
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl" scope="singleton" init-method="init">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>

	<bean id="atmP" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl" scope="prototype" init-method="init">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>
```

Notice the use of the **init-method** attribute in the **bean** tag. The name of the method does not have to be init.

Here is the init method defined in Java. (We also defined a few more methods for the transport to add to the flavor of the example and a shutdown method which we will get to in a moment).

#### Init Method and Shutdown method in Java ####
```
public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{

	
	public void init () {
		System.out.println("INIT");
		transport.connect();
	}
	
	public void shutdown () {
		System.out.println("SHUTDOWN");
		transport.close();
	}

```

The **atm** bean's **init** method gets called right when we first load the application context (you can change this by setting the lazy-init attribute to "true"). The prototype **atmP** bean's init mehtod gets called every time we look it up in the application context.

You can also specify a clean up method using the attribute destroy-method as follows:

#### Using destroy-method attribute ####
```
	<bean id="atm" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl" scope="singleton" 
		init-method="init" destroy-method="shutdown">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>

	<bean id="atmP" class="com.arcmind.springquickstart.AutomatedTellerMachineImpl" scope="prototype" 
		init-method="init" destroy-method="shutdown">
		<constructor-arg index="0" ref="soapTransport" />
	</bean>
```

The destroy method would never get called by Spring on atmP because it does not manage the life cycle of prototype beans after creation. The destroy method on atm would only get called if someone gracefully closed the application context which Spring does for some application contexts (which is beyond the scope of this introduction tutorial).


Figure 10 illustrates using the lifecycle methods.

#### Figure 10 ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide10.jpg](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/Slide10.jpg)


## Conclusion ##
DI can help make your code architecturally pure. It aids in using a design by interface approach as well as test driven development by providing a consistent way to inject dependencies. You don't need Spring to use DI. You could use DI with plain old Java. However, Spring provides a very nice, powerful DI container.

There are other DI containers and frameworks out there Plexus, Pico container, JBoss microcontainer, and more recently Guice. And, other frameworks allow dependency injection like JSF, Seam and more. But Spring is the de facto industry standard way to do DI.

What is also interesting is what we did not cover. We did not cover autowiring using by type, by name or constructor autowiring as these are features that developers just don't use in a production environments. A future tutorial titled, "DI details", will cover this as well as many other topics related to Spring DI like bean definitions, using lists, maps and sets, FactoryBeans, ApplicationContextAware, and yes autowiring.

The next item in this tutorial series will be AOP. Followed by Spring DAO and JPA support.