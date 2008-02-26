<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Contacts</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/main.css" />
    <meta http-equiv="cache-control" content="no-cache"/>
</head>

<body>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

    <h4>Contacts (Struts 1 with multiaction)</h4>

    <label class="errorClass">
        <html:errors property="<%= org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE %>"/>
    </label>
    <logic:messagesPresent message="true">
        <html:messages id="message" property="<%= org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE %>" message="true">
            <label class="infoClass"><bean:write name="message" /></label>
        </html:messages>
    </logic:messagesPresent>    

    <c:if test="${!contactForm.hideAddNewLink}">
        <html:form action="/multiaction.htm?method=hideAddNew" method="post" styleId="addnewform">
            <a href="${contextPath}/multiaction.htm?method=hideAddNew" type="button" onclick="addnewform.submit();">Add New...</a>
        </html:form>
    </c:if>

    <c:if test="${contactForm.rendered}">

    <html:form styleId="contactForm" action="/multiaction.htm?method=addNew" method="post" styleClass="form">

        <c:if test="${contactForm.persistCommand eq 'Add'}">
            <c:set var="id" value="0"/>
        </c:if>
        <c:if test="${contactForm.persistCommand eq 'Update'}">
            <c:set var="id" value="${contactForm.id}"/>
        </c:if>

        <input type="hidden" name="id" value="${id}">
        <html:hidden property="persistCommand" value="${contactForm.persistCommand}"/>

    <table class="formGrid">
                <tr>
                    <td>
                        <logic:messagesPresent property="firstName">
                            <label class="errorClass">
                        </logic:messagesPresent>
                        <logic:messagesNotPresent property="firstName">
                        <label class="labelClass">
                        </logic:messagesNotPresent>
                        First Name
                       </label>
                    </td>
                    <td>
                        <html:text property="firstName" value="${contactForm.firstName}"/>
                    </td>
                    <td>
                        <label class="errorClass"><html:errors property="firstName"/></label>
                    </td>
                <td>
                    <logic:messagesPresent property="lastName">
                        <label class="errorClass">
                    </logic:messagesPresent>
                    <logic:messagesNotPresent property="lastName">
                        <label class="labelClass">
                    </logic:messagesNotPresent>
                   Last Name
                    </label>
                </td>
                <td>
                    <html:text property="lastName"  value="${contactForm.lastName}"/>
                </td>
                <td>
                    <label class="errorClass"><html:errors property="lastName"/></label>
                </td>
            </tr>
            <tr>
                <td>
                    <c:if test="${contactForm.persistCommand eq 'Add'}">
                    <input type="button" onclick="contactForm.action='${contextPath}/multiaction.htm?method=addNew';contactForm.submit();" value="${contactForm.persistCommand}"/>
                    </c:if>
                    <c:if test="${contactForm.persistCommand eq 'Update'}">
                    <input type="button" onclick="contactForm.action='${contextPath}/multiaction.htm?method=update';contactForm.submit();" value="${contactForm.persistCommand}"/>
                    </c:if>
                </td>
                <td>&nbsp;</td>

            </tr>

        </table>
    </html:form>
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
                                <a href="${contextPath}/multiaction.htm?method=remove&id=${contact.id}">remove</a>
                            </td>
                            <td>
                                <a href="${contextPath}/multiaction.htm?method=edit&id=${contact.id}">edit</a>
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

</body>

</html>