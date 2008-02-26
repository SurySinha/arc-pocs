<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>Calculator Application</title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	</head>
	<body>

        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

        <s:form id="calcForm" action="Calculator.htm" method="post">
            <h4>Calculator Example (Spring MVC)</h4>


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

            <s:if test="hasFieldErrors()">
                  <s:iterator value="fieldErrors">
                      <s:iterator>
                          <s:if test="key eq 'calculatorForm.firstNumber'">
                              <c:set var="firstNumber_error" value="true"/>
                          </s:if>
                          <s:if test="key eq 'calculatorForm.secondNumber'">
                              <c:set var="secondNumber_error" value="true"/>
                          </s:if>
                      </s:iterator>
                  </s:iterator>
            </s:if>


            <table class="formGrid">
                <tr>
                        <td>

                            <c:if test="${firstNumber_error}">
                                <label class="errorClass">
                            </c:if>
                            <c:if test="${!firstNumber_error}">
                                <label class="labelClass">
                            </c:if>

                            First Number :
                             </label>
                        </td>
                        <td>
                            <s:textfield key="calculatorForm.firstNumber" value="%{calculatorForm.firstNumber}"/>
                        </td>
                        <td>
                            <label class="errorClass">
                                <s:fielderror theme="mytheme">
                                    <s:param>calculatorForm.firstNumber</s:param>
                                </s:fielderror>
                            </label>
                        </td>
                </tr>
			    <tr>

                        <td>
                            <c:if test="${secondNumber_error}">
                            <label class="errorClass">
                            </c:if>

                            <c:if test="${!secondNumber_error}">
                                <label class="labelClass">
                            </c:if>

                            Second Number :
                            </label>
                        </td>
                        <td>
                            <s:textfield key="calculatorForm.secondNumber" value="%{calculatorForm.secondNumber}"/>
                        </td>
                        <td>
                            <label class="errorClass">
                                <s:fielderror theme="mytheme">
                                    <s:param>calculatorForm.secondNumber</s:param>
                                </s:fielderror>
                            </label>
                        </td>
                 </tr>
		</table>

		</s:form>
		<div>

			<input type="button" onClick="calcForm.action='${contextPath}/Add.htm';calcForm.submit();" value="Add"/>
			<input type="button" onClick="calcForm.action='${contextPath}/Multiply.htm';calcForm.submit();" value="Multiply"/>
			<input type="button" onClick="calcForm.action='${contextPath}/Divide.htm';calcForm.submit();" value="Divide"/>
			<input type="button" onClick="calcForm.action='${contextPath}/Clear.htm';calcForm.submit();" value="Clear"/>
		</div>

		<s:if test="%{calculatorForm.visibleResult}">
            <h4>Results</h4>
			<table class="resultGrid">
				<tr>
					<td class="oddRow">First Number : <s:label value="%{calculatorForm.firstNumber}"/></td>
				</tr>
				<tr>
					<td class="evenRow">Second Number :  <s:label value="%{calculatorForm.secondNumber}"/></td>
				</tr>
				<tr>
					<td class="oddRow">Result : <s:label value="%{calculatorForm.result}"/></td>
				</tr>
			</table>
		</s:if>
		<br/>
	</body>
</html>

