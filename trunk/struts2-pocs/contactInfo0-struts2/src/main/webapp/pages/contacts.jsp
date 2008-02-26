<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>ContactInfo Application</title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />

        <script type="text/javascript">
            function gup(name) {
               name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
               var regexS = "[\\?&]"+name+"=([^&#]*)";
               var regex = new RegExp( regexS );
               var results = regex.exec( window.location.href );
               if( results == null )
                 return "";
               else
                 return results[1];
            }
       </script>
    </head>
	<body>


        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

        <h4>ContactInfo Example (Struts2)</h4>

            <s:if test="hasActionErrors()">
                  <label class="errorClass">
                      <s:actionerror theme="mytheme"/>
                  </label>
              </s:if>

              <s:if test="hasActionMessages()">
                  <label class="infoClass">
                      <s:actionmessage theme="mytheme"/>
                  </label>
              </s:if>        

        <div id="formPlaceholder">
        <s:form id="calcForm" action="AddNew.htm" method="post">

            <s:if test="hasFieldErrors()">
                  <s:iterator value="fieldErrors">
                      <s:iterator>
                          <s:if test="key eq 'contactForm.firstName'">
                              <c:set var="firstName_error" value="true"/>
                          </s:if>
                          <s:if test="key eq 'contactForm.lastName'">
                              <c:set var="lastName_error" value="true"/>
                          </s:if>
                      </s:iterator>
                  </s:iterator>
            </s:if>


            <c:if test="${contactForm.persistCommand eq 'Add'}">
                <c:set var="id" value="0"/>
            </c:if>
            <c:if test="${contactForm.persistCommand eq 'Update'}">
                <c:set var="id" value="${contactForm.id}"/>
            </c:if>



            <!--s:hidden key="contactForm.id" value="id"/-->

            <input type="hidden" name="contactForm.id" value="${id}" id="calcForm_contactForm_id"/>



            <table class="formGrid">
                <tr>
                        <td>

                            <c:if test="${firstName_error}">
                                <label class="errorClass">
                            </c:if>
                            <c:if test="${!firstName_error}">
                                <label class="labelClass">
                            </c:if>

                            First Name :
                             </label>
                        </td>
                        <td>
                            <s:textfield key="contactForm.firstName" value="%{contactForm.firstName}"/>
                        </td>
                        <td>

              
                            <label class="errorClass">
                                <s:fielderror theme="mytheme">
                                    <s:param>contactForm.firstName</s:param>
                                </s:fielderror>
                            </label>
                        </td>
                </tr>
			    <tr>

                        <td>
                            <c:if test="${lastName_error}">
                            <label class="errorClass">
                            </c:if>

                            <c:if test="${!lastName_error}">
                                <label class="labelClass">
                            </c:if>

                            Last Name :
                            </label>
                        </td>
                        <td>
                            <s:textfield key="contactForm.lastName" value="%{contactForm.lastName}"/>
                        </td>
                        <td>
         
                            <label class="errorClass">
                                <s:fielderror theme="mytheme">
                                    <s:param>contactForm.lastName</s:param>
                                </s:fielderror>
                            </label>
                        </td>
                 </tr>
		</table>

            
        </s:form>
            </div>
        <div>

            <s:if test="hasFieldErrors()">
                <script type="text/javascript">
                    document.getElementById('formPlaceholder').style.display = "block";

                    if (gup('id') != "")
                    document.getElementById('calcForm_contactForm_id').value = gup('id');
                     
                </script>
                <c:if test="${contactForm.persistCommand eq 'Add'}">
                    <input type="button" id="inputSubmitId" onClick="calcForm.action='${contextPath}/Add.htm';calcForm.submit();" value="Add"/>
                    <script type="text/javascript">
                        if (gup('id') != '') {
                        document.getElementById('inputSubmitId').value = 'Update';
                        document.getElementById('inputSubmitId').onclick = function (){
                          calcForm.action='${contextPath}/Update.htm?id='+gup('id');
                          calcForm.submit();
                        }
                            }
                    </script>                    
                </c:if>
                <c:if test="${contactForm.persistCommand eq 'Update'}">
                </c:if>
            </s:if>


            <c:if test="${contactForm.rendered}">
                <c:if test="${contactForm.persistCommand eq 'Add'}">
                    <input type="button" onClick="calcForm.action='${contextPath}/Add.htm';calcForm.submit();" value="Add"/>
                </c:if>
                <c:if test="${contactForm.persistCommand eq 'Update'}">
                    <input type="button" onClick="document.getElementById('calcForm_contactForm_id').value='${contactForm.contact.id}';calcForm.action='${contextPath}/Update.htm?id=${contactForm.contact.id}';calcForm.submit();" value="Update"/>
                </c:if>
            </c:if>
        </div>


        <c:if test="${!contactForm.hideAddNewLink}">
            <s:if test="!hasFieldErrors()">
                <a href="#" onclick="calcForm.action='${contextPath}/HideAddNew.htm';calcForm.submit();">Add New...</a>
            </s:if>
        </c:if>


        <c:if test="${contactForm.hideAddNewLink}">
            <script type="text/javascript">
                document.getElementById('formPlaceholder').style.display = "block";
            </script>
        </c:if>

        <c:if test="${contactForm.recordsCount > 0}">

           <table class="contactTable">
               <thead>
               <tr>
               <th class="headerTable" scope="col">Name</th>
               <th class="headerTable" scope="col">Action</th>
               </tr>
               </thead>

           <c:forEach items="${contactForm.records}"  var="contact" varStatus="index">

               <tbody>

                   <c:if test="${(index.count % 2) == 1}">
                       <c:set var="rowClass" value="oddRow"/>
                   </c:if>
                   <c:if test="${(index.count % 2) == 0}">
                       <c:set var="rowClass" value="evenRow"/>
                   </c:if>


               <tr class="${rowClass}">
                   <td class="normal">
                       <c:out value="${contact.lastName}"/>,
                       <c:out value="${contact.firstName}"/>,
                   </td>

                   <td class="centered">
                       <table>
                           <tbody>
                               <tr>
                                   <td>
                                       <a href="#" onclick="document.getElementById('calcForm_contactForm_id').value='${contact.id}';calcForm.action='${contextPath}/Remove.htm?id=${contact.id}';calcForm.submit();">remove</a>
                                   </td>
                                   <td>
                                       <a href="#" onclick="document.getElementById('calcForm_contactForm_id').value='${contact.id}';calcForm.action='${contextPath}/Edit.htm?id=${contact.id}';calcForm.submit();">edit</a>
                                   </td>
                               </tr>
                           </tbody>
                       </table>
                   </td>
               </tr>
               </tbody>
           </c:forEach>

           </table>
           </c:if>
        

        <br/>
	</body>
</html>

