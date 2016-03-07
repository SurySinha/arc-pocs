P.S. found a bug, added contact=new Contact to addNew method will need to add that to the other versions as well.

Changed the app context as follows:

```
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"    
    xsi:schemaLocation="http://www.springframework.org/schema/beans
     http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
     http://www.springframework.org/schema/context
     http://www.springframework.org/schema/context/spring-context-2.5.xsd">
     
     <context:component-scan base-package="com.arcmind.contact"/>
          

</beans>

```

Added annotations
Updated Contact Controller as follows:

```
package com.arcmind.contact.controller;
...
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("session")
public class ContactController extends AbstractCrudController{

```

Modified controllers as follows:

```
package com.arcmind.contact.model;
...
import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {

```

The other repos are setup the same way.

Then you can inject the repos into the controller as follows:

```
public class ContactController extends AbstractCrudController{
        /** Contact Controller collaborates with contactRepository. */
        @Autowired(required=true) @Qualifier("contactRepository")
        private ContactRepository contactRepository;
        
        @Autowired(required=true) @Qualifier("groupRepository")
        private GroupRepository groupRepository;
        
        @Autowired(required=true) @Qualifier("tagRepository")   
        private TagRepository tagRepository;
```