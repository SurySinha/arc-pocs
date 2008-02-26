
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
 
<html> 
	<head>
		<title>Calculator Application</title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	</head>
	<body>
		<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
		<html:form styleId="calcForm"  action="multiaction.html" method="post">
		<h4>Calculator Example (Struts, with Multiaction)</h4>
		<label class="errorClass"><html:errors property="<%= org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE %>"/></label>
		<logic:messagesPresent message="true">
			<html:messages id="message" property="<%= org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE %>" message="true">
        		<label class="infoClass"><bean:write name="message" /></label>
     		</html:messages>
     	</logic:messagesPresent>
		<table class="formGrid">
			<tr>
				<td>
					<logic:messagesPresent property="firstNumber">
						<label class="errorClass">
					</logic:messagesPresent>
					<logic:messagesNotPresent property="firstNumber">
						<label class="labelClass">
					</logic:messagesNotPresent>
					First Number : 
					</label>
				</td>
				<td>
					<html:text property="firstNumber"/>
				</td>
				<td>
					<label class="errorClass"><html:errors property="firstNumber"/></label>
				</td>
			</tr>
			<tr>
				<td>
					<logic:messagesPresent property="secondNumber">
						<label class="errorClass">
					</logic:messagesPresent>
					<logic:messagesNotPresent property="secondNumber">
						<label class="labelClass">
					</logic:messagesNotPresent>
					Second Number : 
					</label>
				</td>
				<td>
					<html:text property="secondNumber"/>
				</td>
				<td>
					<label class="errorClass"><html:errors property="secondNumber"/></label>
				</td>
			</tr>
		</table>			
	
		</html:form>
		<div>
			
			<input type="button" onClick="calcForm.action='${contextPath}/multiaction.html?methodName=add';calcForm.submit();" value="Add"/>
			<input type="button" onClick="calcForm.action='${contextPath}/multiaction.html?methodName=multiply';calcForm.submit();" value="Multiply"/>
			<input type="button" onClick="calcForm.action='${contextPath}/multiaction.html?methodName=divide';calcForm.submit();" value="Divide"/>
			<input type="button" onClick="calcForm.action='${contextPath}/multiaction.html?methodName=clear';calcForm.submit();" value="Clear"/>
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

