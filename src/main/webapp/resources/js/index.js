$(document).ready(function () {
    $('button#exampleBtn').click(function(event) {
        console.debug(event.target.id);
        var orgCode = $('input#orgCode').val();
        $.get("/example/org/" + orgCode).done(function(data, status) {
            $('input#result').val(data[0]['orgName']);
        }).fail(function (response, error, exception) {
            alert(response.responseText);
        });
    });
});