<%@ attribute name="errorMessage" %>
<%@ attribute name="successMessage" %>
<%@ attribute name="infoMessage" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <c:if test="${!successMessage.equals('')}">
            <div class="alert alert-success col-lg-12" id="alert">
                <button type="button" class="close" data-dismiss="alert">x</button>
                <strong>
                        ${successMessage}
                </strong>
            </div>
        </c:if>

        <c:if test="${!infoMessage.equals('')}">
            <div class="alert alert-info col-lg-12" id="alert">
                <button type="button" class="close" data-dismiss="alert">x</button>
                <strong>
                        ${infoMessage}
                </strong>
            </div>
        </c:if>
        <c:if test="${!errorMessage.equals('')}">
            <div class="alert alert-danger col-lg-12" id="alert">
                <button type="button" class="close" data-dismiss="alert">x</button>
                <strong>
                        ${errorMessage}
                </strong>
            </div>
        </c:if>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#alert").hide();
        $("#alert").fadeTo(2000, 500).slideUp(500, function () {
            $("#alert").slideUp(500);
        });
    });
</script>

