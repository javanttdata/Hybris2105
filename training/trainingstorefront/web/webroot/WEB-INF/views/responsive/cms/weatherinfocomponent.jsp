<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:useBean id="result" scope="request" type="de.hybris.training.core.hgbrasil.response.WeatherInfoResultsResponse"/>

<div>
    <c:choose>
        <c:when test="${not empty result}">
            <p>${result.city_name}</p>
            <p>${result.temp}&deg; - ${result.description}</p>
        </c:when>

       <c:otherwise>
            <p>Erro no HG-Brasil</p>
       </c:otherwise>
    </c:choose>
</div>