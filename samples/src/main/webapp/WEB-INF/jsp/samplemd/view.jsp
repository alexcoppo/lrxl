<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<h1>Message Digest testing/benchmarking portlet</h1>

<form>
    <label for="mdEnginesList">Message Digest Algoritm</label>
    <select name="mdEnginesList" id="mdEnginesList">
    </select>
    <label for="text">Text to hash</label>
    <input type="text" name="txtToHash" id="txtToHash"/>
    <hr/>
    <button type="button" name="btnHash" id="btnHash">Hash</button>
    <hr/>
    Hash result:<div id="txtHashResult">--------</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
    console.debug("---------------------");
    
    var fillDigestEnginesCombo = function(digestList) {
        var anchor = $("#mdEnginesList");
        
        for (index = 0; index < digestList.length; index++) {
            var item = digestList[index];

            $("<option>").
                attr("value", item.name).
                attr("id", item.name).
                html(item.name).
                appendTo(anchor);
        }
    };
    
    var ajaxErrorHandler = function(xhr, ajaxOptions, thrownError) {
        console.debug("ajaxErrorHandler");
        console.debug(xhr.status);
        console.debug(thrownError);
    };
    
    var loadKnownEngines = function() {
        //console.debug('loadKnownEngines');
        $.ajax(
            '${listKnownEnginesUrl}',
            {
                method: "GET", dataType: "json",
                data: {
                    command: "listKnownEngines"
                },
                success: function(data) {
                    fillDigestEnginesCombo(data);
                },
                error: ajaxErrorHandler                  
            }
        );
    };

    $("#btnHash").click(function() {
        $.ajax(
            '${computeHashUrl}',
            {
                method: "GET", dataType: "json",
                data: {
                    command : "computeHash",
                    engine  : "MD2",
                    message : $("#txtToHash").val()
                },
                success: function(data) {
                    $("#txtHashResult").html(data.digest);
                },
                error: ajaxErrorHandler                  
            }
        );
    });
    
    loadKnownEngines();
});
</script>