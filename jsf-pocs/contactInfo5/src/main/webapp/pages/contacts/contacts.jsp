<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Contacts</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/main.css" />
</head>

<body>
<f:view>
	<h3>Contacts (6th version)</h3>
	<f:phaseListener binding="#{contactController.phaseListener}" />

	<h:messages infoClass="infoClass" errorClass="errorClass"
		layout="table" globalOnly="true" />

	<h:form>
		<h:commandLink binding="#{contactController.addNewCommand}"
			action="#{contactController.addNew}" value="Add New..." />
	</h:form>

	<h:form binding="#{contactController.form}" rendered="false"
		styleClass="form">

		<h4>Contact Form</h4>

		<h:inputHidden value="#{contactController.contact.id}" />
		<h:inputHidden value="#{contactController.edit}" />
		<h:panelGrid columns="9" columnClasses="input">
			<%-- First Name --%>
			<h:outputLabel value="First Name" for="firstName" accesskey="f" />
			<h:inputText id="firstName" label="First Name" required="true"
				value="#{contactController.contact.firstName}" size="10">
				<f:validateLength minimum="2" maximum="25" />
			</h:inputText>
			<h:message for="firstName" errorClass="errorClass" />

			<%-- Last Name --%>
			<h:outputLabel value="Last Name" for="lastName" accesskey="l" />
			<h:inputText id="lastName" required="true"
				value="#{contactController.contact.lastName}" size="15" />
			<h:message for="lastName" errorClass="errorClass" />

			<%-- Group --%>
			<h:outputLabel value="Group" for="group" accesskey="g" />
			<h:selectOneMenu id="group" validatorMessage="required"
				value="#{contactController.contact.group}">
				<f:selectItems value="#{contactController.availableGroups}" />
			</h:selectOneMenu>
			<h:message for="group" errorClass="errorClass" />

			<%-- Type --%>
			<h:outputLabel value="Type" for="type" accesskey="t" />
			<h:selectOneRadio id="type" value="#{contactController.contact.type}">
				<f:selectItem itemValue="PERSONAL" itemLabel="personal" />
				<f:selectItem itemValue="BUSINESS" itemLabel="business" />
			</h:selectOneRadio>
			<h:message for="type" errorClass="errorClass" />

			<%-- active --%>
			<h:outputLabel value="Active" for="active" accesskey="a" />
			<h:selectBooleanCheckbox id="active"
				value="#{contactController.contact.active}" />
			<h:message for="active" errorClass="errorClass" />

			<%-- email --%>
			<h:outputLabel value="email" for="email" accesskey="e" />
			<h:inputText id="email" size="20"
				value="#{contactController.contact.email}" />
			<h:message for="email" errorClass="errorClass" />

			<%-- zip --%>
			<h:outputLabel value="Zip" for="zip" accesskey="zip" />
			<h:inputText id="zip" size="5"
				value="#{contactController.contact.zip}">
				<f:validator validatorId="arcmind.zipCode" />
			</h:inputText>
			<h:message for="zip" errorClass="errorClass" />

			<%-- age --%>
			<h:outputLabel value="Age" for="age" accesskey="age" />
			<h:inputText id="age" size="3"
				value="#{contactController.contact.age}">
				<f:validateLongRange minimum="0" maximum="150" />
			</h:inputText>
			<h:message for="age" errorClass="errorClass" />

			<%-- birthDate --%>
			<h:outputLabel value="Birth Date" for="birthDate" accesskey="b" />
			<h:inputText id="birthDate"
				value="#{contactController.contact.birthDate}">
				<f:convertDateTime pattern="MM/yyyy" />
			</h:inputText>
			<h:message for="birthDate" errorClass="errorClass" />



		</h:panelGrid>
		<div style="border: solid #000 1px">
		<h4>Phone Numbers</h4>
		<h:panelGrid columns="6" columnClasses="padding">
			<%-- Work --%>
			<h:outputLabel value="Work" for="work" accesskey="w" />
			<h:inputText id="work"
				value="#{contactController.contact.workPhoneNumber}" size="11"
				validator="#{contactValidators.validatePhone}" />
			<h:message for="work" errorClass="errorClass" />
			<%-- Home --%>
			<h:outputLabel value="Home" for="home" accesskey="h" />
			<h:inputText id="home"
				value="#{contactController.contact.homePhoneNumber}" size="11"
				validator="#{contactValidators.validatePhone}" />
			<h:message for="home" errorClass="errorClass" />
			<%-- Mobile --%>
			<h:outputLabel value="Mobile" for="mobile" accesskey="m" />
			<h:inputText id="mobile"
				value="#{contactController.contact.mobilePhoneNumber}" size="11"
				validator="#{contactValidators.validatePhone}" />
			<h:message for="mobile" errorClass="errorClass" />
		</h:panelGrid></div>
		<%-- Tags --%>
		<div style="border: solid #000 1px"><h:panelGrid columns="3">
			<h:outputLabel value="Tags" for="tags" accesskey="t"
				style="font: large;" />
			<h:selectManyCheckbox id="tags"
				value="#{contactController.contact.tags}">
				<f:selectItems value="#{contactController.availableTags}" />
			</h:selectManyCheckbox>
			<h:message for="tags" errorClass="errorClass" />
		</h:panelGrid></div>

		<%-- Description --%>
		<div style="border: solid #000 1px"><h:outputLabel
			value="Description" for="description" accesskey="d"
			style="font: large;" /> <h:inputTextarea id="description" cols="80"
			rows="5" value="#{contactController.contact.description}" /> <h:message
			for="description" errorClass="errorClass" /></div>

		<h:panelGroup>
			<h:commandButton binding="#{contactController.persistCommand}"
				action="#{contactController.persist}" />
			<h:commandButton type="reset" value="Reset" />
			<h:commandButton action="#{contactController.cancel}" value="Cancel"
				immediate="true" />
		</h:panelGroup>
	</h:form>

	<h:form>
		<h:dataTable value="#{contactController.contacts}" var="contact"
			rowClasses="oddRow, evenRow"
			rendered="#{not empty contactController.contacts}"
			styleClass="contactTable" headerClass="headerTable"
			columnClasses="normal,normal,normal,centered">
			<h:column>
				<%-- Name column --%>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Name" />
					</h:column>
				</f:facet>
				<h:outputText value="#{contact.lastName}, #{contact.firstName}" />
			</h:column>
			<h:column>
				<%-- Group column --%>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Group" />
					</h:column>
				</f:facet>
				<h:outputText value="#{contact.group.name}" />
			</h:column>
			<h:column>
				<%-- Type column --%>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Type" />
					</h:column>
				</f:facet>
				<h:outputText value="#{contact.type}" />
			</h:column>
			<h:column>
				<%-- Tag names --%>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Tag names" />
					</h:column>
				</f:facet>
				<h:outputText value="#{contact.tagNames}" />
			</h:column>
			<h:column>
				<%-- Action column (remove / edit) --%>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Action" />
					</h:column>
				</f:facet>
				<h:panelGrid columns="2">
					<h:commandLink value="remove" action="#{contactController.remove}">
						<f:setPropertyActionListener
							target="#{contactController.selectedContact}" value="#{contact}" />
					</h:commandLink>
					<h:commandLink value="edit" action="#{contactController.read}">
						<f:setPropertyActionListener
							target="#{contactController.selectedContact}" value="#{contact}" />
					</h:commandLink>
				</h:panelGrid>
			</h:column>

		</h:dataTable>
	</h:form>



</f:view>
</body>

</html>