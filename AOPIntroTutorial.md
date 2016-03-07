### About this article ###
This article discusses Spring AOP in a tutorial format. It covers some of the newer features of Spring AOP like annotations, improved XML config and more. It builds on the DIIntroTutorial. It is meant as a simple introduction to AOP. Enough information so you could use AOP on a project.

### About the author ###
Rick Hightower
([Rick's bio](http://docs.google.com/View?docid=dw4ms58_7ghf4wbc4)) serves as chief technology officer for [ArcMind Inc., a consulting and training firm specializing in Spring, JPA/Hibernate, and JSF](http://www.arc-mind.com/). Rick enjoys [writing](http://docs.google.com/Doc?id=dw4ms58_9cbddr2xj) and [programming](http://code.google.com/p/krank/).

### Article status ###
This article is still a rough draft. It has been edited and images have been added. It is ready for the copy editor. Updated: added description of AOP proxy.


# Spring 2.x AOP #
For some, AOP seems like voodoo magic. For others, AOP seems like a cure all. For now, let's just say that AOP is a tool that you want in your developer tool box. It can make seemingly impossible things easy.

The first time that I used AOP was with Spring's transaction management support. I did not realize I was using AOP. I just knew Spring could apply EJB-style declarative transaction management to POJOs. It was probably 3 to six months before I realized that I was using was Spring's AOP support. The Spring framework truly brought AOP out of the esoteric closet into the main stream light of day.

You can think of AOP as a way to apply services (called cross-cutting concerns) to objects. AOP encompasses more than this, but this is where it gets used mostly in the main stream.

I've using AOP to apply caching services, transaction management, resource management, etc. to any number of objects in an application. It is not a panacea, but it certainly fits a lot of otherwise difficult use cases.

You can think of AOP as a dynamic decorator design pattern.  The decorator pattern allows additional behavior to be added to an existing class by wrapping the original class and duplicating its interface and then delegating to the original. See this article [decorator pattern](http://en.wikipedia.org/wiki/Decorator_pattern) for more detail about the decorator design pattern.


# Sample application revisited #

For this introduction to AOP, let's take a simple example, let's apply security services to our Automated Teller Machine example from the first [DI example from the first tutorial](http://code.google.com/p/arc-pocs/wiki/DIIntroTutorial) in this series.

Let's say when a user logs into a system that a **SecurityToken** is created that carries the user's credentials and before methods on objects get invoked, we want to check to see if the user has credentials to invoke these methods.

In a web application, you could write a **ServletFilter**, that stored this **SecurityToken** in HttpSession and then on every request retrieved the token from Session and put it into a **ThreadLocal** variable where it could be accessed from a **SecurityService** that you could implement.

Perhaps the objects that needed the **SecurityService** could access it as follows:

```
	public void deposit(BigDecimal bd) {
	    	/* If the user is not logged in, don't let them use this method */
    		if(!securityManager.isLoggedIn()){
    			throw new SecurityViolationException();
    		}
	    	/* Only proceed if the current user is allowed. */

	    	if (!securityManager.isAllowed("AutomatedTellerMachine", operationName)){
    			throw new SecurityViolationException();
    		}
		...

		transport.communicateWithBank(...);
	}
```

In our ATM example, the above might work out well, but imagine a system with thousands of classes that needed security. Now imagine, the way we check to see if a user is logged is changed. If we put this code into every method that needed security, then we could possibly have to change this a thousand times if we changed the way we checked to see if a user was logged in.

What we want to do instead is to use Spring to create a decorated version of the atm bean. The decorated version would add the additional behavior to the atm object without changing the actual implementation of the atm.

Spring does this by creating what is called an AOP proxy. An AOP proxy is like a dynamic decorator. Underneath the covers Spring can generate a class at runtime (the AOP proxy) that has the same interface as our AutomatedTellerMachine. The AOP proxy wraps our existing atm object and provides additional behavior by delegating to a list of method interceptors. The method interceptors provide the additional behavior and are similar to ServletFilters but for methods instead of requests.

Thus before we added Spring AOP, our atm example was like Figure 1.

#### Figure 1: Before AOP advice ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/beforeAOP.png](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/beforeAOP.png)

After we added AOP support, we now get an AOP proxy that applies the **securityAdvice** to the **atm** as show in figure 2.

#### Figure 2: After AOP advice ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/afterAOP.png](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/afterAOP.png)

You can see that the AOP proxy implements the AutomatedTellerMachine interface. When the client object looks up the atm and starts invoking methods instead of executing the methods directly, it executes the method on the proxy, which then delegates the call to a series of method interceptor called advice, which eventually invoke the actual atm instance (now called atmTarget).

Let's actually look at the code for this example.

For this example, we will use a simplified **SecurityToken** that gets stored into a **ThreadLocal** variable, but one could imagine one that was populated with data from a database or an LDAP server or some other source of authentication and authorization.

Here is the **SecurityToken**, which gets stored into a **ThreadLocal** variable, for this example:

#### SecurityToken ####
```
package com.arcmind.springquickstart.security;

/**
 * @author Richard Hightower
 *
 */
public class SecurityToken {
	
	private boolean allowed;
	private String userName;
	
	public SecurityToken() {
		
	}
	
	
	
	public SecurityToken(boolean allowed, String userName) {
		super();
		this.allowed = allowed;
		this.userName = userName;
	}



	public boolean isAllowed(String object, String methodName) {
		return allowed;
	}

	
	/**
	 * @return Returns the allowed.
	 */
	public boolean isAllowed() {
		return allowed;
	}
	/**
	 * @param allowed The allowed to set.
	 */
	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

```

The **SecurityService** stores the **SecurityToken** into the **ThreadLocal** variable, and then delegates to it to see if the current user has access to perform the current operation on the current object as follows:

#### SecurityService ####
```
package com.arcmind.springquickstart.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * @author Richard Hightower
 *
 */
@Service ("defaultSecurityService")
@Qualifier("manager")
public class SecurityService {
	
	private static ThreadLocal<SecurityToken> currentToken = new ThreadLocal<SecurityToken>();
	
	public static void placeSecurityToken(SecurityToken token){
		currentToken.set(token);
	}
	
	public void clearSecuirtyToken(){
		currentToken.set(null);
	}
	
	public boolean isLoggedIn(){
		SecurityToken token = currentToken.get();
		return token!=null;
	}
	
	public boolean isAllowed(String object, String method){
		SecurityToken token = currentToken.get();
		return token.isAllowed();
	}
	
	public String getCurrentUserName(){
		SecurityToken token = currentToken.get();
		if (token!=null){
			return token.getUserName();
		}else {
			return "Unknown";
		}
	}

}

```

#### SecurityViolationException ####
```
package com.arcmind.springquickstart.security;

/**
 * @author Richard Hightower
 *
 */
public class SecurityViolationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

```


To remove the security code out of the AutomatedTellerMachineImpl class and any other class that needs security, we will write an Aspect to intercept calls and perform security checks before the method call. To do this we will create a method interceptor (known is AOP speak as an advice) and intercept method calls on the atm object.

Here is the SecurityAdvice class which will intercept calls on the AutomatedTellerMachineImpl class.

#### SecurityAdvice ####
```
package com.arcmind.springquickstart.security;



import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Richard Hightower
 */
@Component
public class SecurityAdvice {
	
	@Autowired(required=true)
	@Qualifier ("manager")
	private SecurityService securityManager;

	public void checkSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
		
            /* If the user is not logged in, don't let them use this method */
            if(!securityManager.isLoggedIn()){            
                throw new SecurityViolationException();
            }

            /* Get the name of the method being invoked. */
            String operationName = joinPoint.getSignature().getName();
            /* Get the name of the object being invoked. */
            String objectName = joinPoint.getThis().getClass().getName();


           /*
            * Invoke the method or next Interceptor in the list,
            * if the current user is allowed.
            */
            if (!securityManager.isAllowed(objectName, operationName)){
                throw new SecurityViolationException();
            }
    	
            joinPoint.proceed();
        }
	

	/**
	 * @return Returns the manager.
	 */
	public SecurityService getSecurityManager() {
		return securityManager;
	}
	/**
	 * @param manager The manager to set.
	 */
	public void setSecurityManager(SecurityService manager) {
		this.securityManager = manager;
	}
	
}

```

The **checkSecurity** method of **SecurityAdvice** is the method that implements the advice. You can think of advice as the decoration that we want to apply to other objects. The objects getting the decoration are called advised objects.

Notice that the **SecurityService** gets injected into the **SecurityAdvice** and the **checkSecurity** method uses the **SecurityService** to see if the user is logged in and the user has the rights to execute the method.

An instance of **ProceedingJoinPoint**, namely **joinPoint**, is passed as an argument to **checkSecurity**. The  **ProceedingJoinPoint** has information about the method that is being called and provides control that determines if the method on the advised object's methods gets invoked (**AutomatedTellerMachineImpl.withdraw** and **AutomatedTellerMachineImpl.deposit**). If **joinPoint.proceed()** is not called then the wrapped method of the advised object (**withdraw** or **deposit**) is not called. (The proceed method causes the actual decorated method to be invoked or the next interceptor in the chain.)

To apply an Advice like **SecurityAdvice** to an advised object, you need a pointcut. A pointcut is like a filter that picks the objects and methods that get decorated. For this example, we will configure the pointcut into the Spring application context with the **aop:config**,  **aop:aspect**, **aop:pointcut**, and **aop:around** tags as follows:.

#### applicationContext.xml ####
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p" 
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans   http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd
		http://www.springframework.org/schema/aop     http://www.springframework.org/schema/aop/spring-aop-2.5.xsd
		">

	
	<context:component-scan base-package="com.arcmind.springquickstart"/>
	
	<aop:config>
		<aop:aspect id="securityAspect" ref="securityAdvice">
			<aop:pointcut id="atmLayer"  expression="bean(atm)"/>
			<aop:around  pointcut-ref="atmLayer"  method="checkSecurity"/>
		</aop:aspect>
	</aop:config>
	
</beans>

```

Because we have component scan turned on with the **context:component-scan** tag, the **SecurityAdvice** get installed in the Spring application context under the default bean name **securityAdvice**. (The default bean name is the simple name of the class). The atm bean is registered using the component scan as well as follows:

#### AutomatedTellerMachine class using @Service ####
```
@Service ("atm")
public class AutomatedTellerMachineImpl implements AutomatedTellerMachine{
        
        @Autowired (required=true)
        @Qualifier ("default")
        private ATMTransport transport;
```

The **aop:pointcut** tag defines the pointcut rule using this AspectJ expression **bean(atm)** which means decorate the methods of the bean named **atm**. If you want to decorate all beans whose names ends in **Service** you would use

```
 bean(*Service)
```

To see a full description of the AspectJ pointcut language see [pointcuts](http://www.eclipse.org/aspectj/doc/released/progguide/semantics-pointcuts.html). Note that the bean name pointcut can take you far.

You apply this pointcut and advice around the **atm** methods using the **aop:around** tag by referring to the **atmLayer**. You defined with the **aop:pointcut**. Then when the **atm** is looked up with AtmMain (listed below), instead of getting the original **atm**, you get an AOP proxy which is like a dynamic decorator.

Figure 3 shows graphically how the objects and advice are found and applied.

#### Figure 3: AOP Configuration ####
![http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/aopConfig.png](http://arc-pocs.googlecode.com/svn/images/images-spring-quickstart/aopConfig.png)

Let's complete our example by reviewing the AtmMain main method that looks up the atm out of the Spring **applicatoinContext**.

Let's review **AtmMain** as follows:

#### AtmMain.java ####
```
package com.arcmind.springquickstart.atm;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arcmind.springquickstart.security.SecurityService;
import com.arcmind.springquickstart.security.SecurityToken;

public class AtmMain {
	
	public static void simulateLogin() {
		SecurityService.placeSecurityToken(new SecurityToken(true, "Rick Hightower"));
	}

	public static void simulateNoAccess() {
		SecurityService.placeSecurityToken(new SecurityToken(false, "Tricky Lowtower"));
	}	
	
	public static void main(String[] args) throws Exception {
		
		simulateLogin();

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
											"classpath:./spring/applicationContext.xml");

		AutomatedTellerMachine atm = (AutomatedTellerMachine) appContext
				.getBean("atm");

		atm.withdraw(new BigDecimal("10.00"));

		atm.deposit(new BigDecimal("100.00"));
		
		System.out.println("done");

	}

}

```

Before we added AOP support when we looked up the atm, we looked up the object directly as shown in figure 1, now that we applied AOP when we look up the object we get what is in figure 2. When we look up the atm in the application context, we get the AOP proxy that applies the decoration (advice, method interceptor) to the atm target by wrapping the target and delegating to it after it invokes the series of method interceptors.


## Conclusion ##
AOP is neither a cure all or voodoo magic, but a powerful tool that needs to be in your bag of tricks. The Spring framework has brought AOP to the main stream masses and Spring 2.5 has simplified using AOP.

You can use Spring AOP to apply services (called cross-cutting concerns) to objects. AOP need not seem a foreign concept as it is merely a more flexible version of the decorator design pattern. With AOP you can add additional behavior to an existing class without writing a lot of wrapper code. This can be a real time saver when you have a use case where you need to apply a cross cutting concern to a slew of classes.