<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<h1>Message Digest testing/benchmarking portlet</h1>

<form>
    <label for="mdEnginesList">Message Digest Algoritm</label>
    <select name="mdEnginesList" id="mdEnginesList<portlet:namespace>">
    </select>
    <label for="text">Text to hash</label>
    <input type="text" name="txtToHash" id="txtToHash"/>
    <hr/>
    <button type="button" name="btnHash" id="btnHash<portlet:namespace>">Hash</button>
    <hr/>
    Hash result:<div id="txtHashResult<portlet:namespace>">--------</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
    var fillDigestEnginesCombo = function(digestList) {
        var anchor = $("#mdEnginesList<portlet:namespace>");
        
        for (index = 0; index < digestList.length; index++) {
            var item = digestList[index];

            $("<option>").
                attr("value", item.name).
                attr("id", item.name).
                html(item.name).
                appendTo(anchor);
        }
    };

    var ajaxJsonGet = function(url, params, onReceiveHandler, blocking) {
        var settings = {
            method: "GET",
            dataType: "json",
            data : params,
            cache : false,
            success : onReceiveHandler,
            error: function(xhr, ajaxOptions, thrownError) {
                console.debug("ajaxJsonGet.errorHandler");
                console.debug(xhr.status);
                console.debug(thrownError);
            }
        };

        if (blocking === undefined || blocking === false)
            settings.async = true;
        else
            settings.async = false;

        $.ajax(url, settings);
    };
    
    var loadKnownEngines = function() {
        ajaxJsonGet(
            "${listKnownEnginesUrl}", 
            {
                command: "listKnownEngines"
            },
            function(data) {
                fillDigestEnginesCombo(data);
            }
        );
    };

    $("#btnHash<portlet:namespace>").click(function() {
        ajaxJsonGet(
            "${computeHashUrl}", 
            {
                command : "computeHash",
                engine  : $("#mdEnginesList<portlet:namespace>").val(),
                message : $("#txtToHash<portlet:namespace>").val()
            },
            function(data) {
                $("#txtHashResult").html(data.digest);
            }
        );
    });
    
    loadKnownEngines();
});
</script>