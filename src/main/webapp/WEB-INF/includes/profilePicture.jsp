<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${sessionScope.profilePicture != null}">
    <img src="/images/<c:out value="${sessionScope.profilePicture}"/>" alt="Profile Picture" width="100" height="100">
  </c:when>
  <c:otherwise>
    <img src="https://www.mico.dk/wp-content/uploads/2020/05/blank-profile-picture-973460_1280.png" width="100" height="100">
  </c:otherwise>
</c:choose>