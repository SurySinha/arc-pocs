<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Calculator Application</title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	</head>
	<body>

        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

        <form id="calcForm" action="calculator.htm" method="post">
            <h4>Calculator Example (Spring MVC)</h4>

            <spring:hasBindErrors name="calcForm">
                <label class="errorClass">
                    <fmt:message key="message.failure"/>
                </label>
            </spring:hasBindErrors>

            <c:if test="${calcForm.message ne null}">
                <label class="infoClass">
                    <fmt:message key="${calcForm.message}"/>
                </label>
            </c:if>

            <c:if test="${calcForm.error ne null}">
                <label class="errorClass">
                    <fmt:message key="${calcForm.error}"/>
                </label>
            </c:if>

            <table class="formGrid">
                <tr>
                    <spring:bind path="calcForm.firstNumber">
                        <td>
                            <c:if test="${status.errorMessage ne ''}">
                                <label class="errorClass">
                            </c:if>

                            <c:if test="${status.errorMessage eq ''}">
                                <label class="labelClass">
                            </c:if>

                            First Number :
                             </label>
                        </td>
                        <td>
                            <input name="<c:out value="${status.expression}"/>"
                                   value="<c:out value="${status.value}"/>">
                        </td>
                        <td>
                            <c:if test="${status.errorMessage ne ''}">
                            <label class="errorClass">
                                <c:out value="${status.errorMessage}"/>
                            </label>
                            </c:if>
                        </td>
                    </spring:bind>
                </tr>
			    <tr>

                    <spring:bind path="calcForm.secondNumber">
                        <td>
                            <c:if test="${status.errorMessage ne ''}">
                            <label class="errorClass">
                            </c:if>

                            <c:if test="${status.errorMessage eq ''}">
                                <label class="labelClass">
                            </c:if>

                            Second Number :
                            </label>
                        </td>
                        <td>

                            <input name="<c:out value="${status.expression}"/>"
                                    <c:choose>
                                    <c:when test="${status.errorCode eq 'errors.zerodivision'}">
                                    value="1"
                                    </c:when>
                                    <c:otherwise>
                                    value="<c:out value="${status.value}"/>">
                                    </c:otherwise>
                                    </c:choose>
                        </td>
                        <td>
                            <c:if test="${status.errorMessage ne ''}">
                            <label class="errorClass">
                                <c:out value="${status.errorMessage}"/>
                            </label>
                            </c:if>
                        </td>
                    </spring:bind>
                 </tr>
		</table>

		</form>
		<div>

			<input type="button" onClick="calcForm.action='${contextPath}/add.htm';calcForm.submit();" value="Add"/>
			<input type="button" onClick="calcForm.action='${contextPath}/multiply.htm';calcForm.submit();" value="Multiply"/>
			<input type="button" onClick="calcForm.action='${contextPath}/divide.htm';calcForm.submit();" value="Divide"/>
			<input type="button" onClick="calcForm.action='${contextPath}/clear.htm';calcForm.submit();" value="Clear"/>
		</div>

		<c:if test="${calcForm.visibleResult}">
			<h4>Results</h4>
			<table class="resultGrid">
				<tr>
					<td class="oddRow">First Number : <c:out value="${calcForm.firstNumber}"/></td>
				</tr>
				<tr>
					<td class="evenRow">Second Number : <c:out value="${calcForm.secondNumber}"/></td>
				</tr>
				<tr>
					<td class="oddRow">Result : <c:out value="${calcForm.result}"/></td>
				</tr>
			</table>
		</c:if>
		<br/>
	</body>
</html>

