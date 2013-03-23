<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<h1>Message Digest testing/benchmarking portlet</h1>

<form>
    <label for="mdEnginesList">Message Digest Algoritm</label>
    <select name="mdEnginesList" id="mdEnginesList">
        <option value="md2">MD2</option>
        <option value="md5">MD5</option>
    </select>
    <label for="text">Text to hash</label>
    <input type="text" name="txtToHash" id="txtToHash"/>
    <hr/>
    <button type="button" name="btnHash" id="btnHash"/>
    <hr/>
    <label for="txtHashResult">Hash result</label>
    <input type="text" name="txtHashResult" id="txtHashResult"/>
</form>

<script type="text/javascript">
$(document).ready(function() {
    console.debug('pluto');
});
</script>