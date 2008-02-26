<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

    <h4>Contacts (Spring MVC)</h4>

    <c:if test="${contactForm.message ne null}">
        <label class="infoClass">
            <fmt:message key="${contactForm.message}">
                <c:if test="${contactForm.contact ne null}">
                    <fmt:param value="${contactForm.contact}"/>
                </c:if>
            </fmt:message>
        </label>
    </c:if>

    <c:if test="${contactForm.error ne null}">
        <label class="errorClass">
            <fmt:message key="${contactForm.error}"/>
        </label>
    </c:if>

    <c:if test="${!contactForm.hideAddNewLink}">
        <form action="/hideAddNew.htm" method="post" id="addnewform">
            <a href="${contextPath}/hideAddNew.htm" type="button" onclick="addnewform.submit();">Add New...</a>
        </form>
    </c:if>

    <c:if test="${contactForm.rendered}">

    <form id="contactForm" action="${contextPath}/addNew.htm" method="post" class="form">

        <c:if test="${contactForm.persistCommand eq 'Add'}">
            <c:set var="id" value="0"/>
        </c:if>
        <c:if test="${contactForm.persistCommand eq 'Update'}">
            <c:set var="id" value="${contactForm.id}"/>
        </c:if>

        <input type="hidden" name="id" value="${id}">
        <input type="hidden" name="persistCommand" value="${contactForm.persistCommand}"/>

        <table class="formGrid">
                <tr>
                    <spring:bind path="contactForm.firstName">
                    <td>
                            <c:if test="${status.errorMessage ne ''}">
                                <label class="errorClass">
                            </c:if>

                            <c:if test="${status.errorMessage eq ''}">
                                <label class="labelClass">
                            </c:if>
                        First Name
                        </label>

                    </td>
                    <td>
                        <input name="<c:out value="${status.expression}"/>"
                                value="<c:out value="${status.value}"/>">
                    </td>
                    <td>
                        <label class="errorClass">
                            <c:if test="${status.errorMessage ne ''}">
                                <label class="errorClass">
                                    <c:out value="${status.errorMessage}"/>
                                </label>
                            </c:if>
                        </label>
                    </td>
                    </spring:bind>
                <td>
                    <spring:bind path="contactForm.lastName">
                            <c:if test="${status.errorMessage ne ''}">
                                <label class="errorClass">
                            </c:if>

                            <c:if test="${status.errorMessage eq ''}">
                                <label class="labelClass">
                            </c:if>
                   Last Name
                    </label>
                </td>
                <td>
                    <input name="<c:out value="${status.expression}"/>"
                            value="<c:out value="${status.value}"/>">
                </td>
                <td>
                    <label class="errorClass">
                        <c:if test="${status.errorMessage ne ''}">
                            <label class="errorClass">
                                <c:out value="${status.errorMessage}"/>
                            </label>
                        </c:if>
                    </label>

                    </spring:bind>                    
                </td>
            </tr>
            <tr>
                <td>
                    <c:if test="${contactForm.persistCommand eq 'Add'}">
                    <input type="button" onclick="contactForm.action='${contextPath}/addNew.htm';contactForm.submit();" value="${contactForm.persistCommand}"/>
                    </c:if>
                    <c:if test="${contactForm.persistCommand eq 'Update'}">
                    <input type="button" onclick="contactForm.action='${contextPath}/update.htm';contactForm.submit();" value="${contactForm.persistCommand}"/>
                    </c:if>
                </td>
                <td>&nbsp;</td>

            </tr>

        </table>
    </form>
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
                                <a href="${contextPath}/remove.htm?id=${contact.id}">remove</a>
                            </td>
                            <td>
                                <a href="${contextPath}/edit.htm?id=${contact.id}">edit</a>
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