<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">

  <div class="largeBoxHeader">
    <div class="title"><fmt:message key="label.bestehende.projekte"/></div>
  </div>

  <div class="largeBoxContent">
    <p><fmt:message key="label.bestehende.projekte.beschreibung"/></p>

    <div class="buttons">
      <c:url var="url" scope="page" value="/adm/projekte/view.do"/>
      <form action="${url}">
        <input type="hidden" name="dispatch" value="list"/>
        <select name="filtergruppe">
          <option value="all"><fmt:message key="label.alle.projektgruppen"/></option>
          <c:forEach var="groupmap" items="${projektgruppen}">
            <option value="${groupmap.projektgruppeId}"<c:if test="${groupmap.projektgruppeId == filtergruppe}"> selected</c:if>>${groupmap.name}</option>
          </c:forEach>
        </select>
        <input type="submit" value="<fmt:message key="button.label.filtern"/>"/>
      </form>
    </div><br />

    <table>
      <tr>
        <th><fmt:message key="label.projektgruppe"/></th>
        <th><fmt:message key="label.projekte"/></th>
        <th colspan="3"><fmt:message key="label.fragebogen.projektattraktivitaet"/></th>
        <th colspan="3"><fmt:message key="label.fragebogen.nutzenattraktivitaet"/></th>
        <th colspan="3"><fmt:message key="label.fragebogen.operativer.nutzen"/></th>
        <th class="remove"><fmt:message key="label.loeschen"/></th>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
        <th class="remove"><fmt:message key="label.link"/></th>
        <th class="remove"><fmt:message key="label.status"/></th>
        <th><fmt:message key="label.zaehler"/></th>
        <th class="remove"><fmt:message key="label.link"/></th>
        <th class="remove"><fmt:message key="label.status"/></th>
        <th><fmt:message key="label.zaehler"/></th>
        <th class="remove"><fmt:message key="label.link"/></th>
        <th class="remove"><fmt:message key="label.status"/></th>
        <th><fmt:message key="label.zaehler"/></th>
        <th>&nbsp;</th>
      </tr>

      <c:forEach var="groupmap" items="${projektgruppen}">

        <c:forEach var="map" items="${projekte}" varStatus="status">

          <c:if test="${map.projektgruppeId == groupmap.projektgruppeId}">

            <tr class="${status.index%2==0?'even':'odd'}">

              <td>${groupmap.name}</td>

              <c:url var="url" scope="page" value="/adm/projekte/view.do">
                <c:param name="projektId" value="${map.projektId}"/>
                <c:param name="dispatch" value="prepare"/>
              </c:url>
              <td><a href="<c:out value="${url}"/>"><c:out value="${map.name}"/></a></td>
					
              <c:choose>
                <c:when test="${map.paUID != null}">
                  <c:url var="url" scope="page" value="/fragebogen/login/view.do">
                    <c:param name="dispatch" value="show"/>
                    <c:param name="p" value="${map.projektUID}"/>
                    <c:param name="pa" value="${map.paUID}"/>
                  </c:url>
                  <td><a href="<c:out value="${url}"/>">Link</a></td>
                  <td class="centered">
                    <c:url var="url" scope="page" value="/adm/projekte/view.do">
                      <c:param name="projektId" value="${map.projektId}"/>
                      <c:param name="type" value="pa"/>
                      <c:param name="dispatch" value="changeStatus"/>
                    </c:url>
                    <a href="<c:out value="${url}"/>">
                      <c:choose>
                        <c:when test="${map.paStatus}">
                          <img
                              src="<html:rewrite page='/resources/images/active.gif'/>"
                              alt="<fmt:message key="label.aktiv"/>"
                              title="<fmt:message key="label.aktiv"/>"/>
                          </c:when>
                        <c:otherwise>
                          <img
                              src="<html:rewrite page='/resources/images/inactive.gif'/>"
                              alt="<fmt:message key="label.inaktiv"/>"
                              title="<fmt:message key="label.inaktiv"/>"/>
                        </c:otherwise>
                      </c:choose>
                    </a>
                  </td>
                  <td>${map.anzahlPaResultate}</td>
                </c:when>
                <c:otherwise>
                  <td colspan="3">-</td>
                </c:otherwise>
              </c:choose>

              <c:choose>
                <c:when test="${map.naUID != null}">
                  <c:url var="url" scope="page" value="/fragebogen/login/view.do">
                    <c:param name="dispatch" value="show"/>
                    <c:param name="p" value="${map.projektUID}"/>
                    <c:param name="na" value="${map.naUID}"/>
                  </c:url>
                  <td><a href="<c:out value="${url}"/>">Link</a></td>
                  <td class="centered">
                    <c:url var="url" scope="page" value="/adm/projekte/view.do">
                      <c:param name="projektId" value="${map.projektId}"/>
                      <c:param name="type" value="na"/>
                      <c:param name="dispatch" value="changeStatus"/>
                    </c:url>
                    <a href="<c:out value="${url}"/>">
                      <c:choose>
                        <c:when test="${map.naStatus}">
                          <img
                              src="<html:rewrite page='/resources/images/active.gif'/>"
                              alt="<fmt:message key="label.aktiv"/>"
                              title="<fmt:message key="label.aktiv"/>"/>
                        </c:when>
                        <c:otherwise>
                          <img
                              src="<html:rewrite page='/resources/images/inactive.gif'/>"
                              alt="<fmt:message key="label.inaktiv"/>"
                              title="<fmt:message key="label.inaktiv"/>"/>
                        </c:otherwise>
                      </c:choose>
                    </a>
                  </td>
                  <td>${map.anzahlNaResultate}</td>
                </c:when>
                <c:otherwise>
                  <td colspan="3">-</td>
                </c:otherwise>
              </c:choose>

              <c:choose>
                <c:when test="${map.opNuUID != null}">
                  <c:url var="url" scope="page" value="/fragebogen/login/view.do">
                    <c:param name="dispatch" value="show"/>
                    <c:param name="p" value="${map.projektUID}"/>
                    <c:param name="o" value="${map.opNuUID}"/>
                  </c:url>
                  <td><a href="<c:out value="${url}"/>">Link</a></td>
                  <td class="centered">
                    <c:url var="url" scope="page" value="/adm/projekte/view.do">
                      <c:param name="projektId" value="${map.projektId}"/>
                      <c:param name="type" value="opNu"/>
                      <c:param name="dispatch" value="changeStatus"/>
                    </c:url>
                    <a href="<c:out value="${url}"/>">
                      <c:choose>
                        <c:when test="${map.opNuStatus}">
                          <img
                              src="<html:rewrite page='/resources/images/active.gif'/>"
                              alt="<fmt:message key="label.aktiv"/>"
                              title="<fmt:message key="label.aktiv"/>"/>
                        </c:when>
                        <c:otherwise>
                          <img
                              src="<html:rewrite page='/resources/images/inactive.gif'/>"
                              alt="<fmt:message key="label.inaktiv"/>"
                              title="<fmt:message key="label.inaktiv"/>"/>
                        </c:otherwise>
                      </c:choose>
                    </a>
                  </td>
                  <td>${map.anzahlOpNuResultate}</td>
                </c:when>
                <c:otherwise>
                  <td colspan="3">-</td>
                </c:otherwise>
              </c:choose>


              <c:url var="url" scope="page" value="/adm/projekte/view.do">
                <c:param name="projektId" value="${map.projektId}"/>
                <c:param name="dispatch" value="deleteQuestion"/>
              </c:url>
              <td class="centered">
                <a href="<c:out value="${url}"/>">
                  <img src="<html:rewrite page='/resources/images/delete.gif'/>" alt="löschen" />
                </a>
              </td>

            </tr>

          </c:if>

        </c:forEach>

      </c:forEach>

    </table>
		
  </div>

  <div class="buttons">
    <c:url var="url" scope="page" value="/adm/index.do"/>
    <form action="${url}">
      <input type="submit" value="<fmt:message key="button.label.zurueck"/>"/>
    </form>
  </div>
</div>